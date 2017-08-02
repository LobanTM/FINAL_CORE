package version02.View;

import java.util.Arrays;

/**
 * Created by tetya on 23.07.2017.
 */
public class ResultSort {
    String nameSort;
    int[] sizeArrayHolder;
    long[] timeSort;

    public ResultSort(String nameSort) {
        this.nameSort = nameSort;
        this.sizeArrayHolder = new int[100];
        this.timeSort = new long[100];
    }

    public ResultSort() {
        this.nameSort = "";
        this.sizeArrayHolder = new int[100];
        this.timeSort = new long[100];
    }

    public String getNameSort() {
        return nameSort;
    }

    public void setNameSort(String nameSort) {
        this.nameSort = nameSort;
    }

    public int[] getSizeArrayHolder() {
        return sizeArrayHolder;
    }

    public void setSizeArrayHolder(int[] sizeArrayHolder) {
        this.sizeArrayHolder = sizeArrayHolder;
    }

    public long[] getTimeSort() {
        return timeSort;
    }

    public void setTimeSort(long[] timeSort) {
        this.timeSort = timeSort;
    }

    @Override
    public String toString() {
        return "" + nameSort + '\'' +
                ", " + Arrays.toString(sizeArrayHolder) +
                ", " + Arrays.toString(timeSort);
    }
}
