package com.example.android_jetpack_compose.entity

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.util.*

data class MoneyModel(
    val id: String = "",
    val money: Long = 0,
    val note: String? = null,
    val category: ExpenseCategory? = null,
    val method: ExpenseMethod? = null,
    val createDate: Date? = null,
    val updateDate: Date? = null,
)
@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DateSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(value.time.toString())
    }

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeString().toLong())
    }
}
