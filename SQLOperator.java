package sample;

import javafx.scene.control.Alert;

import java.sql.*;

/**
 * Almost every function needs to access the DB. So that class allows to do that more or less painfully
 * Lots of procedures and functions here. But most of them are simple.
 **/
class SQLOperator {
    private static String loginR;
    private static String passwordR;

    /**
     * Just to make sure servers are responding or login info is correct.
     * btw login config file is located /src/sample/config
     **/
    int testConnection(String userLogin, String userPassword) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + userLogin + "&" + "password=" + userPassword + "&serverTimezone=UTC");
            loginR = userLogin;
            passwordR = userPassword;
            connection.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Standard procedure call after the connection to the DB
     **/
    void addAssignment(int UID, String uDiff, String uText, String uQuery) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("CALL `addAssignment`(?, ?, ?, ?)");
        statementP.setInt(1, UID);
        statementP.setString(2, uDiff);
        statementP.setString(3, uText);
        statementP.setString(4, uQuery);
        statementP.execute();
        connection.close();
    }


    void updateAssignment(int AID, String nDiff, String nText, String nQuery) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("CALL `updateAssignment`(?, ?, ?, ?)");
        statementP.setInt(1, AID);
        statementP.setString(2, nDiff);
        statementP.setString(3, nText);
        statementP.setString(4, nQuery);
        statementP.execute();
        connection.close();
    }

    String getAssignmentDifficulty(int AID) throws SQLException {
        String res = "";
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("SELECT asg_Diff FROM assignments WHERE asg_ID = ?");
        statementP.setInt(1, AID);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getString(1);
        }
        connection.close();
        return res;
    }

    void dropAssignment(int AID) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("DELETE FROM assignments WHERE asg_ID = ?");
        statementP.setInt(1, AID);
        statementP.execute();
        connection.close();
    }

    void setUserAssignment(int AID, int UID) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("CALL `setUsersAssignment`(? , ?)");
        statementP.setInt(1, AID);
        statementP.setInt(2, UID);
        statementP.execute();
        connection.close();
    }

    boolean checkIfAssignmentCompleted(int AID, int UID) throws SQLException {
        int res = -1;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("SELECT Count(*) FROM usersassignments WHERE asg_ID = ? AND user_ID = ?");
        statementP.setInt(1, AID);
        statementP.setInt(2, UID);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getInt(1);
        }
        connection.close();
        if (res == 0) {
            return false;
        } else {
            return true;
        }
    }

    String getAssignmentText(int asgID) throws SQLException {
        String res = "";
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("CALL `getAssignmentText`(?)");
        statementP.setInt(1, asgID);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getString(1);
        }
        connection.close();
        return res;
    }

    String getAssignmentQuery(int asgID) throws SQLException {
        String res = "";
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("SELECT asg_Query FROM assignments WHERE asg_ID = ?");
        statementP.setInt(1, asgID);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getString(1);
        }
        connection.close();
        return res;
    }

    int getUsersDate(int userID) throws SQLException {
        int res = 0;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("CALL `getUsersDate`(?)");
        statementP.setInt(1, userID);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getInt(1);
        }
        connection.close();
        return res;
    }

    ResultSet getAllAssignmetns() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("CALL `getAllAssignments`()");
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        return resultSet;
    }

    int getAssignmentID(String asgText1) throws SQLException {
        int res = 0;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("CALL `getAssignmentID`(?)");
        statementP.setString(1, asgText1);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getInt(1);
        }
        connection.close();
        return res;
    }

    int  addUserToDB(String UName, String ULogin, String UPassword) throws SQLException {
        int res = -1;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC&characterEncoding=UTF-8");
        PreparedStatement statementP = connection.prepareStatement("SELECT User_ID FROM users WHERE User_Login = ?");
        statementP.setString(1 ,ULogin);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getInt(1);
        }
        if(res != -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка регистрации");
            alert.setContentText("Пользователь с таким логином уже существует. Пожалуйста, измените логин.");
            alert.showAndWait();
            return -1;
        }
        else {
            statementP = connection.prepareStatement("CALL `AddDBUser`(0, ?, ?, ?, 0)");
            statementP.setString(1, UName);
            statementP.setString(2, ULogin);
            statementP.setString(3, UPassword);
            statementP.execute();
            connection.close();
            return 0;
        }
    }

    int getUsersAssignments(int userID) throws SQLException {
        int res = -1;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("CALL `getUsersAssignmentsCount`(?)");
        statementP.setInt(1, userID);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getInt(2);
        }
        connection.close();
        return res;
    }

    int checkLogin(String userLogin, String userPassword) throws SQLException {
        int res = -1;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("SELECT User_ID FROM users WHERE User_Login = ? AND User_Password = password(?)");
        statementP.setString(1, userLogin);
        statementP.setString(2, userPassword);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getInt(1);
        }
        connection.close();
        return res;
    }

    int getUserID(String userLogin) throws SQLException {
        int res = -1;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("SELECT User_ID FROM users WHERE User_Login = ?");
        statementP.setString(1, userLogin);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getInt(1);
        }
        connection.close();
        return res;
    }

    int getUserRole(String userLogin) throws SQLException {
        int res = -1;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("SELECT User_Role FROM users WHERE User_Login = ?");
        statementP.setString(1, userLogin);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getInt(1);
        }
        return res;
    }

    String getUserName(String userLogin) throws SQLException {
        String res = "-1";
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mongot?user=" + loginR + "&" + "password=" + passwordR + "&serverTimezone=UTC");
        PreparedStatement statementP = connection.prepareStatement("SELECT User_Name FROM users WHERE User_Login = ?");
        statementP.setString(1, userLogin);
        statementP.execute();
        ResultSet resultSet = statementP.getResultSet();
        while (resultSet.next()) {
            res = resultSet.getString(1);
        }
        return res;
    }
    /** In case you were wondering why somewhere there is CALL procedure and somewhere is pure query
     I can say that some queries just to simple to put a procedure to the DB just for them. It's quite easier to
     use the query without calling a procedure. In case injections all of the queries are treated through
     prepared statement and parameters could not be so easily modified. Sorry for not commenting all of the
     functions. I guess all of them are kinda obvious if you look inside the function or judging buy the name of the function
     * **/

}
