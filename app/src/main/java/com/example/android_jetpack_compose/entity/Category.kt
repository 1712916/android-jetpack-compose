package com.example.android_jetpack_compose.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject
import java.util.Date


interface Category {
    val name: String
    val id: Int
}

@Serializable(with = ExpenseCategorySerializer::class)
sealed class ExpenseCategory : Category {
    data class Income(override val name: String, override val id: Int) : ExpenseCategory()

    data class Expense(override val name: String, override val id: Int) : ExpenseCategory()
}

@Serializer(forClass = ExpenseCategory::class)
object ExpenseCategorySerializer : KSerializer<ExpenseCategory> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ExpenseCategory") {
        element<Int>("type")
        element<Int>("id")
        element<String>("name")
    }

    override fun serialize(encoder: Encoder, value: ExpenseCategory) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(
                descriptor, 0, when (value) {
                    is ExpenseCategory.Income -> 0
                    is ExpenseCategory.Expense -> 1
                }
            )
            encodeIntElement(descriptor, 1, value.id)
            encodeStringElement(descriptor, 2, value.name)
        }
    }

    override fun deserialize(decoder: Decoder): ExpenseCategory {
        return decoder.decodeStructure(descriptor) {
            var type: Int? = null
            var id: Int? = null
            var name: String? = null


            loop@ while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    DECODE_DONE -> break@loop

                    0 -> type = decodeIntElement(descriptor, 0)
                    1 -> id = decodeIntElement(descriptor, 1)
                    2 -> name = decodeStringElement(descriptor, 2)
                }
            }

            when (type) {
                0 -> ExpenseCategory.Income(
                    id = id!!,
                    name = name!!,
                )

                1 -> ExpenseCategory.Expense(
                    id = id!!,
                    name = name!!,
                )

                else -> ExpenseCategory.Expense(
                    id = id!!,
                    name = name!!,
                )
            }

        }

    }
}

fun String.toMap(): Map<String, Any> {
    return split(",").associate {
        val (left, right) = it.split(":")
        left to right
    }
}
