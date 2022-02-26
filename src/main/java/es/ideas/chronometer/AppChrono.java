package es.ideas.chronometer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AppChrono extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppChrono.class.getResource("viewChronometer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 200);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Chronometer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}