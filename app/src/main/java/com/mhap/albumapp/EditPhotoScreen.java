package com.mhap.albumapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by mchir on 4/30/2016.
 */
public class EditPhotoScreen extends AppCompatActivity {

    private static final String TAG = AlbumScreen.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editphoto_screen);
        loadData();

        Photo photo = HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).getPhotos().get(AlbumScreen.currentPhoto);

        final Spinner spinner = (Spinner) findViewById(R.id.types);
        final EditText tags = (EditText)findViewById(R.id.tags);
    // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.tag_types, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
         spinner.setAdapter(adapter);

        final Spinner spinner1 = (Spinner) findViewById(R.id.tags_list);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Tag> adapter1 = new ArrayAdapter<Tag>(EditPhotoScreen.this, android.R.layout.simple_spinner_item, photo.getTags());
        spinner1.setAdapter(adapter);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);


        Button add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Rename album and save
                String type = spinner.getSelectedItem().toString();
                String[] input = tags.getText().toString().split(" ");
                Photo photo = HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).getPhotos().get(AlbumScreen.currentPhoto);

                for (int i = 0; i < input.length; i++) {
                    Tag tag = new Tag(type, input[i].toLowerCase());
                    if (!photo.getTags().contains(tag)) {
                        photo.addTag(tag);
                        Log.d(TAG, "Tag added");
                        Toast.makeText(EditPhotoScreen.this, "Tag added", Toast.LENGTH_SHORT).show();
                    }
                }
                saveData();
                finish();
                Intent intent = new Intent(EditPhotoScreen.this, EditPhotoScreen.class);
                startActivity(intent);

            }
        });

        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save and exit
                Photo photo = HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).getPhotos().get(AlbumScreen.currentPhoto);
                Tag tag = (Tag)spinner1.getSelectedItem();
                if (photo.getTags().size() >= 0){
                    photo.deleteTag(tag);
                    Toast.makeText(EditPhotoScreen.this, "Tag deleted", Toast.LENGTH_SHORT).show();
                    saveData();
                    finish();
                    Intent intent = new Intent(EditPhotoScreen.this, EditPhotoScreen.class);
                    startActivity(intent);
                }


            }
        });

        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save and exit
                saveData();
                finish();
                Intent intent = new Intent(EditPhotoScreen.this, PhotoScreen.class);
                startActivity(intent);

            }
        });

        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save and exit
                saveData();
                finish();
                Intent intent = new Intent(EditPhotoScreen.this, PhotoScreen.class);
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
