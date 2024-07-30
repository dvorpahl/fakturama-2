package com.sebulli.fakturama.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;

/**
 * A representation of the model object '<em><b>Dunning</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_DUNNING")
@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "DUNNING_PARENT_ID") })
public class Dunning extends Document implements Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    @Column(name = "DUNNINGLEVEL")
    private Integer dunningLevel = null;

    /**
     * A semantical compare method. This method compares the actual object
     * attribute by attribute to another object.
     *
     * @param other
     *            the other object to compare
     * @generated
     */
    public boolean isSameAs(Dunning other) {
        boolean retval = true;
        if (other != null) {
            if (dunningLevel != null && other.getDunningLevel() != null) {
                retval &= dunningLevel.compareTo(other.getDunningLevel()) == 0;
            }
        } else {
            retval = false;
        }
        return retval;
    }

    @Override
    public Dunning clone() {
        Dunning retval = new Dunning();
        retval.setDunningLevel(this.getDunningLevel());
        return retval;
    }

    /**
     * Returns the value of '<em><b>dunningLevel</b></em>' feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>dunningLevel</b></em>' feature
     * @generated
     */
    public Integer getDunningLevel() {

        return dunningLevel;
    }

    /**
     * Sets the '{@link Dunning#getDunningLevel() <em>dunningLevel</em>}'
     * feature.
     *
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDunningLevel
     *            the new value of the '{@link Dunning#getDunningLevel()
     *            dunningLevel}' feature.
     * @generated
     */
    public void setDunningLevel(Integer newDunningLevel) {
        dunningLevel = newDunningLevel;
    }

    /**
     * A toString method which prints the values of all EAttributes of this
     * instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "Dunning" + " dunningLevel: [" + getDunningLevel() + "]";
    }
}
