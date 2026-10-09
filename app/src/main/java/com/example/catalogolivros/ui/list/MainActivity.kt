package com.example.catalogolivros.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalogolivros.R
import com.example.catalogolivros.data.Book
import com.example.catalogolivros.data.BookRepository
import com.example.catalogolivros.databinding.ActivityMainBinding
import com.example.catalogolivros.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        adapter = BookAdapter(emptyList()) { book ->
            openDetails(book)
        }
        binding.rvBooks.layoutManager = LinearLayoutManager(this)
        binding.rvBooks.adapter = adapter

        // Quando o switch muda, a lista é filtrada novamente
        binding.switchFavorites.setOnCheckedChangeListener { _, _ ->
            loadBooks()
        }
    }

    override fun onResume() {
        super.onResume()
        // Recarrega ao voltar da tela de detalhes, pois o favorito pode ter mudado
        loadBooks()
    }

    private fun loadBooks() {
        var books = BookRepository.getBooks()

        if (binding.switchFavorites.isChecked) {
            books = books.filter { it.isFavorite }
        }

        adapter.updateBooks(books)
        binding.tvCount.text = getString(R.string.books_count, books.size)

        if (books.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvBooks.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvBooks.visibility = View.VISIBLE
        }
    }

    private fun openDetails(book: Book) {
        // Intent explícita: indica exatamente qual Activity será aberta
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_BOOK_ID, book.id)
        intent.putExtra(DetailActivity.EXTRA_BOOK_TITLE, book.title)
        startActivity(intent)
    }
}
