package hr.alenmagdic.githubapp.app.dependencyinjection.component

import dagger.Component
import hr.alenmagdic.githubapp.app.dependencyinjection.ActivityScope
import hr.alenmagdic.githubapp.app.dependencyinjection.module.RepositoryModule
import hr.alenmagdic.githubapp.app.ui.activity.MainActivity
import hr.alenmagdic.githubapp.app.ui.activity.RepositoryDetailsActivity

@ActivityScope
@Component(modules = [RepositoryModule::class], dependencies = [AppComponent::class])
interface RepositoryComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(repositoryDetailsActivity: RepositoryDetailsActivity)
}