package com.sebulli.fakturama.model;

import jakarta.persistence.Entity;

/**
 * A representation of the model object '<em><b>ContactCategory</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
public class ContactCategory extends AbstractCategory implements Cloneable {
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
    public boolean isSameAs(ContactCategory other) {
        boolean retval = true;
        if (other != null) {
        } else {
            retval = false;
        }
        return retval;
    }

    @Override
    public ContactCategory clone() {
        ContactCategory retval = new ContactCategory();
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
        return "ContactCategory";
    }
}
