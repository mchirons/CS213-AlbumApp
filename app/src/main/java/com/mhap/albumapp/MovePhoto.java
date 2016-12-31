package com.mhap.albumapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by knigh on 5/3/2016.
 */
public class MovePhoto extends AppCompatActivity {

    //Array of Albums
    ArrayList<Album> albums = HomeScreen.user.getAlbums();
    ListView listView;
    Album previousAlbum = HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum);
    int albumsIndex = HomeScreen.currentAlbum;
    int size = HomeScreen.user.getAlbums().size();
    Photo photo = previousAlbum.getPhotos().get(AlbumScreen.currentPhoto);
    Album toAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_move_list);

        ArrayAdapter adapter = new ArrayAdapter<Album>(this, R.layout.album_move, albums);
        listView = (ListView) findViewById(R.id.album_move_list);


        //listView.getChildAt(albumsIndex).setVisibility(View.GONE);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Album album = (Album) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MovePhoto.this, AlbumScreen.class);
                previousAlbum.deletePhoto(photo);
                toAlbum = HomeScreen.user.getAlbums().get(position);
                toAlbum.addPhoto(photo);
                Toast.makeText(MovePhoto.this, "Photo moved to: " + toAlbum.toString(),
                        Toast.LENGTH_SHORT).show();
                saveData();
                finish();
                startActivity(intent);
            }
        });
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


