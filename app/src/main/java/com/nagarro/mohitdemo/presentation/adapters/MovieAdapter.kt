package com.nagarro.mohitdemo.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nagarro.mohitdemo.databinding.ItemMovieBinding
import com.nagarro.mohitdemo.data.models.MovieDetailDto
import com.nagarro.mohitdemo.util.Constants
import com.nagarro.mohitdemo.util.loadImage

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: ItemMovieBinding):
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<MovieDetailDto>(){
        override fun areItemsTheSame(oldItem: MovieDetailDto, newItem: MovieDetailDto): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: MovieDetailDto, newItem: MovieDetailDto): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var moviesList: List<MovieDetailDto>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.binding.apply {
            tvNameCharacter.text = movie.title
            loadImage(imgCharacter,
                Constants.IMAGE_BASE_PATH,
                movie.posterPath.toString())
        }

        holder.itemView.setOnClickListener{
            onItemClickListener?.let {
                it(movie)
            }
        }
    }

    private var onItemClickListener: ((MovieDetailDto) -> Unit)? =  null

    fun setOnClickListener(listener: (MovieDetailDto) -> Unit){
        onItemClickListener = listener
    }
}