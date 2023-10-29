package com.sebulli.fakturama.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;

/**
 * A representation of the model object '<em><b>Proforma</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_PROFORMA")
@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "PROFORMA_PARENT_ID") })
public class Proforma extends Document implements Cloneable {
    /**
     * A common serial ID.
     * 
     * @generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * A semantical compare method. This method compares the actual object
     * attribute by attribute to another object.
     *
     * @param other
     *            the other object to compare
     * @generated
     */
    public boolean isSameAs(Proforma other) {
        boolean retval = true;
        if (other != null) {
        } else {
            retval = false;
        }
        return retval;
    }

    @Override
    public Proforma clone() {
        Proforma retval = new Proforma();
        return retval;
    }

    /**
     * A toString method which prints the values of all EAttributes of this
     * instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "Proforma";
    }
}
