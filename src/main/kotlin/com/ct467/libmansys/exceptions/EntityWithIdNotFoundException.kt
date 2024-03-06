package com.ct467.libmansys.exceptions

class EntityWithIdNotFoundException(objectName: String, id: Long) : RuntimeException("Can not find $objectName with id: $id")