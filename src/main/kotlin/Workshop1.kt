package org.example

// ข้อความสำหรับเมนู
val menuTitle = "=== Unit Converter ==="
val menuOption1 = "1. Celsius → Fahrenheit"
val menuOption2 = "2. Kilometers → Miles"
val menuExit = "พิมพ์ exit เพื่อออกจากโปรแกรม"
val menuPrompt = "เลือกเมนู (1, 2, or exit): "

fun main() {
    // ส่วนที่ 1: แสดงเมนู และ ส่วนที่ 2: วนซ้ำด้วย while (true)
    while (true) {
        println(menuTitle)
        println(menuOption1)
        println(menuOption2)
        println(menuExit)
        print(menuPrompt)

        // ส่วนที่ 3: รับตัวเลือกและใช้ when
        val choice = readln()

        when (choice) {
            "1" -> convertCelsiusToFahrenheit()
            "2" -> convertKilometersToMiles()
            "exit" -> break
            else -> println("เมนูไม่ถูกต้อง กรุณาเลือก 1, 2 หรือ exit")
        }

        println()
    }

    println("ขอบคุณที่ใช้งาน Unit Converter!")
}

// ส่วนที่ 4: ฟังก์ชันคำนวณ Celsius → Fahrenheit
fun celsiusToFahrenheit(celsius: Double): Double {
    return celsius * 9.0 / 5.0 + 32
}

// ส่วนที่ 5: ฟังก์ชันคำนวณ Kilometers → Miles
fun kilometersToMiles(kilometers: Double): Double {
    return kilometers * 0.621371
}

// ส่วนที่ 7 + 8: ฟังก์ชันควบคุมกระบวนการ Celsius (I/O + Null Safety)
fun convertCelsiusToFahrenheit() {
    print("ป้อนค่าองศาเซลเซียส (Celsius): ")
    val input = readln()

    // ส่วนที่ 6: Null Safety ด้วย toDoubleOrNull() และ Elvis operator ?:
    val celsius = input.toDoubleOrNull() ?: run {
        println("ข้อมูลไม่ถูกต้อง กรุณาป้อนตัวเลข")
        return
    }

    val fahrenheitResult = celsiusToFahrenheit(celsius)

    // ส่วนที่ 10: แสดงผลทศนิยมสองตำแหน่ง
    println("ผลลัพธ์: $celsius °C เท่ากับ ${"%.2f".format(fahrenheitResult)} °F")
}

// ส่วนที่ 7 + 8: ฟังก์ชันควบคุมกระบวนการ Kilometers (I/O + Null Safety)
fun convertKilometersToMiles() {
    print("ป้อนระยะทาง (Kilometers): ")
    val input = readln()

    // Null Safety ด้วย toDoubleOrNull() และ Elvis operator ?:
    val kilometers = input.toDoubleOrNull() ?: run {
        println("ข้อมูลไม่ถูกต้อง กรุณาป้อนตัวเลข")
        return
    }

    val milesResult = kilometersToMiles(kilometers)

    // แสดงผลทศนิยมสองตำแหน่ง
    println("ผลลัพธ์: $kilometers km เท่ากับ ${"%.2f".format(milesResult)} miles")
}