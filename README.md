# Nasa-Asteroid-Radar
## _daily cached asteroid viewer application_

an android application demonstrating the use of: 

- Internet data retrieval using Retrofit get requests to [NASA Open APIs](https://api.nasa.gov/)
- Data Persistence and offline caching using Room Database
- JSON data response parsing with Kotlinx.serialization
- Coroutine Image Loading with Picasso
- Listing dynamic data sets using RecyclerView, Adapter, ViewHolder, and DiffUtil
- Updating views dynamically using BindingAdapters
- Daily background thread work with CoroutineWorkers

## Features

- NASA's Astronomoy Picture of the Day (APOD)
- NASA's Nearth Earth Object Web Service (Asteroids NeoWs)
- Asteroid data updated/cached daily and stored for up to 1-week
- Asteroid database list filtering by today, this week, or all
- An internet connection is not needed to view cached data


## Dependencies

- [Retrofit](https://square.github.io/retrofit/) - a type-safe HTTP client for Android and Java
- [Picasso](https://square.github.io/picasso/) - powerful image downloading and caching library for Android
- [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization/) - kotlin multiplatform / multi-format serialization
- [Scalars Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/scalars) - converter which supports converting strings and primitives to text/plain bodies
- [Kotlin Coroutines] - asynchronous programming
- [Androidx ViewModel] - a business logic or screen level state holder
- [Androidx RecyclerView] - recycleable UI for displaying large data sets
- [Androidx WorkManager] - deferrable, reliable, asynchronous tasking
- [Androidx Navigation] - framework for navigating across application destinations
