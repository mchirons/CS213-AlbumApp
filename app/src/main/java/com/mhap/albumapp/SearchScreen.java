package com.mhap.albumapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by mchir on 5/2/2016.
 */
public class SearchScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        loadData();

        final EditText tags = (EditText)findViewById(R.id.tags);

        Button search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Search and creates list of photos with matching tags)
                ArrayList<Photo> photos = new ArrayList<Photo>();
                String text = tags.getText().toString();
                if (text.length() > 0){
                    String[] tokens = tags.getText().toString().split(" ");

                    for (int z = 0; z < tokens.length; z++){
                        String input = tokens[z];
                        for (int i = 0; i < HomeScreen.user.getAlbums().size(); i++){
                            Album album = HomeScreen.user.getAlbums().get(i);
                            for (int j = 0; j < album.getPhotos().size(); j++){
                                Photo photo = album.getPhotos().get(j);
                                for (int k = 0; k < photo.getTags().size(); k++){
                                    Tag tag = photo.getTags().get(k);
                                    if (tag.getValue().contains(input)){
                                        if (!photos.contains(photo))
                                            photos.add(photo);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                HomeScreen.results = photos;
                saveData();
                Intent intent = new Intent(SearchScreen.this,ResultsScreen.class);
                finish();
                startActivity(intent);

            }
        });

        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save and exit
                saveData();
                Intent intent = new Intent(SearchScreen.this, HomeScreen.class);
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
