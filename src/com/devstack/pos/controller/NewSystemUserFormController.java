package com.devstack.pos.controller;

import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.UserBo;
import com.devstack.pos.bo.custom.UserRoleBo;
import com.devstack.pos.dto.UserDto;
import com.devstack.pos.dto.UserRoleDto;
import com.devstack.pos.view.tm.SystemUserTm;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewSystemUserFormController {
    public AnchorPane newSystemUserContext;
    public JFXComboBox<String> cmbUserRole;
    public JFXTextField txtUsername;
    public JFXTextField txtDisplayName;
    public JFXTextField txtSearchText;
    public TableView<SystemUserTm> tblUsers;
    public TableColumn<SystemUserTm, Long> colId;
    public TableColumn<SystemUserTm, String> colUserRole;
    public TableColumn<SystemUserTm, String> colStatus;
    public TableColumn<SystemUserTm, String> colDisplayName;
    public TableColumn<SystemUserTm, String> colEmail;
    public TableColumn<SystemUserTm, Button> colDelete;
    public TableColumn<SystemUserTm, Button> colModify;

    private UserRoleBo userRoleBo = BoFactory.getBo(BoFactory.BoType.USER_ROLE);
    private UserBo userBo = BoFactory.getBo(BoFactory.BoType.USER);

    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private ObservableList<SystemUserTm> systemUserTms = FXCollections.observableArrayList();
    private List<UserRoleDto> userRoleDtos = new ArrayList<>();

    private String searchText = "";

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDisplayName.setCellValueFactory(new PropertyValueFactory<>("displayName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        colModify.setCellValueFactory(new PropertyValueFactory<>("modify"));

        loadAllUserRoles();
        loadAllSystemUser();

        txtSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            loadAllSystemUser();
        });
    }

    private void loadAllUserRoles() {
        userRoleDtos = userRoleBo.loadAllUserRoles();
        for (UserRoleDto dto : userRoleDtos) {
            observableList.add(dto.getRoleName());
        }

        cmbUserRole.setItems(observableList);
    }

    private void loadAllSystemUser() {
        systemUserTms.clear();
        for (UserDto userDto : userBo.loadAllUsers(searchText)) {

            Button deleteButton = new Button("Delete");
            Button updateButton = new Button("Update");

            SystemUserTm tm = new SystemUserTm(
                    userDto.getPropertyId(),
                    userDto.getUserRoleDto().getRoleName(),
                    userDto.isActiveState() ? "Active" : "Disabled",
                    userDto.getDisplayName(),
                    userDto.getUsername(),
                    deleteButton,
                    updateButton
            );
            systemUserTms.add(tm);
        }

        tblUsers.setItems(systemUserTms);
//        tblUsers.refresh();
    }

    private void clearAll() {
        cmbUserRole.setValue(null);
        txtUsername.clear();
        txtDisplayName.clear();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) newSystemUserContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("UserManagementForm");
    }

    public void createSystemUserOnAction(ActionEvent actionEvent) {
        String userRole = cmbUserRole.getValue();
        Optional<UserRoleDto> selectedUserRoleDto =
                userRoleDtos.stream().filter(e -> e.getRoleName().equals(userRole)).findFirst();

        String displayName = txtDisplayName.getText();
        String userName = txtUsername.getText();

        selectedUserRoleDto.ifPresent(userRoleDto -> userBo.createNewSystemUser(userRoleDto.getPropertyId(), displayName, userName));

        loadAllSystemUser();
        clearAll();
    }
}
