package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Principal.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (Exception e){
            System.out.println("Erro ao iniciar Main");
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
