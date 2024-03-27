package com.ct467.libmansys.exceptions

class AssociatedEntityNotFoundException(entityName: String, association: String, id: String)
    : RuntimeException("Entity '$entityName' associated with '$association' ID $id not found")