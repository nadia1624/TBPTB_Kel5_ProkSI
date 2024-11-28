package com.example.proksi_tbptb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent::class)
@Module
public class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("http://localhost:3000/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

}
