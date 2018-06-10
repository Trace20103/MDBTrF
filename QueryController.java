package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class QueryController {
    public static int currentAsgID;

    private static String neededResult;

    @FXML
    private TextArea inputText;

    @FXML
    private TextArea outText;


    /**
     * Executing the right query from the MySQL DB just to get
     * the data that we need to look up to when we'll compare two queries
     **/
    @FXML
    public void initialize() throws SQLException, IOException {
        SQLOperator sql = new SQLOperator();
        MongoOperator mongo = new MongoOperator();
        this.neededResult = mongo.getQueryResult(sql.getAssignmentQuery(this.currentAsgID));
    }

    /**
     * This function gets the text in the output window more or less readable
     * Name was inspired by the mongo function pretty()
     * Simply puts /n before every record id and terminates the first /n
     * to make it look more comfortable to read
     **/
    private String pretty(String oldText) {
        String newText = oldText.replace("{ \"_id\"", "\n{ \"_id\"");
        if (!oldText.equals(newText)){
            char n = Character.MIN_VALUE;
            StringBuilder noSpace = new StringBuilder(newText);
            noSpace.setCharAt(0, n);
            newText = String.valueOf(noSpace);
        }
        newText = newText.replace("Type \"it\" for more", "");
        return newText;
    }

    /**
     * The way shell treats the incorrect data access from queries is
     * beautiful - it just returns an empty string. So a quick fix of the shell here again.
     * Other than that just casually comparing two query result to make sure the user got his
     * assignment right. Oh yeah, if the response from the server is not correct or null
     * all of the syntax or other mistakes will be put up to the out Text field.
     **/
    @FXML
    private void checkButtonPressed(ActionEvent event) throws IOException, SQLException {
        outText.setStyle("-fx-text-fill: #000000");
        String query = inputText.getText();
        String userResult;
        MongoOperator mongo = new MongoOperator();
        userResult = mongo.getQueryResult(query);
        if (userResult.length() > 0) {
            outText.setText(pretty(userResult));
        } else {
            outText.setText("No data found.");
            return;
        }
        if (userResult.equals(this.neededResult)) {
            SQLOperator sql = new SQLOperator();
            if (sql.checkIfAssignmentCompleted(this.currentAsgID, sql.getUserID(Main.currentUserLogin))) {
                outText.setStyle("-fx-text-fill: #00ff00");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание");
                alert.setHeaderText(null);
                alert.setContentText("Задание уже было выполнено вами ранее.");
                alert.showAndWait();
                Parent tablePage = FXMLLoader.load(getClass().getResource("Assignments.fxml"));
                Scene tableScene = new Scene(tablePage);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(tableScene);
                app_stage.show();
            } else {
                outText.setStyle("-fx-text-fill: #00ff00");
                sql.setUserAssignment(this.currentAsgID, sql.getUserID(Main.currentUserLogin));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Поздравляем");
                alert.setHeaderText(null);
                alert.setContentText("Задание выполнено!");
                alert.showAndWait();
                Parent tablePage = FXMLLoader.load(getClass().getResource("Assignments.fxml"));
                Scene tableScene = new Scene(tablePage);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(tableScene);
                app_stage.show();
            }
        }
    }

    /**
     * If the user for some reasons needs a reminder of his assignment.s
     **/
    @FXML
    private void remindButtonPressed(ActionEvent event) throws SQLException {
        SQLOperator sql = new SQLOperator();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Текст задания");
        alert.setHeaderText(null);
        alert.setContentText(sql.getAssignmentText(QueryController.currentAsgID));
        alert.showAndWait();
    }

    /**
     * Standard scene change procedure
     **/
    @FXML
    private void backButtonPressed(ActionEvent event) throws IOException {
        Parent tablePage = FXMLLoader.load(getClass().getResource("Assignments.fxml"));
        Scene tableScene = new Scene(tablePage);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(tableScene);
        app_stage.show();
    }
}
