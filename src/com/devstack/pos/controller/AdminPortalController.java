package com.devstack.pos.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AdminPortalController {
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
            if(open) {
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
}
