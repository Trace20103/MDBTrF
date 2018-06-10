package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class assignmentsAddController {
    @FXML
    protected TextField diffField;

    @FXML
    protected TextArea textField;

    @FXML
    protected TextField queryField;


    /**
     * Check for the spaces only vlues in the text fields
     **/
    private boolean checkForSpaces(String in) {
        char test;
        for (int i = 0; i < in.length(); i++) {
            test = in.charAt(i);
            if (!Character.toString(test).equals(" ")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Standard scene change procedure.
     * A lot of control just to make sure no one will break through :)
     **/
    @FXML
    public void addButtonPressed(ActionEvent event) throws SQLException, IOException {
        if (diffField.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, введите данные");
            alert.showAndWait();
            return;
        }
        if (!checkForSpaces(diffField.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Запись не может состоять из пробелов");
            alert.showAndWait();
            return;
        }
        if (textField.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, введите данные");
            alert.showAndWait();
            return;
        }
        if (!checkForSpaces(textField.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Запись не может состоять из пробелов");
            alert.showAndWait();
            return;
        }
        if (queryField.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, введите данные");
            alert.showAndWait();
            return;
        }
        if (!checkForSpaces(queryField.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Запись не может состоять из пробелов");
            alert.showAndWait();
            return;
        }
        SQLOperator sql = new SQLOperator();
        Alert confAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confAlert.setTitle("Подтвердите действие");
        confAlert.setHeaderText("Добавление задания");
        confAlert.setContentText("Вы точно хотите добавить задание с заданными параметрами?");
        Optional<ButtonType> result = confAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sql.addAssignment(sql.getUserID(Main.currentUserLogin), diffField.getText(), textField.getText(), queryField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Запись была успешно обновлена");
            alert.showAndWait();
            Parent tablePage = FXMLLoader.load(getClass().getResource("Assignments.fxml"));
            Scene tableScene = new Scene(tablePage);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(tableScene);
            app_stage.show();
        } else {
            return;
        }
    }

    /**
     * Standard scene change procedure.
     **/
    @FXML
    protected void backButtonPressed(ActionEvent event) throws IOException {
        Parent tablePage = FXMLLoader.load(getClass().getResource("Assignments.fxml"));
        Scene tableScene = new Scene(tablePage);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(tableScene);
        app_stage.show();
    }
}
