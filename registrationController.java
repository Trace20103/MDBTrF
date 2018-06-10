package sample;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class registrationController {

    @FXML
    private TextField nameText;

    @FXML
    private TextField loginText;

    @FXML
    private PasswordField passwordText;

    private int maxLength = 30;

    /**
     * If someone would try to enter only the spaces in some field :)
     **/
    private boolean checkNulls(String str) {
        if (str.length() < 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка регистрации");
            alert.setContentText("Вы оставили некоторые поля незаполненными. Пожалуйста, заполните их.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean checkForSpaces(String in) {
        char test;
        for (int i = 0; i < in.length(); i++) {
            test = in.charAt(i);
            if (!Character.toString(test).equals(" ")) {
                return true;
            }
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка регистрации");
        alert.setContentText("В некоторых полях вы ввели одни пробелы. Пожалуйста, заполните эти поля правильными данными.");
        alert.showAndWait();
        return false;
    }

    /**
     * Just the registration procedure. A lot of checks. Just in case :)
     **/
    @FXML
    protected void SubmitButtonPressed(ActionEvent event) throws IOException, SQLException {
        if (!checkNulls(nameText.getText())||!checkForSpaces(nameText.getText())) {
            return;
        }
        if (!checkNulls(loginText.getText())||!checkForSpaces(loginText.getText())) {
            return;
        }
        if (!checkNulls(passwordText.getText())||!checkForSpaces(passwordText.getText())) {
            return;
        }
        if (nameText.getLength() > maxLength) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка регистрации");
            alert.setContentText("Вы ввели слишком много символов в поле имени. Пожалуйста, сократите введенные данные.");
            alert.showAndWait();
            return;
        }
        if (loginText.getLength() > maxLength) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ощибка");
            alert.setHeaderText("Ошибка регистрации");
            alert.setContentText("Вы ввели слишком много символов в поле логина. Пожалуйста, сократите введенные данные.");
            alert.showAndWait();
            return;
        }
        if(passwordText.getLength() < 4){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка регистрации");
            alert.setContentText("Пароль должен состоять минимум из 4 символов. Введите новый пароль.");
            alert.showAndWait();
            return;
        }
        if (passwordText.getLength() > maxLength) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка регистрации");
            alert.setContentText("Вы ввели слишком много символов в поле пароля. Пожалуйста, сократите введенные данные.");
            alert.showAndWait();
            return;
        }
        SQLOperator sql = new SQLOperator();
         if   (sql.addUserToDB(nameText.getText(), loginText.getText(), passwordText.getText()) == 0) {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Готово");
             alert.setHeaderText("Регистрация прошла успешно");
             alert.setContentText("Спасибо за регистрацию, теперь вы можете авторизоваться под своим новым аккаунтом");
             alert.showAndWait();
             Parent tablePage = FXMLLoader.load(getClass().getResource("NLogin.fxml"));
             Scene tableScene = new Scene(tablePage);
             Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             app_stage.setScene(tableScene);
             app_stage.show();
         }
    }

    /**
     * Standard scene change procedure
     **/
    @FXML
    protected void BackButtonPressed(ActionEvent event) throws Exception {
        Parent tablePage = FXMLLoader.load(getClass().getResource("NLogin.fxml"));
        Scene tableScene = new Scene(tablePage);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(tableScene);
        app_stage.show();
    }
}
