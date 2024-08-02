package com.sebulli.fakturama.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A representation of the literals of the enumeration
 * '<em><b>ItemType</b></em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
 * <!-- begin-model-doc --> The type of a document position. This can be a
 * "normal" Position, Free text, a delivery part (which is a part of a delivery
 * in a collecting invoice) or a subtotal line. <!-- end-model-doc -->
 * 
 * @generated
 */
public enum ItemType {

    /**
     * The enum: POSITION <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    POSITION(0, "POSITION", "POSITION") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isPOSITION() {
            return true;
        }
    },
    /**
     * The enum: FREETEXT <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    FREETEXT(1, "FREETEXT", "") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isFREETEXT() {
            return true;
        }
    },
    /**
     * The enum: DELIVERY_PART <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    DELIVERY_PART(2, "DELIVERY_PART", "DELIVERY_PART") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isDELIVERY_PART() {
            return true;
        }
    },
    /**
     * The enum: SUBTOTAL <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    SUBTOTAL(3, "SUBTOTAL", "SUBTOTAL") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isSUBTOTAL() {
            return true;
        }
    };

    /**
     * An array of all the '<em><b>ItemType</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final ItemType[] VALUES_ARRAY = new ItemType[] { POSITION, FREETEXT, DELIVERY_PART, SUBTOTAL };

    /**
     * A public read-only list of all the '<em><b>ItemType</b></em>'
     * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final List<ItemType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>ItemType</b></em>' literal with the specified literal
     * value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal to use to get the enum instance
     * @return the ItemType, the literal enum class
     * @generated
     */
    public static ItemType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ItemType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>ItemType</b></em>' literal with the specified name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param name
     *            the name to use to get the enum instance
     * @return the ItemType, the literal enum class
     * @generated
     */
    public static ItemType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ItemType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>ItemType</b></em>' literal with the specified integer
     * value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the value to use to get the enum instance
     * @return the ItemType, the literal enum
     * @generated
     */
    public static ItemType get(int value) {
        for (ItemType enumInstance : VALUES_ARRAY) {
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
    private ItemType(int value, String name, String literal) {
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
    public boolean isPOSITION() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isFREETEXT() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isDELIVERY_PART() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isSUBTOTAL() {
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
