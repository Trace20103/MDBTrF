package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class userInfoController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label daysLabel;

    @FXML
    private Label tasksLabel;

    @FXML
    private Button refButton;

    /**
     * To classify the user's role switch is used
     **/
    @FXML
    public void initialize() throws SQLException {
        SQLOperator sql = new SQLOperator();
        nameLabel.setText(sql.getUserName(Main.currentUserLogin));
        switch (sql.getUserRole(Main.currentUserLogin)) {
            case 0:
                roleLabel.setText("Студент");
                refButton.setVisible(false);
                break;

            case 1:
                roleLabel.setText("Учитель/редактор");
                break;

            case 2:
                roleLabel.setText("Администратор");
                break;
        }
        daysLabel.setText(Integer.toString(sql.getUsersDate(sql.getUserID(Main.currentUserLogin))));
        tasksLabel.setText(Integer.toString(sql.getUsersAssignments(sql.getUserID(Main.currentUserLogin))));
    }
    @FXML
    private void refreshDBButtonPressed(ActionEvent event) throws IOException {
        MongoOperator mongo = new MongoOperator();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Импорт данных");
        alert.setHeaderText(null);
        alert.setContentText("Импорт данных в БД фильмов может занять несколько минут. Пожалуйста, не закрывайте приложение во время импортирования.");
        alert.showAndWait();
        mongo.resetDB();
    }

    /**
     * Standard scene change procedure
     **/
    @FXML
    private void backButtonPressed(ActionEvent event) throws IOException {
        Parent tablePage = FXMLLoader.load(getClass().getResource("NMainMenu.fxml"));
        Scene tableScene = new Scene(tablePage);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(tableScene);
        app_stage.show();
    }
}
