package com.kitchenforce.restdoc

import org.springframework.restdocs.payload.JsonFieldType

sealed class DocsFieldType(
    val type: JsonFieldType,
)

object BOOLEAN : DocsFieldType(JsonFieldType.BOOLEAN)
object NUMBER : DocsFieldType(JsonFieldType.NUMBER)
object NULL : DocsFieldType(JsonFieldType.NULL)
object STRING : DocsFieldType(JsonFieldType.STRING)
object OBJECT : DocsFieldType(JsonFieldType.OBJECT)
object ARRAY : DocsFieldType(JsonFieldType.ARRAY)
