package com.example.android_jetpack_compose.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

@Serializable
data class MoneyModel(
    val id: Long? = null,
    val money: Long,
    val note: String?,
    val expenseCategory: ExpenseCategory,
    val expenseMethod: ExpenseMethod,
    @Serializable(with = DateSerializer::class)
    val createDate: Date,
    @Serializable(with = DateSerializer::class)
    val updateDate: Date,
)


//@Serializer(forClass = DateSerializer::class)
//object DateSerializer : KSerializer<Date> {
//    override val descriptor: SerialDescriptor =
//        StringDescriptor.withName("DateSerializer")
//
//    override fun serialize(output: Encoder, obj: Date) {
//        output.encodeString(obj.time.toString())
//    }
//
//    override fun deserialize(input: Decoder): Date {
//        return Date(input.decodeString().toLong())
//    }
//}
//

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
