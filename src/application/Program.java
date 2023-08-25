package application;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Program extends javafx.application.Application {

    private static Scene mainScene;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public static Scene getMainScene(){
        return mainScene;
    }

    @Override
    public void start(Stage stage){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane scrollPane = loader.load();
            mainScene = new Scene(scrollPane);
            stage.setScene(mainScene);
            stage.setTitle("Java Chess");
            stage.show();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
