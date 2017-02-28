package com.hp.mss.print.item;

import android.graphics.Bitmap;

/**
 * Created by hop on 24/02/2017.
 */

public class MenuItem {

    public Bitmap getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(Bitmap imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
    }

    Bitmap imgIcon;
    String txtTitle;

    public MenuItem(Bitmap icon, String title) {
        super();
        this.imgIcon = icon;
        this.txtTitle = title;
    }
}
