package app.sample.diandroid.kotlinsampleapp.dagger;


import javax.inject.Singleton;

import app.sample.diandroid.kotlinsampleapp.todoitems.view.ItemsListActivity;
import dagger.Component;


@Singleton
@Component(modules = arrayOf(
        AppModule::class)
)

interface AppComponent {
    fun inject(itemsActivity: ItemsListActivity)
}

