package com.mhap.albumapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by mchir on 4/30/2016.
 */
public class AlbumScreen extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;
    private static final String TAG = AlbumScreen.class.getSimpleName();
    File file;
    private String[] def = {"Add some photos!"};
    public static final int KITKAT_VALUE = 1002;
    static final int REQUEST_PICK_PHOTO = 1;
    public static int currentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_screen);
        Log.d(TAG, "AlbumView onCreate ENTERED!!!!");

        loadData();

        GridView gridview = (GridView) findViewById(R.id.photo_list);
        Log.d(TAG, "current album SIZE = " + HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).getPhotos().size());
        if (HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).getPhotos().size() != 0){
            gridview.setAdapter(new AlbumGridAdapter(this));
            registerForContextMenu(gridview);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    //position++;
                    //Toast.makeText(AlbumScreen.this, "" + position,
                    //Toast.LENGTH_SHORT).show();
                    currentPhoto = position;
                    saveData();
                    Intent intent = new Intent(AlbumScreen.this, PhotoScreen.class);
                    startActivity(intent);
                }
            });
        }
        else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, def);
            gridview.setAdapter(adapter);
        }


        TextView addPhoto = (TextView)findViewById(R.id.add_photos);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseFileBox();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {

            case R.id.delete:
                HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).deletePhoto(HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).getPhotos().get(position));
                saveData();
                Intent intent = new Intent(AlbumScreen.this, AlbumScreen.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.move:
                saveData();
                MenuInflater inflater = getMenuInflater();
                if(HomeScreen.user.getAlbums().size() == 1)
                {
                    Toast.makeText(AlbumScreen.this, "There is only one album, and you are in it!" ,
                    Toast.LENGTH_SHORT).show();

                }
                else
                {
                    saveData();
                    intent = new Intent(AlbumScreen.this, MovePhoto.class);
                    finish();
                    startActivity(intent);
                }

                //inflater.inflate(R.menu.choices_menu, ContextMenu menu);
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void chooseFileBox() {
        Intent intent;

        if(Build.VERSION.SDK_INT >= 19){
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }else{
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.form_pick_photos)), REQUEST_PICK_PHOTO);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Build.VERSION.SDK_INT >= 19) {
            if (data != null){
                Uri uri = data.getData();
                int takeFlags = data.getFlags();
                takeFlags &= Intent.FLAG_GRANT_READ_URI_PERMISSION;
                final int takeFlags2 = takeFlags;
                ContentResolver resolver = AlbumScreen.this.getContentResolver();
                resolver.takePersistableUriPermission(uri, takeFlags2);
                HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).addPhoto(new Photo(uri));
                Log.d(TAG, "File Uri: " + uri.toString());
                saveData();
            }

         }
        Intent intent = new Intent(AlbumScreen.this, AlbumScreen.class);
        finish();
        startActivity(intent);

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
