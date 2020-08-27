package com.funcs;

import com.MakeBar;
import javafx.animation.Timeline;

import java.util.ArrayList;

public class QuickSort extends com.SortingFuncs {
    public QuickSort(ArrayList<MakeBar> varr, double rate) {
        super(varr, rate);
    }

    @Override
    public void run() {
        quick();
    }

    public void quick() {
        double[] hvalues = getHeights();
        System.out.println(rate);
        quickDivide(hvalues, 0, hvalues.length - 1);
    }

    private void quickDivide(double[] arr, int l, int r) {
        if (l < r) {
            int p = quickP(arr, l, r);
            quickDivide(arr, l, p - 1);
            quickDivide(arr, p + 1, r);
        }
    }

    private int quickP(double[] arr, int l, int r) {
        double pivot = arr[r];
        int i = l - 1;
        double temp;
        Timeline swap;
        Timeline comp;

        seq.getChildren().add(varr.get(r).blink(rate, "2", "2"));

        for (int j = l; j < r; j++) {

            if (arr[j] < pivot) {
                comp = new Timeline();

                i++;

                if (i != l)
                    comp.getKeyFrames().addAll(varr.get(i - 1).blink(rate, "1", "BLUE").getKeyFrames());

                if (i != j) {
                    comp.getKeyFrames().addAll(varr.get(j).blink(rate, "RED", "RED").getKeyFrames());
                    seq.getChildren().add(comp);

                    seq.getChildren().add(varr.get(i).blink(rate, "1", "1"));

                    temp = arr[i];
                    arr[i] = arr[j];
                    swap = varr.get(i).swapOne(rate, "1", "1", arr[j]);

                    arr[j] = temp;
                    swap.getKeyFrames().addAll(varr.get(j).swapOne(rate, "RED", "BLUE", temp).getKeyFrames());
                    seq.getChildren().add(swap);

                } else {
                    comp.getKeyFrames().addAll(varr.get(j).blink(rate, "RED", "1").getKeyFrames());
                    seq.getChildren().add(comp);
                }
            } else {
                seq.getChildren().add(varr.get(j).blink(rate, "RED", "BLUE"));
            }
        }

        i++;
        comp = varr.get(i).blink(rate, "1", "1");
        if (i != l)
            comp.getKeyFrames().addAll(varr.get(i - 1).blink(rate, "BLUE", "BLUE").getKeyFrames());
        seq.getChildren().add(comp);

        temp = arr[r];
        arr[r] = arr[i];
        swap = varr.get(r).swapOne(rate, "BLUE", "BLUE", arr[i]);

        arr[i] = temp;
        swap.getKeyFrames().addAll(varr.get(i).swapOne(rate, "GREEN", "GREEN", temp).getKeyFrames());
        seq.getChildren().add(swap);

        return i;
    }

}
