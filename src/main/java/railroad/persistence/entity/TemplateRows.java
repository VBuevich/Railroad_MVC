package railroad.persistence.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="template_rows")
public class TemplateRows implements Serializable {
    @NotNull
    private int templateRowsId;
    @NotEmpty @Size(min=1, max=3)
    private String templateId;
    @NotNull @Min(1)
    private int rowNumber;
    @NotEmpty @Size(min=1, max=10)
    private String rowSeats;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "Template_rows_id")
    public int getTemplateRowsId() {
        return templateRowsId;
    }

    public void setTemplateRowsId(int templateRowsId) {
        this.templateRowsId = templateRowsId;
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
    @Column(name = "row_number")
    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Basic
    @Column(name = "row_seats")
    public String getRowSeats() {
        return rowSeats;
    }

    public void setRowSeats(String rowSeats) {
        this.rowSeats = rowSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateRows that = (TemplateRows) o;

        if (templateRowsId != that.templateRowsId) return false;
        if (rowNumber != that.rowNumber) return false;
        if (templateId != null ? !templateId.equals(that.templateId) : that.templateId != null) return false;
        if (rowSeats != null ? !rowSeats.equals(that.rowSeats) : that.rowSeats != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = templateRowsId;
        result = 31 * result + (templateId != null ? templateId.hashCode() : 0);
        result = 31 * result + rowNumber;
        result = 31 * result + (rowSeats != null ? rowSeats.hashCode() : 0);
        return result;
    }
}
