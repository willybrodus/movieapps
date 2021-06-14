package com.company.myfavoritepage

import android.content.Context
import com.dicoding.whatsnewmoview.di.MyFavoriteModuleDependencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [MyFavoriteModuleDependencies::class])
interface MyFavoriteComponent {

    fun inject(activity: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(mapsModuleDependencies: MyFavoriteModuleDependencies): Builder
        fun build(): MyFavoriteComponent
    }

}