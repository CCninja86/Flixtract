package nz.co.ninjastudios.flixfinder;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by james on 21/08/2017.
 */

public class Country implements Serializable {
    private String name;
    private String flagImageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagImageUrl() {
        return flagImageUrl;
    }

    public void setFlagImageUrl(String url) {
        this.flagImageUrl = url;
    }
}
