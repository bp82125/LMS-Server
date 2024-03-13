package com.ct467.libmansys.exceptions

class AssociatedEntityNotFoundException(entityName: String, association: String, id: Long)
    : RuntimeException("Entity '$entityName' associated with '$association' ID '$id' not found")