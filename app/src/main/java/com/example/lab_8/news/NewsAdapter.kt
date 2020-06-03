package com.example.lab_8.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_8.databinding.NewsViewBinding
import com.example.lab_8.network.News

class NewsAdapter( val onClickListener: OnClickListener ) :
    ListAdapter<News, NewsAdapter.NewsViewHolder>(DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holderNews: NewsViewHolder, position: Int) {
        val hackerNewsUser = getItem(position)
        holderNews.itemView.setOnClickListener {
            onClickListener.onClick(hackerNewsUser)
        }
        holderNews.bind(hackerNewsUser)
    }

    /**
     * The RepoViewHolder constructor takes the binding variable from the associated
     * RepoItemLayout, which nicely gives it access to the full [HackersNews] information.
     */
    class NewsViewHolder(private var binding: NewsViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hackerNews: News) {
            binding.news = hackerNews
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : NewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsViewBinding.inflate(layoutInflater, parent, false)

                return NewsViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [HackersNews]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.urlNews == newItem.urlNews
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [HackersNews]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [HackersNews]
     */
    class OnClickListener(val clickListener: (hackerNews: News) -> Unit) {
        fun onClick(hackerNews: News) = clickListener(hackerNews)
    }
}