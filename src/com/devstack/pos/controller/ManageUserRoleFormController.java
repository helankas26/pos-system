package com.devstack.pos.controller;

import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.UserRoleBo;
import com.devstack.pos.dto.UserRoleDto;
import com.devstack.pos.util.KeyGenerator;
import com.devstack.pos.view.tm.UserRoleTm;
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

public class ManageUserRoleFormController {
    public AnchorPane manageUserRoleContext;
    public JFXTextField txtUserRole;
    public JFXTextField txtDescription;
    public JFXTextField txtSearchText;
    public TableView<UserRoleTm> tblUserRoles;
    public TableColumn<UserRoleTm, Long> colId;
    public TableColumn<UserRoleTm, String> colUserRole;
    public TableColumn<UserRoleTm, String> colDescription;
    public TableColumn<UserRoleTm, Button> colDelete;
    public TableColumn<UserRoleTm, Button> colModify;
    public JFXButton btnNew;
    public JFXButton btnUpdate;
    public JFXButton btnCancel;

    private UserRoleBo userRoleBo = BoFactory.getBo(BoFactory.BoType.USER_ROLE);

    private String searchText = "";
    private Long selectedUserRoleId;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        colModify.setCellValueFactory(new PropertyValueFactory<>("modify"));

        loadAllUserRoles();

        txtSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            loadAllUserRoles();
        });
    }

    private void loadAllUserRoles() {
        ObservableList<UserRoleTm> userRoleTms = FXCollections.observableArrayList();

        for (UserRoleDto userRoleDto : userRoleBo.loadAllUserRoles(searchText)) {

            Button deleteButton = new Button("Delete");
            Button updateButton = new Button("Update");

            UserRoleTm tm = new UserRoleTm(
                    userRoleDto.getPropertyId(),
                    userRoleDto.getRoleName(),
                    userRoleDto.getRoleDescription(),
                    deleteButton,
                    updateButton
            );
            userRoleTms.add(tm);

            deleteButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                    if (userRoleBo.dropUserRole(tm.getId())) {
                        loadAllUserRoles();
                    }
                }
            });

            updateButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you need to update?", ButtonType.YES, ButtonType.CANCEL);
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                    txtUserRole.setText(tm.getRoleName());
                    txtDescription.setText(tm.getDescription());
                    selectedUserRoleId = tm.getId();

                    btnUpdate.setVisible(true);
                    btnCancel.setVisible(true);
                    btnNew.setVisible(false);
                }
            });
        }

        tblUserRoles.setItems(userRoleTms);
    }

    private void clearAll() {
        btnNew.setVisible(true);
        btnCancel.setVisible(false);
        btnUpdate.setVisible(false);

        txtUserRole.clear();
        txtDescription.clear();
        txtSearchText.clear();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) manageUserRoleContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("UserRoleAndAuthoritiesForm");
    }

    public void createUserRoleOnAction(ActionEvent actionEvent) {
        userRoleBo.saveUserRole(
                new UserRoleDto(KeyGenerator.generateId(), txtUserRole.getText().trim().toUpperCase(), txtDescription.getText())
        );

        loadAllUserRoles();
        clearAll();
    }

    public void updateUserRoleOnAction(ActionEvent actionEvent) {
        if (selectedUserRoleId != null) {
            userRoleBo.updateUserRole(
                    new UserRoleDto(selectedUserRoleId, txtUserRole.getText(), txtDescription.getText())
            );

            selectedUserRoleId = null;
            loadAllUserRoles();
        }

        clearAll();
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        clearAll();
    }
}
