package com.rodrigoguerrero.flickrgallery.domain.ui

import com.rodrigoguerrero.flickrgallery.domain.repositories.PhotoRepository
import com.rodrigoguerrero.flickrgallery.domain.repositories.PhotoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainBindingModule {
    @Binds
    abstract fun bindPhotoRepository(photoRepository: PhotoRepositoryImpl): PhotoRepository
}
