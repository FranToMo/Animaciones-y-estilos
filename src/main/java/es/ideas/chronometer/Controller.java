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

/**
 * Clase controlador de la aplicacion encargada de manejar la lógica que actualiza la vista.
 * @since 1.0
 * @author Francisco Tortillol Molina & Carlos José Sanchez Carmona
 * @see <a href="https://github.com/03-colores/coloresmain.git">Repositorio en GitHub</a>
 */
public class Controller implements Initializable {

    @FXML
    private ComboBox<Integer> horasInput, minutosInput, segundosInput;
    @FXML
    private Text horasTime, minutosTime, segundosTime;
    @FXML
    private AnchorPane timerPane, menuPane;
    @FXML
    private Button btnPause;

    private static final String MEDIA_URL = "media/alarma.mp3";
    boolean detener,pausar;
    Map<Integer, String> numberMap;
    Integer segundosActuales;
    Thread thread;
    TranslateTransition transition01,transition02;
    ParallelTransition parallelTransition;

    /**
     * Transforma los valores enteros que recibe (representando horas, minutos y segundos),
     * en un entero que representa los segundos totales equivalentes.
     * @param h entero que representa cantidad de horas
     * @param m entero que representa cantidad de minutos
     * @param s entero que representa cantidad de segundos
     * @return Un integer (cantidad total de segundos equivalentes).
     */
    public Integer hmsToSeconds(Integer h, Integer m, Integer s) {
        Integer hToSeconds = h * 3600;
        Integer mToSecond = m * 60;

        return hToSeconds + mToSecond + s;
    }

    /**
     * Transforma el valor que recibe como parámetro y que representa segundos,
     * en horas, minutos y segundos.
     * @param segundosActuales Su valor representa el total de segundos.
     * @return devuelve una lista con tres valores (horas, minutos y segundos).
     */

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

    /**
     * Métodos encargados de animar los Pane, estos se encuentran uno
     * encima del otro y ante determinados eventos se produce una animación.
     * En este caso el pane superior se aparta para mostrar al inferior.
     */
    public void scrollUp(){

        transition01 = new TranslateTransition();
        transition01.setDuration(Duration.millis(100));
        transition01.setToX(0);
        transition01.setToY(200);
        selectedPane(menuPane, timerPane);
        parallelTransition.setOnFinished(e -> {
          startCountdown();
        });
        parallelTransition.play();
    }

    /**
     * Método que optimiza el uso de las variables que
     * se repiten en los dos métodos encargados de
     * ejecutar la animación.
     * @param menuPane pane que contiene los nodos del tipo ComboBox.
     * @param timerPane pane que contiene los nodos del tipo Text.
     */
    private void selectedPane(AnchorPane menuPane, AnchorPane timerPane) {
        transition01.setNode(menuPane);
        transition02 = new TranslateTransition();
        transition02.setDuration(Duration.millis(100));
        transition02.setFromX(0);
        transition02.setFromY(200);
        transition02.setToX(0);
        transition02.setToY(0);
        transition02.setNode(timerPane);
        parallelTransition = new ParallelTransition(transition01,transition02);
    }
    /**
     * Métodos encargados de animar los Pane, estos se encuentran uno
     * encima del otro y ante determinados eventos se produce una animación.
     * En este caso el pane superior vuelve a su posición inicial cubriendo
     * al inferior para mostrarse el mismo.
     */
    public void scrollDown(){

        transition01 = new TranslateTransition();
        transition01.setDuration(Duration.millis(100));
        transition01.setToX(0);
        transition01.setToY(-200);
        selectedPane(timerPane, menuPane);
        parallelTransition.setOnFinished(e -> {

        });
        parallelTransition.play();
    }

    /**
     * Heredado de la interfaz Initializable. En él se inicializan dos listas
     * del tipo ObservableList y se le asignan valores en función del dato que
     * van a representar (horas, minutos o segundos).
     * También se inicializa y asignan pares de valores a un Map, con un número para
     * cada clave que un string por valor, este último con el formato correcto para
     * ser asignado a cada uno de los nodos del tipo Text.
     * @param url del la clase URL
     * @param resources de la clase ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
    pausar = false;

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


    /**
     * Comienza asigna los valores false a los booleanos detener y pausar
     * Asigna a una variable al total de segundos que representan los valores
     * seleccionados en los comboBox y reinicia estos a 0.
     * Modifica el texto que presenta el botón encargado de pausar este decremento.
     * Por último llama al método encargado de realizar una animación.
     *
     * @param event en este caso, el evento que ocurre al pulsar el botón
     */
    @FXML
    void start(ActionEvent event) {
        detener=false;
        pausar = false;
        btnPause.setText("Pausar");
        segundosActuales = hmsToSeconds(horasInput.getValue(),
                minutosInput.getValue(),
                segundosInput.getValue());
        horasInput.setValue(0);
        minutosInput.setValue(0);
        segundosInput.setValue(0);
        scrollUp();
    }

    /**
     * Mediante la instancia de un hilo, clase Thread, procesa en
     * su método run el decremento del valor obtenido anteriormente
     * (segundos totales) y detiene la ejecución cada segundo,
     * mediante un método envía los datos obtenidos a los nodos
     * del tipo Text.
     * Esta ejecución se detendrá al llegar a 0 el valor decrementado
     * y desencadenará las acciones (animation, sonido) contenidas en
     * varios métodos
     */
    void startCountdown(){
       thread = new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   while (!detener){
                       setOutput();
                       Thread.sleep(1000);
                       if(segundosActuales == 0) {
                           scrollDown();
                           playAudio();
                           detener=true;
                       }
                       if(!pausar)segundosActuales-=1;
                   }
               }catch (Exception e){}
           }
       }) ;
       thread.start();
    }

    /**
     * Asigna a cada item del LinkedList el valor que le corresponde,
     * como horas, minutos o segundos, obtenidos de convertir la
     * cantidad de segundos que quedan tras realizar un decremento
     * en estos valores antes mencionados.
     * Una vez almacenados en esta lista son asignados a su nodo del tipo
     * Text correspondiente.
     */
    void setOutput(){
        LinkedList<Integer> actualHms = secondsToHms(segundosActuales);
        horasTime.setText(numberMap.get(actualHms.get(0)));
        minutosTime.setText(numberMap.get(actualHms.get(1)));
        segundosTime.setText(numberMap.get(actualHms.get(2)));
    }
    /**
     * Al pulsar sobre el botón que desencadena este evento se asigna el
     * valor necesario al parámetro boolean para que finalice la ejecución
     * del hilo, además reinicia el valor del booleano encargado de controlar
     * la pausa en previsión de que esta estuviera activa.
     */
    @FXML
    void stopped(ActionEvent event) {
        detener = true;
        scrollDown();
        pausar=false;
    }
    /**
     * Al pulsar sobre el botón que desencadena este evento se asigna el
     * valor necesario al parámetro boolean para que sea pausado
     * el decremento al valor que representa a los segundos totales,
     * si esta acción no se estuviera realizando ya.
     * También se cambia el texto del botón para que indique la acción
     * que este puede realizar al ser pulsado.
     */
    @FXML
    void pause(ActionEvent event) {
        if(!pausar){
            btnPause.setText("Reanudar");
            pausar=true;
        }
        else {
            btnPause.setText("Pausar");
            pausar=false;
        }
    }

    /**
     * Instancia un objeto del tipo AudioClip y reproduce un sonido durante 3 segundos.  *
     * @throws InterruptedException
     */
    private void playAudio() throws InterruptedException {
        AudioClip alarma = new AudioClip(this.getClass().getResource(MEDIA_URL).toString());
        alarma.setVolume(0.2);
        alarma.play();
        Thread.sleep(3000);
        alarma.stop();
    }
}