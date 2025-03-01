Architecture: This app follows the MVVM architecture:
ViewModel: Manages UI-related data and handles user actions and business logic.
UseCases: Encapsulate business logic and interact with the data layer.
Repositories: Manage data operations, including transactions and balances.

Notes:
Categories are currently hardcoded, but should be stored in the local database in the future. The category enum class should use string resources for localization support.

For certain features, I didn’t implement UseCases as they would act like proxies without any business logic. However, I might implement them later for a cleaner architecture of bigger project.

I attempted to cover HomeViewModel with unit tests, but I don’t have commercial experience in writing tests. I couldn’t complete them fully correctly, but I aimed to demonstrate my understanding of the concept.

The correct format for transaction amounts is a double value with a dot (e.g., "0.1264"). If the format is incorrect, you'll receive the default error snackbar.This is also the case for any errors from the exchange rate API. It'll be a good idea to make errors more customized in the future.

All Bitcoin transaction amounts (BTS) are displayed rounded to 10 decimal places. However, it’s possible to create transactions with as small an amount as you want.All calculations are done with full precision, and only the displayed values are rounded for the UI.

There are also no placeholders for loading and error states, but it should be done in future.
