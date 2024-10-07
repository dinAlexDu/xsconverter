# XSConverter

**XSConverter** is a multi-purpose unit conversion app designed to convert various units of measurement across different categories, including temperature, length, weight, and currencies. The app features a simple and intuitive user interface with real-time currency conversion powered by an external API.

## Objectives

The goal of **XSConverter** is to provide a user-friendly solution for quickly converting common units of measurement. The app is ideal for users who need quick and reliable conversions in various scenarios, from science and engineering to day-to-day needs like currency conversion while traveling.

## Features

- **Unit Conversion for Multiple Categories**: 
  - Convert between Celsius, Fahrenheit, and Kelvin for temperatures.
  - Convert between meters, feet, miles, and other length units.
  - Convert between kilograms, pounds, grams, and ounces for weight.
  - Real-time currency conversion with rates fetched via an API.
  
- **API Integration for Real-Time Currency Rates**: 
  - Currency conversion rates are retrieved using the [ExchangeRate-API](https://www.exchangerate-api.com/) to ensure users have the most up-to-date exchange rates.
  
- **User-Friendly Interface**:
  - A clean and intuitive interface that is easy to navigate for users of all levels.

## Technologies Used

- **Android Studio**: The app was built using Android Studio, ensuring smooth performance and a native Android experience.
- **Kotlin**: The core programming language for the application, leveraging modern Kotlin features like data classes, coroutines, and extensions.
- **Retrofit & Gson**: Retrofit is used to handle HTTP requests to fetch live currency exchange rates, while Gson is used to parse the JSON responses.
- **RecyclerView**: RecyclerView is used to display lists, such as currencies, providing a smooth scrolling experience.
- **View Binding**: ViewBinding is enabled to avoid frequent calls to `findViewById()` and to ensure type safety.

## How to Install

1. Download the APK from the [latest release](https://github.com/dinAlexDu/xsconverter/releases/latest).
2. Install the APK on your Android device.
3. Start converting units!
