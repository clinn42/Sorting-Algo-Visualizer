package com.funcs;

import com.MakeBar;
import javafx.animation.Timeline;

import java.util.ArrayList;

public class MergeSort extends com.SortingFuncs {

    public MergeSort(ArrayList<MakeBar> varr, double rate) {
        super(varr, rate);
    }

    @Override
    public void run() {
        mergeInit();
    }

    public void mergeInit() {
        double[] hvalues = getHeights();
        mergeDivide(hvalues, 0, hvalues.length - 1);
        System.out.println(rate);
    }

    private void mergeDivide(double[] arr, int l, int r) {

        if (l < r) {
            int m = (l + r) / 2;

            mergeDivide(arr, l, m);
            mergeDivide(arr, m + 1, r);
            mergeConquer(arr, l, m, r);
        }
    }

    private void mergeConquer(double[] arr, int l, int m, int r) {

        int n1 = m - l + 1;
        int n2 = r - m;

        double[] left = new double[n1];
        double[] right = new double[n2];

        System.arraycopy(arr, l, left, 0, n1);
        System.arraycopy(arr, m + 1, right, 0, n2);

        Timeline bounds = varr.get(m).blink(rate, "2", "2");
        bounds.getKeyFrames().addAll(varr.get(r).blink(rate, "2", "2").getKeyFrames());
        seq.getChildren().add(bounds);

        int k = l, i = 0, j = 0;
        ArrayList<Timeline> heightMods = new ArrayList<>();
        Timeline tim;

        while (i < n1 && j < n2) {
            double newHeightMod;
            if (left[i] < right[j]) {
                arr[k] = left[i];
                newHeightMod = left[i];
                seq.getChildren().add(varr.get(l + i).blink(rate, "RED", "BLUE"));
                i++;
            } else {
                arr[k] = right[j];
                newHeightMod = right[j];
                seq.getChildren().add(varr.get(m + 1 + j).blink(rate, "RED", "BLUE"));
                j++;
            }
            heightMods.add(varr.get(k).swapOne(rate, "GREEN", "BLUE", newHeightMod));
            k++;
        }

        while (i < n1) {
            arr[k] = left[i];
            double newHeightMod = left[i];
            seq.getChildren().add(varr.get(l + i).blink(rate, "RED", "BLUE"));

            heightMods.add(varr.get(k).swapOne(rate, "GREEN", "BLUE", newHeightMod));

            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = right[j];
            double newHeightMod = right[j];
            seq.getChildren().add(varr.get(m + 1 + j).blink(rate, "RED", "BLUE"));

            heightMods.add(varr.get(k).swapOne(rate, "GREEN", "BLUE", newHeightMod));

            j++;
            k++;
        }

        for (Timeline timmy : heightMods) {
            seq.getChildren().add(timmy);
        }


    }

}
