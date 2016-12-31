package com.mhap.albumapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by knigh on 4/30/2016.
 */
public class Photo implements java.io.Serializable
{

    private static final long serialVersionUID = 2L;
    private String uri;
    private ArrayList<Tag> tags;

    public Photo(Uri uri){
        this.uri = uri.toString();
        this.tags = new ArrayList<Tag>();
    }


    public Uri getURI()
    {
        return Uri.parse(uri);
    }


    public ArrayList<Tag> getTags(){
        return tags;
    }

    public void addTag(Tag tag)
    {
        tags.add(tag);
    }

    public boolean deleteTag(Tag tag)
    {
        if(tags.contains(tag))
        {
            tags.remove(tag);
            return true;
        }
        else return false;
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
    {
        stream.writeObject(uri);
        stream.writeObject(tags);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        uri = (String)stream.readObject();
        tags = (ArrayList<Tag>)stream.readObject();
    }
    /*
     * Simply for ordering based on date.
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    /*
     * This is to check if the actual images are equal.
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o)
    {

        if (!(o instanceof Photo)) return false;
        else{
            Photo photo = (Photo)o;
            if (photo.getURI().equals(this.getURI())){
                return true;
            }
            else
            {
                return false;
            }
        }
    }


}
