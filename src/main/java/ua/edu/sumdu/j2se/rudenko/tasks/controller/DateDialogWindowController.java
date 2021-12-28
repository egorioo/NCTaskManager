package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.view.DateDialogWindowView;

import java.time.LocalDate;

public class DateDialogWindowController extends DateDialogWindowView {
    private static final Logger logger = Logger.getLogger(DateDialogWindowController.class);

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
        date = getSelectedDatePicker();
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
