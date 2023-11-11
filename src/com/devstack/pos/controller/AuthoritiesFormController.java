package com.devstack.pos.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AuthoritiesFormController {
    public AnchorPane authoritiesContext;
    public AnchorPane overlayPane;
    public AnchorPane overlayPaneContext;
    public TableView tblAuthorities;

    public void initialize() {
        overlayPane.setVisible(false);
        overlayPane.setDisable(true);

        overlayPane.setOnMouseClicked(event -> {
            FadeTransition popup = new FadeTransition(Duration.seconds(0.4), overlayPane);
            popup.setFromValue(1.0);
            popup.setToValue(0);
            popup.play();

            popup.setOnFinished(innerEvent -> {
                overlayPane.setVisible(false);
                overlayPane.setDisable(true);
                overlayPaneContext.getChildren().clear();
            });
        });
    }

    public void tableOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        tblAuthorities.getSelectionModel().getSelectedItem();
        openAuthorityDetailsModal("code is here");
    }

    public void openAuthorityDetailsModal(String code) throws IOException {
        overlayPane.setVisible(true);
        overlayPane.setDisable(false);
        FadeTransition popup = new FadeTransition(Duration.seconds(0.4), overlayPane);
        popup.setFromValue(0);
        popup.setToValue(1.0);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AuthorityDetailsForm.fxml"));
        AnchorPane AuthorityDetailsForm = loader.load();
        AuthorityDetailsFormController controller = loader.getController();
        controller.initialize(code);

        overlayPaneContext.getChildren().clear();
        overlayPaneContext.getChildren().add(AuthorityDetailsForm);

        popup.play();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) authoritiesContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("UserRoleAndAuthoritiesForm");
    }
}
