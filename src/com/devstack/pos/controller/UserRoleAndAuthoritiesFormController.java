package com.devstack.pos.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserRoleAndAuthoritiesFormController {
    public AnchorPane UserRoleAndAuthoritiesContext;

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) UserRoleAndAuthoritiesContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("UserManagementForm");
    }

    public void manageUserRolesOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("ManageUserRoleForm");
    }

    public void manageAccessPointsOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("ManageAccessPointsForm");
    }

    public void privilegesOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("ManagePrivilegesForm");
    }

    public void authoritiesOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("AuthoritiesForm");
    }
}
