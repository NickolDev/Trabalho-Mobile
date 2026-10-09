package com.example.catalogolivros.ui.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catalogolivros.R
import com.example.catalogolivros.data.Book
import com.example.catalogolivros.databinding.ItemBookBinding

class BookAdapter(
    private var books: List<Book>,
    private val onItemClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        // Infla o layout reutilizável de cada item (item_book.xml)
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        val context = holder.itemView.context

        holder.binding.tvTitle.text = book.title
        holder.binding.tvAuthor.text = book.author
        holder.binding.tvMeta.text = context.getString(R.string.book_meta, book.genre, book.year)
        holder.binding.cover.setBackgroundColor(Color.parseColor(book.coverColor))

        // rating é opcional
        if (book.rating != null) {
            holder.binding.tvRating.text = context.getString(R.string.rating_format, book.rating)
        } else {
            holder.binding.tvRating.text = context.getString(R.string.no_rating)
        }

        holder.binding.ivFavorite.visibility = if (book.isFavorite) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            onItemClick(book)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateBooks(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
