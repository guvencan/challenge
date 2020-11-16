package karpat.guvencan.challenge.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import karpat.guvencan.challenge.common.Constants
import karpat.guvencan.challenge.data.local.AppDatabase
import karpat.guvencan.challenge.data.local.FavouriteDao
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DbModule {

    @Provides
    @Named("DatabaseName")
    fun provideDatabaseName() = Constants.DBNAME

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context,
        @Named("DatabaseName") dbNamed: String
    ) =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            dbNamed
        ).fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideRepoDao(appDatabase: AppDatabase): FavouriteDao {
        return appDatabase.favDao()
    }
}