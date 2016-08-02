package com.techpalle.b30_rottentomatoes;

/**
 * Created by SHASHI on 19-04-2016.
 */
public class Movies {
    public String Title;
    public String [] cast;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }
}