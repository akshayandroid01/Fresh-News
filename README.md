# Fresh-News

This app showing multi categories news fetched from Hindustan time api.

I have used hindustan time RSS feed to show news for testing purpose. 
The UI is basic to show news listing and details.
Also app support offline mode as well.

Tech Skills implemented in app:

- Jetpack Compose : Modern UI implementation
- Room : Local Database for support offline mode
- Coil : For image loading from Url in ImageView
- Ktor Client : For call Rest Api and make network call
- Hilt Framework : Dependency Injection (DI)

Screens:
This app contains 3 Screens,
Home Screen - which contains categories list of news.
Feeds Screen - which contains single category news feed list.
Feed Detail Screen - which is for show the details of single news.

In case of no internet available, A dialog screen is there to tell no internet.

