package nz.co.ninjastudios.flixfinder;

import java.io.Serializable;

/**
 * Created by james on 21/08/2017.
 */

public class Genre implements Serializable {

    private int id;
    private String name;

    public Genre(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
