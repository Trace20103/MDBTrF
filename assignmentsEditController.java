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

public class assignmentsEditController {

    /**
     * Just to make sure we're doing the right thing here
     **/
    public static int currentAsgID;

    @FXML
    protected TextField diffField;

    @FXML
    protected TextArea textField;

    @FXML
    protected TextField queryField;

    /**
     * Filling the text edits with the previous info
     **/
    @FXML
    public void initialize() throws SQLException {
        SQLOperator sql = new SQLOperator();
        diffField.setText(sql.getAssignmentDifficulty(this.currentAsgID));
        textField.setText(sql.getAssignmentText(this.currentAsgID));
        queryField.setText(sql.getAssignmentQuery(this.currentAsgID));
    }

    /**
     * Check for the spaces only vlues in the text fields
     **/
    public boolean checkForSpaces(String in) {
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
     * A lot of checks as well. Just to make sure :)
     * Using the SQL update procedure here
     **/
    @FXML
    protected void editButtonPressed(ActionEvent event) throws SQLException, IOException {
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
        confAlert.setHeaderText("Обновление задания");
        confAlert.setContentText("Вы точно хотите обновить задание по заданным параметрам?");
        Optional<ButtonType> result = confAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sql.updateAssignment(this.currentAsgID, diffField.getText(), textField.getText(), queryField.getText());
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
     * First have to check the rights to do so.
     * Only the administrator has the right to delete an assignment
     * Then using SQL query as well
     **/
    @FXML
    protected void deleteButtonPressed(ActionEvent event) throws SQLException, IOException {
        SQLOperator sql = new SQLOperator();
        if (sql.getUserRole(Main.currentUserLogin) < 2) {
            Alert dAlert = new Alert(Alert.AlertType.INFORMATION);
            dAlert.setTitle("Ошбика");
            dAlert.setHeaderText(null);
            dAlert.setContentText("У вас недостаточно прав для удаления задания!");
            dAlert.showAndWait();
            return;
        }
        Alert confAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confAlert.setTitle("Подтвердите действие");
        confAlert.setHeaderText("Удаление задания");
        confAlert.setContentText("Вы точно хотите удалить данное задание?");
        Optional<ButtonType> result = confAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sql.dropAssignment(this.currentAsgID);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Запись была успешно удалена");
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
     * Standard scene change procedure
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

