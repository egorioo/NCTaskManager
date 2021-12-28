package ua.edu.sumdu.j2se.rudenko.tasks.services;

import javafx.stage.Stage;

//Singleton pattern
public class StageManager {
    private static StageManager instance;
    private static Stage primaryStage;

    private StageManager() {
    }

    public synchronized static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        StageManager.primaryStage = primaryStage;
    }
}
