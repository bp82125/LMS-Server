package com.ct467.libmansys.enums

import com.ct467.libmansys.utils.AccountRoleDeserializer
import com.ct467.libmansys.utils.AccountRoleSerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = AccountRoleSerializer::class)
@JsonDeserialize(using = AccountRoleDeserializer::class)
enum class AccountRole {
    USER,
    ADMIN
}