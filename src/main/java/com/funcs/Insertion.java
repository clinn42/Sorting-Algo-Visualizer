package com.funcs;

import com.MakeBar;
import javafx.animation.Timeline;

import java.util.ArrayList;

public class Insertion extends com.SortingFuncs {

    public Insertion(ArrayList<MakeBar> varr, double rate) {
        super(varr, rate);
    }

    @Override
    public void run() {
        insertion();

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

}
