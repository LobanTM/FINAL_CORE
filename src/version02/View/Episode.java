package version02.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tetya on 23.07.2017.
 */
public class Episode {
    String nameEpisode;
    List<ResultSort> sorters ;

    public int[] generateArraySortUp(int n){
        int[] m = new int[n];
        for (int i = 0; i < n; i++) {
            m[i]=i+1;
        }
        return m;
    }

    public int[] generateArraySortUpPlusRandom(int n){
        int[] m = new int[n];
        for (int i = 0; i < n-1; i++) {
            m[i]=i+1;
        }
        Random random = new Random();
        m[n-1]=random.nextInt();
        return m;
    }

    public int[] generateArraySortDown(int n){
        int[] m = new int[n];
        for (int i = 0; i < n; i++) {
            m[i]=n-i;
        }
        return m;
    }

    public int[] generateArrayRandom(int n){
        int[] m = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            m[i]=random.nextInt();
        }
        return m;
    }

    public Episode(String nameEpisode) {
        this.nameEpisode = nameEpisode;
        this.sorters = new ArrayList<>();
    }
    public Episode() {
        this.nameEpisode = "";
        this.sorters = new ArrayList<>();
    }

    public String getNameEpisode() {
        return nameEpisode;
    }

    public void setNameEpisode(String nameEpisode) {
        this.nameEpisode = nameEpisode;
    }

    public List<ResultSort> getSorters() {
        return sorters;
    }

    public void setSorters(List<ResultSort> sorters) {
        this.sorters = sorters;
    }

    @Override
    public String toString() {
        return "" + nameEpisode + '\'' +
                ", sorters=" + sorters.get(0).toString() /////!!!!!!!!!!!!!!!!!only first sort
                ;
    }
}
