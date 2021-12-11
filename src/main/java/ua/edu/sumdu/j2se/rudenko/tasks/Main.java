package ua.edu.sumdu.j2se.rudenko.tasks;
import com.google.gson.internal.bind.util.ISO8601Utils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskEditDialogController;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskOverviewController;
import ua.edu.sumdu.j2se.rudenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.rudenko.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main extends Application{

    private Stage primaryStage;

    private Task t1 = new Task("name1", LocalDateTime.now());
    private Task t2 = new Task("name2", LocalDateTime.now().plusDays(2));
    private Task t3 = new Task("name3", LocalDateTime.now().plusDays(5));
    private Task t4 = new Task("name4", LocalDateTime.now(),LocalDateTime.now().plusDays(2),1000);

    private AbstractTaskList list = new LinkedTaskList();
    private ObservableList<Task> tasksData = FXCollections.observableArrayList();

    /**
     * Конструктор
     */
    public Main() {
        // В качестве образца добавляем некоторые данные
        list.add(t1);
        list.add(t3);
        list.add(t2);
        list.add(t4);

        tasksData.add(t1);
        tasksData.add(t3);
        tasksData.add(t2);
        tasksData.add(t4);

    }

    public static void main(String[] args) {

        Application.launch(args);

    }

    public ObservableList<Task> getData() {
        return tasksData;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/view/TaskOverview.fxml"));
        Parent root = loader.load();
        TaskOverviewController controller = loader.getController();

        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Hello JavaFX");
        stage.setWidth(600);
        stage.setHeight(400);

        stage.show();
        System.out.println(controller);
        DateUtil date = new DateUtil();
        System.out.println(date.dateToString(LocalDateTime.now()));
        controller.setMainApp(this);
    }

    public boolean showTaskEditDialog(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TaskEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TaskEditDialogController controller = loader.getController();
            controller.showCurrentTask(task);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.okClicked;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}