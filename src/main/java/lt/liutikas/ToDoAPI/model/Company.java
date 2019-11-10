package lt.liutikas.ToDoAPI.model;

import javax.persistence.Entity;

@Entity
public class Company extends User {
    public String name;
    public String contactPersonPhone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }
}
