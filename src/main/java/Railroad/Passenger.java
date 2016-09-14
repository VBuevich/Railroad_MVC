package Railroad;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
public class Passenger {
    private Integer passengerId;
    private String name;
    private String surname;
    private Date dob;
    private String email;
    private String password;
    private Collection<Ticket> ticketsByPassengerId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "passenger_id", nullable = false)
    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 20)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "dob", nullable = false)
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 40)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param o Object to compare
     * @return true if equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (passengerId != null ? !passengerId.equals(passenger.passengerId) : passenger.passengerId != null)
            return false;
        if (name != null ? !name.equals(passenger.name) : passenger.name != null) return false;
        if (surname != null ? !surname.equals(passenger.surname) : passenger.surname != null) return false;
        if (dob != null ? !dob.equals(passenger.dob) : passenger.dob != null) return false;
        if (email != null ? !email.equals(passenger.email) : passenger.email != null) return false;
        if (password != null ? !password.equals(passenger.password) : passenger.password != null) return false;

        return true;
    }

    /**
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = passengerId != null ? passengerId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "passengerByPassengerId")
    public Collection<Ticket> getTicketsByPassengerId() {
        return ticketsByPassengerId;
    }

    public void setTicketsByPassengerId(Collection<Ticket> ticketsByPassengerId) {
        this.ticketsByPassengerId = ticketsByPassengerId;
    }
}
