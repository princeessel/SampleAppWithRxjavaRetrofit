package com.example.rxjava.userdetails;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxjava.Album;
import com.example.rxjava.AlbumDialogFragment;
import com.example.rxjava.PhotosAdapter;
import com.example.rxjava.Post;
import com.example.rxjava.PostAdapter;
import com.example.rxjava.R;
import com.example.rxjava.RestApi;
import com.example.rxjava.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    private static final String EXTRA_IMAGE_PATH = "EXTRA_IMAGE_PATH";

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.user_id)
    TextView userId;

    @BindView(R.id.user_website)
    TextView userWebsite;

    @BindView(R.id.user_Email)
    TextView userEmail;

    @BindView(R.id.user_street_address)
    TextView userAddress;

    @BindView(R.id.user_city)
    TextView userCity;

    @BindView(R.id.user_phone)
    TextView userPhoneNumber;

    @BindView(R.id.user_company)
    TextView userCompany;

    @BindView(R.id.real_name)
    TextView userRealName;


    @BindView(R.id.user_post_btn)
    Button userPostButton;

    @BindView(R.id.get_photos_btn)
    Button onGetPhotosButtonClicked;

    @BindView(R.id.postRecyclerView)
    RecyclerView postRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.spinner)
    AppCompatSpinner spinner;

    @BindView(R.id.detail_image_placeholder)
    public ImageView replaceUserImagePlaceholder;

    public PostAdapter postAdapter;

    private ArrayList<Album> albumsList = new ArrayList<>();

    private ArrayList<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        String stringId = getIntent().getStringExtra(EXTRA_USER_ID);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter();
        postRecyclerView.setAdapter(postAdapter);

        spinner.setOnItemSelectedListener(this);

        loadAlbums(stringId);
        loadUserData();
    }

    private void loadUserData() {
        String stringId = getIntent().getStringExtra(EXTRA_USER_ID);
        loadUserDetails(stringId);
    }

    @OnClick(R.id.user_post_btn)
    protected void postButtonClicked() {
        String userId = getIntent().getStringExtra(EXTRA_USER_ID);
        progressBar.setVisibility(View.VISIBLE);
        loadPost(userId);
    }

    @OnClick(R.id.get_photos_btn)
    protected void onGetPhotosButtonClicked() {
        showAlbumDialog();
    }

    public void showAlbumDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AlbumDialogFragment albumDialogFragment = AlbumDialogFragment.newInstance("Retrieve User Photos");
        Fragment fr = getSupportFragmentManager().findFragmentByTag("fragment_album_dialog");
        if (fr == null) {
            albumDialogFragment.show(fragmentManager, "fragment_album_dialog");
        }
    }

    void loadPost(String id) {

        RestApi.getInstance()
                .getUserPostDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Post> postList) {
                        posts = (ArrayList<Post>) postList;
                        setPost(posts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Otis", "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);

                    }
                });
    }

    void loadUserDetails(String id) {
        RestApi.getInstance().getUserDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(user -> {
                    user.setUsername(user.getUsername().toUpperCase());
                    user.setName(user.getName().toUpperCase());
                    return user;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        showUserDetails(user);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    void showUserDetails(User user) {
        userName.setText(user.getUsername());
        userId.setText(String.valueOf(user.getId()));
        userAddress.setText(user.getAddress().getStreet());
        userRealName.setText(user.getName());
        userWebsite.setText(user.getWebsite());
        userCompany.setText(user.getCompany().getName());
        userCity.setText(user.getAddress().getCity());
        userPhoneNumber.setText(user.getPhone());
        userEmail.setText(user.getEmail());
    }

    void loadAlbums(String id) {
        progressBar.setVisibility(View.VISIBLE);
        RestApi.getInstance()
                .getUserAlbums(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Album>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Album> albums) {
                        progressBar.setVisibility(View.GONE);
                        albumsList = (ArrayList<Album>) albums;
                        showSpinnerList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.getMessage();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showSpinnerList() {
        String[] albumsitems = new String[albumsList.size()];

        for (int i = 0; i < albumsList.size(); i++) {
            albumsitems[i] = albumsList.get(i).getTitle();
        }

        ArrayAdapter userAlbumsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, albumsitems);
        spinner.setAdapter(userAlbumsAdapter);
    }

    void setPost(ArrayList<Post> posts) {
        postAdapter.setUserPosts(posts);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        String value = spinner.getSelectedItem().toString();
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break;
            }
        }
        spinner.setSelection(index);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
