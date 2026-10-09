package com.example.catalogolivros.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.catalogolivros.R
import com.example.catalogolivros.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BOOK_ID = "EXTRA_BOOK_ID"
        const val EXTRA_BOOK_TITLE = "EXTRA_BOOK_TITLE"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Recebe os dados enviados pela MainActivity
        val bookId = intent.getIntExtra(EXTRA_BOOK_ID, -1)
        val bookTitle = intent.getStringExtra(EXTRA_BOOK_TITLE)

        if (bookId == -1) {
            finish()
            return
        }

        supportActionBar?.title = bookTitle ?: getString(R.string.detail_title)

        // Só adiciona o fragment na primeira vez (evita duplicar ao girar a tela)
        if (savedInstanceState == null) {
            val fragment = BookDetailFragment.newInstance(bookId)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
