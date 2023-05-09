# BudgetManagement Android App

![App Logo](screenshot_app/icon_logo.png.png)

> Keep track of your expenses and manage your budget effectively with BudgetManagement, an Android app designed to simplify your financial management.

## Introduction

Welcome to the README.md file for the BudgetManagement Android App. This document provides an overview of the app, its features, installation instructions, and other relevant information to help you get started.

## Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Libraries](#libraries)
- [Architecture](#architecture)


## Features

- **Expense Tracking:** Record your expenses and categorize them for easy analysis.
- **Budget Planning:** Set budgets for different categories and track your progress.
- **Visualize Data:** View interactive charts and graphs to understand your spending habits.
- **Language:** Our app support Arabic and English.
- **Theme:** If you prefer to use application with light theme you can do it or try dark.
- **Frendliy design:** So you can enjoy an easy way for you to use Budget Management App :).


## Screenshots

| ![Screenshot 1](screenshot1.png) | ![Screenshot 2](screenshot2.png) |
|----------------------------------|----------------------------------|
| Overview of expenses            | Budget planning screen           |

## Installation

To install and run the BudgetManagement Android App on your device, follow these steps:

1. Download the latest APK file from the [releases](https://github.com/your-repo/releases) section.
2. Enable installation from unknown sources on your Android device.
   - Go to **Settings** > **Security** (or **Privacy**).
   - Enable the option **Unknown sources** or **Install unknown apps**.
3. Transfer the APK file to your Android device using USB or any file transfer method.
4. Open the file manager on your device and locate the APK file.
5. Tap on the APK file to start the installation process.
6. Follow the on-screen instructions to complete the installation.
7. Once installed, you can find the BudgetManagement app on your device's app drawer.

## Usage

1. Launch the BudgetManagement app from your device's app.
2. Create an account for any categories.
3. Start by adding your income and expense categories.
4. Enter your expenses and assign them to respective categories.
5. Set up your budget for different categories.
6. You can add / delete / update Account data, also for Month, Transaction pages.
7. Try to switch between dark theme, from setting page.


## Architecture

The project uses MVVM architecture pattern.

1. Code Language: Kotlin.
2. Using Hilt techniques.
3. Binding view.
4. Android Jetpack Components(ViewModel, Navigation Component ..etc).
5. Room database.
6. Kotlin coroutines.
7. Kotlin flow.
8. Clean Architecture

## Libraries

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel/) - Manage UI
  related data in a lifecycle conscious way and act as a channel between use cases and ui
* [DataBinding](https://developer.android.com/topic/libraries/data-binding) - support library that
  allows binding of UI components in layouts to data sources,binds character details and search
  results to UI
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) Android Jetpack's Navigation component helps in implementing navigation between fragments
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Dependency injection with Hilt.
* [Retrofit](https://square.github.io/retrofit/) - To access the Rest Api
* [Kotlin Flow](https://developer.android.com/kotlin/flow) - To access data sequentially
* [Encrypted SharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences) -An implementation of SharedPreferences that encrypts keys and values
* [Room components](https://developer.android.com/training/data-storage/room) - Save data in a local database using Room.
* [Kotlin components Coroutine](https://developer.android.com/topic/libraries/architecture/coroutines) - Use Kotlin coroutines with lifecycle-aware components
* [Lifecycle components](https://developer.android.com/topic/libraries/architecture/lifecycle) -Handling Lifecycles with Lifecycle-Aware Components.
* [SSP](https://github.com/intuit/ssp) a scalable size unit for texts and Support muitl screen density.

## Contributing

Thank you for considering contributing to the BudgetManagement app! If you have any suggestions, bug reports, or would like to contribute new features, please feel free to submit a pull request or open an issue in the [GitHub repository](https://github.com/your-repo).

## License

The BudgetManagement Android App is released under the [MIT License](LICENSE). Feel free to modify and distribute the app as per the terms of the license.
