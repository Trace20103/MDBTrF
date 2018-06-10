package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class assignmentsController {

    @FXML
    private TableView<assignmentsInfo> tableAsg;

    @FXML
    private TableColumn<assignmentsInfo, String> columnLevel;

    @FXML
    private TableColumn<assignmentsInfo, String> columnAuthor;

    @FXML
    private TableColumn<assignmentsInfo, String> columnAssignment;

    private ObservableList<assignmentsInfo> data;

    /**
     * Filling the table with the info; have to admit
     * that was kinda hard to implement but it works
     **/
    @FXML
    public void initialize() throws SQLException {
        SQLOperator sql = new SQLOperator();
        data = FXCollections.observableArrayList();
        ResultSet rs = sql.getAllAssignmetns();
        try {
            rs = sql.getAllAssignmetns();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            data.add(new assignmentsInfo(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        tableAsg.selectionModelProperty();
        columnLevel.setCellValueFactory(new PropertyValueFactory<>("asg_Diff"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("User_Name"));
        columnAssignment.setCellValueFactory(new PropertyValueFactory<>("asg_Text"));
        tableAsg.setItems(null);
        tableAsg.setItems(data);
    }

    /**
     * Standard scene change procedure.
     * But with the value of the chosen task ID
     * delivered to the query controller class for further usage
     **/
    @FXML
    protected void CompleteButtonPressed(ActionEvent event) throws Exception {
        int selectedRow;
        String asgText;
        selectedRow = tableAsg.getSelectionModel().getFocusedIndex();
        asgText = tableAsg.getColumns().get(2).getCellObservableValue(selectedRow).getValue().toString();
        SQLOperator sql = new SQLOperator();
        QueryController.currentAsgID = sql.getAssignmentID(asgText);
        Parent tablePage = FXMLLoader.load(getClass().getResource("Query.fxml"));
        Scene tableScene = new Scene(tablePage);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(tableScene);
        app_stage.show();
    }

    /**
     * Standard scene change procedure.
     * But this time have to check the user's rights inside
     * of the system for him to be able to edit assignments
     **/
    @FXML
    protected void AddButtonPressed(ActionEvent event) throws IOException, SQLException {
        SQLOperator sql = new SQLOperator();
        if (sql.getUserRole(Main.currentUserLogin) < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошбика");
            alert.setHeaderText(null);
            alert.setContentText("У вас недостаточно прав для добавления заданий");
            alert.showAndWait();
            return;
        } else {
            Parent tablePage = FXMLLoader.load(getClass().getResource("assignmentsAdd.fxml"));
            Scene tableScene = new Scene(tablePage);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(tableScene);
            app_stage.show();
        }
    }


    /**
     * Standard change of the scene procedure.
     * Have to check the user's rights fot the editing
     * and make sure we have the assignment ID ready
     **/
    @FXML
    protected void EditButtonPressed(ActionEvent event) throws Exception {
        SQLOperator sql = new SQLOperator();
        if (sql.getUserRole(Main.currentUserLogin) < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошбика");
            alert.setHeaderText(null);
            alert.setContentText("У вас недостаточно прав для редактирования заданий");
            alert.showAndWait();
            return;
        } else {
            int selectedRow;
            String asgText;
            selectedRow = tableAsg.getSelectionModel().getFocusedIndex();
            asgText = tableAsg.getColumns().get(2).getCellObservableValue(selectedRow).getValue().toString();
            assignmentsEditController.currentAsgID = sql.getAssignmentID(asgText);
            Parent tablePage = FXMLLoader.load(getClass().getResource("assignmentsEdit.fxml"));
            Scene tableScene = new Scene(tablePage);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(tableScene);
            app_stage.show();
        }
    }

    /**
     * Standard scene change procedure.
     **/
    @FXML
    protected void BackButtonPressed(ActionEvent event) throws Exception {
        Parent tablePage = FXMLLoader.load(getClass().getResource("NMainMenu.fxml"));
        Scene tableScene = new Scene(tablePage);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(tableScene);
        app_stage.show();
    }
}
