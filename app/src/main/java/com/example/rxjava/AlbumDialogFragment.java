package com.example.rxjava;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumDialogFragment extends AppCompatDialogFragment implements PhotosAdapter.OnPhotoClickedItemListener {

    private static final String ARG_PARAM1 = "title";
    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    public static final String TAG = "UserActivity";

    private ArrayList<Photo> photoList = new ArrayList<>();
    private ArrayList<String> photoUrls = new ArrayList<>();
    private PhotosAdapter photosAdapter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.userphotosGV)
    GridView userphotosGV;

    @BindView(R.id.detail_image_placeholder)
    ImageView replaceUserImagePlaceholder;

    public AlbumDialogFragment() {
        // Required empty public constructor
    }

    public static AlbumDialogFragment newInstance(String title) {
        AlbumDialogFragment fragment = new AlbumDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_album_dialog, null);
        ButterKnife.bind(getActivity());
        GridView userphotosGridView = (GridView) view.findViewById(R.id.userphotosGV);

        String title = getArguments().getString(ARG_PARAM1);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setTitle(title);

        photosAdapter = new PhotosAdapter(getActivity(), photoUrls);
        userphotosGridView.setAdapter(photosAdapter);

        photosAdapter.setOnPhotoClickedItemListener(this);

        String stringId = getActivity().getIntent().getStringExtra(EXTRA_USER_ID);
        fetchPhotos(stringId);

        return alertDialogBuilder.create();
    }

    private void fetchPhotos(String id) {
        RestApi.getInstance()
                .getUserPhotos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Photo> photos) {
                        photoList = (ArrayList<Photo>) photos;
                        setPhotos();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void setPhotos() {
        for (int i = 0; i < photoList.size(); i++) {
            photoUrls.add(photoList.get(i).getThumbnailUrl());
        }
        photosAdapter.notifyDataSetChanged(); //updates the recyclerview/gridView
    }

    @Override
    public void onPhotoItemClicked(int position) {
        Toast.makeText(getActivity(), "Photo Clicked Successful", Toast.LENGTH_SHORT).show();
    }
}
