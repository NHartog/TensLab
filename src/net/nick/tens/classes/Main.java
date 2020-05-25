package net.nick.tens.classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //when program is run, initalizes the root and stage to actaully show project
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/net/nick/tens/files/fxml/testingcss.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 718, 523));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
