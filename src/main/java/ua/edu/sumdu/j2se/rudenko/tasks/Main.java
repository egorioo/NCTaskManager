package ua.edu.sumdu.j2se.rudenko.tasks;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskOverviewController;
import ua.edu.sumdu.j2se.rudenko.tasks.model.*;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;
import ua.edu.sumdu.j2se.rudenko.tasks.util.Notification;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class Main extends Application{
    private Stage primaryStage;
    private AbstractTaskList list = new ArrayTaskList();
    private ObservableList<Task> tasksData = FXCollections.observableArrayList();

    public Main() {
        TaskIO.readBinary(list,new File("data.txt"));
        for (Task task : list) {
            tasksData.add(task);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, AWTException  {
        this.primaryStage = stage;

        // instructs the javafx system not to exit implicitly when the last application window is shut.
        Platform.setImplicitExit(false);

        Notification notification = new Notification();
        notification.setMain(this);
        notification.createIcon();
        notification.enableNotifications();

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

    @Override
    public void stop() throws Exception {
        AbstractTaskList tempList = new ArrayTaskList();
        for (Task task : tasksData) {
            tempList.add(task);
        }
        TaskIO.writeBinary(tempList,new File("data.txt"));
        System.out.println("ending");
        super.stop();
    }

    public ObservableList<Task> getData() {
        return tasksData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}