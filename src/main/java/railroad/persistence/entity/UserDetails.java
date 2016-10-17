package railroad.persistence.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="user")
public class UserDetails {
    @NotNull
    private int userId;
    @NotEmpty @Size(min=2, max=20)
    private String name;
    @NotEmpty @Size(min=2, max=20)
    private String surname;
    @NotEmpty @Email @Size(min=5, max=40)
    private String email;
    @NotNull @DateTimeFormat(pattern="yyyy-dd-MM") @Past
    private Date dob;
    @NotEmpty @Size(min=8, max=100) // SHA1 Generated, length == 40
    private String password;
    @NotEmpty @Size(min=6, max=100)
    private String passRecovery;
    @NotEmpty @Size(min=1, max=10)
    private String userRole;
    @NotNull
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

        UserDetails userDetails = (UserDetails) o;

        if (userId != userDetails.userId) return false;
        if (name != null ? !name.equals(userDetails.name) : userDetails.name != null) return false;
        if (surname != null ? !surname.equals(userDetails.surname) : userDetails.surname != null) return false;
        if (email != null ? !email.equals(userDetails.email) : userDetails.email != null) return false;
        if (dob != null ? !dob.equals(userDetails.dob) : userDetails.dob != null) return false;
        if (password != null ? !password.equals(userDetails.password) : userDetails.password != null) return false;
        if (passRecovery != null ? !passRecovery.equals(userDetails.passRecovery) : userDetails.passRecovery != null) return false;
        if (userRole != null ? !userRole.equals(userDetails.userRole) : userDetails.userRole != null) return false;
        if (enabled != null ? !enabled.equals(userDetails.enabled) : userDetails.enabled != null) return false;

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

    @OneToMany(mappedBy = "userDetailsByPassengerId")
    public Collection<Ticket> getTicketsByUserId() {
        return ticketsByUserId;
    }

    public void setTicketsByUserId(Collection<Ticket> ticketsByUserId) {
        this.ticketsByUserId = ticketsByUserId;
    }

    @OneToMany(mappedBy = "userDetailsByPassengerOwner")
    public Collection<Seatmap> getSeatmapsByUserId() {
        return seatmapsByUserId;
    }

    public void setSeatmapsByUserId(Collection<Seatmap> seatmapsByUserId) {
        this.seatmapsByUserId = seatmapsByUserId;
    }
}
