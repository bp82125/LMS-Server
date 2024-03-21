package com.ct467.libmansys.utils

import com.ct467.libmansys.enums.AccountRole
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import java.io.IOException
import java.util.*

class AccountRoleSerializer : JsonSerializer<AccountRole>() {
    @Throws(IOException::class)
    override fun serialize(
        role: AccountRole,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider
    ) {
        jsonGenerator.writeString(role.name.lowercase(Locale.getDefault())) // Serialize as lowercase
    }
}

class AccountRoleDeserializer : JsonDeserializer<AccountRole>() {
    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): AccountRole {
        val node: JsonNode = jsonParser.codec.readTree(jsonParser)
        val roleString = node.textValue()
        return when (roleString.lowercase(Locale.getDefault())) { // Deserialize from lowercase
            "user" -> AccountRole.USER
            "admin" -> AccountRole.ADMIN
            else -> throw IllegalArgumentException("Invalid role value: $roleString")
        }
    }
}