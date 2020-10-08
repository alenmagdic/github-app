package hr.alenmagdic.githubapp.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import hr.alenmagdic.githubapp.R
import hr.alenmagdic.githubapp.app.model.RepositoryModel
import hr.alenmagdic.githubapp.app.model.UserModel

class RepositoryAdapter(
    private val repositoryClickListener: RepositoryClickListener,
    private val userClickListener: UserClickListener
) : PagedListAdapter<RepositoryModel, RepositoryAdapter.ViewHolder>(PaginationItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var name = view.findViewById<TextView>(R.id.text_repository_name)
        private var ownerName = view.findViewById<TextView>(R.id.text_owner_username)
        private var ownerThumbnail = view.findViewById<ImageView>(R.id.image_owner_thumbnail)
        private var forks = view.findViewById<TextView>(R.id.text_forks_value)
        private var issues = view.findViewById<TextView>(R.id.text_issues_value)
        private var watchers = view.findViewById<TextView>(R.id.text_watchers_value)

        fun bind(repository: RepositoryModel) {
            name.text = repository.name
            ownerName.text = repository.owner.username
            forks.text = repository.forksCount.toString()
            issues.text = repository.issuesCount.toString()
            watchers.text = repository.watchersCount.toString()

            Glide.with(itemView.context)
                .load(repository.owner.logoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ownerThumbnail)

            itemView.setOnClickListener {
                repositoryClickListener.onRepositoryClick(repository)
            }

            ownerThumbnail.setOnClickListener {
                userClickListener.onUserClicked(repository.owner)
            }
        }

    }

    interface RepositoryClickListener {
        fun onRepositoryClick(repository: RepositoryModel)
    }

    interface UserClickListener {
        fun onUserClicked(user: UserModel)
    }

    object PaginationItemCallback : DiffUtil.ItemCallback<RepositoryModel?>() {

        override fun areContentsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}