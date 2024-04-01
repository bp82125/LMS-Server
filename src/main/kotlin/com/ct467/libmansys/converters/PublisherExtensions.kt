package com.ct467.libmansys.converters

import com.ct467.libmansys.dtos.RequestPublisher
import com.ct467.libmansys.dtos.ResponsePublisher
import com.ct467.libmansys.dtos.ResponsePublisherCount
import com.ct467.libmansys.models.Publisher

fun RequestPublisher.toEntity(id: Long = 0): Publisher {
    return Publisher(
        id = id,
        publisherName = this.publisherName,
        address = this.address,
        email = this.email,
        representativeInfo = this.representativeInfo
    )
}

fun Publisher.toResponse(): ResponsePublisher {
    return ResponsePublisher(
        id = this.id,
        publisherName = this.publisherName,
        address = this.address,
        email = this.email,
        representativeInfo = this.representativeInfo,
        numberOfBooks = this.numberOfBooks()
    )
}

fun Publisher.toQuantity(): ResponsePublisherCount {
    return ResponsePublisherCount(
        id = this.id,
        publisherName = this.publisherName,
        numberOfBooks = this.numberOfBooks()
    )
}