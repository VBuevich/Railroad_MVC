package railroad.persistence.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="user")
public class User {
    private int userId;
    private String name;
    private String surname;
    private String email;
    private Date dob;
    private String password;
    private String passRecovery;
    private String userRole;
    private Boolean enabled;
    private Collection<Ticket> ticketsByUserId;
    private Collection<Seatmap> seatmapsByUserId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "dob")
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "pass_recovery")
    public String getPassRecovery() {
        return passRecovery;
    }

    public void setPassRecovery(String passRecovery) {
        this.passRecovery = passRecovery;
    }

    @Basic
    @Column(name = "user_role")
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Basic
    @Column(name = "enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (dob != null ? !dob.equals(user.dob) : user.dob != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (passRecovery != null ? !passRecovery.equals(user.passRecovery) : user.passRecovery != null) return false;
        if (userRole != null ? !userRole.equals(user.userRole) : user.userRole != null) return false;
        if (enabled != null ? !enabled.equals(user.enabled) : user.enabled != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (passRecovery != null ? passRecovery.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userByPassengerId")
    public Collection<Ticket> getTicketsByUserId() {
        return ticketsByUserId;
    }

    public void setTicketsByUserId(Collection<Ticket> ticketsByUserId) {
        this.ticketsByUserId = ticketsByUserId;
    }

    @OneToMany(mappedBy = "userByPassengerOwner")
    public Collection<Seatmap> getSeatmapsByUserId() {
        return seatmapsByUserId;
    }

    public void setSeatmapsByUserId(Collection<Seatmap> seatmapsByUserId) {
        this.seatmapsByUserId = seatmapsByUserId;
    }
}
