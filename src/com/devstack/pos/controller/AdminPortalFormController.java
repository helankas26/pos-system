package com.devstack.pos.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AdminPortalFormController {
    public AnchorPane adminPortalContext;
    public ImageView menu;
    public ImageView menuClose;
    public AnchorPane overlayPane;
    public VBox menuSlider;

    public void initialize() {
        menuSlider.setVisible(true);
        menuSlider.setTranslateX(-275);
        overlayPane.setVisible(false);
        overlayPane.setDisable(true);

        menu.setOnMouseClicked(event -> {
            menu.setDisable(true);
            toggleMenu(true);
        });

        menuClose.setOnMouseClicked(event -> {
            menuClose.setDisable(true);
            toggleMenu(false);
        });

        overlayPane.setOnMouseClicked(event -> {
            toggleMenu(false);
        });
    }

    private void toggleMenu(boolean open) {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.4), menuSlider);
        /*TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menuSlider);*/
        slide.setToX(open ? 0 : -275);
        slide.play();

        slide.setOnFinished(e -> {
            if (open) {
                menu.setVisible(false);
                menuClose.setVisible(true);
                overlayPane.setVisible(true);
                overlayPane.setDisable(false);
                menuClose.setDisable(false);
            } else {
                menu.setVisible(true);
                menuClose.setVisible(false);
                overlayPane.setVisible(false);
                overlayPane.setDisable(true);
                menu.setDisable(false);
            }
        });
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) adminPortalContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void userManagementOnAction(ActionEvent actionEvent) throws IOException {
        setUi("UserManagementForm");
    }

    public void userRoleAndAuthoritiesOnAction(ActionEvent actionEvent) {
    }

    public void incomeAndReportOnAction(ActionEvent actionEvent) {
    }

    public void manageInventoryOnAction(ActionEvent actionEvent) {
    }

    public void customerManagementOnAction(ActionEvent actionEvent) {
    }

    public void dealsAndDiscountOnAction(ActionEvent actionEvent) {
    }

    public void userActivitiesOnAction(ActionEvent actionEvent) {
    }

    public void reportAndStatisticsOnAction(ActionEvent actionEvent) {
    }
}
