package es.ideas.chronometer;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.Map;





public class Controller  {

    @FXML
    private Button botonCancelar, botonInicio;
    @FXML
    private ComboBox<Integer> horasInput, minutosInput, segundosInput;
    @FXML
    private Text horasTime, minutosTime, segundosTime;
    @FXML
    private AnchorPane timerPane, menuPane;

    
    
    public Integer hmsToSeconds(Integer h, Integer m, Integer s){
        Integer hToSeconds = h*3600;
        Integer mToSecond = m*60;

        return hToSeconds+mToSecond+s;
    }
    public LinkedList<Integer> secondsToHms(Integer segundosActuales){
        Integer horas = segundosActuales/3600;
        segundosActuales = segundosActuales%3600;
        Integer minutos = segundosActuales/60;
        Integer segundos = segundosActuales%60;
        LinkedList<Integer> respuesta = new LinkedList<>();
        respuesta.add(horas);
        respuesta.add(minutos);
        respuesta.add(segundos);
        return respuesta;
    }
}