import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;

import javafx.util.Duration;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SortingFuncs {

    private SequentialTransition seq;
    private MakeBar[] varr;
    private double rate;

    public SortingFuncs(MakeBar[] varr, double rate) {
        this.varr = varr;
        this.rate = rate;
        this.seq = new SequentialTransition();
    }

    public SequentialTransition getSeq() {
        return seq;
    }

    public double getRate() {
        return rate;
    }


    private void printIteration() {
        for (MakeBar m:varr) {
            System.out.printf("%.2f ",m.retHeight());
        }
        System.out.println();
    }

    private double[] getHeights() {
        double[] heights = new double[varr.length];
        for (int m=0;m<varr.length;m++) {
            heights[m] = varr[m].retHeight();
        }
        return heights;
    }


    public void insertion() {
        System.out.println(rate);

        double[] hvalues = getHeights();//get values to be sorted

        /*Timeline firstVal = new Timeline(new KeyFrame(Duration.seconds(rate),event -> varr[0].greenInserted()));
        seq.getChildren().add(firstVal);//first value is always sorted*/
        seq.getChildren().add(varr[0].blink(rate,"GREEN","GREEN"));

        for (int i=1;i<hvalues.length;i++) {
            int j = i;

            /*int firstJ = j;
            Timeline makeActive = new Timeline(new KeyFrame(Duration.seconds(rate), event -> varr[firstJ].redActive()));
            seq.getChildren().add(makeActive);*/
            seq.getChildren().add(varr[j].blink(rate,"RED","RED"));

            while (hvalues[j] < hvalues[j-1]) {

                double swap1 = hvalues[j];
                double swap2 = hvalues[j-1];

                /*int finalJ = j;
                Timeline swapSeq = new Timeline(new KeyFrame(Duration.seconds(rate),event -> {
                    varr[finalJ - 1].modifyHeight(swap1);
                    varr[finalJ - 1].redActive();
                    varr[finalJ].greenInserted();
                    varr[finalJ].modifyHeight(swap2);
                }));
                seq.getChildren().add(swapSeq);*/
                Timeline swapFrame = varr[j].swapOne(rate,"GREEN","GREEN",swap2);
                swapFrame.getKeyFrames().addAll(varr[j-1].swapOne(rate,"RED","RED",swap1).getKeyFrames());
                seq.getChildren().add(swapFrame);

                hvalues[j] = swap2;
                hvalues[j-1] = swap1;

                j--;
                if (j==0) {
                    break;
                }
            }
            /*int doneJ = j;
            Timeline finPos = new Timeline(new KeyFrame(Duration.seconds(rate), event -> {
                varr[doneJ].greenInserted();
                System.out.println("Inserted at:" + seq.getCurrentTime());
            }));
            seq.getChildren().add(finPos);*/
            seq.getChildren().add(varr[j].blink(rate,"GREEN","GREEN"));

        }
    }

    public void selection() {
        System.out.println(rate);

        int len = varr.length;
        double[] hvalues = getHeights();

        for (int i=0;i<len-1;i++) {

            int smi = i;
            seq.getChildren().add(varr[i].blink(rate,"1","1"));

            Timeline compare;
            for (int j=i+1;j<len;j++) {

                //compare -> Makes red

                if (hvalues[j] < hvalues[smi]) {
                    compare = varr[smi].blink(rate,"1","BLUE");
                    compare.getKeyFrames().addAll(varr[j].blink(rate,"RED","1").getKeyFrames());
                    seq.getChildren().add(compare);

                    if (j == len -1) {
                        seq.getChildren().add(new PauseTransition(Duration.seconds(rate)));
                    }
                    smi = j;

                } else {
                    compare = varr[j].blink(rate,"RED","BLUE");
                    seq.getChildren().add(compare);
                }

            }

            /*double smallest = hvalues[smi];
            double I = hvalues[i];
            hvalues[smi] = I;
            hvalues[i] = smallest;*/
            double s1 = hvalues[i];
            double s2 = hvalues[smi];
            hvalues[i] = s2;
            hvalues[smi] = s1;

            Timeline swap = varr[smi].swapOne(rate,"BLUE","BLUE",s1);
            swap.getKeyFrames().addAll(varr[i].swapOne(rate,"GREEN","GREEN",s2).getKeyFrames());
            seq.getChildren().add(swap);

            /*int finalI = i;
            int finalSmi1 = smi;
            Timeline swap = new Timeline(new KeyFrame(Duration.seconds(rate),event -> {
                varr[finalSmi1].ogBlue();
                varr[finalSmi1].modifyHeight(I);
                varr[finalI].modifyHeight(smallest);
                varr[finalI].greenInserted();
            }));
            seq.getChildren().add(swap);*/

        }

        /*Timeline lastSorted = new Timeline(new KeyFrame(Duration.seconds(rate), event -> varr[len - 1].greenInserted()));
        seq.getChildren().add(lastSorted);*/
        seq.getChildren().add(varr[len-1].blink(rate,"GREEN","GREEN"));

    }

    public void mergeInit() {
        double[] hvalues = getHeights();
        mergeDivide(hvalues,0,hvalues.length-1);
        System.out.println(rate);
    }

    private void mergeDivide(double[] arr,int l,int r) {

        if (l<r) {
            int m = (l+r)/2;

            mergeDivide(arr,l,m);
            mergeDivide(arr,m+1,r);
            mergeConquer(arr,l,m,r);
        }
    }

    private void mergeConquer(double[] arr, int l, int m, int r) {

        int n1 = m - l +1;
        int n2 = r - m;

        double[] left = new double[n1];
        double[] right = new double[n2];

        System.arraycopy(arr,l,left,0,n1);
        System.arraycopy(arr,m+1,right,0,n2);

        Timeline bounds = varr[m].blink(rate,"2","2");
        bounds.getKeyFrames().addAll(varr[r].blink(rate,"2","2").getKeyFrames());
        seq.getChildren().add(bounds);

        int k=l,i=0,j=0;
        ArrayList<Timeline> heightMods = new ArrayList<>();
        Timeline tim;

        while (i<n1 && j<n2) {
            double newHeightMod;
            if (left[i] < right[j]) {
                arr[k] = left[i];
                newHeightMod = left[i];
                seq.getChildren().add(varr[l+i].blink(rate,"RED","BLUE"));
                i++;
            } else {
                arr[k] = right[j];
                newHeightMod = right[j];
                seq.getChildren().add(varr[m+1+j].blink(rate,"RED","BLUE"));
                j++;
            }
            /*int finalK1 = k;
            tim = new Timeline(new KeyFrame(Duration.ONE, event -> varr[finalK1].modifyHeight(newHeightMod)));
            tim.getKeyFrames().addAll(varr[k].blink(rate,"GREEN","BLUE").getKeyFrames());*/
            heightMods.add(varr[k].swapOne(rate,"GREEN","BLUE",newHeightMod));
            k++;
        }

        while (i<n1){
            arr[k] = left[i];
            double newHeightMod = left[i];
            seq.getChildren().add(varr[l+i].blink(rate,"RED","BLUE"));

            /*int finalK1 = k;
            tim = new Timeline(new KeyFrame(Duration.ONE, event -> varr[finalK1].modifyHeight(newHeightMod)));
            tim.getKeyFrames().addAll(varr[k].blink(rate,"GREEN","BLUE").getKeyFrames());
            heightMods.add(tim);*/
            heightMods.add(varr[k].swapOne(rate,"GREEN","BLUE",newHeightMod));

            i++; k++;
        }

        while (j<n2) {
            arr[k] = right[j];
            double newHeightMod = right[j];
            seq.getChildren().add(varr[m+1+j].blink(rate,"RED","BLUE"));

            /*int finalK1 = k;
            tim = new Timeline(new KeyFrame(Duration.ONE, event -> varr[finalK1].modifyHeight(newHeightMod)));
            tim.getKeyFrames().addAll(varr[k].blink(rate,"GREEN","BLUE").getKeyFrames());
            heightMods.add(tim);*/
            heightMods.add(varr[k].swapOne(rate,"GREEN","BLUE",newHeightMod));

            j++; k++;
        }

        for (Timeline timmy:heightMods) {
            seq.getChildren().add(timmy);
        }


    }

    public void quick() {
        double[] hvalues = getHeights();
        System.out.println(rate);
        quickDivide(hvalues,0,hvalues.length-1);
    }

    private void quickDivide(double[] arr, int l, int r) {
        if (l<r) {
            int p = quickP(arr,l,r);
            quickDivide(arr,l,p-1);
            quickDivide(arr,p+1,r);
        }
    }

    private int quickPartition(double[] arr, int l,int r) {
        int bound = l;
        int pivot = r--;
        double temp;
        Timeline swapLR,swapLP;

        seq.getChildren().add(varr[pivot].blink(rate,"2","2"));

        while (true) {
            /*while (arr[r]>arr[pivot] && r!=pivot){
                r--;
            }
            if (r==pivot) {
                flag = false;
            } else {
                temp = arr[pivot];
                arr[pivot] = arr[r];
                varr[pivot].modifyHeight(arr[r]);
                arr[r] = temp;
                varr[r].modifyHeight(temp);
                pivot = r;
            }

            if (flag) {
                while (arr[l]<arr[pivot] && l!=pivot){
                    l++;
                }
                if (l==pivot) {
                    flag = false;
                } else {
                    temp = arr[pivot];
                    arr[pivot] = arr[l];
                    varr[pivot].modifyHeight(arr[l]);
                    arr[l] = temp;
                    varr[l].modifyHeight(temp);
                    pivot = l;
                }
            }*/
            while(arr[l]<arr[pivot]) {
                seq.getChildren().add(varr[l].blink(rate,"RED","BLUE"));
                l++;
            }
            seq.getChildren().add(varr[l].blink(rate,"1","1"));

            while (arr[r]>=arr[pivot] && r>bound){
                seq.getChildren().add(varr[r].blink(rate,"RED","BLUE"));
                r--;
            }
            seq.getChildren().add(varr[r].blink(rate,"3","3"));
            //seq.getChildren().add(new PauseTransition(Duration.seconds(rate)));

            if (l>=r) {
                break;
            } else {
                temp = arr[l];
                arr[l] = arr[r];
                //varr[l].modifyHeight(arr[r]);
                swapLR = varr[l].swapOne(rate,"3","BLUE",arr[r]);

                arr[r] = temp;
                //varr[r].modifyHeight(temp);
                swapLR.getKeyFrames().addAll(varr[r].swapOne(rate,"1","BLUE",temp).getKeyFrames());
                seq.getChildren().addAll(swapLR);
            }
        }

        double s1 = arr[l];
        double s2 = arr[pivot];

        swapLP = varr[pivot].swapOne(rate,"1","BLUE",s1);
        swapLP.getKeyFrames().addAll(varr[r].blink(rate,"BLUE","BLUE").getKeyFrames());
        swapLP.getKeyFrames().addAll(varr[l].swapOne(rate,"GREEN","GREEN",s2).getKeyFrames());
        seq.getChildren().add(swapLP);

        arr[l] = s2;
        arr[pivot] = s1;

        return l;
    }

    private int quickP(double[] arr,int l, int r) {
        double pivot = arr[r];
        int i = l-1;
        double temp;
        Timeline swap;
        Timeline comp;

        seq.getChildren().add(varr[r].blink(rate,"2","2"));

        for (int j=l;j<r;j++) {

            if (arr[j]<pivot) {
                comp = new Timeline();

                i++;

                if (i!=l)
                    comp.getKeyFrames().addAll(varr[i-1].blink(rate,"1","BLUE").getKeyFrames());

                if (i!=j) {
                    comp.getKeyFrames().addAll(varr[j].blink(rate,"RED","RED").getKeyFrames());
                    seq.getChildren().add(comp);

                    seq.getChildren().add(varr[i].blink(rate,"1","1"));

                    temp = arr[i];
                    arr[i] = arr[j];
                    swap = varr[i].swapOne(rate,"1","1",arr[j]);

                    arr[j] = temp;
                    swap.getKeyFrames().addAll(varr[j].swapOne(rate,"RED","BLUE",temp).getKeyFrames());
                    seq.getChildren().add(swap);

                } else {
                    comp.getKeyFrames().addAll(varr[j].blink(rate,"RED","1").getKeyFrames());
                    seq.getChildren().add(comp);
                }
            } else {
                seq.getChildren().add(varr[j].blink(rate,"RED","BLUE"));
            }
        }

        i++;
        comp = varr[i].blink(rate,"1","1");
        if (i!=l)
            comp.getKeyFrames().addAll(varr[i-1].blink(rate,"BLUE","BLUE").getKeyFrames());
        seq.getChildren().add(comp);

        temp = arr[r];
        arr[r] = arr[i];
        swap = varr[r].swapOne(rate,"BLUE","BLUE",arr[i]);

        arr[i] = temp;
        swap.getKeyFrames().addAll(varr[i].swapOne(rate,"GREEN","GREEN",temp).getKeyFrames());
        seq.getChildren().add(swap);

        return i;
    }

    private void radix(MakeBar[] arr, double rate) {

    }

    public void heap(MakeBar[] arr, double rate) {

    }

}
