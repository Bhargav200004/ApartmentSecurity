package com.example.apartmentsecurity.util

enum class ValidationResult(val message: String?) {
    VALID(""),
    EMPTY_EMAIL("Email cannot be empty"),
    INVALID_EMAIL("Invalid email format"),
    EMPTY_PASSWORD("Password cannot be empty"),
    SHORT_PASSWORD("Password must be at least 8 characters"),
    NO_UPPERCASE("Password must contain at least one uppercase letter"),
    NO_LOWERCASE("Password must contain at least one lowercase letter"),
    NO_DIGIT("Password must contain at least one digit"),
    PASSWORD_AND_CONFIRM_PASSWORD_NOT_SAME("Password and confirm password must be same")
}