package ua.edu.sumdu.j2se.rudenko.tasks.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskOverviewController;
import ua.edu.sumdu.j2se.rudenko.tasks.services.StageManager;

import java.io.IOException;
import java.time.LocalDate;

public class DateIntervalDialogWindowView {
    @FXML
    protected DatePicker startIntervalDatePicker;
    @FXML
    protected DatePicker endIntervalDatePicker;
    @FXML
    private TextField startIntervalField;
    @FXML
    private TextField endIntervalField;

    public static FXMLLoader createDateIntervalWindow(Stage dateSelectStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DateIntervalDialogWindowView.class.getResource("/fxml/DateIntervalDialogWindow.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        dateSelectStage.setTitle("");
        dateSelectStage.initModality(Modality.WINDOW_MODAL);
        dateSelectStage.initOwner(StageManager.getInstance().getPrimaryStage());
        Scene scene = new Scene(page);
        dateSelectStage.setScene(scene);
        dateSelectStage.setResizable(false);
        dateSelectStage.getIcons().add(new Image(TaskOverviewController.class.getResourceAsStream("/images/icon24px.png")));
        return loader;
    }

    public void displayStartIntervalDatePicker(LocalDate start) {
        startIntervalDatePicker.setValue(start);
    }

    public void displayEndIntervalDatePicker(LocalDate end) {
        endIntervalDatePicker.setValue(end);
    }

    public LocalDate getStartIntervalDatePicker() {
        return startIntervalDatePicker.getValue();
    }

    public LocalDate getEndIntervalDatePicker() {
        return endIntervalDatePicker.getValue();
    }

    public String getStartHoursIntervalField() {
        return startIntervalField.getText();
    }

    public String getEndHoursIntervalField() {
        return endIntervalField.getText();
    }
}
