package com.devstack.pos.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserManagementFormController {
    public AnchorPane userManagementContext;

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) userManagementContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("AdminPortalForm");
    }

    public void manageSystemUserOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("NewSystemUserForm");
    }

    public void newUserRoleOnMouseClicked(MouseEvent mouseEvent) throws IOException {
    }
}
