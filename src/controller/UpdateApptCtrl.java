package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import tools.GUIEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateApptCtrl implements Initializable {

    @FXML
    private AnchorPane addAppt;
    @FXML
    private Label updateApptLbl;
    @FXML
    private Button cancelBtn;
    @FXML
    private ComboBox<?> contactCombo;
    @FXML
    private Label contactLbl;
    @FXML
    private ComboBox<?> custIdCombo;
    @FXML
    private Label descLbl;
    @FXML
    private TextField descTxt;
    @FXML
    private DatePicker endDatePkr;
    @FXML
    private ComboBox<?> endTimeCombo;
    @FXML
    private Label idLbl;
    @FXML
    private TextField idTxt;
    @FXML
    private Label locationLbl;
    @FXML
    private TextField locationTxt;
    @FXML
    private Label minLbl;
    @FXML
    private Button saveBtn;
    @FXML
    private DatePicker startDatePkr;
    @FXML
    private ComboBox<?> startTimeCombo;
    @FXML
    private Label titleLbl;
    @FXML
    private TextField titleTxt;
    @FXML
    private ComboBox<?> typeCombo;
    @FXML
    private Label typeLbl;
    @FXML
    private ComboBox<?> userIdCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        GUIEvent.cancelButtonAction("None of your changes will be saved, do you want to continue?", "Cancel button clicked", "/view/Customers.fxml", "Customer Appointment Form",event);
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        System.out.println("Save button clicked!");
        GUIEvent.buttonAction("/view/Customers.fxml", "Customer Appointment Form", event);
    }
}
