package com.mhap.albumapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by mchir on 4/30/2016.
 */
public class RenameScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rename_screen);
        loadData();

        final EditText album_name = (EditText)findViewById(R.id.album_name);

        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Rename album and save

                String input = album_name.getText().toString();
                boolean exists = false;
                for (int i = 0; i < HomeScreen.user.getAlbums().size(); i++){
                    if (input.equalsIgnoreCase(HomeScreen.user.getAlbums().get(i).getAlbumName())){
                        Toast.makeText(RenameScreen.this, "Album name already exists", Toast.LENGTH_SHORT).show();
                        exists = true;
                    }
                }
                if (exists == true){
                    saveData();
                }
                else if (input.length() > 0 ){
                    HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).setAlbumName(input);
                    saveData();
                    Intent intent = new Intent(RenameScreen.this, HomeScreen.class);
                    startActivity(intent);
                }

            }
        });

        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save and exit
                saveData();
                Intent intent = new Intent(RenameScreen.this, HomeScreen.class);
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
