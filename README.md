# Flickr Images App

Sample app for fetching images by search query using Flickr Public API. 
> More information about the used endpoint can be found here - https://www.flickr.com/services/api/explore/flickr.photos.search

The app lets user enter search query, fetches photos based on the query and displays them in a 3-column scrollable view. It has endless scrolling functionality which means that more images will be fetched when user scrolls down to the bottom of the view. 

The application is built for Android OS using Android Studio, Gradle and Kotlin. Chosen architecture is MVP using the Repository pattern. Unit tests are included for the presenters. Since the base requirement is not to use any third party libraries, I have chosen to go with MVP instead of MVVM. MVVM will be better cause the ViewModel outlives lifecycle changes and can store already fetched data. Also LiveData can properly be used with MVVM. All HTTP requests are done on a separate thread with the help of AsyncTask class. Memory image caching is also implemented using LruCache.

For future improvements of the app, the following cases should be handled:
- Device orientation change. At the moment when the user rotates the device, data is cleared
- Storage caching. At the moment only memory caching is available
- Background requests cancellation if view is destroyed