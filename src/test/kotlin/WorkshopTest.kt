import kotlin.test.Test
import kotlin.test.assertEquals
import org.example.celsiusToFahrenheit
import org.example.kilometersToMiles

class WorkshopTest {

    // --- Tests for Workshop #1: Unit Converter ---

    // celsius input: 20.0
    // expected output: 68.0
    @Test
    fun `test celsiusToFahrenheit with positive value`() {
        // Arrange: ตั้งค่า input และผลลัพธ์ที่คาดหวัง
        val celsiusInput = 20.0
        val expectedFahrenheit = 68.0

        // Act: เรียกใช้ฟังก์ชันที่ต้องการทดสอบ
        val actualFahrenheit = celsiusToFahrenheit(celsiusInput)

        // Assert: ตรวจสอบว่าผลลัพธ์ที่ได้ตรงกับที่คาดหวัง
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001, "20°C should be 68°F")
    }

    // celsius input: 0.0
    // expected output: 32.0
    @Test
    fun `test celsiusToFahrenheit with zero`() {
        // Arrange
        val celsiusInput = 0.0
        val expectedFahrenheit = 32.0

        // Act
        val actualFahrenheit = celsiusToFahrenheit(celsiusInput)

        // Assert
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001, "0°C should be 32°F")
    }

    // celsius input: -10.0
    // expected output: 14.0
    @Test
    fun `test celsiusToFahrenheit with negative value`() {
        // Arrange
        val celsiusInput = -10.0
        val expectedFahrenheit = 14.0

        // Act
        val actualFahrenheit = celsiusToFahrenheit(celsiusInput)

        // Assert
        assertEquals(expectedFahrenheit, actualFahrenheit, 0.001, "-10°C should be 14°F")
    }

    // test for kilometersToMiles function
    // kilometers input: 1.0
    // expected output: 0.621371
    @Test
    fun `test kilometersToMiles with one kilometer`() {
        // Arrange
        val kmInput = 1.0
        val expectedMiles = 0.621371

        // Act
        val actualMiles = kilometersToMiles(kmInput)

        // Assert
        assertEquals(expectedMiles, actualMiles, 0.001, "1 km should be approximately 0.621371 miles")
    }

    // --- Tests for Workshop #1: Unit Converter End ---

    // --- Tests for Workshop #2: Data Analysis Pipeline ---
    // (เก็บไว้ทำใน Workshop #2 เมื่อ implement ฟังก์ชันและ Data Class ใน Workshop2.kt เรียบร้อยแล้ว)
    // --- Tests for Workshop #2: Data Analysis Pipeline End ---
}