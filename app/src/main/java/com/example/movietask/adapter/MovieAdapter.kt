package com.example.movietask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movietask.R
import com.example.movietask.models.Result
import com.example.movietask.util.OnMovieClickListener
import com.squareup.picasso.Picasso
import org.koin.androidx.compose.get

const val restOfUrl = "https://image.tmdb.org/t/p/w500/"

class MovieAdapter(var context: Context, var onMovieClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

     var movieList: ArrayList<Result> = arrayListOf()
    public fun setMovieList(resultList: List<Result>) {
        movieList.addAll(resultList)
        notifyDataSetChanged()
    }

    public fun setMovieListAndDeleteOld(resultList: List<Result>) {
        movieList.clear()
        movieList.addAll(resultList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item, null)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        var currentResult = movieList.get(position)
        holder.bind(currentResult, onMovieClickListener, movieList)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var imageView: ImageView
        lateinit var txtMovieName: TextView
        lateinit var txtPublishDate: TextView
        lateinit var txtRate: TextView

        init {
            imageView = itemView.findViewById(R.id.movieItem_imageView)
            txtMovieName = itemView.findViewById(R.id.movieItem_TV_movieName)
            txtPublishDate = itemView.findViewById(R.id.movieItem_TV_moviepublishDate)
            txtRate = itemView.findViewById(R.id.movieItem_TV_movieRate)

        }

        fun bind(
            currentResult: Result,
            onMovieClickListener: OnMovieClickListener,
            movieList: List<Result>
        ) {

            Picasso.get().load(currentResult.poster_path).fit().centerCrop()
                .into(imageView)
            txtMovieName.setText(currentResult.title)
            txtPublishDate.setText(currentResult.release_date)
            txtRate.setText(currentResult.vote_average.toString())
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onMovieClickListener.onClick(movieList.get(adapterPosition))
                }
            }


        }


    }


}