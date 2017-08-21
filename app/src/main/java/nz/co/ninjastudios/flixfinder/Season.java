package nz.co.ninjastudios.flixfinder;

import java.util.ArrayList;

/**
 * Created by james on 21/08/2017.
 */

public class Season {

    private ArrayList<Episode> episodes;

    public Season(){

    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }
}
