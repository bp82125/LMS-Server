package com.ct467.libmansys.exceptions

class InvalidPasswordException(
    message: String = "Invalid password provided. It must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character. Example: Testing123@"
): RuntimeException(message)