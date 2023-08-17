# Nasa-Asteroid-Radar
## _daily cached asteroid viewer_


![Screenshot_20230817_083516_nasa-asteroid-radar](https://github.com/SVENTRIPIKAL/nasa-asteroid-radar/assets/90730468/a849862d-9cbc-42cc-8166-030f32944a70)


an android application demonstrating the use of: 

- Internet data retrieval using Retrofit get requests to [NASA Open APIs](https://api.nasa.gov/)  _{user api-key required}_
- Data Persistence and offline caching using Room Database
- JSON data response parsing with Kotlinx.serialization
- Coroutine Image Loading with Picasso
- Listing dynamic data sets using RecyclerView, Adapter, ViewHolder, and DiffUtil
- Updating views dynamically using BindingAdapters
- Daily background thread work with CoroutineWorkers


![Screenshot_20230817_083539_nasa-asteroid-radar](https://github.com/SVENTRIPIKAL/nasa-asteroid-radar/assets/90730468/7314d36c-de89-4944-ad3d-d373d824a8fe)


## Features

- NASA's Astronomoy Picture of the Day (APOD)
- NASA's Nearth Earth Object Web Service (Asteroids NeoWs)
- Asteroid data updated/cached daily and stored for up to 1-week
- Asteroid database list filtering by today, this week, or all
- An internet connection is not needed to view cached data


![Screenshot_20230817_083549_nasa-asteroid-radar](https://github.com/SVENTRIPIKAL/nasa-asteroid-radar/assets/90730468/7d0c2ed3-6ac2-40f8-93b0-102d1a45c186)


## Dependencies

- [Retrofit](https://square.github.io/retrofit/) - a type-safe HTTP client for Android and Java
- [Picasso](https://square.github.io/picasso/) - powerful image downloading and caching library for Android
- [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization/) - kotlin multiplatform / multi-format serialization
- [Scalars Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/scalars) - converter which supports converting strings and primitives to text/plain bodies
- [Kotlin Coroutines] - asynchronous programming
- [Androidx ViewModel] - business logic or screen level state holder
- [Androidx Room] – persistence library providing database access
- [Androidx RecyclerView] - recyclable UI for displaying large data sets
- [Androidx WorkManager] - deferrable, reliable, asynchronous tasking
- [Androidx Navigation] - framework for navigating across application destinations
