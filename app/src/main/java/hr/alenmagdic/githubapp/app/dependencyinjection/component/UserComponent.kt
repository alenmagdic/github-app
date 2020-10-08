package hr.alenmagdic.githubapp.app.dependencyinjection.component

import dagger.Component
import hr.alenmagdic.githubapp.app.dependencyinjection.ActivityScope
import hr.alenmagdic.githubapp.app.dependencyinjection.module.UserModule
import hr.alenmagdic.githubapp.app.ui.activity.UserDetailsActivity

@ActivityScope
@Component(modules = [UserModule::class], dependencies = [AppComponent::class])
interface UserComponent {
    fun inject(userDetailsActivity: UserDetailsActivity)
}