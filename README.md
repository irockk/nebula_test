## Technologies Used:
Kotlin – Primary language for development <br />
Jetpack Compose – UI framework for building responsive UIs <br />
Coroutines – For asynchronous programming <br />
Room – Local database for storing transactions and caching USD exchange rates <br />
Paging3 – For paginated loading of transactions <br />
DataStore – Local storage for user balance <br />
Koin (with Koin Annotations) – Dependency injection <br />
Retrofit – API client for fetching Bitcoin exchange rates <br />
Mockito & JUnit – For unit testing the ViewModel and business logic <br />

## Architecture: This app follows the MVVM architecture:
ViewModel: Manages UI-related data and handles user actions and business logic. <br />
UseCases: Encapsulate business logic and interact with the data layer. <br />
Repositories: Manage data operations, including transactions and balances. <br />

## Notes:
Categories are currently hardcoded, but should be stored in the local database in the future. The category enum class should use string resources for localization support. <br />

For certain features, I didn’t implement UseCases as they would act like proxies without any business logic. However, I might implement them later for a cleaner architecture of bigger project. <br />

The correct format for transaction amounts is a double value with a dot (e.g., "0.1264"). If the format is incorrect, you'll receive the default error snackbar.This is also the case for any errors from the exchange rate API. It'll be a good idea to make errors more customized in the future. <br />

All Bitcoin transaction amounts (BTS) are displayed rounded to 10 decimal places. However, it’s possible to create transactions with as small an amount as you want.All calculations are done with full precision, and only the displayed values are rounded for the UI. <br />

There are also no placeholders for loading and error states, but it should be done in future. <br />

https://github.com/user-attachments/assets/d63e2aeb-9c5d-4fdd-94d1-37c31b9039be
