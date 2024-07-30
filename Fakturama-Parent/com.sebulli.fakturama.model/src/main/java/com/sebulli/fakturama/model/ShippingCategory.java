package com.sebulli.fakturama.model;

import jakarta.persistence.Entity;

/**
 * A representation of the model object '<em><b>ShippingCategory</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
public class ShippingCategory extends AbstractCategory implements Cloneable {
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
    public boolean isSameAs(ShippingCategory other) {
        boolean retval = true;
        if (other != null) {
        } else {
            retval = false;
        }
        return retval;
    }

    @Override
    public ShippingCategory clone() {
        ShippingCategory retval = new ShippingCategory();
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
        return "ShippingCategory";
    }
}
