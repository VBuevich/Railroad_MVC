package persistence.entity;

import javax.persistence.*;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="template_seats")
public class TemplateSeats {
    private int templateSeatsId;
    private String templateId;
    private String seat;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "Template_seats_id")
    public int getTemplateSeatsId() {
        return templateSeatsId;
    }

    public void setTemplateSeatsId(int templateSeatsId) {
        this.templateSeatsId = templateSeatsId;
    }

    @Basic
    @Column(name = "template_id")
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Basic
    @Column(name = "seat")
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateSeats that = (TemplateSeats) o;

        if (templateSeatsId != that.templateSeatsId) return false;
        if (templateId != null ? !templateId.equals(that.templateId) : that.templateId != null) return false;
        if (seat != null ? !seat.equals(that.seat) : that.seat != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = templateSeatsId;
        result = 31 * result + (templateId != null ? templateId.hashCode() : 0);
        result = 31 * result + (seat != null ? seat.hashCode() : 0);
        return result;
    }
}
