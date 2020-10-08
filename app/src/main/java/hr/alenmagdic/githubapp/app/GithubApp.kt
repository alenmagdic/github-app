package hr.alenmagdic.githubapp.app

import android.app.Application
import hr.alenmagdic.githubapp.app.dependencyinjection.component.AppComponent
import hr.alenmagdic.githubapp.app.dependencyinjection.component.DaggerAppComponent
import hr.alenmagdic.githubapp.app.dependencyinjection.module.AppModule

class GithubApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()
    }
}