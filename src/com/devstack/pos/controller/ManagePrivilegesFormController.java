package com.devstack.pos.controller;

import com.devstack.pos.bo.BoFactory;
import com.devstack.pos.bo.custom.AccessPointBo;
import com.devstack.pos.bo.custom.AccessPointCrudBo;
import com.devstack.pos.dto.AccessPointCrudDto;
import com.devstack.pos.dto.AccessPointDto;
import com.devstack.pos.enums.Crud;
import com.devstack.pos.util.KeyGenerator;
import com.devstack.pos.view.tm.AccessPrivilegesTm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ManagePrivilegesFormController {
    public AnchorPane managePrivilegesContext;
    public JFXComboBox<AccessPointDto> cmbAccessPoint;
    public JFXCheckBox chkCreate;
    public JFXCheckBox chkRead;
    public JFXCheckBox chkUpdate;
    public JFXCheckBox chkDelete;
    public JFXTextField txtSearchText;
    public TableView<AccessPrivilegesTm> tblPrivileges;
    public TableColumn<AccessPrivilegesTm, Long> colId;
    public TableColumn<AccessPrivilegesTm, String> colAccessPoint;
    public TableColumn<AccessPrivilegesTm, List<JFXCheckBox>> colOperation;
    public TableColumn<AccessPrivilegesTm, Button> colDelete;
    public TableColumn<AccessPrivilegesTm, Button> colModify;
    public JFXButton btnNew;
    public JFXButton btnUpdate;
    public JFXButton btnCancel;

    private AccessPointBo accessPointBo = BoFactory.getBo(BoFactory.BoType.ACCESS_POINT);
    private AccessPointCrudBo accessPointCrudBo = BoFactory.getBo(BoFactory.BoType.ACCESS_POINT_CRUD);

    private List<AccessPointCrudDto> allPrivilegesByAccessPoint;
    private Map<Crud, JFXCheckBox> privilegesToCheckboxMap = new HashMap<>();
    private Map<Crud, String> colorMap = new HashMap<>();

    private String searchText = "";
    private AccessPointDto selectedAccessPoint;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAccessPoint.setCellValueFactory(new PropertyValueFactory<>("accessPoint"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        colModify.setCellValueFactory(new PropertyValueFactory<>("modify"));
        colOperation.setCellFactory(column -> new TableCell<AccessPrivilegesTm, List<JFXCheckBox>>() {
            @Override
            protected void updateItem(List<JFXCheckBox> checkboxes, boolean empty) {
                super.updateItem(checkboxes, empty);
                if (empty || checkboxes == null) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10);
                    hbox.getChildren().addAll(checkboxes);
                    setGraphic(hbox);
                }
            }
        });

        colorMap.put(Crud.CREATE, "#0f9d58");
        colorMap.put(Crud.READ, "#1791c9");
        colorMap.put(Crud.UPDATE, "#ee7d36");
        colorMap.put(Crud.DELETE, "#e12727");

        privilegesToCheckboxMap.put(Crud.CREATE, chkCreate);
        privilegesToCheckboxMap.put(Crud.READ, chkRead);
        privilegesToCheckboxMap.put(Crud.UPDATE, chkUpdate);
        privilegesToCheckboxMap.put(Crud.DELETE, chkDelete);

        loadAllAccessPoints();
        loadAllAccessPointsPrivileges();

        txtSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            loadAllAccessPointsPrivileges();
        });

        cmbAccessPoint.setOnAction(event -> {
            setCheckBoxBasedOnCrud();
        });
    }

    private void loadAllAccessPoints() {
        ObservableList<AccessPointDto> accessPointDtos = FXCollections.observableArrayList(accessPointBo.loadAlAccessPoints());
        cmbAccessPoint.setItems(accessPointDtos);
    }

    private void loadAllAccessPointsPrivileges() {
        List<AccessPointCrudDto> accessPointCrudDtos = accessPointCrudBo.loadAllAccessPointCruds(searchText);

        // Grouping AccessPointCrud objects by AccessPoint
        Map<AccessPointDto, List<AccessPointCrudDto>> groupedByAccessPoint = accessPointCrudDtos.stream()
                .collect(Collectors.groupingBy(AccessPointCrudDto::getAccessPointDto));

        // Mapping to AccessPrivilegesTm
        ObservableList<AccessPrivilegesTm> accessPrivilegesTms = FXCollections.observableArrayList(
                groupedByAccessPoint.entrySet().stream()
                        .map(entry -> {
                            AccessPointDto accessPoint = entry.getKey();
                            List<AccessPointCrudDto> crudList = entry.getValue();

                            Button deleteButton = new Button("Delete");
                            Button updateButton = new Button("Update");

                            List<JFXCheckBox> crudTmList = crudList.stream()
                                    .map(crudDto -> {
                                        JFXCheckBox checkBox = new JFXCheckBox(crudDto.getCrud().name());
                                        checkBox.setStyle("-jfx-checked-color: " + colorMap.get(crudDto.getCrud()) + ";");
                                        // checkBox.setDisable(true);
                                        checkBox.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent::consume);
                                        checkBox.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent::consume);
                                        checkBox.setSelected(true);

                                        return checkBox;
                                    })
                                    .collect(Collectors.toList());

                            AccessPrivilegesTm privilegesTm = new AccessPrivilegesTm(
                                    accessPoint.getPropertyId(),
                                    accessPoint.getPointName(),
                                    crudTmList,
                                    deleteButton,
                                    updateButton
                            );

                            deleteButton.setOnAction(e -> {
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure?", ButtonType.YES, ButtonType.NO);
                                Optional<ButtonType> buttonType = alert.showAndWait();

                                if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                                    if (accessPointCrudBo.dropAllAccessPointCruds(accessPoint)) {
                                        loadAllAccessPointsPrivileges();
                                    }
                                }
                            });

                            updateButton.setOnAction(e -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you need to update?", ButtonType.YES, ButtonType.CANCEL);
                                Optional<ButtonType> buttonType = alert.showAndWait();

                                if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                                    cmbAccessPoint.setValue(accessPoint);
                                    selectedAccessPoint = accessPoint;

                                    btnUpdate.setVisible(true);
                                    btnCancel.setVisible(true);
                                    btnNew.setVisible(false);
                                }
                            });

                            return privilegesTm;
                        })
                        .collect(Collectors.toList())
        );

        tblPrivileges.setItems(accessPrivilegesTms);
    }

    private void setCheckBoxBasedOnCrud() {
        if (null != cmbAccessPoint.getValue()) {
            AccessPointDto selectedAccessPointDto = cmbAccessPoint.getSelectionModel().getSelectedItem();
            allPrivilegesByAccessPoint = accessPointCrudBo.findAllPrivilegesByAccessPoint(
                    selectedAccessPointDto.getPropertyId()
            );

            clearAll(selectedAccessPointDto);

            if (!allPrivilegesByAccessPoint.isEmpty()) {
                selectedAccessPoint = selectedAccessPointDto;

                btnUpdate.setVisible(true);
                btnCancel.setVisible(true);
                btnNew.setVisible(false);

                allPrivilegesByAccessPoint.forEach(accessPointCrudDto -> {
                    JFXCheckBox correspondingCheckbox = privilegesToCheckboxMap.get(accessPointCrudDto.getCrud());
                    if (correspondingCheckbox != null) {
                        correspondingCheckbox.setSelected(true);
                    }
                });
            }
        }
    }

    private void clearAll(AccessPointDto selectedAccessPoint) {
        btnNew.setVisible(true);
        btnCancel.setVisible(false);
        btnUpdate.setVisible(false);

        cmbAccessPoint.setValue(selectedAccessPoint);
        chkCreate.setSelected(false);
        chkRead.setSelected(false);
        chkUpdate.setSelected(false);
        chkDelete.setSelected(false);
        txtSearchText.clear();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) managePrivilegesContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        setUi("UserRoleAndAuthoritiesForm");
    }

    public void createPrivilegesOnAction(ActionEvent actionEvent) {
        AccessPointDto accessPointDto = cmbAccessPoint.getValue();
        if (null != accessPointDto) {
            List<AccessPointCrudDto> privileges = new ArrayList<>();

            privilegesToCheckboxMap.forEach((crudType, checkbox) -> {
                if (checkbox.isSelected()) {
                    privileges.add(new AccessPointCrudDto(KeyGenerator.generateId(), crudType, accessPointDto));
                }
            });

            accessPointCrudBo.setPrivileges(privileges);

            loadAllAccessPointsPrivileges();
            clearAll(null);
        }
    }

    public void updatePrivilegesOnAction(ActionEvent actionEvent) {
        AccessPointDto accessPointDto = cmbAccessPoint.getValue();
        if (null != selectedAccessPoint && selectedAccessPoint.equals(accessPointDto)) {
            Set<Crud> addPrivilegeSet = new HashSet<>();
            Set<Crud> removePrivilegeSet = new HashSet<>();


            privilegesToCheckboxMap.forEach((crudType, checkbox) -> {
                boolean foundInPreviousPrivileges = allPrivilegesByAccessPoint.stream()
                        .anyMatch(accessPointCrudDto -> crudType == accessPointCrudDto.getCrud());

                if (checkbox.isSelected()) {
                    if (!foundInPreviousPrivileges && !addPrivilegeSet.contains(crudType)) {
                        addPrivilegeSet.add(crudType);
                    }
                } else {
                    if (foundInPreviousPrivileges && !removePrivilegeSet.contains(crudType)) {
                        removePrivilegeSet.add(crudType);
                    }
                }
            });

            List<AccessPointCrudDto> addPrivileges = addPrivilegeSet.stream()
                    .map(crudType -> new AccessPointCrudDto(KeyGenerator.generateId(), crudType, accessPointDto))
                    .collect(Collectors.toList());

            List<AccessPointCrudDto> removePrivileges = removePrivilegeSet.stream()
                    .flatMap(crudType -> allPrivilegesByAccessPoint.stream().filter(accessPointCrudDto -> accessPointCrudDto.getCrud() == crudType))
                    .collect(Collectors.toList());

            if (!addPrivileges.isEmpty()) accessPointCrudBo.setPrivileges(addPrivileges);
            if (!removePrivileges.isEmpty()) accessPointCrudBo.dropPrivileges(removePrivileges);

            selectedAccessPoint = null;
            loadAllAccessPointsPrivileges();
        }

        clearAll(null);
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        clearAll(null);
    }
}
