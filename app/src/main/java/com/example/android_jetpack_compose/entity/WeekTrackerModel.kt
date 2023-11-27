package com.example.android_jetpack_compose.entity

import android.graphics.drawable.Icon
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.*
import com.example.android_jetpack_compose.ui.theme.*
import java.util.Date

data class WeekTrackerModel(val date: Date, val dateSpend: Long)
data class WeekTrackerInfoModel(
    val totalSpend: Long = 0,
    val differenceNumber: Double = 0.0,
    val differentEnum: DifferentEnum = DifferentEnum.BALANCE,
    val weekTackers: Array<WeekTrackerModel>? = null,
    val budget: Long = 0,
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
        var result = totalSpend.hashCode()
        result = 31 * result + (differenceNumber.hashCode())
        result = 31 * result + (differentEnum.hashCode())
        result = 31 * result + weekTackers.contentHashCode()
        return result
    }
}

data class DatesTrackerInfoModel(
    val totalSpend: Long = 0,
    val weekTackers: Array<WeekTrackerModel>? = null,
    val budget: Long = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeekTrackerInfoModel

        if (totalSpend != other.totalSpend) return false

        if (!weekTackers.contentEquals(other.weekTackers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = totalSpend.hashCode()

        result = 31 * result + weekTackers.contentHashCode()
        return result
    }
}

enum class DifferentEnum {
    INCREASE, DECREASE, BALANCE;

    fun getColor(): Color {
        return when (this) {
            INCREASE -> redColor
            DECREASE -> textGreenColor
            BALANCE -> grayColor
        }
    }

    fun getIcon(): ImageVector {
        return when (this) {
            INCREASE -> Icons.Filled.KeyboardArrowUp
            DECREASE -> Icons.Filled.KeyboardArrowDown
            BALANCE -> Icons.Filled.Star
        }
    }
}
