package com.ct467.libmansys.exceptions

class AdminAccountDeletionException(message: String = "Deleting admin account is not allowed") : RuntimeException(message)