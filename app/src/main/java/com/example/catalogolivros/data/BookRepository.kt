package com.example.catalogolivros.data

// Dados simulados (mock). Nesta etapa não usamos API nem banco de dados.
object BookRepository {

    private val books = listOf(
        Book(
            id = 1,
            title = "Dom Casmurro",
            author = "Machado de Assis",
            year = 1899,
            pages = 256,
            genre = "Romance",
            coverColor = "#6D4C41",
            synopsis = "Bentinho narra sua história com Capitu e a dúvida que o acompanha por toda a vida.",
            rating = 4.6
        ),
        Book(
            id = 2,
            title = "O Hobbit",
            author = "J. R. R. Tolkien",
            year = 1937,
            pages = 336,
            genre = "Fantasia",
            coverColor = "#2E7D32",
            subtitle = "Lá e de volta outra vez",
            synopsis = "Bilbo Bolseiro deixa o Condado para acompanhar um grupo de anões em busca de um tesouro guardado por um dragão.",
            rating = 4.8
        ),
        Book(
            id = 3,
            title = "Código Limpo",
            author = "Robert C. Martin",
            year = 2008,
            pages = 464,
            genre = "Tecnologia",
            coverColor = "#1565C0",
            subtitle = "Habilidades práticas do Agile Software",
            synopsis = "Boas práticas para escrever código legível, fácil de manter e de testar.",
            rating = 4.4
        ),
        Book(
            id = 4,
            title = "1984",
            author = "George Orwell",
            year = 1949,
            pages = 416,
            genre = "Ficção científica",
            coverColor = "#B71C1C",
            synopsis = "Em um governo totalitário que vigia tudo, Winston Smith começa a questionar o sistema.",
            rating = 4.7
        ),
        Book(
            id = 5,
            title = "Vidas Secas",
            author = "Graciliano Ramos",
            year = 1938,
            pages = 176,
            genre = "Romance",
            coverColor = "#F9A825",
            synopsis = "Uma família de retirantes enfrenta a seca e a miséria no sertão nordestino."
            // sem avaliação (rating = null)
        ),
        Book(
            id = 6,
            title = "O Programador Pragmático",
            author = "David Thomas e Andrew Hunt",
            year = 1999,
            pages = 352,
            genre = "Tecnologia",
            coverColor = "#00695C",
            subtitle = "De aprendiz a mestre"
            // sem sinopse e sem avaliação
        )
    )

    // Guarda os ids dos livros favoritados enquanto o app está aberto
    private val favoriteIds = mutableSetOf<Int>()

    fun getBooks(): List<Book> {
        // copy() cria um novo objeto, o original da lista não é alterado
        return books.map { book -> book.copy(isFavorite = favoriteIds.contains(book.id)) }
    }

    fun getBookById(id: Int): Book? {
        val book = books.find { it.id == id } ?: return null
        return book.copy(isFavorite = favoriteIds.contains(id))
    }

    fun toggleFavorite(id: Int): Book? {
        if (getBookById(id) == null) return null

        if (favoriteIds.contains(id)) {
            favoriteIds.remove(id)
        } else {
            favoriteIds.add(id)
        }
        return getBookById(id)
    }
}
