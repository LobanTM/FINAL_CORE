package version02.Model;

import java.util.Arrays;

/**
 * Created by tetya on 21.07.2017.
 */
public class ArrayHolder {
    private int [] array;

    public ArrayHolder() {
        this.array = new int[16];
    }

    public ArrayHolder(int size) {
        this.array = new int[size];
    }

    public ArrayHolder(int[] array) {
        this.array = array;
    }

    public ArrayHolder (ArrayHolder array) {
        //clone
        int[] result = new int[array.getArray().length];
        System.arraycopy(array.getArray(),0,result,0,array.getArray().length);
        this.array = result;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
