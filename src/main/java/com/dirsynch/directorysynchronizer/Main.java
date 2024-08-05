package com.dirsynch.directorysynchronizer;

import com.dirsynch.directorysynchronizer.di.context.ApplicationContext;
import com.dirsynch.directorysynchronizer.di.context.ApplicationContextConfigurator;
import com.dirsynch.directorysynchronizer.gui.MainService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 320);
        stage.setTitle("Synchronizer");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(el -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        ApplicationContext context = ApplicationContextConfigurator.run();
        MainService instance = MainService.getInstance();
        instance.setContext(context);
        instance.init();
        launch();
    }
}