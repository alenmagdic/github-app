package hr.alenmagdic.githubapp.app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import hr.alenmagdic.githubapp.R
import hr.alenmagdic.githubapp.app.GithubApp
import hr.alenmagdic.githubapp.app.adapter.RepositoryAdapter
import hr.alenmagdic.githubapp.app.contract.RepositorySearchContract
import hr.alenmagdic.githubapp.app.dependencyinjection.component.DaggerRepositoryComponent
import hr.alenmagdic.githubapp.app.dependencyinjection.module.RepositoryModule
import hr.alenmagdic.githubapp.app.model.RepositoryModel
import hr.alenmagdic.githubapp.app.model.UserModel
import hr.alenmagdic.githubapp.domain.SortOrder
import hr.alenmagdic.githubapp.domain.model.Repository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), RepositoryAdapter.RepositoryClickListener,
    RepositoryAdapter.UserClickListener, RepositorySearchContract.ViewInterface {

    @Inject
    lateinit var presenter: RepositorySearchContract.PresenterInterface

    private lateinit var repositoryAdapter: RepositoryAdapter
    private var searchViewMenuItem: MenuItem? = null
    private var searchViewQuerySavedState: String? = null
    private var submittedQuery: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependencies()
        initViews()

        presenter.view = this
        presenter.initialize()
    }

    private fun injectDependencies() {
        DaggerRepositoryComponent.builder()
            .appComponent((application as GithubApp).appComponent)
            .repositoryModule(RepositoryModule())
            .build()
            .inject(this)
    }

    private fun initRepositoryAdapter() {
        repositoryAdapter = RepositoryAdapter(this, this)
        search_results_list.adapter = repositoryAdapter
    }

    private fun initViews() {
        initSortSpinner()

        search_results_list.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

    }

    private fun initSearchView(searchViewMenuItem: MenuItem) {
        val searchView = searchViewMenuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.hint_search_repositories)

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?) = false

                override fun onQueryTextSubmit(query: String?): Boolean {
                    this@MainActivity.submittedQuery = query
                    submitSearchQuery()
                    return true
                }
            }
        )

        searchViewMenuItem.setOnActionExpandListener(
            object : MenuItem.OnActionExpandListener {

                override fun onMenuItemActionCollapse(menuItem: MenuItem?): Boolean {
                    presenter.onSearchClose()
                    submittedQuery = null
                    return true
                }

                override fun onMenuItemActionExpand(menuItem: MenuItem?): Boolean {
                    prepareForQueryInput()
                    return true
                }
            }
        )

        if (submittedQuery != null || searchViewQuerySavedState != null) {
            searchViewMenuItem.expandActionView()
            prepareForQueryInput()
        }
        submittedQuery?.let {
            searchView.setQuery(submittedQuery, true)
        }
        searchViewQuerySavedState?.let {
            searchView.setQuery(searchViewQuerySavedState, false)
        }

    }

    private fun prepareForQueryInput() {
        presenter.onSearchOpen()
        initRepositoryAdapter()
    }

    private fun submitSearchQuery() {
        val sort = getSelectedSort()
        submittedQuery?.let {
            presenter.onSubmitSearchQuery(it, sort = sort.first, sortOrder = sort.second)
        }
    }

    private fun initSortSpinner() {
        val options = SORT_OPTIONS_MAP.keys.map { getString(it) }.toList()

        spinner_sort_by.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        spinner_sort_by.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val sort = getSelectedSort()
                    submittedQuery?.let { presenter.onChangeSort(sort.first, sort.second, it) }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun getSelectedSort() = SORT_OPTIONS_MAP.values.toList()[spinner_sort_by.selectedItemPosition]

    override fun onRepositoryClick(repository: RepositoryModel) {
        presenter.onRepositoryClick(repository)
    }

    override fun onUserClicked(user: UserModel) {
        presenter.onUserClick(user)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        searchViewMenuItem = menu?.findItem(R.id.action_search)
        searchViewMenuItem?.let { initSearchView(it) }

        return true
    }

    override fun showSearchResults(repositories: PagedList<RepositoryModel?>) {
        repositoryAdapter.submitList(repositories)
        layout_search_results.visibility = View.VISIBLE
    }

    override fun showSearchResultsTotalCount(totalCount: Int) {
        text_results_count.text = getString(R.string.label_results_count, totalCount)
    }

    override fun showNoResults() {
        text_no_results.visibility = View.VISIBLE
    }

    override fun showDataLoadFailedMessage() {
        Toast.makeText(this, getString(R.string.message_data_load_failed), Toast.LENGTH_LONG).show()
    }

    override fun showEnterQueryMessage() {
        text_enter_query.visibility = View.VISIBLE
    }

    override fun showInitialContent() {
        layout_initial_content.visibility = View.VISIBLE
    }

    override fun showRepositoryDetails(repository: RepositoryModel) {
        RepositoryDetailsActivity.startActivity(this, repository.name, repository.owner.username)
    }

    override fun showUserDetails(user: UserModel) {
        UserDetailsActivity.startActivity(this, user.username)
    }

    override fun showLoading() {
        progress_bar_loading_repos.visibility = View.VISIBLE
    }

    override fun hideEnterQueryMessage() {
        text_enter_query.visibility = View.GONE
    }

    override fun hideInitialContent() {
        layout_initial_content.visibility = View.GONE
    }

    override fun hideLoading() {
        progress_bar_loading_repos.visibility = View.GONE
    }

    override fun hideNoResults() {
        text_no_results.visibility = View.GONE
    }

    override fun hideSearchResults() {
        layout_search_results.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INSTANCE_STATE_SUBMITTED_QUERY, submittedQuery)

        if (layout_initial_content?.visibility != View.VISIBLE) {
            val searchView = searchViewMenuItem?.actionView as SearchView?
            outState.putString(INSTANCE_STATE_SEARCH_VIEW_QUERY, searchView?.query.toString())
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        submittedQuery = savedInstanceState.getString(INSTANCE_STATE_SUBMITTED_QUERY)
        searchViewQuerySavedState = savedInstanceState.getString(INSTANCE_STATE_SEARCH_VIEW_QUERY)
    }

    companion object {
        private const val INSTANCE_STATE_SUBMITTED_QUERY = "INSTANCE_STATE_PARAM_SUBMITTED_QUERY"
        private const val INSTANCE_STATE_SEARCH_VIEW_QUERY = "INSTANCE_STATE_SEARCH_VIEW_QUERY"

        private val SORT_OPTIONS_MAP = linkedMapOf(
            R.string.item_sort_by_stars_desc to Pair(Repository.Sort.STARS, SortOrder.DESCENDING),
            R.string.item_sort_by_stars_asc to Pair(Repository.Sort.STARS, SortOrder.ASCENDING),
            R.string.item_sort_by_forks_desc to Pair(Repository.Sort.FORKS, SortOrder.DESCENDING),
            R.string.item_sort_by_forks_asc to Pair(Repository.Sort.FORKS, SortOrder.ASCENDING),
            R.string.item_sort_by_updated_desc to Pair(Repository.Sort.UPDATED, SortOrder.DESCENDING),
            R.string.item_sort_by_updated_asc to Pair(Repository.Sort.UPDATED, SortOrder.ASCENDING)
        )
    }

}
