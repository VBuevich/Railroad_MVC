package railroad.service;

import java.sql.Date;

/**
 * DTO
 *
 * @author vbuevich
 */
public class PassengerList {
    private String name;
    private String surname;
    private Date dob;
    private String seat;

    public PassengerList() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
