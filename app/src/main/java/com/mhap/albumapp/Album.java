package com.mhap.albumapp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by knigh on 4/30/2016.
 */
public class Album implements java.io.Serializable
{
    private String albumName;
    private ArrayList<Photo> photos;

    public Album(){

    }
    /**
     * This is the default constructor. It accepts a String as a
     * parameter and sets that equal to the album's name.
     *
     * @param albumName
     */
    public Album(String albumName){
        this.albumName = albumName;
        this.photos = new ArrayList<Photo>();
    }
    /**
     * The purpose of this function is to add a photo
     * to the current album.
     */
    public void addPhoto(Photo photo)
    {
        if (photo != null && !photos.contains(photo)){
            photos.add(photo);
        }
    }

    public boolean deletePhoto(Photo photo)
    {
        if(photos.contains(photo))
        {
            photos.remove(photo);
            return true;
        }
        else return false;
    }

    public ArrayList<Photo> getPhotos()
    {
        return photos;
    }

    public String getAlbumName(){
        return albumName;
    }

    public void setAlbumName(String newName){
        this.albumName = newName;
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
    {
        stream.writeObject(albumName);
        stream.writeObject(photos);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        albumName = (String) stream.readObject();
        photos = (ArrayList<Photo>)stream.readObject();
    }

    public String toString(){
        return albumName;
    }

}
