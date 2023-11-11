package com.devstack.pos.controller;

import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.UserDao;
import com.devstack.pos.util.ResponseData;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginFormController {

    public AnchorPane loginContext;
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        UserDao userDao = DaoFactory.getDao(DaoFactory.DaoType.USER);
        ResponseData data = userDao.login(txtUsername.getText().trim(), txtPassword.getText().trim());

        if (null != data) {
            if ((boolean) data.getData()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, data.getMessage());
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Stage stage = (Stage) loginContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/AdminPortalForm.fxml"))));
                    stage.centerOnScreen();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, data.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
        }
    }
}
