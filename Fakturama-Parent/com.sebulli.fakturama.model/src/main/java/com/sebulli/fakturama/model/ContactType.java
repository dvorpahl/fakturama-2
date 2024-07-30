package com.sebulli.fakturama.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A representation of the literals of the enumeration
 * '<em><b>ContactType</b></em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
 * <!-- begin-model-doc --> The type of contact (i.e., if it is the invoice
 * address, the delivery address or other). <!-- end-model-doc -->
 * 
 * @generated
 */
public enum ContactType {

    /**
     * The enum: BILLING <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    BILLING(0, "BILLING", "BILLING") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isBILLING() {
            return true;
        }
    },
    /**
     * The enum: DELIVERY <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    DELIVERY(1, "DELIVERY", "DELIVERY") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isDELIVERY() {
            return true;
        }
    };

    /**
     * An array of all the '<em><b>ContactType</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final ContactType[] VALUES_ARRAY = new ContactType[] { BILLING, DELIVERY };

    /**
     * A public read-only list of all the '<em><b>ContactType</b></em>'
     * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final List<ContactType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>ContactType</b></em>' literal with the specified
     * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal to use to get the enum instance
     * @return the ContactType, the literal enum class
     * @generated
     */
    public static ContactType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ContactType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>ContactType</b></em>' literal with the specified
     * name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param name
     *            the name to use to get the enum instance
     * @return the ContactType, the literal enum class
     * @generated
     */
    public static ContactType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ContactType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>ContactType</b></em>' literal with the specified
     * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the value to use to get the enum instance
     * @return the ContactType, the literal enum
     * @generated
     */
    public static ContactType get(int value) {
        for (ContactType enumInstance : VALUES_ARRAY) {
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
    private ContactType(int value, String name, String literal) {
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
    public boolean isBILLING() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isDELIVERY() {
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
