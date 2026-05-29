package com.sebulli.fakturama.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A representation of the literals of the enumeration
 * '<em><b>VoucherType</b></em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
 * <!-- begin-model-doc --> The type enum for Vouchers. <!-- end-model-doc -->
 * 
 * @generated
 */
public enum VoucherType {

    /**
     * The enum: RECEIPTVOUCHER <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    RECEIPTVOUCHER(0, "RECEIPTVOUCHER", "RECEIPTVOUCHER") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isRECEIPTVOUCHER() {
            return true;
        }
    },
    /**
     * The enum: EXPENDITURE <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EXPENDITURE(1, "EXPENDITURE", "EXPENDITURE") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isEXPENDITURE() {
            return true;
        }
    };

    /**
     * An array of all the '<em><b>VoucherType</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final VoucherType[] VALUES_ARRAY = new VoucherType[] { RECEIPTVOUCHER, EXPENDITURE };

    /**
     * A public read-only list of all the '<em><b>VoucherType</b></em>'
     * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final List<VoucherType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>VoucherType</b></em>' literal with the specified
     * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal to use to get the enum instance
     * @return the VoucherType, the literal enum class
     * @generated
     */
    public static VoucherType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            VoucherType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>VoucherType</b></em>' literal with the specified
     * name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param name
     *            the name to use to get the enum instance
     * @return the VoucherType, the literal enum class
     * @generated
     */
    public static VoucherType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            VoucherType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>VoucherType</b></em>' literal with the specified
     * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the value to use to get the enum instance
     * @return the VoucherType, the literal enum
     * @generated
     */
    public static VoucherType get(int value) {
        for (VoucherType enumInstance : VALUES_ARRAY) {
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
    private VoucherType(int value, String name, String literal) {
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
    public boolean isRECEIPTVOUCHER() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isEXPENDITURE() {
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
