module es.ideas.chronometer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens es.ideas.chronometer to javafx.fxml;
    exports es.ideas.chronometer;
}