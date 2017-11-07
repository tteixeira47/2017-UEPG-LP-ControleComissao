package application;

import static config.Config.i18n;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Define a raiz do projeto e a fonte de internacionalização
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"), i18n);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.getIcons().add(new Image(("file:get-money.png")));
        stage.setTitle(i18n.getString("titulo.text"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
