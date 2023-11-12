
package gob.gamo.activosf.app.domain;

import java.text.MessageFormat;
import java.util.Objects;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a representative model of the fixed asset's category for purposes of
 * depreciation, that is its name, its depreciation rate and its depreciation
 * logic
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Setter
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_configuration")
/*
 * @Table(name = "category_configuration"), uniqueConstraints =
 * { @UniqueConstraint(columnNames = { "designation",
 * "depreciation_logic", "depreciation_logic", "deprecant", "depreciation_rate",
 * "category_ledger_id" }) })
 */
// @Audited
public class CategoryConfiguration {

    @Id
    @Column(name = "id_configcat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "clase")
    private String clase;

    @Column(name = "familia")
    private String familia;

    @Column(name = "item")
    private String item;

    @NotNull(message = "Please provide a valid designation for category")
    @Column(name = "designation")
    private String designation;

    /**
     * The name of the depreciation logic
     */
    @NotNull(message = "Please provide a valid designation for depreciation logic")
    @Column(name = "depreciation_logic")
    private String depreciationLogic;

    /**
     * This is the item on which the depreciation rate is applied, as in either the
     * cost or the net book value
     */
    @NotNull(message = "Please provide a valid designation for depreciation deprecant")
    @Column(name = "deprecant")
    private String deprecant;

    @NotNull(message = "Please provide depreciation per annum")
    @Column(name = "depreciation_rate")
    private double depreciationRate;

    @NotNull(message = "Kindly supply the ledgerId for thiis category")
    @Column(name = "category_ledger_id")
    private String categoryLedgerId;

    /**
     * <p>
     * Constructor for CategoryConfiguration.
     * </p>
     *
     * @param designation       a {@link java.lang.String} object.
     * @param depreciationLogic a {@link java.lang.String} object.
     * @param deprecant         a {@link java.lang.String} object.
     * @param depreciationRate  a double.
     * @param categoryLedgerId  a {@link java.lang.String} object.
     */

    public CategoryConfiguration(
            @NotNull(message = "Please provide a valid designation for category") String designation,
            @NotNull(message = "Please provide a valid designation for depreciation logic") String depreciationLogic,
            @NotNull(message = "Please provide a valid designation for depreciation deprecant") String deprecant,
            @NotNull(message = "Please provide depreciation per annum") double depreciationRate,
            @NotNull(message = "Kindly supply the ledgerId for thiis category") String categoryLedgerId) {
        this.designation = designation.toUpperCase();
        this.depreciationLogic = depreciationLogic.toUpperCase();
        this.deprecant = deprecant.toUpperCase();
        this.depreciationRate = depreciationRate;
        this.categoryLedgerId = categoryLedgerId;
    }

    /**
     * <p>
     * Constructor for CategoryConfiguration.
     * </p>
     */
    /*
     * public CategoryConfiguration() {
     * }
     */

    /**
     * <p>
     * Getter for the field <code>designation</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * <p>
     * Setter for the field <code>designation</code>.
     * </p>
     *
     * @param designation a {@link java.lang.String} object.
     */
    public void setDesignation(String designation) {
        this.designation = designation.toUpperCase();
    }

    /**
     * <p>
     * Getter for the field <code>depreciationLogic</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDepreciationLogic() {
        return depreciationLogic;
    }

    /**
     * <p>
     * Setter for the field <code>depreciationLogic</code>.
     * </p>
     *
     * @param depreciationLogic a {@link java.lang.String} object.
     */
    public void setDepreciationLogic(String depreciationLogic) {
        this.depreciationLogic = depreciationLogic.toUpperCase();
    }

    /**
     * <p>
     * Getter for the field <code>deprecant</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getDeprecant() {
        return deprecant;
    }

    /**
     * <p>
     * Setter for the field <code>deprecant</code>.
     * </p>
     *
     * @param deprecant a {@link java.lang.String} object.
     */
    public void setDeprecant(String deprecant) {
        this.deprecant = deprecant.toUpperCase();
    }

    /**
     * <p>
     * Getter for the field <code>depreciationRate</code>.
     * </p>
     *
     * @return a double.
     */
    public double getDepreciationRate() {
        return depreciationRate;
    }

    /**
     * <p>
     * Setter for the field <code>depreciationRate</code>.
     * </p>
     *
     * @param depreciationRate a double.
     */
    public void setDepreciationRate(double depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    /**
     * <p>
     * Getter for the field <code>categoryLedgerId</code>.
     * </p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCategoryLedgerId() {
        return categoryLedgerId;
    }

    /**
     * <p>
     * Setter for the field <code>categoryLedgerId</code>.
     * </p>
     *
     * @param categoryLedgerId a {@link java.lang.String} object.
     */
    public void setCategoryLedgerId(String categoryLedgerId) {
        this.categoryLedgerId = categoryLedgerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CategoryConfiguration that = (CategoryConfiguration) o;
        return Double.compare(that.depreciationRate, depreciationRate) == 0
                && Objects.equals(designation, that.designation)
                && Objects.equals(depreciationLogic, that.depreciationLogic) &&
                Objects.equals(deprecant, that.deprecant) && Objects.equals(categoryLedgerId, that.categoryLedgerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), designation, depreciationLogic, deprecant, depreciationRate,
                categoryLedgerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MessageFormat.format(
                "CategoryConfiguration'{'designation=''{0}'', depreciationLogic=''{1}'', deprecant=''{2}'', depreciationRate={3}, categoryLedgerId=''{4}'''}'",
                designation,
                depreciationLogic, deprecant, depreciationRate, categoryLedgerId);
    }
}
