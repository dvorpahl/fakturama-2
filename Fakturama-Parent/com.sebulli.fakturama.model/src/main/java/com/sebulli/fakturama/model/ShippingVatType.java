package com.sebulli.fakturama.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A representation of the literals of the enumeration
 * '<em><b>ShippingVatType</b></em>'. <!-- begin-user-doc --> <!-- end-user-doc
 * -->
 * 
 * @generated
 */
public enum ShippingVatType {

    /**
     * The enum: SHIPPINGVATFIX <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> Calculate the shipping's vat with a fix vat
     * value. <!-- end-model-doc -->
     * 
     * @generated
     */
    SHIPPINGVATFIX(0, "SHIPPINGVATFIX", "SHIPPINGVATFIX") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isSHIPPINGVATFIX() {
            return true;
        }
    },
    /**
     * The enum: SHIPPINGVATGROSS <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> Calculate the shipping's vat with the same vat
     * of the items. The shipping vat is a gross value. <!-- end-model-doc -->
     * 
     * @generated
     */
    SHIPPINGVATGROSS(1, "SHIPPINGVATGROSS", "SHIPPINGVATGROSS") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isSHIPPINGVATGROSS() {
            return true;
        }
    },
    /**
     * The enum: SHIPPINGVATNET <!-- begin-user-doc --> <!-- end-user-doc -->
     * <!-- begin-model-doc --> Calculate the shipping's vat with the same vat
     * of the items. The shipping vat is a net value. <!-- end-model-doc -->
     * 
     * @generated
     */
    SHIPPINGVATNET(2, "SHIPPINGVATNET", "SHIPPINGVATNET") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isSHIPPINGVATNET() {
            return true;
        }
    };

    /**
     * An array of all the '<em><b>ShippingVatType</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final ShippingVatType[] VALUES_ARRAY = new ShippingVatType[] { SHIPPINGVATFIX, SHIPPINGVATGROSS, SHIPPINGVATNET };

    /**
     * A public read-only list of all the '<em><b>ShippingVatType</b></em>'
     * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final List<ShippingVatType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>ShippingVatType</b></em>' literal with the specified
     * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal to use to get the enum instance
     * @return the ShippingVatType, the literal enum class
     * @generated
     */
    public static ShippingVatType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ShippingVatType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>ShippingVatType</b></em>' literal with the specified
     * name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param name
     *            the name to use to get the enum instance
     * @return the ShippingVatType, the literal enum class
     * @generated
     */
    public static ShippingVatType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ShippingVatType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>ShippingVatType</b></em>' literal with the specified
     * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the value to use to get the enum instance
     * @return the ShippingVatType, the literal enum
     * @generated
     */
    public static ShippingVatType get(int value) {
        for (ShippingVatType enumInstance : VALUES_ARRAY) {
            if (enumInstance.getValue() == value) {
                return enumInstance;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    private ShippingVatType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isSHIPPINGVATFIX() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isSHIPPINGVATGROSS() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isSHIPPINGVATNET() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the value
     * @generated
     */
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the name
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the literal of this enum instance
     * @generated
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the literal value of the enumerator, which is its string
     *         representation.
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }
}
