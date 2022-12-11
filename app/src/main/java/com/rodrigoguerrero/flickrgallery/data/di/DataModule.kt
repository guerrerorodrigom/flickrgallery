package com.rodrigoguerrero.flickrgallery.data.di

import android.content.Context
import androidx.room.Room
import com.rodrigoguerrero.flickrgallery.data.network.services.PhotoService
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.SearchDataSource
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.SearchDataSourceImpl
import com.rodrigoguerrero.flickrgallery.data.storage.FlickrDatabase
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.PhotoDataSource
import com.rodrigoguerrero.flickrgallery.data.storage.datasources.PhotoDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun providePhotoService(okHttpClient: OkHttpClient): PhotoService {
        return Retrofit.Builder()
            .baseUrl("https://www.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PhotoService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("api_key", "1508443e49213ff84d566777dc211f2a")
                    .addQueryParameter("format", "json")
                    .addQueryParameter("nojsoncallback", "1")
                    .build()
                val request = chain.request().newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FlickrDatabase {
        return Room.databaseBuilder(context, FlickrDatabase::class.java, "flickr_db").build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindingModule {
    @Binds
    abstract fun bindSearchDataSource(dataSource: SearchDataSourceImpl): SearchDataSource

    @Binds
    abstract fun bindPhotosDataSource(dataSource: PhotoDataSourceImpl): PhotoDataSource
}
