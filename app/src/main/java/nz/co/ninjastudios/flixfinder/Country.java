package nz.co.ninjastudios.flixfinder;

import android.graphics.Bitmap;

/**
 * Created by james on 21/08/2017.
 */

public class Country {
    private String name;
    private Bitmap flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getFlag() {
        return flag;
    }

    public void setFlag(Bitmap flag) {
        this.flag = flag;
    }
}
