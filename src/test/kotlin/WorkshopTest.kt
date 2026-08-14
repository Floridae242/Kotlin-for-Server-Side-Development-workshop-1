import kotlin.test.Test
import kotlin.test.assertEquals
import org.example.Product
import org.example.celsiusToFahrenheit
import org.example.kilometersToMiles
import org.example.totalElectronicsPriceOver500
import org.example.totalElectronicsPriceOver500Sequence

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

    @Test
    fun `pipelines include only exact electronics category priced strictly over 500`() {
        // Arrange
        val products = listOf(
            Product("Keyboard", 500.0, "Electronics"),
            Product("Mouse", 499.0, "Electronics"),
            Product("Jeans", 10_000.0, "Apparel"),
            Product("Adapter", 500.01, "Electronics"),
        )

        // Act
        val listTotal = totalElectronicsPriceOver500(products)
        val sequenceTotal = totalElectronicsPriceOver500Sequence(products)

        // Assert
        assertEquals(500.01, listTotal, 0.001)
        assertEquals(500.01, sequenceTotal, 0.001)
    }

    @Test
    fun `pipelines calculate the expected total for the workshop products`() {
        // Arrange
        val products = listOf(
            Product("Laptop", 35_000.0, "Electronics"),
            Product("Smartphone", 25_000.0, "Electronics"),
            Product("T-shirt", 450.0, "Apparel"),
            Product("Monitor", 7_500.0, "Electronics"),
            Product("Keyboard", 499.0, "Electronics"),
            Product("Jeans", 1_200.0, "Apparel"),
            Product("Headphones", 1_800.0, "Electronics"),
        )

        // Act
        val listTotal = totalElectronicsPriceOver500(products)
        val sequenceTotal = totalElectronicsPriceOver500Sequence(products)

        // Assert
        assertEquals(69_300.0, listTotal, 0.001)
        assertEquals(69_300.0, sequenceTotal, 0.001)
    }

    @Test
    fun `pipelines return zero for an empty product list`() {
        // Arrange
        val products = emptyList<Product>()

        // Act
        val listTotal = totalElectronicsPriceOver500(products)
        val sequenceTotal = totalElectronicsPriceOver500Sequence(products)

        // Assert
        assertEquals(0.0, listTotal, 0.001)
        assertEquals(0.0, sequenceTotal, 0.001)
    }

    // --- Tests for Workshop #2: Data Analysis Pipeline End ---
}
