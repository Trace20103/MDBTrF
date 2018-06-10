package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Properties;

public class Main extends Application {
    public static String currentUserLogin;
    private static Stage window;

    private static String serverLogin;
    private static String serverPassword;

    /**
     * Basically only the start of the whole application except the connection test at the start
     **/
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("NLogin.fxml"));
        primaryStage.setTitle("MongoDB Trainer");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        /** Checking if the connection to the SQLServer is available to prevent a bunch of mistakes further on
         *  using the FileInputStream to get the login values**/
        SQLOperator sql = new SQLOperator();
        FileInputStream fis;
        Properties prop = new Properties();
        fis = new FileInputStream("src/sample/config/config.ini");
        prop.load(fis);
        serverLogin = prop.getProperty("SERVER_LOGIN");
        serverPassword = prop.getProperty("SERVER_PASSWORD");
        if (sql.testConnection(serverLogin, serverPassword) == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Connection error");
            alert.setContentText("Could not connect to the database. Please, wait until the servers respond");
            alert.showAndWait();
            primaryStage.close();
        }
    }


    /**
     * For the all exit buttons in all of the scenes (one)
     **/
    public static void terminateWindow() {
        window.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
