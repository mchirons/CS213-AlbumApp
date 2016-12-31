package com.mhap.albumapp;

import java.io.IOException;

/**
 * Created by knigh on 4/30/2016.
 */
public class Tag implements java.io.Serializable
{
    private String type;
    private String value;

    public Tag(){

    }

    public Tag(String type, String value){
        this.type = type;
        this.value = value;
    }

    public String getType(){
        return type;
    }

    public String getValue(){
        return value;
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
    {
        stream.writeObject(type);
        stream.writeObject(value);
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        type = (String) stream.readObject();
        value = (String)stream.readObject();
    }
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Tag)) return false;
        else{
            Tag tag = (Tag)o;
            if (tag.getType().equals(this.getType()) && (tag.getValue().equals((this.getValue())))){
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public String toString(){
        return type + ":  " + value;
    }

}
