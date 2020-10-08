package hr.alenmagdic.githubapp.app.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import hr.alenmagdic.githubapp.R
import hr.alenmagdic.githubapp.app.GithubApp
import hr.alenmagdic.githubapp.app.contract.RepositoryDetailsContract
import hr.alenmagdic.githubapp.app.dependencyinjection.component.DaggerRepositoryComponent
import hr.alenmagdic.githubapp.app.dependencyinjection.module.RepositoryModule
import hr.alenmagdic.githubapp.app.model.RepositoryModel
import hr.alenmagdic.githubapp.app.model.UserModel
import kotlinx.android.synthetic.main.activity_repository_details.*
import java.text.DateFormat
import javax.inject.Inject

class RepositoryDetailsActivity : AppCompatActivity(), RepositoryDetailsContract.ViewInterface {
    @Inject
    lateinit var presenter: RepositoryDetailsContract.PresenterInterface

    private val dateFormat = DateFormat.getDateTimeInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_details)
        injectDependencies()

        title = getString(R.string.title_repository_details)

        presenter.view = this

        val repositoryName = intent.getStringExtra(EXTRA_REPOSITORY_NAME)
        val repositoryOwnerUsername = intent.getStringExtra(EXTRA_REPOSITORY_OWNER_USERNAME)

        if (repositoryName != null && repositoryOwnerUsername != null) {
            presenter.initialize(repositoryName, repositoryOwnerUsername)
        }

    }

    private fun injectDependencies() {
        DaggerRepositoryComponent.builder()
            .appComponent((application as GithubApp).appComponent)
            .repositoryModule(RepositoryModule())
            .build()
            .inject(this)
    }

    override fun showRepositoryDetails(repository: RepositoryModel) {
        layout_repository_details.visibility = View.VISIBLE

        text_repository_name.text = repository.name
        text_repository_description.text = repository.description
        text_watchers_value.text = repository.watchersCount.toString()
        text_stars_value.text = repository.starsCount.toString()
        text_forks_value.text = repository.forksCount.toString()
        text_issues_value.text = repository.issuesCount.toString()
        text_language_value.text = repository.programmingLanguage
        text_date_created_value.text = dateFormat.format(repository.dateCreated)
        text_date_modified_value.text = dateFormat.format(repository.dateUpdated)
        text_owner_name.text = repository.owner.username
        text_owner_type.text = repository.owner.type

        Glide.with(this)
            .load(repository.owner.logoUrl)
            .into(image_owner_thumbnail)

        button_view_in_browser.setOnClickListener {
            presenter.onViewRepositoryInBrowserClick(repository)
        }

        image_owner_thumbnail.setOnClickListener {
            presenter.onUserClick(repository.owner)
        }
    }

    override fun hideRepositoryDetails() {
        layout_repository_details.visibility = View.GONE
    }

    override fun showRepositoryDetailsOnWebsite(repositoryUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(repositoryUrl)
        startActivity(intent)
    }

    override fun showUserDetails(user: UserModel) {
        UserDetailsActivity.startActivity(this, user.username)
    }

    override fun showRepositoryUnavailableMessage() {
        Toast.makeText(this, getString(R.string.message_repository_unavailable), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    companion object {
        private const val EXTRA_REPOSITORY_OWNER_USERNAME = "EXTRA_REPOSITORY_OWNER_USERNAME"
        private const val EXTRA_REPOSITORY_NAME = "EXTRA_REPOSITORY_NAME"

        fun startActivity(context: Context, repositoryName: String, repositoryOwnerUsername: String) {
            val startActivityIntent = Intent(context, RepositoryDetailsActivity::class.java)
            startActivityIntent.putExtra(EXTRA_REPOSITORY_NAME, repositoryName)
            startActivityIntent.putExtra(EXTRA_REPOSITORY_OWNER_USERNAME, repositoryOwnerUsername)

            context.startActivity(startActivityIntent)
        }
    }
}
