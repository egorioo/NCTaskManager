package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateIntervalDialogWindowController {
    @FXML
    private DatePicker startIntervalDatePicker;
    @FXML
    private DatePicker endIntervalDatePicker;
    @FXML
    private TextField startIntervalField;
    @FXML
    private TextField endIntervalField;

    private Stage stage;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public boolean okClicked = false;

    @FXML
    private void initialize() {
        startIntervalDatePicker.setValue(LocalDate.now());
        endIntervalDatePicker.setValue(LocalDate.now());
    }

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @FXML
    private void okDateIntervalButtonPressed() {
        if (validInput()) {
            startDateTime = startIntervalDatePicker.getValue().atTime(DateUtil.stringToTime(startIntervalField.getText()));
            endDateTime = endIntervalDatePicker.getValue().atTime(DateUtil.stringToTime(endIntervalField.getText()));
            okClicked = true;
            stage.close();
        }
    }

    @FXML
    private void cancelDateIntervalButtonPressed() {
        stage.close();
    }

    private boolean validInput() {
        String errorMessage = "";
        if (startIntervalDatePicker == null || startIntervalField.getText().length() == 0) {
            errorMessage += "No valid start time!\n";
        } else if (!DateUtil.validTime(startIntervalField.getText())) {
            errorMessage += "No valid start time! Use the format HH:mm:ss\n";
        }
        if (endIntervalDatePicker == null || endIntervalField.getText().length() == 0) {
            errorMessage += "No valid end time!\n";
        } else if (!DateUtil.validTime(endIntervalField.getText())) {
            errorMessage += "No valid end time! Use the format HH:mm:ss\n";
        }
        if (!startIntervalField.getText().equals("") && !endIntervalField.getText().equals("")) {
            if (startIntervalDatePicker.getValue().equals(endIntervalDatePicker.getValue())
                    && DateUtil.stringToTime(startIntervalField.getText()).isAfter(DateUtil.stringToTime(endIntervalField.getText()))) {
                errorMessage += "No valid start time and end time!\n";
            }
            if (startIntervalDatePicker.getValue().equals(endIntervalDatePicker.getValue())
                    && DateUtil.stringToTime(startIntervalField.getText()).equals(DateUtil.stringToTime(endIntervalField.getText()))) {
                errorMessage += "No valid start time and end time! Time can't be the same\n";
            }
            if (startIntervalDatePicker.getValue().isAfter(endIntervalDatePicker.getValue())) {
                errorMessage += "No valid start time and end time! \n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
