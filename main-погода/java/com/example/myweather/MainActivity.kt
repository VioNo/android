package com.example.myweather


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация элементов UI
        val citySpinner: Spinner = findViewById(R.id.citySpinner)
        val weatherTextView: TextView = findViewById(R.id.weatherInfo)
        val getWeatherBtn: Button = findViewById(R.id.getWeatherBtn)

        // Список доступных городов
        val cities = listOf(
            "Москва", "Санкт-Петербург", "Новосибирск",
            "Екатеринбург", "Казань", "Сочи",
            "Владивосток", "Калининград", "Нижний Новгород"
        )

        // Настройка Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = adapter

        // Обработчик кнопки
        getWeatherBtn.setOnClickListener {
            val selectedCity = citySpinner.selectedItem.toString()
            val weatherData = generateWeatherData(selectedCity)
            weatherTextView.text = formatWeatherData(selectedCity, weatherData)
        }
    }

    private fun generateWeatherData(city: String): WeatherData {
        val random = Random(System.currentTimeMillis() + city.hashCode())
        return WeatherData(
            temperature = (-15..30).random(),
            condition = listOf("Ясно", "Облачно", "Дождь", "Снег", "Туман").random(),
            humidity = random.nextInt(30, 90),
            windSpeed = random.nextInt(0, 15),
            pressure = random.nextInt(720, 780)
        )
    }

    private fun formatWeatherData(city: String, data: WeatherData): String {
        return """
            Погода в $city:
            Состояние: ${data.condition}
            Температура: ${data.temperature}°C
            Влажность: ${data.humidity}%
            Ветер: ${data.windSpeed} м/с
            Давление: ${data.pressure} мм рт.ст.
        """.trimIndent()
    }

    data class WeatherData(
        val temperature: Int,
        val condition: String,
        val humidity: Int,
        val windSpeed: Int,
        val pressure: Int
    )
}