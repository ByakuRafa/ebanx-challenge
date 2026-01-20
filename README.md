# Balance Service - Ebanx Take Home Assignment

This is a REST API implementation for the EBANX technical challenge, built with Java 17 and Spring Boot 3.

##  Demo

The API is published and available at:  
 **[https://ebanx-challenge-rafael.onrender.com](https://ebanx-challenge-rafael.onrender.com)**

Since the automated test suite link provided in the instructions was unavailable, I deployed the application to Render to ensure persistent availability for review.


## Architectural Decisions

To meet the requirements of simplicity and malleability and ensuring quality, the following decisions were made:

Memory Persistence: As durability was not a requirement, I utilized a 'ConcurrentHashMap' to ensure thread safety for concurrent operations.

Layered Architecture: The project follows a strict separation of concerns:
    'Controller': Handles HTTP requests/responses and JSON mapping.
    'Service': Contains pure business logic (Deposit, Withdraw, Transfer rules).
    'Repository': Manages data storage implementation.

Testing Strategy: Due to the unavailability of the official test suite, I implemented integration tests (JUnit 5 and MockMvc) to validate all scenarios (Deposit, Withdraw and Transfer).


## API Reference & Examples

Here are the payloads to test the '/event' endpoint manually.

### Reset State
Clears all data. **Run this before starting tests.**
POST '/reset'
Response: '200 OK' 'OK'

### Get Balance
GET '/balance?account_id=100'
Response (Found): '200 OK' '20'
Response (Not Found): '404 Not Found' '0'

###  Events (POST /event)

#### Deposit (Creates account if not exists)
Body:
    '''json
    {
        "type": "deposit",
        "destination": "100",
        "amount": 10
    }
    '''

#### Withdraw
Body:
    '''json
    {
        "type": "withdraw",
        "origin": "100",
        "amount": 5
    }
    '''

#### Transfer
Body:
    '''json
    {
        "type": "transfer",
        "origin": "100",
        "amount": 15,
        "destination": "300"
    }
    '''
