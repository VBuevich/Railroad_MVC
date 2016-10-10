package persistence.entity;

import javax.persistence.*;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="employee")
public class Employee {
    private Integer employeeId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String passRecovery;
    private String Seatrow;

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "employee_id", nullable = false)
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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
    @Column(name = "email", nullable = false, length = 40)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "pass_recovery", nullable = false, length = 100, insertable = false, updatable = false) // IU)
    public String getPassRecovery() { return passRecovery; }

    public void setPassRecovery(String passRecovery) { this.passRecovery = passRecovery; }

    /**
     *
     * @param o Object to compare
     * @return true if equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (employeeId != null ? !employeeId.equals(employee.employeeId) : employee.employeeId != null) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;
        if (surname != null ? !surname.equals(employee.surname) : employee.surname != null) return false;
        if (email != null ? !email.equals(employee.email) : employee.email != null) return false;
        if (password != null ? !password.equals(employee.password) : employee.password != null) return false;
        if (passRecovery != null ? !passRecovery.equals(employee.passRecovery) : employee.passRecovery != null) return false;

        return true;
    }

    /**
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = employeeId != null ? employeeId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (passRecovery != null ? passRecovery.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "pass_recovery")
    public String getSeatrow() {
        return Seatrow;
    }

    public void setSeatrow(String seatrow) {
        Seatrow = seatrow;
    }
}
