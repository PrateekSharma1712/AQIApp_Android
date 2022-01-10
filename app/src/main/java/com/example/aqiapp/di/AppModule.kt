package com.example.aqiapp.di

import android.content.Context
import com.example.aqiapp.AQIApplication
import com.example.aqiapp.framework.network.services.AqiService
import com.example.aqiapp.framework.network.services.FlowStreamAdapter
import com.squareup.moshi.Moshi
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.lifecycle.android.BuildConfig
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  fun providesApplication(@ApplicationContext context: Context): AQIApplication {
    return context as AQIApplication
  }

  @Provides
  fun provideMoshi(): Moshi {
    return Moshi.Builder()
      .build()
  }

  @Provides
  fun provideHttpClient(): OkHttpClient {
    val logger = HttpLoggingInterceptor()
      .setLevel(
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
        else HttpLoggingInterceptor.Level.NONE
      )

    return OkHttpClient.Builder()
      .addInterceptor(logger)
      .build()
  }

  @ExperimentalCoroutinesApi
  @Provides
  fun provideScarlet(application: AQIApplication, client: OkHttpClient, moshi: Moshi): Scarlet {
    return Scarlet.Builder()
      .webSocketFactory(client.newWebSocketFactory("ws://city-ws.herokuapp.com/"))
      .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
      .addStreamAdapterFactory(FlowStreamAdapter.Factory())
      .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
      .build()
  }

  @Provides
  fun provideAqiService(scarlet: Scarlet): AqiService {
    return scarlet.create()
  }
}