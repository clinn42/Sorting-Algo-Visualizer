
/**
 * Sample Skeleton for 'guitest1.fxml' Controller Class
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class Test1 implements Initializable {

    private static int[] vals;
    private static ArrayList<MakeBar> bars = new ArrayList<>();
    private static int pos = 0;
    private static double posy = 0;

    @FXML // fx:id="values"
    private TextField values; // Value injected by FXMLLoader

    @FXML // fx:id="dosort"
    private Button dosort; // Value injected by FXMLLoader

    @FXML
    private HBox dispain;

    @FXML
    private Slider numbars;

    @FXML
    void gosort(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numbars.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                values.setText(String.format("%d",newValue.intValue()));
                dispain.getChildren().clear();
                Random rando = new Random();

                for (int i=0;i<newValue.intValue();i++) {
                    MakeBar m = new MakeBar(rando.nextInt(500));
                    dispain.getChildren().add(m.getBar());
                }
            }
        });
    }
}
