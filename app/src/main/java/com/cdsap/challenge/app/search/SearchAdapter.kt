package com.cdsap.challenge.app.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cdsap.challenge.app.R
import com.cdsap.challenge.app.photo.ImageController
import com.cdsap.challenge.app.photo.PhotoViewModel


class SearchAdapter(
        val imageController: ImageController
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    val photos: MutableList<PhotoViewModel> = mutableListOf()

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
            holder.bind(photos[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(v)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setItems(photos: List<PhotoViewModel>, refresh: Boolean) {
        if (refresh) {
            this.photos.addAll(photos)
        } else {
            this.photos.clear()
            this.photos.addAll(photos)
        }
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int = photos.size

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val id: TextView = itemView.findViewById(R.id.id)
        private val image: ImageView = itemView.findViewById(R.id.image)

        fun bind(data: PhotoViewModel) {
            title.text = data.title
            id.text = data.id
            image.setImageDrawable(null)
            imageController.loadImage(image, data.url)
        }
    }
}
