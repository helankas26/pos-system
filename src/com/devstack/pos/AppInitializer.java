package com.devstack.pos;

import com.devstack.pos.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL resource = getClass().getResource("view/LoginForm.fxml");
        Parent parent = FXMLLoader.load(resource);
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("POS");
        primaryStage.centerOnScreen();
        primaryStage.show();

        try(Session session = HibernateUtil.getSession()) {
        }
    }
}
