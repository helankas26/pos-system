package com.devstack.pos.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageUserRoleFormController {
    public AnchorPane manageUserRoleContext;

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) manageUserRoleContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("UserRoleAndAuthoritiesForm");
    }
}
