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

        for (int i = 0; i < arrsize; i++) {
            tempm = new MakeBar(random.nextInt(500));
            valueArray.add(tempm);
            displayBox.getChildren().add(tempm.getBar());
        }
    }

    void newSelect(int nc) {
        if (sortChoice != 0) {
            sortBox.getChildren().get(sortChoice - 1).setStyle("");
        }
        sortChoice = nc;
        sortBox.getChildren().get(sortChoice - 1).setStyle("-fx-background-color: #2c3e50;");
        vizButton.setDisable(false);
    }

    @FXML
    void setHeap(MouseEvent event) {
        newSelect(6);
    }

    @FXML
    void setInsert(MouseEvent event) {
        newSelect(1);
    }

    @FXML
    void setMerge(MouseEvent event) {
        newSelect(3);
    }

    @FXML
    void setQuick(MouseEvent event) {
        newSelect(4);
    }

    @FXML
    void setRadix(MouseEvent event) {
        newSelect(5);
    }

    @FXML
    void setSelect(MouseEvent event) {
        newSelect(2);
    }

    void startprocess(boolean Start) {
        if (Start) {
            topBox.setDisable(true);
            sortBox.setDisable(true);
        } else {
            sortBox.getChildren().get(sortChoice - 1).setStyle("");
            sortChoice = 0;
            topBox.setDisable(false);
        }
    }

    @FXML
    void startVisual(ActionEvent event) {

        startprocess(true);

        double rate;
        int barval = (int) barNumSlider.getValue();
        SortingFuncs sorter;
        SequentialTransition finalseq = new SequentialTransition();

        switch (sortChoice) {
            case 1:
                rate = 1.5 / barval;
                sorter = new SortingFuncs(valueArray, rate);
                sorter.insertion();

                finalseq = sorter.finEffect();
                break;
            case 2:
                rate = 1.0 / barval;
                sorter = new SortingFuncs(valueArray, rate);
                sorter.selection();

                finalseq = sorter.finEffect();
                break;
            case 3:
                rate = 3.0 / barval;
                sorter = new SortingFuncs(valueArray, rate);
                sorter.mergeInit();
                finalseq = sorter.finEffect();
                break;
            case 4:
                rate = 3.0 / barval;
                sorter = new SortingFuncs(valueArray, rate);
                sorter.quick();
                finalseq = sorter.finEffect();
                break;
            case 5:
                rate = 0;
                break;
            case 6:
                rate = 0;
                break;
        }
        finalseq.setOnFinished(event1 -> startprocess(false));
        finalseq.play();

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

                for (int i = 0; i < arrsize; i++) {
                    tempm = new MakeBar(random.nextInt(500));
                    valueArray.add(tempm);
                    displayBox.getChildren().add(tempm.getBar());
                }
            }
        });
    }
}
