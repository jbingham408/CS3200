package com.example.jason.projectgutenbergauthorbrowser;

/*
    Jason Bingham
    CS3200 Assignment 5
    Project Gutenberg browser Custom View
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jason on 10/10/15.
 */
public class AuthorAdapter extends ArrayAdapter<Author> {

    //store res id
    int resId;
    // store context
    Context context;
    //store list of authors
    List<Author> listOfAuthors;

    public AuthorAdapter(Context con, int resid, List<Author> auth){
        super(con, resid, auth);
        context = con;
        resId = resid;
        listOfAuthors = auth;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        //store view
        RelativeLayout authView;
        //store selected author
        Author selected = getItem(position);
        //store first name
        String firstNameSelected = selected.getFirstName();
        //store last name
        String lastNameSelected = selected.getLastName();

        if(convertView == null){
            authView = new RelativeLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resId, authView, true);
        }
        else{
            authView = (RelativeLayout)convertView;
        }

        //set text view of first and last name
        ((TextView)authView.findViewById(R.id.lastName)).setText(lastNameSelected + ",");
        ((TextView)authView.findViewById(R.id.firstName)).setText(firstNameSelected);

        //create imageview
        ImageView image = (ImageView)authView.findViewById(R.id.icon);
        Resources res = context.getResources();

        Drawable setImage = null;

        //sets the icon image
        switch (position){
            case 0:
                setImage = res.getDrawable(R.drawable.hc_andersen);
                break;
            case 1:
                setImage = res.getDrawable(R.drawable.m_twain);
                break;
            case 2:
                setImage = res.getDrawable(R.drawable.r_sabatini);
                break;
            case 3:
                setImage = res.getDrawable(R.drawable.c_dickens);
                break;
            case 4:
                setImage = res.getDrawable(R.drawable.gd_maupassant);
                break;
            case 5:
                setImage = res.getDrawable(R.drawable.v_hugo);
                break;
            case 6:
                setImage = res.getDrawable(R.drawable.leo_tolstoy);
                break;
            case 7:
                setImage = res.getDrawable(R.drawable.r_kipling);
                break;
            case 8:
                setImage = res.getDrawable(R.drawable.r_tagore);
                break;
            case 9:
                setImage = res.getDrawable(R.drawable.w_shakespeare);
                break;
        }

        image.setImageDrawable(setImage);

        return authView;
    }
}
