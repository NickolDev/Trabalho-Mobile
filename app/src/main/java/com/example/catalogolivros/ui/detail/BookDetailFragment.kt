package com.example.catalogolivros.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.catalogolivros.R
import com.example.catalogolivros.data.Book
import com.example.catalogolivros.data.BookRepository
import com.example.catalogolivros.databinding.FragmentBookDetailBinding
import com.example.catalogolivros.databinding.ViewInfoRowBinding

class BookDetailFragment : Fragment() {

    companion object {
        private const val ARG_BOOK_ID = "ARG_BOOK_ID"

        fun newInstance(bookId: Int): BookDetailFragment {
            val fragment = BookDetailFragment()
            val args = Bundle()
            args.putInt(ARG_BOOK_ID, bookId)
            fragment.arguments = args
            return fragment
        }
    }

    // O binding só existe entre onCreateView e onDestroyView
    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bookId = arguments?.getInt(ARG_BOOK_ID) ?: -1
        book = BookRepository.getBookById(bookId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentBook = book
        if (currentBook == null) {
            showNotFound()
            return
        }

        showBook(currentBook)

        binding.btnFavorite.setOnClickListener {
            onFavoriteClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Libera o binding para evitar vazamento de memória
        _binding = null
    }

    private fun showBook(book: Book) {
        binding.coverContainer.setBackgroundColor(Color.parseColor(book.coverColor))
        binding.tvTitle.text = book.title
        binding.tvAuthor.text = book.author

        // Subtítulo é opcional: só aparece se existir
        if (book.subtitle != null) {
            binding.tvSubtitle.text = book.subtitle
            binding.tvSubtitle.visibility = View.VISIBLE
        } else {
            binding.tvSubtitle.visibility = View.GONE
        }

        // Sinopse é opcional: usa um texto padrão quando for null
        binding.tvSynopsis.text = book.synopsis ?: getString(R.string.no_synopsis)

        val ratingText = if (book.rating != null) {
            getString(R.string.rating_value, book.rating)
        } else {
            getString(R.string.no_rating)
        }

        binding.infoContainer.removeAllViews()
        addInfoRow(R.drawable.ic_info, getString(R.string.label_genre), book.genre)
        addInfoRow(R.drawable.ic_info, getString(R.string.label_year), book.year.toString())
        addInfoRow(R.drawable.ic_info, getString(R.string.label_pages), book.pages.toString())
        addInfoRow(R.drawable.ic_star, getString(R.string.label_rating), ratingText)

        updateFavoriteButton(book)
    }

    // Infla o componente reutilizável view_info_row.xml e adiciona na tela
    private fun addInfoRow(icon: Int, label: String, value: String) {
        val rowBinding = ViewInfoRowBinding.inflate(layoutInflater, binding.infoContainer, false)
        rowBinding.ivIcon.setImageResource(icon)
        rowBinding.tvLabel.text = label
        rowBinding.tvValue.text = value
        binding.infoContainer.addView(rowBinding.root)
    }

    private fun onFavoriteClick() {
        val currentBook = book ?: return
        val updatedBook = BookRepository.toggleFavorite(currentBook.id) ?: return
        book = updatedBook

        updateFavoriteButton(updatedBook)

        val message = if (updatedBook.isFavorite) {
            getString(R.string.favorite_added)
        } else {
            getString(R.string.favorite_removed)
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateFavoriteButton(book: Book) {
        if (book.isFavorite) {
            binding.btnFavorite.text = getString(R.string.remove_favorite)
            binding.btnFavorite.setIconResource(R.drawable.ic_star)
        } else {
            binding.btnFavorite.text = getString(R.string.add_favorite)
            binding.btnFavorite.setIconResource(R.drawable.ic_star_outline)
        }
    }

    private fun showNotFound() {
        binding.tvTitle.text = getString(R.string.book_not_found)
        binding.tvAuthor.visibility = View.GONE
        binding.tvSubtitle.visibility = View.GONE
        binding.labelSynopsis.visibility = View.GONE
        binding.tvSynopsis.visibility = View.GONE
        binding.btnFavorite.visibility = View.GONE
    }
}
