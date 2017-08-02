package version02.Controller;

import version02.Model.ArrayHolder;

import java.util.Arrays;

/**
 * Created by tetya on 21.07.2017.
 */
public class ArrayHolderControllerImpl implements ArrayHolderController {
    private long timeSort;

    @Sort
    @Override
    public long bubbleSort(ArrayHolder arrayHolder) {
        int[] m ;
        //clone
        ArrayHolder result = new ArrayHolder(arrayHolder);
        m = result.getArray();
        timeSort = System.nanoTime();
        //bubleSort
        for (int i = m.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (m[j] > m[j + 1]) {
                    int t = m[j];
                    m[j] = m[j + 1];
                    m[j + 1] = t;
                }
            }
        }
        result.setArray(m);
        timeSort = System.nanoTime()- timeSort;
        return timeSort;
    }

    @Sort
    @Override
    public long exchangeSort(ArrayHolder arrayHolder) {
        int[] m ;
        //clone
        ArrayHolder result = new ArrayHolder(arrayHolder);
        m = result.getArray();
        timeSort = System.nanoTime();

        //SortExchange
        int t, j;
        for(int i = 0; i < m.length - 1; i++){
            if (m[i] > m[i + 1]) {
                t = m[i + 1];
                m[i + 1] = m[i];
                j = i;
                while (j > 0 && t < m[j - 1]) {
                    m[j] = m[j - 1];
                    j--;
                }
                m[j] = t;
            }
        }

        result.setArray(m);
        timeSort = System.nanoTime()- timeSort;
        return timeSort;
    }

    @Sort
    @Override
    public long mergeSort(ArrayHolder arrayHolder) {
        int[] m ;
        //clone
        ArrayHolder result = new ArrayHolder(arrayHolder);
        m = result.getArray();
        timeSort = System.nanoTime();

        //sortMergeNoRecursive
        int len = m.length;
        int n = 1;
        int shift;
        int two_size;
        int[] arr2;
        while (n < len) {
            shift = 0;
            while (shift < len) {
                if (shift + n >= len) break;
                two_size = (shift + n * 2 > len) ? (len - (shift + n)) : n;
                arr2 = merge(Arrays.copyOfRange(m, shift, shift + n),
                        Arrays.copyOfRange(m, shift + n, shift + n + two_size));
                for (int i = 0; i < n + two_size; ++i)
                    m[shift + i] = arr2[i];
                shift += n * 2;
            }
            n *= 2;
        }

        result.setArray(m);
        timeSort = System.nanoTime()- timeSort;
        return timeSort;
    }

    private static int[] merge(int[] arr_1, int[] arr_2) {
        int len_1 = arr_1.length, len_2 = arr_2.length;
        int a = 0, b = 0, len = len_1 + len_2;
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            if (b < len_2 && a < len_1) {
                if (arr_1[a] > arr_2[b]) result[i] = arr_2[b++];
                else result[i] = arr_1[a++];
            } else if (b < len_2) {
                result[i] = arr_2[b++];
            } else {
                result[i] = arr_1[a++];
            }
        }
        return result;
    }

    @Sort
    @Override
    public long recursiveSort(ArrayHolder arrayHolder) {
        int[] m ;
        //clone
        ArrayHolder result = new ArrayHolder(arrayHolder);
        m = result.getArray();
        timeSort = System.nanoTime();

        //SortRecursive
        sortMerge(m);

        result.setArray(m);
        timeSort = System.nanoTime()- timeSort;
        return timeSort;
    }

    private static int[] sortMerge(int[] arr) {
        int len = arr.length;
        if (len < 2) return arr;
        int middle = len / 2;
        return merge(sortMerge(Arrays.copyOfRange(arr, 0, middle)),
                sortMerge(Arrays.copyOfRange(arr, middle, len)));
    }

    @Sort
    @Override
    public long basicSort(ArrayHolder arrayHolder) {
        int[] m ;
        //clone
        ArrayHolder result = new ArrayHolder(arrayHolder);
        m = result.getArray();
        timeSort = System.nanoTime();

        //SortBasic
        Arrays.sort(m);

        result.setArray(m);
        timeSort = System.nanoTime()- timeSort;
        return timeSort;
    }

    @Sort
    @Override
    public long newSort(ArrayHolder arrayHolder) {
        int[] m ;
        //clone
        ArrayHolder result = new ArrayHolder(arrayHolder);
        m = result.getArray();
        timeSort = System.nanoTime();

        //bubbleSortDown
        int t;
        for (int i=0; i < m.length-1; i++) {
            for (int j = i + 1; j < m.length; j++) {
                if (m[i] > m[j]) {
                    t = m[i];
                    m[i] = m[j];
                    m[j] = t;
                }
            }
        }

        result.setArray(m);
        timeSort = System.nanoTime()- timeSort;
        return timeSort;
    }
}
