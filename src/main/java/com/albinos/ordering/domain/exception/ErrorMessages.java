package com.albinos.ordering.domain.exception;

public class ErrorMessages {

    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";

    public static final String VALIDATION_ERROR_FULLNAME_IS_NULL = "FullName cannot be null";
    public static final String VALIDATION_ERROR_FULLNAME_IS_BLANK = "FullName cannot be blank";

    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";

    public static final String VALIDATION_ERROR_DOCUMENT_IS_NULL = "Document cannot be null";
    public static final String VALIDATION_ERROR_DOCUMENT_IS_BLANK = "Document cannot be blank";

    public static final String VALIDATION_ERROR_PHONE_IS_NULL = "Phone cannot be null";
    public static final String VALIDATION_ERROR_PHONE_IS_BLANK = "Phone cannot be blank";

    public static final String VALIDATION_ERROR_MONEY_IS_NEGATIVE = "Money cannot be negative";

    public static final String VALIDATION_ERROR_QUANTITY_IS_NEGATIVE = "Quantity cannot be negative";
    public static final String VALIDATION_ERROR_QUANTITY_MUST_BE_AT_LEAST_ONE = "Quantity must be at least one";

    public static final String VALIDATION_ERROR_PRODUCT_NAME_IS_BLANK = "ProductName cannot be blank";

    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is archived it cannot be changed";
}
