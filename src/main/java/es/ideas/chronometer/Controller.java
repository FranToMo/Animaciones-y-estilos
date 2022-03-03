package es.ideas.chronometer;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class Controller implements Initializable {


    @FXML
    private ComboBox<Integer> horasInput, minutosInput, segundosInput;
    Map<Integer, String> numberMap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Integer>listaDeHoras = FXCollections.observableArrayList();
        ObservableList<Integer>listaDeMinutosYSegundos = FXCollections.observableArrayList();


        for (int i = 0; i <=59 ; i++) {
            if(0<=i && i<=24){
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
        for (Integer i = 0; i <=59 ; i++) {
                if(0<=i && i<=9){
                    numberMap.put(i,"0"+i.toString());
                }else{
                    numberMap.put(i,i.toString());
                }
        }


    }

}