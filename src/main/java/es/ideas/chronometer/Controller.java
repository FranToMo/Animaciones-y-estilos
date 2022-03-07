package es.ideas.chronometer;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class Controller implements Initializable {

    
    @FXML
    private Button botonCancelar, botonInicio;
    @FXML
    private ComboBox<Integer> horasInput, minutosInput, segundosInput;
    @FXML
    private Text horasTime, minutosTime, segundosTime;
    @FXML
    private AnchorPane timerPane, menuPane;

    private static final String MEDIA_URL = "media/alarma.mp3";
    boolean detener;
    Map<Integer, String> numberMap;
    Integer segundosActuales;
    Thread thread;
    TranslateTransition transition01,transition02;
    ParallelTransition parallelTransition;


    public Integer hmsToSeconds(Integer h, Integer m, Integer s) {
        Integer hToSeconds = h * 3600;
        Integer mToSecond = m * 60;

        return hToSeconds + mToSecond + s;
    }

    public LinkedList<Integer> secondsToHms(Integer segundosActuales) {
        Integer horas = segundosActuales / 3600;
        segundosActuales = segundosActuales % 3600;
        Integer minutos = segundosActuales / 60;
        Integer segundos = segundosActuales % 60;
        LinkedList<Integer> respuesta = new LinkedList<>();
        respuesta.add(horas);
        respuesta.add(minutos);
        respuesta.add(segundos);
        return respuesta;
    }

    public void scrollUp(){

        transition01 = new TranslateTransition();
        transition01.setDuration(Duration.millis(100));
        transition01.setToX(0);
        transition01.setToY(200);
        transition01.setNode(menuPane);
        transition02 = new TranslateTransition();
        transition02.setDuration(Duration.millis(100));
        transition02.setFromX(0);
        transition02.setFromY(200);
        transition02.setToX(0);
        transition02.setToY(0);
        transition02.setNode(timerPane);
        parallelTransition = new ParallelTransition(transition01,transition02);
      parallelTransition.setOnFinished(e -> {
          startCountdown();
        });
        parallelTransition.play();
    }

    public void scrollDown(){

        transition01 = new TranslateTransition();
        transition01.setDuration(Duration.millis(100));
        transition01.setToX(0);
        transition01.setToY(-200);
        transition01.setNode(timerPane);
        transition02 = new TranslateTransition();
        transition02.setDuration(Duration.millis(100));
        transition02.setFromX(0);
        transition02.setFromY(200);
        transition02.setToX(0);
        transition02.setToY(0);
        transition02.setNode(menuPane);
        parallelTransition = new ParallelTransition(transition01,transition02);
        parallelTransition.play();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
detener = true;

        ObservableList<Integer> listaDeHoras = FXCollections.observableArrayList();
        ObservableList<Integer> listaDeMinutosYSegundos = FXCollections.observableArrayList();


        for (int i = 0; i <= 59; i++) {
            if (0 <= i && i <= 23) {
                listaDeHoras.add(Integer.valueOf(i));
            }
            listaDeMinutosYSegundos.add(Integer.valueOf(i));
        }
        horasInput.setItems(listaDeHoras);
        horasInput.setValue(0);
        minutosInput.setItems(listaDeMinutosYSegundos);
        minutosInput.setValue(0);
        segundosInput.setItems(listaDeMinutosYSegundos);
        segundosInput.setValue(0);

        numberMap = new TreeMap<>();
        for (Integer i = 0; i <= 59; i++) {
            if (0 <= i && i <= 9) {
                numberMap.put(i, "0" + i.toString());
            } else {
                numberMap.put(i, i.toString());
            }
        }
    }


    @FXML
    void start(ActionEvent event) {
        detener=false;
        segundosActuales = hmsToSeconds(horasInput.getValue(),
                minutosInput.getValue(),
                segundosInput.getValue());
        horasInput.setValue(0);
        minutosInput.setValue(0);
        segundosInput.setValue(0);
        scrollUp();
    }

    void startCountdown(){
       thread = new Thread(() -> {
           try {
               while (!detener){
                   setOutput();
                   Thread.sleep(1000);
                   if(segundosActuales == 0) {
                       scrollDown();
                       playAudio();
                       detener=true;
                   }
                   segundosActuales-=1;
               }
           }catch (Exception e){}
       }) ;
       thread.start();
    }

    /**
     * Asigna el valor seleccionado en cada comboBox
     * al valor inicial de cada uno de los nodos Text
     * que forman el cronómetro.
     */
    void setOutput(){
        LinkedList<Integer> actualHms = secondsToHms(segundosActuales);
        horasTime.setText(numberMap.get(actualHms.get(0)));
        minutosTime.setText(numberMap.get(actualHms.get(1)));
        segundosTime.setText(numberMap.get(actualHms.get(2)));
    }
    @FXML
    void stopped(ActionEvent event) {
        detener = true;
        scrollDown();

    }

    private void playAudio(){
        AudioClip alarma = new AudioClip(this.getClass().getResource(MEDIA_URL).toString());
        alarma.setVolume(0.2);
        alarma.play();
    }
}