package org.eclipse.emf.texo.utils;

/**
 * Collection of static utility methods for checking invariants. Methods will throw an {@link IllegalStateException} or
 * an {@link IllegalArgumentException} if an invariant fails.
 * 
 * @author <a href="mtaal@elver.org">Martin Taal</a>
 */
public class Check {

  /**
   * Always throws an IllegalStateException.
   * 
   * @param message
   *          The message for the exception to throw.
   * @throws IllegalStateException
   */
  public static void fail(final String message) {
    throw new IllegalStateException(message);
  }

  /**
   * Check if a value is true.
   * 
   * @param value
   *          The value to check.
   * @param message
   *          The message for the exception to throw if the value is not true.
   * @throws IllegalStateException
   *           If the value is not true.
   */
  public static void isTrue(final boolean value, final String message) {
    if (!value) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Check if a value is false.
   * 
   * @param value
   *          The value to check.
   * @param message
   *          The message for the exception to throw if the value is not false.
   * @throws IllegalStateException
   *           If the value is not false.
   */
  public static void isFalse(final boolean value, final String message) {
    if (value) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Check if a value is not null.
   * 
   * @param value
   *          The value to check.
   * @param message
   *          The message for the exception to throw if the value is null.
   * @throws IllegalStateException
   *           If the value is null.
   */
  public static void isNotNull(final Object value, final String message) {
    if (value == null) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Check if a value is not null. The message is specific for parameters
   * 
   * @param value
   *          The value to check.
   * @param parameterName
   *          The parameterName of the parameter which is checked
   * @throws IllegalArgumentException
   *           If the value is null.
   */
  public static void isNotNullArgument(final Object value, final String parameterName) {
    if (value == null) {
      throw new IllegalArgumentException(parameterName + " is not set."); //$NON-NLS-1$
    }
  }

  /**
   * Check if a value is null.
   * 
   * @param value
   *          The value to check.
   * @param message
   *          The message for the exception to throw if the value is not null.
   * @throws IllegalStateException
   *           If the value is not null.
   */
  public static void isNull(final Object value, final String message) {
    if (value != null) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Checks if an array is not empty (!= null and length > 0)..
   * 
   * @param array
   *          The array to check.
   * @param message
   *          The message for the exception to throw if the array is empty.
   * @throws IllegalStateException
   *           If the array is empty or null.
   */
  public static void isNotEmpty(final Object[] array, final String message) {
    if (array == null || array.length == 0) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Checks if a String is set (!= null and length > 0)..
   * 
   * @param array
   *          The array to check.
   * @param message
   *          The message for the exception to throw if the String is not set.
   * @throws IllegalStateException
   *           If the String is empty or null.
   */
  public static void isNotEmpty(final String value, final String message) {
    if (value == null || value.length() == 0) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Checks if the passed object is of the class specified, null values are ignored
   * 
   * @param obj
   * @param expClass
   * @throws IllegalStateException
   *           If the obj is not of (or inherits from) the expClass
   */
  public static void isInstanceOf(final Object obj, final Class<?> expClass) {
    if (obj == null) {
      return;
    }
    if (!expClass.isAssignableFrom(obj.getClass())) {
      throw new IllegalStateException("Expected class: " //$NON-NLS-1$
          + expClass.getName() + " but object has class: "//$NON-NLS-1$ 
          + obj.getClass().getName());
    }
  }

  /**
   * Checks object memory equality.
   * 
   * @param obj1
   *          the first object
   * @param obj2
   *          the second object
   * @throws IllegalStateException
   *           If obj1 != obj2
   */
  public static void isSameObject(final Object obj1, final Object obj2) {
    if (obj1 != obj2) {
      throw new IllegalStateException("Objects are not the same");//$NON-NLS-1$
    }
  }

  /**
   * Checks object memory inequality.
   * 
   * @param obj1
   *          the first object
   * @param obj2
   *          the second object
   * @throws IllegalStateException
   *           If obj1 == obj2
   */
  public static void isNotSameObject(final Object obj1, final Object obj2) {
    if (obj1 == obj2) {
      throw new IllegalStateException("Objects are not the same");//$NON-NLS-1$
    }
  }

  /**
   * Constructor made private to prevent instantiation and subclassing.
   */
  private Check() {
  }
}
