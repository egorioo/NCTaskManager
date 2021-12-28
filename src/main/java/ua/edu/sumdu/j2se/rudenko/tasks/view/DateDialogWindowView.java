package ua.edu.sumdu.j2se.rudenko.tasks.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskOverviewController;
import ua.edu.sumdu.j2se.rudenko.tasks.services.StageManager;

import java.io.IOException;
import java.time.LocalDate;

public class DateDialogWindowView {
    @FXML
    private DatePicker dateSelectPickerDialog;

    public LocalDate getSelectedDatePicker() {
        return dateSelectPickerDialog.getValue();
    }

    public static FXMLLoader createDateWindow(Stage dateSelectStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DateDialogWindowView.class.getResource("/fxml/DateDialogWindow.fxml"));
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
}
