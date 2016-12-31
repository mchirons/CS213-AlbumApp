package com.mhap.albumapp;

/**
 * Created by mchir on 5/1/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mchir on 5/1/2016.
 */
public class AlbumGridAdapter extends BaseAdapter {
    private Context mContext;
    private int THUMBNAIL_SIZE = 150;

    public AlbumGridAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return albumNum;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    Bitmap bt = null;
            try{
                bt = getThumbnail(HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).getPhotos().get(position).getURI());
            }catch(Exception e){
                e.printStackTrace();
            }


        View imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater li = LayoutInflater.from(mContext);
            imageView = new View(mContext);
            imageView = li.inflate(R.layout.album_grid, null);
        } else {
            imageView = (View) convertView;
        }
        GridViewItem iV = (GridViewItem)imageView.findViewById(R.id.image);
        iV.setImageBitmap(bt);
        //iV.setScaleType(ImageView.ScaleType.FIT_XY);
        //iV.setAdjustViewBounds(true);
        //TextView textView = (TextView)imageView.findViewById(R.id.name);

        return imageView;

    }

    // references to our images
    private int albumNum = HomeScreen.user.getAlbums().get(HomeScreen.currentAlbum).getPhotos().size();

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = mContext.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = mContext.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
}
