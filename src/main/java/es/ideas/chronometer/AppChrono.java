package es.ideas.chronometer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Clase principal, utilizando JavaFX, crea un Cronómetro y utilizo la clase
 * TranslateTransition.
 * @since 1.0
 * @author Francisco Tortillol Molina & Carlos José Sanchez Carmona
 * @see <a href="https://github.com/FranToMo/Animaciones-y-estilos.git">Repositorio en GitHub</a>
 */
public class AppChrono extends Application {

    /**
     * Carga los parámetros de la vista definidos en el archivo fxml los
     * cuales pasaran a ser uno de los parámetros de una instancia del objeto
     * de la clase Scene, junto con sus dimensiones.
     *
     * @param stage Recibe una instancia de la clase Scene y se muestra.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppChrono.class.getResource("viewChronometer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 200);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Chronometer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal de la aplicación, ejecuta el método launch().
     * @param args Un array de String
     */
    public static void main(String[] args) {
        launch();
    }
}