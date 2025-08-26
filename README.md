# Eatlytics - Macro Tracking App

A modern Android macro tracking application built with cutting-edge technologies and best practices,
inspired by MyFitnessPal.

## 🚀 Features

- **Macro Tracking**: Track calories, protein, carbohydrates, and fat
- **Food Search**: Search and add foods to your diary
- **Daily Nutrition Summary**: View your daily nutrition overview
- **Statistics**: Track your progress over time
- **Profile Management**: Set goals and manage personal information
- **Modern UI**: Beautiful Material 3 design with Compose

## 🛠 Tech Stack

### Core Technologies

- **Kotlin 2.0.21** - Modern Kotlin with latest features
- **Jetpack Compose** - Declarative UI toolkit
- **Navigation 3** - Next-generation navigation library
- **Dagger 2 / Hilt** - Dependency injection
- **Kotlinx Serialization** - JSON serialization
- **Result4k** - Functional error handling

### Architecture

- **MVI Pattern** - Model-View-Intent architecture
- **Onion Architecture** - Clean architecture with Presentation-Domain-Data layers
- **Functional Programming** - Extensive use of functional patterns
- **Value Classes** - Type-safe domain modeling
- **Context Parameters** - Modern Kotlin context features

### Testing

- **JUnit 5** - Unit testing framework
- **MockK** - Mocking library
- **Strikt** - Assertion library
- **TDD** - Test-Driven Development approach
- **Custom Turbine-like API** - Flow testing utilities
- **Compose Screenshot Testing** - UI testing

### Development Tools

- **Gradle Best Practices Plugin** - Build optimization
- **Gradle Doctor** - Build health monitoring
- **Dependency Analysis** - Dependency management
- **Renovate** - Automated dependency updates
- **LeakCanary** - Memory leak detection
- **Baseline Profiles** - Performance optimization

## 📱 Screenshots

*Coming soon*

## 🏗 Project Structure

```
app/src/main/java/com/harishoulis/eatlytics/
├── presentation/           # UI Layer
│   ├── mvi/               # MVI architecture components
│   ├── screens/           # Compose screens
│   ├── viewmodel/         # ViewModels
│   ├── navigation/        # Navigation 3 setup
│   └── util/              # Presentation utilities
├── domain/                # Domain Layer
│   ├── model/             # Domain models with value classes
│   ├── usecase/           # Use cases with functional programming
│   └── repository/        # Repository interfaces
└── data/                  # Data Layer (to be implemented)
    ├── repository/        # Repository implementations
    ├── datasource/        # Data sources
    └── di/                # Dependency injection modules
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17
- Android SDK 36
- Minimum SDK 24

### Installation

1. Clone the repository:

```bash
git clone https://github.com/harishoulis/eatlytics.git
cd eatlytics
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the app on an emulator or device

### Build Variants

- **Debug**: Development build with debugging enabled
- **Release**: Production build with optimizations

## 🧪 Testing

### Unit Tests

```bash
./gradlew test
```

### Instrumentation Tests

```bash
./gradlew connectedAndroidTest
```

### Screenshot Tests

```bash
./gradlew app:recordDebugAndroidTestScreenshotTest
./gradlew app:verifyDebugAndroidTestScreenshotTest
```

## 🔧 Development

### Code Style

- Follow Kotlin coding conventions
- Use functional programming patterns
- Implement TDD approach
- Use meaningful commit messages

### Architecture Guidelines

- Follow MVI pattern for UI
- Use value classes for domain modeling
- Implement functional error handling with Result4k
- Use context parameters for type safety

### Testing Guidelines

- Write tests first (TDD)
- Use descriptive test names
- Test edge cases and error scenarios
- Use the custom FlowTurbine API for flow testing

## 📊 Performance

### Baseline Profiles

The app includes baseline profiles for performance optimization:

- Startup time optimization
- UI rendering improvements
- Memory usage optimization

### Monitoring

- LeakCanary for memory leak detection
- Build performance monitoring with Gradle Doctor
- Dependency analysis for build optimization

## 🔄 CI/CD

### Automated Tools

- **Renovate**: Automated dependency updates
- **Gradle Doctor**: Build health monitoring
- **Dependency Analysis**: Dependency management
- **Gradle Best Practices**: Build optimization

### Future Integrations

- **Maestro**: End-to-end testing
- **Trailblaze**: Performance testing
- **Foundry**: Design system
- **Dependency Guard**: Security scanning

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Follow TDD approach
4. Write comprehensive tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Inspired by [MyFitnessPal](https://www.myfitnesspal.com/)
- Built with modern Android development practices
- Uses cutting-edge libraries and tools from the Android ecosystem

## 🔮 Future Enhancements

- [ ] Data layer implementation with Room database
- [ ] Network layer with Retrofit and API integration
- [ ] Barcode scanning for food lookup
- [ ] Social features and sharing
- [ ] Advanced analytics and insights
- [ ] Wear OS companion app
- [ ] Widgets for quick tracking
- [ ] Dark theme support
- [ ] Internationalization
- [ ] Accessibility improvements

## 📞 Support

For support and questions, please open an issue on GitHub.

---

**Built with ❤️ using modern Android development practices**
