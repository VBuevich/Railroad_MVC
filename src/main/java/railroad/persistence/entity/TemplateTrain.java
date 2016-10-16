package railroad.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name="template_train")
public class TemplateTrain implements Serializable {
    private int templateTrainId;
    private String templateId;
    private Train trainByTemplateId;
    private TemplateRows templateRowsByTemplateId;
    private TemplateSeats templateSeatsByTemplateId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // SEQUENCE
    @Column(name = "template_train_id")
    public int getTemplateTrainId() {
        return templateTrainId;
    }

    public void setTemplateTrainId(int templateTrainId) {
        this.templateTrainId = templateTrainId;
    }

    @Basic
    @Column(name = "template_id")
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateTrain that = (TemplateTrain) o;

        if (templateTrainId != that.templateTrainId) return false;
        if (templateId != null ? !templateId.equals(that.templateId) : that.templateId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = templateTrainId;
        result = 31 * result + (templateId != null ? templateId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "template_id", referencedColumnName = "template_id", insertable = false, updatable = false) // IU
    public Train getTrainByTemplateId() {
        return trainByTemplateId;
    }

    public void setTrainByTemplateId(Train trainByTemplateId) {
        this.trainByTemplateId = trainByTemplateId;
    }

    @ManyToOne
    @JoinColumn(name = "template_id", referencedColumnName = "template_id", insertable = false, updatable = false) // IU
    public TemplateRows getTemplateRowsByTemplateId() {
        return templateRowsByTemplateId;
    }

    public void setTemplateRowsByTemplateId(TemplateRows templateRowsByTemplateId) {
        this.templateRowsByTemplateId = templateRowsByTemplateId;
    }

    @ManyToOne
    @JoinColumn(name = "template_id", referencedColumnName = "template_id", insertable = false, updatable = false) // IU
    public TemplateSeats getTemplateSeatsByTemplateId() {
        return templateSeatsByTemplateId;
    }

    public void setTemplateSeatsByTemplateId(TemplateSeats templateSeatsByTemplateId) {
        this.templateSeatsByTemplateId = templateSeatsByTemplateId;
    }
}
