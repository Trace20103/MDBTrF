package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MMController {

    /**
     * Exiting the program after the exit button is pressed
     **/
    @FXML
    protected void exitButtonClicked() {
        Main.terminateWindow();
    }

    /**
     * Standard scene change procedure.
     * Giving user the info after the button is pressed
     **/
    @FXML
    protected void profileButtonClicked(javafx.event.ActionEvent event) throws IOException {
        Parent tablePage = FXMLLoader.load(getClass().getResource("userInfo.fxml"));
        Scene tableScene = new Scene(tablePage);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(tableScene);
        app_stage.show();
    }

    /**
     * Standard scene change procedure
     **/
    public void assignmentsButtonClicked(javafx.event.ActionEvent event) throws IOException {
        Parent tablePage = FXMLLoader.load(getClass().getResource("Assignments.fxml"));
        Scene tableScene = new Scene(tablePage);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(tableScene);
        app_stage.show();
    }
}