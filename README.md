
Random Number Generator is a Jakarta-EE application that generates pseudo-random numbers and exposes a REST API.


```
POST /random: requests a new random number generation
GET /history: get list of generated random numbers
PUT /{requestId}/cancel: cancels a random number request
GET stats: get system usage statistics
GET /pending: get the list of pending requests
PUT /threads: changes the size of the thread-pool
PUT /minRequestDuration: changes the minumum request duration
```


