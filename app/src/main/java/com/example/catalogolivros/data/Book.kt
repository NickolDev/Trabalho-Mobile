package com.example.catalogolivros.data

// Modelo imutável que representa um livro (todos os campos são val).
// subtitle, synopsis e rating são opcionais, por isso podem ser null.
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val year: Int,
    val pages: Int,
    val genre: String,
    val coverColor: String,
    val subtitle: String? = null,
    val synopsis: String? = null,
    val rating: Double? = null,
    val isFavorite: Boolean = false
)
