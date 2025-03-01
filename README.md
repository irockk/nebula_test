Technologies Used:
Kotlin – Primary language for development
Jetpack Compose – UI framework for building responsive UIs
Coroutines – For asynchronous programming
Room – Local database for storing transactions and caching USD exchange rates
Paging3 – For paginated loading of transactions
DataStore – Local storage for user balance
Koin (with Koin Annotations) – Dependency injection
Retrofit – API client for fetching Bitcoin exchange rates
Mockito & JUnit – For unit testing the ViewModel and business logic

Architecture: This app follows the MVVM architecture:
ViewModel: Manages UI-related data and handles user actions and business logic.
UseCases: Encapsulate business logic and interact with the data layer.
Repositories: Manage data operations, including transactions and balances.

Notes:
Categories are currently hardcoded, but should be stored in the local database in the future.
The category enum class should use string resources for localization support.

For certain features, I didn’t implement UseCases as they would act like proxies without any business logic. 
However, I might implement them later for a cleaner architecture of bigger project.

I attempted to cover HomeViewModel with unit tests, but I don’t have commercial experience in writing tests. 
I couldn’t complete them fully correctly, but I aimed to demonstrate my understanding of the concept.
