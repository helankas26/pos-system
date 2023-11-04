package com.devstack.pos.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserManagementController {
    public AnchorPane userManagementContext;

    public void btnBackToHome(MouseEvent mouseEvent) throws IOException {
        setUi("AdminPortal");    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) userManagementContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
