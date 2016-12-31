package com.mhap.albumapp;

/**
 * Created by mchir on 5/1/2016.
 */
import java.io.IOException;
import java.util.ArrayList;

public class User implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    private ArrayList<Album> albums;

    public User(){
        this.albums = new ArrayList<Album>();
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
    {
        stream.writeObject(albums);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        albums = (ArrayList<Album>) stream.readObject();
    }

    public void setAlbums()
    {

    }

    public void addAlbum(Album album)
    {
        albums.add(album);
    }

    public boolean deleteAlbum(Album album)
    {
        if(albums.contains(album))
        {
            albums.remove(album);
            return true;
        }
        else return false;
    }

    public ArrayList<Album> getAlbums(){
        return albums;
    }

}
