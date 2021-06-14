package com.company.core.utility.serializable

import com.company.core.utility.formatDate
import com.google.gson.*
import java.lang.reflect.Type

class DateSerializer : JsonDeserializer<String?>, JsonSerializer<String?> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String? {
        val data = json.asString
        return if (data == null || data.isEmpty()) {
            null
        } else {
            data.formatDate()
        }
    }

    override fun serialize(src: String?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return context.serialize(src)
    }
}