package com.mhap.albumapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by mchir on 4/30/2016.
 */
public class AddAlbumScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addalbum_screen);
        loadData();

       final EditText album_name = (EditText)findViewById(R.id.new_name);

        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create album and save
                String input = album_name.getText().toString();
                if (input.length() > 0){
                    HomeScreen.user.addAlbum(new Album(input));
                }
                saveData();
                Intent intent = new Intent(AddAlbumScreen.this, HomeScreen.class);
                startActivity(intent);

            }
        });

        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save and exit
                saveData();
                Intent intent = new Intent(AddAlbumScreen.this, HomeScreen.class);
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
