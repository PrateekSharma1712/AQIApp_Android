# Air Quality Monitoring App

 > Disclaimer: I haven't written code-level comments because this readme is gonna take you over design decisions and what each component in the app is for. After reading the readme, the code should be self explanatory.


**APP NAME:** AQI Monitoring

Air Quality Monitoring Application is a single activity Android app that displays cities list with
their Air Quality Index(AQI) and time recorded. The list is ordered in descending order by the AQI
value.

![Loading AQI data](/screenshots/aqi_list.png)

The app connects with web socket (ws://city-ws.herokuapp.com/) and the Aqi list updates itself with
latest data. Over the time that user spends in the app, the AQI values received per city are saved
in app runtime memory. When a city is tapped, a dialog opens up to show realtime chart of AQI for
that city.

![Loading AQI data](/screenshots/realtime.png)

## UX Considerations

As it is a single screen app, I searched and analysed some designed
on [Dribbble](https://dribbble.com/). I decided to take [this design](<https://dribbble.com/shots/13927395-Air-Quality-App-Ranking-Screen>) as reference to show list of cities with
their values on right. As per the requirement, I have added last updated at header.

## App Architecture

I have used 75% of the Clean architecture concepts where there is a UI layer (**ui**) and a Data layer (**framework/network**). For simplicity, I haven't created a domain layer and hence you'll see use of **AqiService** and **AirQualityIndexDTO** model in **MainViewModel**. If, I was to use domain layer in between, then **MainViewModel** would use repository instead of service and that repository would convert data model to domain entity. Below is a brief about decisions and my thought process behind using specific libraries and choosing a specific architecture.

### Dependency Injection

**di/AppModule.kt**: The **di** folder is to keep all the dependency injections. I have used Hilt library to inject and resolve dependencies in this app.

### Data layer

**framework/network**: This is the data layer that contains **models** which hold response from web socket.

Previous to this, I have worked with REST APIs and used Retrofit to make HTTP API calls. But, since here the data is coming from a web socket, I have used **Scarlet** that is similar to Retrofit. I referred [this article](https://notificare.com/blog/2021/05/21/WebSockets-With-Scarlet/) to integrate Scarlet.

The **AqiService** interface contains a single method `observeAqiData` which is called from **MainViewModel** from `init` block. This means that as soon as **MainViewModel** instance is attached to it's calling activity, the app will start observing data from socket. Once app is in background or killed, the socket will also close and app won't receive further data.

### UI layer

I had considered both approaches to create UI for this app - the legacy XML way and the latest Jetpack Compopse way. I preferred Jetpack Compose to be my base for screen because the code is much lesser compared to XMLs, especially when creating lists.

**ui/composables**: Consists of common UI blocks that can be part of multiple journeys in app.

**ui/journeys**: package is supposed to host journeys. As there is only one journey, I have kept files flat in this folder.

The **MainActivity** is a **ComponentActivity** that hosts only the first journey **HomeScreen**.

The **MainViewModel** works as a tying thread between the data received from network and UI. By using **LiveData**, the UI updates itself when there is change in data.

The **HomeScreen** is a **Composable** that takes care of displaying _loading text_ or the _AqiList_ or even show the _ChartDialog_ when user taps a city. Designing it this way has allowed for me to put any UI state logic to only one composable only.

**ui/theme**: Kept it as it was created by IDE for a Compose Activity. As it turned out, the app also works seemless in Dark mode. The `AQIAppTheme` composable in the **theme.kt** selects color palette as per the system theme.

#### Decision on Charts

Biggest relief came in when I was able to use a view created using XML in Jetpack Compose. Because, otherwise I would have to write UI in a time consuming way.

I had searched around for libraries and their demos for drawing charts in real time. Initially, I came across these -

- **GraphView**: <https://github.com/jjoe64/GraphView>
- **LiveChart**: <https://github.com/Pfuster12/LiveChart>

Once, I was ready with rest of the UI, I created a **Chart** Composable to host these charts. They both didn't work for me because of below reasons -

- **GraphView** was unable to calculate y-axis efficiently and the chart line was always straight whereas in LiveChart it wasn't the case.
- **LiveChart** didn't have capability to draw x-axis and even the smooth path was not possible which was one of the reason to select this library.

I had previously seen **[MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)**, but because it's bigger library that contains many chart types, I wanted a library that have 2-3 chart types so that the overall app memory is less.

But after looking at all others and their output, I had to use **MPAndroidChart** to draw charts. Even though, this library doesn't support realtime charting, I have made it possible by refreshing the chart manually with latest data received for a city.

## Time Taken

15-18 hours taken across 4 days. I started by reviewing library for web socket connection, chart. Then finalised UX design and Jetpack Compose.
