package com.ct467.libmansys.exceptions

class EntityAssociationException(entityName: String, entityId: String, associatedEntityName: String)
    : RuntimeException("The $entityName with ID $entityId cannot be deleted because it is associated with $associatedEntityName. Try to delete all the associated $associatedEntityName before deleting the $entityName.")