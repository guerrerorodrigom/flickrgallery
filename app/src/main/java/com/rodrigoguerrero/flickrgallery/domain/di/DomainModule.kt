package com.rodrigoguerrero.flickrgallery.domain.di

import android.content.Context
import androidx.work.WorkManager
import com.rodrigoguerrero.flickrgallery.domain.repositories.PhotoRepository
import com.rodrigoguerrero.flickrgallery.domain.repositories.PhotoRepositoryImpl
import com.rodrigoguerrero.flickrgallery.domain.repositories.SearchRepository
import com.rodrigoguerrero.flickrgallery.domain.repositories.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainBindingModule {
    @Binds
    abstract fun bindSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository

    @Binds
    abstract fun bindPhotoRepository(photoRepository: PhotoRepositoryImpl): PhotoRepository
}

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context) = WorkManager.getInstance(context)

    @Provides
    fun provideContentResolver(@ApplicationContext context: Context) = context.contentResolver
}
