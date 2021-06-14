package com.company.core.utility.serializable

import com.google.gson.*
import java.lang.reflect.Type

class ImageSerializer : JsonDeserializer<String?>, JsonSerializer<String?> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String? {
        val data = json.asString
        return if (data == null || data.isEmpty()) {
            null
        } else {
            "https://image.tmdb.org/t/p/w500/${data}"
        }
    }

    override fun serialize(src: String?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return context.serialize(src)
    }
}