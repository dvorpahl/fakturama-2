package com.sebulli.fakturama.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;

/**
 * A representation of the model object '<em><b>Debitor</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@Entity()
@Table(name = "FKT_DEBITOR")
public class Debitor extends Contact implements Cloneable {

    /** Bit mask of additional e-invoice profiles requested by this customer. */
    @Basic()
    @Column(name = "EINVOICEFORMAT")
    private byte eInvoiceFormat = 0;

    public byte getEInvoiceFormat() {
        return eInvoiceFormat;
    }

    public void setEInvoiceFormat(final byte eInvoiceFormat) {
        final byte sanitized = (byte) (eInvoiceFormat & 0x03);
        firePropertyChange("eInvoiceFormat", this.eInvoiceFormat, sanitized);
        this.eInvoiceFormat = sanitized;
    }
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
    public boolean isSameAs(Debitor other) {
        boolean retval = true;
        if (other != null) {
        } else {
            retval = false;
        }
        return retval;
    }

    @Override
    public Debitor clone() {
        Debitor retval = new Debitor();
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
        return "Debitor";
    }
}
