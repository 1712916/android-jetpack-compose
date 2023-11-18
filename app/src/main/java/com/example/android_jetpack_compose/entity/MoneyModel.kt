package com.example.android_jetpack_compose.entity

import com.google.firebase.database.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

@Serializable
data class MoneyModel(
    @PropertyName("id")
    val id: String,
    @PropertyName("money")
    val money: Long,
    @PropertyName("note")
    val note: String?,
    @PropertyName("expenseCategory")
    val expenseCategory: ExpenseCategory,
    @PropertyName("expenseMethod")
    val expenseMethod: ExpenseMethod,
    @PropertyName("createDate")
    @Serializable(with = DateSerializer::class)
    val createDate: Date? = null,
    @PropertyName("updateDate")
    @Serializable(with = DateSerializer::class)
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
