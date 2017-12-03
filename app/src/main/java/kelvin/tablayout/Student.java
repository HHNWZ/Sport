package kelvin.tablayout;

import java.io.Serializable;

/**
 * Created by CCDBA on 2017/12/2.
 */

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String emailId;

    private boolean isSelected;
    private int photo;

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public Student() {
        /* Empty Constructor */
    }

    public Student(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;

    }

    public Student(String name, String emailId,int photo,boolean isSelected) {
        this.name = name;
        this.emailId = emailId;
        this.photo=photo;
        this.isSelected = isSelected;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean isSelected) { this.isSelected = isSelected; }
}
