package com.example.damien.myapplication.Model.Draw;

import android.graphics.Color;

/**
 * Created by damien on 07/10/2015.
 */

/**
 * Classe définissant un dessin
 * avec les paramètres généraux
 */
public class Dessin {
    public static int color = Color.GREEN;
    public static int colorSelected = Color.RED;
    public static float size = 10;
    public float x;
    public float y;
    public boolean isNew;

    public Dessin() {
        this.x = 0;
        this.y = 0;
        this.isNew = true;
    }
}
