package com.ct467.libmansys.exceptions

class UsernameAlreadyExistsException(username: String) : RuntimeException("Username '$username' already exists.")