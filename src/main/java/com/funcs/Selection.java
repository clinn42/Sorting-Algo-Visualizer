package com.funcs;

import com.MakeBar;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

public class Selection extends com.SortingFuncs{
    public Selection(ArrayList<MakeBar> varr, double rate) {
        super(varr, rate);
    }

    @Override
    public void run() {
        selection();
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
}
