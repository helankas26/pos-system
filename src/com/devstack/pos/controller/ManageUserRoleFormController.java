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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        colModify.setCellValueFactory(new PropertyValueFactory<>("modify"));

        loadAllUserRoles();
    }

    private void loadAllUserRoles() {
        ObservableList<UserRoleTm> userRoleTms = FXCollections.observableArrayList();

        for (UserRoleDto userRoleDto : userRoleBo.loadAllUserRoles()) {

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
        }

        tblUserRoles.setItems(userRoleTms);
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
    }

    public void updateUserRoleOnAction(ActionEvent actionEvent) {
    }

    public void cancelOnAction(ActionEvent actionEvent) {
    }
}
