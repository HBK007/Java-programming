/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Nguyen Duc Hai
 */
public class User {
    private String userName;
    private String ID;
    private String address;
    private String numberPhone;
    private String email;
    private String birthday;
    private float totalPoint;

    public User() {
    }

    public User(String userName, String ID, String address, String numberPhone, String email, String birthday, float totalPoint) {
        this.userName = userName;
        this.ID = ID;
        this.address = address;
        this.numberPhone = numberPhone;
        this.email = email;
        this.birthday = birthday;
        this.totalPoint = totalPoint;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public float getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(float totalPoint) {
        this.totalPoint = totalPoint;
    }

    
   
}
