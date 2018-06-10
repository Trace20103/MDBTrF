package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {
    @FXML
    private TextField loginInfo;

    @FXML
    private PasswordField passInfo;

    /**
     * Here the typed info is checked. And in the case or right login and password the new scene will pop out
     **/
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        SQLOperator sql = new SQLOperator();
        if (sql.checkLogin(loginInfo.getText(), passInfo.getText()) != -1) {
            Main.currentUserLogin = loginInfo.getText();
            Parent mainMenuPage = FXMLLoader.load(getClass().getResource("NMainMenu.fxml"));
            Scene mainMenuScene = new Scene(mainMenuPage);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainMenuScene);
            app_stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка авторизации");
            alert.setContentText("Неверный логин или пароль. Пожалуйста, попытайтесь ещё раз.");
            alert.showAndWait();
        }
    }

    /**
     * Standard scene change procedure
     **/
    @FXML
    protected void handleRegisterButtonAction(ActionEvent event) throws Exception {
        Parent mainMenuPage = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene mainMenuScene = new Scene(mainMenuPage);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainMenuScene);
        app_stage.show();
    }
}


