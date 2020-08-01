/**
 * Sample Skeleton for 'template.fxml' Controller Class
 */

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SortContr implements Initializable {

    //static MakeBar[] valueArray;
    static ArrayList<MakeBar> valueArray;

    private static final Random random = new Random();
    static int sortChoice = 0;

    @FXML
    private GridPane topBox;

    @FXML
    private Label newRandArray;

    @FXML
    private Button vizButton;

    @FXML
    private Slider barNumSlider;

    @FXML
    private GridPane sortBox;

    @FXML
    private Label insertLab;

    @FXML
    private Label selectLab;

    @FXML
    private Label mergeLab;

    @FXML
    private Label quickLab;

    @FXML
    private Label radixLab;

    @FXML
    private Label heapLab;

    @FXML
    private HBox displayBox;

    @FXML
    private HBox debug;

    @FXML
    private Label msgbox;


    @FXML
    void genNewArray(MouseEvent event) {
        sortBox.setDisable(false);
        displayBox.getChildren().clear();
        int arrsize = (int) barNumSlider.getValue();
        msgbox.setText(String.valueOf(arrsize));
        valueArray = new ArrayList<>();
        MakeBar tempm;

        for (int i=0;i<arrsize;i++) {
            /*valueArray[i] = new MakeBar(random.nextInt(500));
            displayBox.getChildren().add(valueArray[i].getBar());*/
            tempm = new MakeBar(random.nextInt(500));
            valueArray.add(tempm);
            displayBox.getChildren().add(tempm.getBar());

        }
    }

    void newSelect(int nc) {
        if (sortChoice!=0) {
            sortBox.getChildren().get(sortChoice-1).setStyle("");
        }
        sortChoice = nc;
        sortBox.getChildren().get(sortChoice-1).setStyle("-fx-background-color: #2c3e50;");

    }

    @FXML
    void setHeap(MouseEvent event) {
        newSelect(6);
        vizButton.setDisable(false);
    }

    @FXML
    void setInsert(MouseEvent event) {
        newSelect(1);
        vizButton.setDisable(false);
    }

    @FXML
    void setMerge(MouseEvent event) {
        newSelect(3);
        vizButton.setDisable(false);
    }

    @FXML
    void setQuick(MouseEvent event) {
        newSelect(4);
        vizButton.setDisable(false);
    }

    @FXML
    void setRadix(MouseEvent event) {
        newSelect(5);
        vizButton.setDisable(false);
    }

    @FXML
    void setSelect(MouseEvent event) {
        newSelect(2);
        vizButton.setDisable(false);
    }

    void finEffect(SortingFuncs sorted) {
        double rate = Math.min(sorted.getRate(), 0.25);
        SequentialTransition finalSeq = sorted.getSeq();
        Timeline tim;

        for (int m=0;m<valueArray.size()-1;m++) {
            int finalM = m;
            tim = new Timeline(new KeyFrame(Duration.seconds(rate), event -> valueArray.get(finalM).purpleSorted()));
            finalSeq.getChildren().add(tim);
        }

        finalSeq.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(rate), event -> {
            valueArray.get(valueArray.size()-1).purpleSorted();
            //sortBox.getChildren().get(sortChoice-1).setStyle("");
            sortChoice = 0;
            topBox.setDisable(false);
        })));
        finalSeq.play();
    }

    @FXML
    void startVisual(ActionEvent event) {

        /*Timeline timeline = new Timeline(2.0);
        timeline.setCycleCount(1);
        SequentialTransition sq = new SequentialTransition();
        for (int i=0;i<valueArray.length;i++) {
            Timeline timmy = valueArray[i].redBlink(1,"BLUE");
            int finalI = i;
            timmy.setOnFinished(event1 -> valueArray[finalI].modifyHeight(100));

            *//*KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.05),
                    new KeyValue(valueArray[i].getBar().prefHeightProperty(),random.nextInt(500)));

            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.ONE,
                    event1 -> valueArray[finalI].modifyHeight(100));*//*

            //timeline.getKeyFrames().add(keyFrame);
            sq.getChildren().add(timmy);
        }
        //timeline.play();
        sq.play();*/

        topBox.setDisable(true);
        sortBox.setDisable(true);

        double rate;
        SortingFuncs sorter;

        switch (sortChoice) {
            case 1:
                rate = 1.5/(int)barNumSlider.getValue();
                sorter = new SortingFuncs(valueArray,rate);
                sorter.insertion();

                finEffect(sorter);
                break;
            case 2:
                rate = 1.0/(int)barNumSlider.getValue();
                sorter = new SortingFuncs(valueArray,rate);
                sorter.selection();

                finEffect(sorter);
                break;
            case 3:
                rate = 3.0/(int)barNumSlider.getValue();
                sorter = new SortingFuncs(valueArray,rate);
                sorter.mergeInit();
                finEffect(sorter);
                break;
            case 4:
                rate =3.0/(int)barNumSlider.getValue();
                sorter = new SortingFuncs(valueArray,rate);
                sorter.quick();
                finEffect(sorter);
                break;
            case  5:
                rate =0;
                break;
            case 6:
                rate =0;
                break;
        }
        vizButton.setDisable(true);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        barNumSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sortBox.setDisable(false);
                displayBox.getChildren().clear();
                int arrsize = newValue.intValue();
                msgbox.setText(String.valueOf(arrsize));
                valueArray = new ArrayList<>();
                MakeBar tempm;

                for (int i=0;i<arrsize;i++) {

                    tempm = new MakeBar(random.nextInt(500));
                    valueArray.add(tempm);
                    displayBox.getChildren().add(tempm.getBar());

                }
            }
        });
    }
}
