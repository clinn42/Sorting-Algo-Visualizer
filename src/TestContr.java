/**
 * Sample Skeleton for 'guitest.fxml' Controller Class
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class TestContr implements Initializable {

    static boolean toggle = false;

    @FXML
    private AnchorPane ipdisp;

    @FXML // fx:id="ipvalues"
    private TextField ipvalues; // Value injected by FXMLLoader

    @FXML // fx:id="showsrt"
    private Button showsrt; // Value injected by FXMLLoader

    @FXML // fx:id="basicrec"
    private Rectangle basicrec; // Value injected by FXMLLoader

    @FXML
    void gosrt(ActionEvent event) {
        if (!toggle) {
            double half = basicrec.getHeight()/2;
            basicrec.setHeight(half);
            basicrec.setFill(Color.web("#ff5733"));
            ipvalues.setPromptText("Yolo");
            toggle = !toggle;
            basicrec.setWidth(basicrec.getWidth() *2);
        } else {
            double twice = basicrec.getHeight() * 2;
            basicrec.setHeight(twice);
            ipvalues.setPromptText("YOMAMA");
            basicrec.setFill(Color.web("#5dade2"));
            toggle = !toggle;
            basicrec.setWidth(basicrec.getWidth()/2);
        }
        Scene test = basicrec.getScene();
        System.out.println(test.getHeight());
        System.out.println(ipdisp.getHeight());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(ipdisp.getParent());
        //ipdisp.setMaxHeight(ipdisp.);
        System.out.println(ipdisp.getChildren());
        //splitpain.setDividerPosition(0,splitpain.getHeight()/0.2);
    }
}
