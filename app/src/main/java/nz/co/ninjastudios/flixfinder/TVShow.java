package nz.co.ninjastudios.flixfinder;

import java.util.ArrayList;

/**
 * Created by james on 21/08/2017.
 */

public class TVShow extends Show {

    private ArrayList<Season> seasons;

    public TVShow(){

    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }
}
