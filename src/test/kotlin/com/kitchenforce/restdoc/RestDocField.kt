package com.kitchenforce.restdoc

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.ResponseFieldsSnippet

open class RestDocField(
    val descriptor: FieldDescriptor
) {
    open infix fun isOptional(value: Boolean): RestDocField {
        if (value) descriptor.optional()
        return this
    }

    open infix fun means(value: String): RestDocField {
        descriptor.description(value)
        return this
    }

    open infix fun attributes(block: RestDocField.() -> Unit): RestDocField {
        block()
        return this
    }
}

infix fun String.type(
    docsFieldType: DocsFieldType
): RestDocField {
    return createField(this, docsFieldType.type)
}

private fun createField(value: String, type: JsonFieldType, optional: Boolean = true): RestDocField {
    val descriptor = PayloadDocumentation.fieldWithPath(value)
        .type(type)
        .description("")

    if (optional) descriptor.optional()

    return RestDocField(descriptor)
}

fun <T : RestDocField> wrappedResponseFields(vararg fields: T): ResponseFieldsSnippet = PayloadDocumentation.responseFields(fields.map { it.descriptor })
