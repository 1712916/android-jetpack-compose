package com.example.android_jetpack_compose

import com.example.android_jetpack_compose.util.date.CDate.Companion.parseDate
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.*
import java.text.*

class WrapDateUnitTest {

    @Test
    fun `test parseDate with valid date string`() {
        // Arrange
        val dateString = "2022-01-05"
        val expectedDate = SimpleDateFormat("yyyy-MM-dd").parse(dateString)

        // Act
        val actualDate = parseDate(dateString)

        // Assert
        assertEquals(expectedDate, actualDate)
    }

    @Test
    fun `test parseDate with invalid date string`() {
        // Arrange
        val invalidDateString = "invalid_date"

        // Act
        val result = parseDate(invalidDateString)

        // Assert
        assertNull(result)
    }

    @Test
    fun `test parseDate with custom date pattern`() {
        // Arrange
        val dateString = "05/01/2022"
        val customPattern = "dd/MM/yyyy"
        val expectedDate = SimpleDateFormat(customPattern).parse(dateString)

        // Act
        val actualDate = parseDate(dateString, customPattern)

        // Assert
        assertEquals(expectedDate, actualDate)
    }
}
