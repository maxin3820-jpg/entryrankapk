package com.entryrank.presentation.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entryrank.data.local.entity.BookEntity
import com.entryrank.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<BooksUiState>(BooksUiState.Loading)
    val uiState: StateFlow<BooksUiState> = _uiState.asStateFlow()
    
    init {
        loadBooks()
    }
    
    private fun loadBooks() {
        viewModelScope.launch {
            try {
                // Sync books from server
                launch {
                    bookRepository.syncBooks()
                }
                
                // Collect books from local database
                bookRepository.getBooks().collectLatest { books ->
                    _uiState.value = if (books.isEmpty()) {
                        BooksUiState.Empty
                    } else {
                        BooksUiState.Success(books)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = BooksUiState.Error(
                    e.message ?: "Failed to load books"
                )
            }
        }
    }
    
    fun refresh() {
        _uiState.value = BooksUiState.Loading
        loadBooks()
    }
    
    fun filterBySubject(subjectId: String?) {
        viewModelScope.launch {
            bookRepository.getBooks(subjectId).collectLatest { books ->
                _uiState.value = if (books.isEmpty()) {
                    BooksUiState.Empty
                } else {
                    BooksUiState.Success(books)
                }
            }
        }
    }
}

sealed class BooksUiState {
    object Loading : BooksUiState()
    data class Success(val books: List<BookEntity>) : BooksUiState()
    object Empty : BooksUiState()
    data class Error(val message: String) : BooksUiState()
}
