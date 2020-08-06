import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class MakeBar {

    @FXML
    private Label bar;

    MakeBar(int ht) {
        this.bar = new Label();
        this.bar.setPrefWidth(60.0);
        this.bar.setStyle("-fx-background-color: #5dade2;");
        this.bar.setPrefHeight(ht);

        //bar.setText(String.valueOf(ht));
        /*bar.setTextOverrun(OverrunStyle.ELLIPSIS);
        bar.setEllipsisString("");
        bar.setFont(new Font("Monospaced Bold", 24));
        bar.setAlignment(Pos.TOP_CENTER);
        bar.setTextFill(Paint.valueOf("#ffffff"));*/
    }

    Label getBar() {
        return this.bar;
    }

    double retHeight() {
        return this.bar.getPrefHeight();
    }


    Timeline blink(double rate, String firsColor, String secColor) {
        KeyFrame red = new KeyFrame(Duration.ONE, event -> this.setColor(firsColor));
        KeyFrame afterBlink = new KeyFrame(Duration.seconds(rate), event -> this.setColor(secColor));
        return new Timeline(red, afterBlink);
    }

    Timeline swapOne(double rate, String c1, String c2, double height) {
        Timeline s1 = new Timeline(new KeyFrame(Duration.ONE, event -> this.modifyHeight(height)));
        s1.getKeyFrames().addAll(this.blink(rate, c1, c2).getKeyFrames());
        return s1;
    }

    void modifyHeight(double newHeight) {
        this.bar.setPrefHeight(newHeight);
    }

    void ogBlue() {
        this.bar.setStyle("-fx-background-color: #5dade2;");
    }

    void redActive() {
        this.bar.setStyle("-fx-background-color: #ff0000;");
    }

    void greenInserted() {
        this.bar.setStyle("-fx-background-color: #8bff3a;");
    }

    void purpleSorted() {
        this.bar.setStyle("-fx-background-color: #ce5dff;");
    }

    private void setColor(String color) {
        switch (color) {
            case "GREEN":
                this.greenInserted();
                return;
            case "BLUE":
                this.ogBlue();
                return;
            case "RED":
                this.redActive();
                return;
            case "PINK":
                this.purpleSorted();
                return;
            case "1":
                this.bar.setStyle("-fx-background-color: #fff93a;");
                break;
            case "2":
                this.bar.setStyle("-fx-background-color: #3aeeff;");
                break;
            case "3":
                this.bar.setStyle("-fx-background-color: #f39c12;");
                break;
            default:
                this.bar.setStyle("-fx-background-color: #16a085;");
        }
    }

}
