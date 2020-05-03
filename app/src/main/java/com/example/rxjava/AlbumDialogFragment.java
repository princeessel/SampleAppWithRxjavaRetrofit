package com.example.rxjava;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumDialogFragment extends AppCompatDialogFragment {

    private static final String ARG_PARAM1 = "title";
    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    private ArrayList<Photo> photoList = new ArrayList<>();
    private ArrayList<String> photoUrls = new ArrayList<>();

    @BindView(R.id.progressBar)
    ProgressBar progressBar;




    public AlbumDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @return A new instance of fragment AlbumDialogFragment.
     */
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
        GridView  userphotosGridView = (GridView)view.findViewById(R.id.userphotosGV);

        String title = getArguments().getString(ARG_PARAM1);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setTitle(title);

        PhotosAdapter photosAdapter = new PhotosAdapter(getActivity(), photoUrls);
        userphotosGridView.setAdapter(photosAdapter);
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
                        if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);}
                    }
                });
    }

    private void setPhotos() {
        for (int i = 0; i < photoList.size(); i++) {
            photoUrls.add(photoList.get(i).getThumbnailUrl());
        }
    }
}
