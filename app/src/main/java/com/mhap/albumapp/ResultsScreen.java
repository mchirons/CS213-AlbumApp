package com.mhap.albumapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by mchir on 5/2/2016.
 */
public class ResultsScreen extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;
    private static final String TAG = AlbumScreen.class.getSimpleName();
    File file;
    private String[] def = {"No matching results"};
    public static final int KITKAT_VALUE = 1002;
    static final int REQUEST_PICK_PHOTO = 1;
    public static int currentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_screen);
        Log.d(TAG, "ResultsScreen onCreate ENTERED!!!!");

        loadData();

        GridView gridview = (GridView) findViewById(R.id.photo_list);
        //Log.d(TAG, "current album SIZE = " + HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).getPhotos().size());
        if (HomeScreen.results.size() > 0){
            gridview.setAdapter(new ResultsGridAdapter(this));
            registerForContextMenu(gridview);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    //position++;
                    //Toast.makeText(AlbumScreen.this, "" + position,
                    //Toast.LENGTH_SHORT).show();
                    currentPhoto = position;
                    saveData();
                    Intent intent = new Intent(ResultsScreen.this, ResultsPhotoScreen.class);
                    finish();
                    startActivity(intent);
                }
            });
        }
        else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, def);
            gridview.setAdapter(adapter);
        }

    }


    private void loadData(){
        try {
            FileInputStream fis = openFileInput(HomeScreen.saveFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HomeScreen.user = (User)ois.readObject();
            ois.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void saveData(){


        try {
            FileOutputStream fos = openFileOutput(HomeScreen.saveFile, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(HomeScreen.user);
            oos.flush();
            oos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}

