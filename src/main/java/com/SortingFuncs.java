package com;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;

import javafx.util.Duration;

import java.util.ArrayList;

public class SortingFuncs {

    private SequentialTransition seq;
    private ArrayList<MakeBar> varr;
    private double rate;

    public SortingFuncs(ArrayList<MakeBar> varr, double rate) {
        this.varr = varr;
        this.rate = rate;
        this.seq = new SequentialTransition();
    }

    public SequentialTransition getSeq() {
        return seq;
    }

    private void printIteration() {
        for (MakeBar m : varr) {
            System.out.printf("%.2f ", m.retHeight());
        }
        System.out.println();
    }

    private double[] getHeights() {
        double[] heights = new double[varr.size()];
        for (int m = 0; m < varr.size(); m++) {
            heights[m] = varr.get(m).retHeight();
        }
        return heights;
    }

    public SequentialTransition finEffect() {
        for (int i = 0; i < varr.size(); i++) {
            seq.getChildren().add(varr.get(i).blink(rate, "PINK", "PINK"));
        }
        return seq;
    }

    public void insertion() {
        System.out.println(rate);

        double[] hvalues = getHeights();

        seq.getChildren().add(varr.get(0).blink(rate, "GREEN", "GREEN"));

        for (int i = 1; i < hvalues.length; i++) {
            int j = i;

            seq.getChildren().add(varr.get(j).blink(rate, "RED", "RED"));

            while (hvalues[j] < hvalues[j - 1]) {

                double swap1 = hvalues[j];
                double swap2 = hvalues[j - 1];

                Timeline swapFrame = varr.get(j).swapOne(rate, "GREEN", "GREEN", swap2);
                swapFrame.getKeyFrames().addAll(varr.get(j - 1).swapOne(rate, "RED", "RED", swap1).getKeyFrames());
                seq.getChildren().add(swapFrame);

                hvalues[j] = swap2;
                hvalues[j - 1] = swap1;

                j--;
                if (j == 0) {
                    break;
                }
            }
            seq.getChildren().add(varr.get(j).blink(rate, "GREEN", "GREEN"));

        }
    }

    public void selection() {
        System.out.println(rate);

        int len = varr.size();
        double[] hvalues = getHeights();

        for (int i = 0; i < len - 1; i++) {

            int smi = i;
            seq.getChildren().add(varr.get(i).blink(rate, "1", "1"));

            Timeline compare;
            for (int j = i + 1; j < len; j++) {

                if (hvalues[j] < hvalues[smi]) {
                    compare = varr.get(smi).blink(rate, "1", "BLUE");
                    compare.getKeyFrames().addAll(varr.get(j).blink(rate, "RED", "1").getKeyFrames());
                    seq.getChildren().add(compare);

                    if (j == len - 1) {
                        seq.getChildren().add(new PauseTransition(Duration.seconds(rate)));
                    }
                    smi = j;

                } else {
                    compare = varr.get(j).blink(rate, "RED", "BLUE");
                    seq.getChildren().add(compare);
                }

            }

            double s1 = hvalues[i];
            double s2 = hvalues[smi];
            hvalues[i] = s2;
            hvalues[smi] = s1;

            Timeline swap = varr.get(smi).swapOne(rate, "BLUE", "BLUE", s1);
            swap.getKeyFrames().addAll(varr.get(i).swapOne(rate, "GREEN", "GREEN", s2).getKeyFrames());
            seq.getChildren().add(swap);

        }

        seq.getChildren().add(varr.get(len - 1).blink(rate, "GREEN", "GREEN"));

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

    private void radix(MakeBar[] arr, double rate) {

    }

    public void heap(MakeBar[] arr, double rate) {

    }

}
