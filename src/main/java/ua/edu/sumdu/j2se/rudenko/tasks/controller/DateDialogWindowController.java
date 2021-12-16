package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.time.LocalDate;

public class DateDialogWindowController {
    private static final Logger logger = Logger.getLogger(DateDialogWindowController.class);
    @FXML
    private DatePicker dateSelectPickerDialog;
    private Stage stage;
    private LocalDate date;

    public boolean okClicked;

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    public LocalDate getDate() {
        return date;
    }

    @FXML
    private void okDateButtonDialog() {
        logger.debug("button OK pressed");
        date = dateSelectPickerDialog.getValue();
        okClicked = true;
        stage.close();
        logger.debug("tasks filtered");
    }

    @FXML
    private void cancelDateButtonDialog() {
        stage.close();
        logger.debug("button cancel pressed");
    }
}
