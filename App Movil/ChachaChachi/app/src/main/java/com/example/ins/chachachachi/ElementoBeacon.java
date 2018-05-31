package com.example.ins.chachachachi;

import android.graphics.drawable.Drawable;

/**
 * Created by mrodriguez on 31/05/2018.
 */

public class ElementoBeacon {

    private String nombre;
    private String majorminor;
    private Drawable imagen;

    public ElementoBeacon() {
        super();
    }

    public ElementoBeacon(String nombre, String majorminor, Drawable imagen) {
        super();
        this.nombre = nombre;
        this.majorminor = majorminor;
        this.imagen = imagen;
    }
}
