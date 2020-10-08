package hr.alenmagdic.githubapp.app.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import hr.alenmagdic.githubapp.R
import hr.alenmagdic.githubapp.app.GithubApp
import hr.alenmagdic.githubapp.app.contract.UserDetailsContract
import hr.alenmagdic.githubapp.app.dependencyinjection.component.DaggerUserComponent
import hr.alenmagdic.githubapp.app.dependencyinjection.module.UserModule
import hr.alenmagdic.githubapp.app.model.UserModel
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.content_user_details.*
import java.text.DateFormat
import javax.inject.Inject

class UserDetailsActivity : AppCompatActivity(), UserDetailsContract.ViewInterface {

    @Inject
    lateinit var presenter: UserDetailsContract.PresenterInterface

    private val dateFormat = DateFormat.getDateTimeInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        injectDependencies()

        title = getString(R.string.title_user_details)

        presenter.view = this

        intent.getStringExtra(EXTRA_USERNAME)?.let { username ->
            presenter.initialize(username)
        }
    }

    private fun injectDependencies() {
        DaggerUserComponent.builder()
            .appComponent((application as GithubApp).appComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
    }

    override fun showUserDetails(user: UserModel) {
        layout_all_user_details.visibility = View.VISIBLE

        text_username.text = user.username
        text_user_type.text = user.type
        text_public_repos_value.text = user.publicRepositories.toString()
        text_followers_value.text = user.followersCount.toString()
        text_following_value.text = user.followingCount.toString()

        displayIfNotNull(user.name, text_name, text_name_value)
        displayIfNotNull(user.company, text_company, text_company_value)
        displayIfNotNull(user.location, text_location, text_location_value)
        displayIfNotNull(user.dateCreated?.let { dateFormat.format(it) }, text_date_created, text_date_created_value)
        displayIfNotNull(user.dateUpdated?.let { dateFormat.format(it) }, text_date_modified, text_date_modified_value)

        Glide.with(this)
            .load(user.logoUrl)
            .into(image_owner_thumbnail)

        button_view_in_browser.setOnClickListener {
            presenter.onViewUserInBrowserClick(user)
        }
    }

    override fun hideUserDetails() {
        layout_all_user_details.visibility = View.GONE
    }

    override fun showUserDetailsOnWebsite(userUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(userUrl)
        startActivity(intent)
    }

    private fun displayIfNotNull(value: String?, nameView: TextView, valueView: TextView) {
        if (value != null) {
            valueView.text = value
        } else {
            nameView.visibility = View.GONE
            valueView.visibility = View.GONE
        }
    }

    override fun showUserUnavailableMessage() {
        Toast.makeText(this, getString(R.string.message_user_unavailable), Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val EXTRA_USERNAME = "EXTRA_USERNAME"

        fun startActivity(context: Context, username: String) {
            val startActivityIntent = Intent(context, UserDetailsActivity::class.java)
            startActivityIntent.putExtra(EXTRA_USERNAME, username)

            context.startActivity(startActivityIntent)
        }
    }
}
