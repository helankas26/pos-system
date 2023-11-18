package com.devstack.pos.controller;

import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.AccessPointBo;
import com.devstack.pos.dto.AccessPointDto;
import com.devstack.pos.util.KeyGenerator;
import com.devstack.pos.view.tm.AccessPointTm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ManageAccessPointsFormController {
    public AnchorPane manageAccessPointsContext;
    public JFXTextField txtAccessPoint;
    public JFXTextField txtSearchText;
    public TableView<AccessPointTm> tblAccessPoints;
    public TableColumn<AccessPointTm, Long> colId;
    public TableColumn<AccessPointTm, String> colAccessPointName;
    public TableColumn<AccessPointTm, Button> colDelete;
    public TableColumn<AccessPointTm, Button> colModify;
    public JFXButton btnNew;
    public JFXButton btnUpdate;
    public JFXButton btnCancel;

    private AccessPointBo accessPointBo = BoFactory.getBo(BoFactory.BoType.ACCESS_POINT);

    private String searchText = "";
    private Long selectedAccessPointId;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAccessPointName.setCellValueFactory(new PropertyValueFactory<>("AccessPointName"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        colModify.setCellValueFactory(new PropertyValueFactory<>("modify"));

        loadAllAccessPoints();

        txtSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            loadAllAccessPoints();
        });
    }

    private void loadAllAccessPoints() {
        ObservableList<AccessPointTm> accessPointTms = FXCollections.observableArrayList();

        for (AccessPointDto accessPointDto : accessPointBo.loadAlAccessPoints(searchText)) {

            Button deleteButton = new Button("Delete");
            Button updateButton = new Button("Update");

            AccessPointTm tm = new AccessPointTm(
                    accessPointDto.getPropertyId(),
                    accessPointDto.getPointName(),
                    deleteButton,
                    updateButton
            );

            accessPointTms.add(tm);

            deleteButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                    if (accessPointBo.dropAccessPoint(tm.getId())) {
                        loadAllAccessPoints();
                    }
                }
            });

            updateButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you need to update?", ButtonType.YES, ButtonType.CANCEL);
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                    txtAccessPoint.setText(tm.getAccessPointName());
                    selectedAccessPointId = tm.getId();

                    btnUpdate.setVisible(true);
                    btnCancel.setVisible(true);
                    btnNew.setVisible(false);
                }
            });
        }

        tblAccessPoints.setItems(accessPointTms);
    }

    private void clearAll() {
        btnNew.setVisible(true);
        btnCancel.setVisible(false);
        btnUpdate.setVisible(false);

        txtAccessPoint.clear();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) manageAccessPointsContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("UserRoleAndAuthoritiesForm");
    }

    public void createAccessPointOnAction(ActionEvent actionEvent) {
        if (accessPointBo.createAccessPoint(
                new AccessPointDto(KeyGenerator.generateId(), txtAccessPoint.getText())
        )) {
            new Alert(Alert.AlertType.INFORMATION, "Saved!").show();
            loadAllAccessPoints();
            clearAll();
        }
    }

    public void updateAccessPointOnAction(ActionEvent actionEvent) {
        if (selectedAccessPointId != null) {
            accessPointBo.updateAccessPoint(
                    new AccessPointDto(selectedAccessPointId, txtAccessPoint.getText())
            );

            selectedAccessPointId = null;
            loadAllAccessPoints();
        }

        clearAll();
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        clearAll();
    }
}
