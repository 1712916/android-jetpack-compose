package com.example.android_jetpack_compose.entity

import java.util.Date

data class WeekTrackerModel(val date: Date, val dateBudget: Double, val dateSpend: Long)

data class WeekTrackerInfoModel(
    val totalSpend: Double? = null,
    val differenceNumber: Double? = null,
    val differentEnum: DifferentEnum? = null,
    val weekTackers: Array<WeekTrackerModel>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeekTrackerInfoModel

        if (totalSpend != other.totalSpend) return false
        if (differenceNumber != other.differenceNumber) return false
        if (differentEnum != other.differentEnum) return false
        if (!weekTackers.contentEquals(other.weekTackers)) return false

        return true
    }
    override fun hashCode(): Int {
        var result = totalSpend?.hashCode() ?: 0
        result = 31 * result + (differenceNumber?.hashCode() ?: 0)
        result = 31 * result + (differentEnum?.hashCode() ?: 0)
        result = 31 * result + weekTackers.contentHashCode()
        return result
    }
}

enum class DifferentEnum {
    INCREASE, DECREASE, BALANCE
}
