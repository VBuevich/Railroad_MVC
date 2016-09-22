package Persistence.Entity;

import javax.persistence.*;

/**
 * @author vbuevich
 *
 * Hibernate entity class
 */
@Entity
@Table(name = "template_train", schema = "javaschool", catalog = "")
public class TemplateTrain {
    private int templateTrainId;
    private String templateId;

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
}
