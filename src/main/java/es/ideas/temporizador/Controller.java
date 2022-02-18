package es.ideas.temporizador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Timeline lineaPrimaria,lineaSecundaria;
    private Calendar now;
    private int hour1,hour2,minute1,minute2,second1,second2;
    private KeyFrame keyPrimario,keySecundario;
    @FXML
    private Label lCifra1,lCifra2,lCifra3,lCifra4,lCifra5,lCifra6;
    @FXML
    private TextField tC1,tC2,tC3,tC4,tC5,tC6;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
        animateClock();
    }

//    private void update(){
//        now = Calendar.getInstance();
//        hour1 = (now.get(Calendar.HOUR_OF_DAY)/10);
//        hour2 = (now.get(Calendar.HOUR_OF_DAY))%10;
//        minute1 = (now.get(Calendar.MINUTE))/10;
//        minute2 = (now.get(Calendar.MINUTE))%10;
//        second1 = (now.get(Calendar.SECOND))/10;
//        second2 = (now.get(Calendar.SECOND))%10;
//
//
//        tC1.setText("0");
//        tC2.setText("0");
//        tC3.setText("0");
//        tC4.setText("0");
//        tC5.setText("0");
//        tC6.setText("0");
//
//
//    }
//
//    private void animateClock(){
//        lineaPrimaria = new Timeline();
//        lineaSecundaria = new Timeline();
//        lineaSecundaria.setCycleCount(Timeline.INDEFINITE);
//        keyPrimario = new KeyFrame(
//                new Duration(1000-now.get(Calendar.MILLISECOND)%1000),
//                (event)->{
//                    update();
//                    lineaSecundaria.play();
//                }
//        );
//        keySecundario = new KeyFrame(
//                Duration.seconds(1),
//                (event)-> {
//                    update();
//                }
//        );
//        lineaPrimaria.getKeyFrames().add(keyPrimario);
//        lineaSecundaria.getKeyFrames().add(keySecundario);
//        lineaPrimaria.play();
//    }

    private void update(){
        now = Calendar.getInstance();
        hour1 = (now.get(Calendar.HOUR_OF_DAY)/10);
        hour2 = (now.get(Calendar.HOUR_OF_DAY))%10;
        minute1 = (now.get(Calendar.MINUTE))/10;
        minute2 = (now.get(Calendar.MINUTE))%10;
        second1 = (now.get(Calendar.SECOND))/10;
        second2 = (now.get(Calendar.SECOND))%10;
        lCifra1.setText(""+hour1);
        lCifra2.setText(""+hour2);
        lCifra3.setText(""+minute1);
        lCifra4.setText(""+minute2);
        lCifra5.setText(""+second1);
        lCifra6.setText(""+second2);
    }

    private void animateClock(){
        lineaPrimaria = new Timeline();
        lineaSecundaria = new Timeline();
        lineaSecundaria.setCycleCount(Timeline.INDEFINITE);
        keyPrimario = new KeyFrame(
                new Duration(1000-now.get(Calendar.MILLISECOND)%1000),
                        (event)->{
                    update();
                    lineaSecundaria.play();
                        }
        );
        keySecundario = new KeyFrame(
                Duration.seconds(1),
                (event)-> {
                    update();
                }
        );
        lineaPrimaria.getKeyFrames().add(keyPrimario);
        lineaSecundaria.getKeyFrames().add(keySecundario);
        lineaPrimaria.play();
    }
}