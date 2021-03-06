package controller;

import dbAccess.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;
import tools.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static model.Appointment.getTypeList;
import static tools.StaticObservableLists.*;
import static tools.TimeHelper.getETLocalClosingHours;
import static tools.TimeHelper.getETLocalOpeningHours;

/** This class is responsible for the functionality of the "Add Appt" controller. */
public class AddApptCtrl implements Initializable {
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private ComboBox<Customer> custIdCombo;
    @FXML
    private TextField descTxt;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private TextField locationTxt;
    @FXML
    private DatePicker datePkr;
    @FXML
    private ComboBox<LocalTime> startTimeCombo;
    @FXML
    private TextField titleTxt;
    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private ComboBox<User> userIdCombo;

    /** This method activates when the scene starts.
     *  @param url for initialization
     *  @param resourceBundle for initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIdCombo.setPromptText("User ID");
        contactCombo.setPromptText("Contact Name");
        typeCombo.setPromptText("Meeting Type");
        custIdCombo.setPromptText("Customer ID");
        startTimeCombo.setPromptText("First, Start Time");
        endTimeCombo.setPromptText("Then, End Time");
        datePkr.setPromptText("Appointment Date");
        userIdCombo.setItems(getUserList());
        contactCombo.setItems(getContactList());
        typeCombo.setItems(getTypeList());
        custIdCombo.setItems(getCustomerList());

        LocalTime start = getETLocalOpeningHours().toLocalTime();
        LocalTime end = getETLocalClosingHours().toLocalTime();

        while (start.isBefore(end.minusMinutes(15).plusSeconds(1))) {
            addStartTimeList(start);
            start = start.plusMinutes(15);
        }
        startTimeCombo.setItems(getStartTimeList());
    }

    /** This method activates when the Save button is clicked.
     *  The input in the text boxes and combo boxes will be validated and then saved to a new appointment.
     *  The appointment will be saved in the the database with Java based SQL methods.
     *  @param event object to trigger actions
     *  @throws IOException If an input or output exception occurred
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        System.out.println("Save button clicked!");

        String title = TextBoxEvent.validateString(titleTxt, "Title");
        String description = TextBoxEvent.validateString(descTxt, "Description");
        String location = TextBoxEvent.validateString(locationTxt, "Location");

        // Checks return values for each field to ensure they are valid
        if (title == null || description == null || location == null) {
            return;
        }

        if (userIdCombo.getValue() == null) {
            AlertEvent.alertBox("Error Dialog", "Please select a value for the User ID combo box.");
            return;
        }
        int userId = userIdCombo.getSelectionModel().getSelectedItem().getUserId();

        if (custIdCombo.getValue() == null) {
            AlertEvent.alertBox("Error Dialog", "Please select a value for the Customer ID combo box.");
            return;
        }
        int custId = custIdCombo.getSelectionModel().getSelectedItem().getCustomerId();

        if (contactCombo.getValue() == null) {
            AlertEvent.alertBox("Error Dialog", "Please select a value for the Contact combo box.");
            return;
        }
        int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();

        if (typeCombo.getValue() == null) {
            AlertEvent.alertBox("Error Dialog", "Please select a value for the Meeting Type combo box.");
            return;
        }
        String type = typeCombo.getSelectionModel().getSelectedItem();

        if (datePkr.getValue() == null) {
            AlertEvent.alertBox("Error Dialog", "Please select a value for the Date picker box.");
            return;
        }
        LocalDate date = datePkr.getValue();

        if (startTimeCombo.getValue() == null) {
            AlertEvent.alertBox("Error Dialog", "Please select a value for the Start Time combo box.");
            return;
        }
        LocalTime startTime = startTimeCombo.getSelectionModel().getSelectedItem();

        if (endTimeCombo.getValue() == null) {
            AlertEvent.alertBox("Error Dialog", "Please select a value for the End Time combo box.");
            return;
        }
        LocalTime endTime = endTimeCombo.getSelectionModel().getSelectedItem();

        LocalDateTime localStart = LocalDateTime.of(date, startTime);
        LocalDateTime localEnd = LocalDateTime.of(date, endTime);

        /*
        if (localStart.isBefore(TimeHelper.etLocalOpen) || localStart.isAfter(TimeHelper.etLocalClose)) {
            AlertEvent.alertBox("Error Dialog", "You are creating an appointment whose start time falls outside of the business hours of 8:00am to 10pm EST.");
            return;
        }

        if (localEnd.isBefore(TimeHelper.etLocalOpen) || localEnd.isAfter(TimeHelper.etLocalClose)) {
            AlertEvent.alertBox("Error Dialog", "You are creating an appointment whose end time falls outside of the business hours of 8:00am to 10pm EST.");
            return;
        }
         */

        clearSameCustApptList();
        for (Appointment a : getAppointmentList()) {
            if (a.getCustomerId() == custId) {
                setSameCustApptList(a);
            }
        }

        for (Appointment a : getSameCustApptList()) {
            if ((localStart.isAfter(a.getStart()) || localStart.isEqual(a.getStart())) && localStart.isBefore(a.getEnd())) {
                AlertEvent.alertBox("Error Dialog", "You are creating an appointment whose start time conflicts with an existing meeting for customer #" + custId + ".");
                return;
            }
            if (localEnd.isAfter(a.getStart()) && (localEnd.isBefore(a.getEnd()) || localEnd.isEqual(a.getEnd()))) {
                AlertEvent.alertBox("Error Dialog", "You are creating an appointment whose end time conflicts with an existing meeting for customer #" + custId + ".");
                return;
            }
            if ((localStart.isBefore(a.getStart()) || localStart.isEqual(a.getStart())) && (localEnd.isAfter(a.getEnd()) || localEnd.isEqual(a.getEnd()))) {
                AlertEvent.alertBox("Error Dialog", "You are creating an appointment whose start time and end time completely envelope an existing meeting for customer #" + custId + ".");
                return;
            }
        }

        Timestamp start = Timestamp.valueOf(localStart);
        Timestamp end = Timestamp.valueOf(localEnd);

        DBAppointments.addAppointment(title, description, location, type, start, end, custId, userId, contactId);
        ButtonEvent.buttonAction("/view/Appointments.fxml", "Appointment Table", event);
    }

    /** This method activates when the Cancel button is clicked.
     *  This will clear all input fields and go back to the Appointment controller.
     *  @param event object to trigger actions
     *  @throws IOException If an input or output exception occurred
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        ButtonEvent.cancelButtonAction("This will clear all field values, do you want to continue?", "Cancel button clicked", "/view/Appointments.fxml", "Appointments Table", event);
    }

    /** This method is responsible for setting the times in the End time combo box based on the time selected in the Start time combo box. */
    public void onActionStartTime() {
        LocalTime selectedStartTime = startTimeCombo.getSelectionModel().getSelectedItem();
        clearEndTimeList();

        LocalTime start = selectedStartTime.plusMinutes(15);
        LocalTime end = getETLocalClosingHours().toLocalTime();

        while (start.isBefore(end.plusSeconds(1))) {
            addEndTimeList(start);
            start = start.plusMinutes(15);
        }
        endTimeCombo.setItems(getEndTimeList());
        endTimeCombo.setValue(selectedStartTime.plusMinutes(15));
    }
}

