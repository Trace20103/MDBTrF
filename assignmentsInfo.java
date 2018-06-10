package sample;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class is here because the table won't work without it properly.
 **/
public class assignmentsInfo {
    private final StringProperty asg_Diff;
    private final StringProperty User_Name;
    private final StringProperty asg_Text;

    public assignmentsInfo(String asg_Diff, String User_name, String asg_Text) {
        this.asg_Diff = new SimpleStringProperty(asg_Diff);
        this.User_Name = new SimpleStringProperty(User_name);
        this.asg_Text = new SimpleStringProperty(asg_Text);
    }

    /**
     * Getters and setters here. Never use them but still
     * they are somehow crucial to the table functioning
     **/
    public String getDiff() {
        return asg_Diff.get();
    }

    public String getUName() {
        return User_Name.get();
    }

    public String getAsg() {
        return asg_Text.get();
    }

    public void setAsg_Diff(String value) {
        asg_Diff.set(value);
    }

    public void setUName(String value) {
        User_Name.set(value);
    }

    public void setText(String value) {
        asg_Text.set(value);
    }

    /**
     * All that is used from this class, basically
     **/
    public StringProperty asg_DiffProperty() {
        return asg_Diff;
    }

    public StringProperty User_NameProperty() {
        return User_Name;
    }

    public StringProperty asg_TextProperty() {
        return asg_Text;
    }

}
