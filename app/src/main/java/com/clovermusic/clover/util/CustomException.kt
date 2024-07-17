package com.clovermusic.clover.util


sealed class CustomException(
    val repository: String,
    val functionName: String, // Function name where the exception occurred
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {

    // Exception thrown when the response from the API or data source is empty.
    class EmptyResponseException(repository: String, functionName: String) :
        CustomException(repository, functionName, "Response is empty")

    // Exception thrown when there is an error while fetching data from the API or data source.
    class DataFetchException(repository: String, functionName: String, cause: Throwable? = null) :
        CustomException(repository, functionName, "Error while fetching data", cause)

    // Exception thrown when there is an authentication error, such as invalid credentials.
    class AuthException(repository: String, functionName: String, cause: Throwable? = null) :
        CustomException(repository, functionName, "Error during authentication", cause)

    // Exception thrown when there is a network-related error, such as no internet connection or timeout.
    class NetworkException(repository: String, functionName: String, cause: Throwable? = null) :
        CustomException(repository, functionName, "Network error", cause)

    // Exception thrown when there is an error related to the local database operation.
    class DatabaseException(repository: String, functionName: String, cause: Throwable? = null) :
        CustomException(repository, functionName, "Database error", cause)

    // Exception thrown when there is an error parsing data, such as JSON parsing errors.
    class ParsingException(repository: String, functionName: String, cause: Throwable? = null) :
        CustomException(repository, functionName, "Error parsing data", cause)

    // Exception thrown when there is an error on the server side, indicated by server-side errors or HTTP status codes.
    class ServerException(repository: String, functionName: String, cause: Throwable? = null) :
        CustomException(repository, functionName, "Server error", cause)

    // Exception thrown when there is a timeout error, such as API request timeout.
    class TimeoutException(repository: String, functionName: String, cause: Throwable? = null) :
        CustomException(repository, functionName, "Timeout error", cause)

    // Exception thrown when the rate limit for API requests is exceeded.
    class RateLimitExceededException(
        repository: String,
        functionName: String,
        cause: Throwable? = null
    ) :
        CustomException(repository, functionName, "Rate limit exceeded", cause)

    // Exception thrown when the input data provided is invalid.
    class InvalidInputException(
        repository: String,
        functionName: String,
        cause: Throwable? = null
    ) :
        CustomException(repository, functionName, "Invalid input", cause)

    // Exception thrown for any other api errors.
    class ApiException(
        repository: String,
        functionName: String,
        message: String,
        cause: Throwable? = null
    ) :
        CustomException(repository, functionName, message, cause)

    // Exception thrown for any other unknown or unexpected errors.
    class UnknownException(
        repository: String,
        functionName: String,
        message: String,
        cause: Throwable? = null
    ) :
        CustomException(repository, functionName, message, cause)

}
