package com.ct467.libmansys.exceptions

class EntityWithIdNotFoundException(objectName: String, id: String) : RuntimeException("Can not find $objectName with id: $id")