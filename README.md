# Currency Converter - Multiplatform App

A modern currency converter application built with Kotlin Multiplatform and Jetpack Compose, following Clean Architecture principles and MVI (Model-View-Intent) pattern. The app provides real-time currency conversion and historical rates for the last three days.

## 🌟 Features

- Real-time currency conversion with latest exchange rates
- Historical exchange rates for the last three days
- Support for multiple currencies
- Offline support with cached exchange rates
- Clean and intuitive UI built with Compose Multiplatform
- Dark/Light theme support
- Cross-platform support (Android, iOS, Desktop)


## 🏗️ Architecture

The project follows Clean Architecture principles with MVI pattern:

```
├── app/
│   ├── core/
│   ├── currency/
│   │   ├── presentation/
│   │   ├── domain/
│   │   └── data/
│   ├── android/
│   ├── ios/
│   └── desktop/
```

### Technical Stack

- **Multiplatform**: Kotlin Multiplatform
- **UI Framework**: Jetpack Compose Multiplatform
- **Architecture Pattern**: MVI (Model-View-Intent)
- **Dependency Injection**: Koin
- **Networking**: Ktor
- **Local Storage**: SQLDelight
- **Async Operations**: Kotlin Coroutines & Flow
- **Testing**: KotlinTest, JUnit5
- **CI/CD**: GitHub Actions

## 🏛️ Clean Architecture Layers

### Presentation Layer
- Implements MVI pattern
- ViewModels handle business logic
- UI States represent screen data
- Events handle user interactions
- Side effects manage one-time actions

### Domain Layer
- Contains business logic and rules
- Use cases orchestrate data flow
- Repository interfaces define data contracts
- Models represent business objects

### Data Layer
- Implements repository interfaces
- Manages data sources (API/Local)
- Handles data caching
- Maps DTOs to domain models

## 💉 Dependency Injection

Using Koin for dependency injection across all platforms:

```kotlin
val appModule = module {
    single { CurrencyRepository(get(), get()) }
    single { CurrencyDatabase(get()) }
    viewModel { CurrencyViewModel(get(), get()) }
}
```

## 🔄 MVI Implementation

### State
```kotlin
data class CurrencyState(
    val currencies: List<Currency> = emptyList(),
    val fromCurrency: Currency? = null,
    val toCurrency: Currency? = null,
    val amount: String = "",
    val convertedAmount: String = "",
    val historicalRates: List<HistoricalRate> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
```

### Events
```kotlin
sealed class CurrencyEvent {
    data class SelectFromCurrency(val currency: Currency) : CurrencyEvent()
    data class SelectToCurrency(val currency: Currency) : CurrencyEvent()
    data class AmountChanged(val amount: String) : CurrencyEvent()
    object SwapCurrencies : CurrencyEvent()
    object RefreshRates : CurrencyEvent()
}
```

### Effects
```kotlin
sealed class CurrencyEffect {
    data class ShowError(val message: String) : CurrencyEffect()
    object NavigateToSettings : CurrencyEffect()
}
```

## 📱 Screenshots

![Screenshot_20241223_132115](https://github.com/user-attachments/assets/6ed1fc7b-5bb3-49b7-baf2-552a8827309c)

<img width="1536" alt="Screenshot 2024-12-23 at 3 37 52 pm" src="https://github.com/user-attachments/assets/f9231550-89e5-41d2-8441-a8ea2aec7d94" />
![simulator_screenshot_D75F063D-E9A9-4289-B3BC-A86D050D463E](https://github.com/user-attachments/assets/7d723db7-1da9-4cbd-b8e1-16d7db7ee6e8)



## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Xcode 13 or later (for iOS)
- JDK 11 or later
- Kotlin Multiplatform Mobile plugin

### Setup
1. Clone the repository:
```bash
git clone https://github.com/yourusername/currency-converter.git
```

2. Open the project in Android Studio

3. Add your API key in `local.properties`:
```properties
CURRENCY_API_KEY=your_api_key_here
```

4. Build and run the project

## 🧪 Testing

The project includes:
- Unit tests for business logic
- Integration tests for repositories
- UI tests for components
- End-to-end tests for critical flows

Run tests:
```bash
./gradlew test
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## 🙏 Acknowledgments

- [Exchange Rate API](https://exchangerate-api.com/) for currency data
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) for UI framework
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) for cross-platform development
