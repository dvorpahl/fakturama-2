package com.sebulli.fakturama.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;

/**
 * A representation of the model object '<em><b>Letter</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_LETTER")
@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "LETTER_PARENT_ID") })
public class Letter extends Document implements Cloneable {
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
    public boolean isSameAs(Letter other) {
        boolean retval = true;
        if (other != null) {
        } else {
            retval = false;
        }
        return retval;
    }

    @Override
    public Letter clone() {
        Letter retval = new Letter();
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
        return "Letter";
    }
}
