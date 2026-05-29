package com.sebulli.fakturama.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A representation of the literals of the enumeration
 * '<em><b>ReliabilityType</b></em>'. <!-- begin-user-doc --> <!-- end-user-doc
 * -->
 * 
 * @generated
 */
public enum ReliabilityType {

    /**
     * The enum: NONE <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    NONE(0, "NONE", "NONE") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isNONE() {
            return true;
        }
    },
    /**
     * The enum: POOR <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    POOR(1, "POOR", "POOR") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isPOOR() {
            return true;
        }
    },
    /**
     * The enum: MEDIUM <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    MEDIUM(2, "MEDIUM", "MEDIUM") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isMEDIUM() {
            return true;
        }
    },
    /**
     * The enum: GOOD <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    GOOD(3, "GOOD", "GOOD") {

        /**
         * @return always true for this instance
         * @generated
         */
        @Override
        public boolean isGOOD() {
            return true;
        }
    };

    /**
     * An array of all the '<em><b>ReliabilityType</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static final ReliabilityType[] VALUES_ARRAY = new ReliabilityType[] { NONE, POOR, MEDIUM, GOOD };

    /**
     * A public read-only list of all the '<em><b>ReliabilityType</b></em>'
     * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final List<ReliabilityType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>ReliabilityType</b></em>' literal with the specified
     * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal to use to get the enum instance
     * @return the ReliabilityType, the literal enum class
     * @generated
     */
    public static ReliabilityType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ReliabilityType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>ReliabilityType</b></em>' literal with the specified
     * name. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param name
     *            the name to use to get the enum instance
     * @return the ReliabilityType, the literal enum class
     * @generated
     */
    public static ReliabilityType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ReliabilityType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>ReliabilityType</b></em>' literal with the specified
     * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the value to use to get the enum instance
     * @return the ReliabilityType, the literal enum
     * @generated
     */
    public static ReliabilityType get(int value) {
        for (ReliabilityType enumInstance : VALUES_ARRAY) {
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
    private ReliabilityType(int value, String name, String literal) {
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
    public boolean isNONE() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isPOOR() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isMEDIUM() {
        return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return false, is overridden by actual enum types.
     * @generated
     */
    public boolean isGOOD() {
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
