package com;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;

import javafx.util.Duration;

import java.util.ArrayList;

public class SortingFuncs implements Runnable {

    protected SequentialTransition seq;
    protected ArrayList<MakeBar> varr;
    protected double rate;

    public SortingFuncs(ArrayList<MakeBar> varr, double rate) {
        this.varr = varr;
        this.rate = rate;
        this.seq = new SequentialTransition();
    }

    protected void printIteration() {
        for (MakeBar m : varr) {
            System.out.printf("%.2f ", m.retHeight());
        }
        System.out.println();
    }

    protected double[] getHeights() {
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

    @Override
    public void run() {
        System.out.println("Superclass SortingFuncs");
    }
}
