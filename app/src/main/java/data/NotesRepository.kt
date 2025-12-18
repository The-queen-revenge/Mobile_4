package com.example.Dev.data

class NotesRepository {
    private val notes = mutableListOf<Note>()

    init {
        // Тестовые данные
        notes.addAll(listOf(
            Note(
                title = "Покупки на неделю",
                content = "Молоко, хлеб, яйца, сыр, фрукты, овощи",
                tag = "Покупки"
            ),
            Note(
                title = "Идеи для проекта",
                content = "Создать приложение для заметок с красивым дизайном",
                isFavorite = true,
                tag = "Работа"
            ),
            Note(
                title = "Книги для чтения",
                content = "1. Clean Code\n2. Design Patterns\n3. Kotlin в действии",
                tag = "Личное"
            ),
            Note(
                title = "Рецепт торта",
                content = "Ингредиенты:\n- Мука 300г\n- Сахар 200г\n- Яйца 3шт\n- Масло 100г",
                tag = "Рецепты"
            )
        ))
    }

    fun getAllNotes(): List<Note> = notes.toList()

    fun getNoteById(id: String): Note? = notes.find { it.id == id }

    fun addNote(note: Note) {
        notes.add(0, note)
    }

    fun updateNote(updatedNote: Note) {
        val index = notes.indexOfFirst { it.id == updatedNote.id }
        if (index != -1) {
            notes[index] = updatedNote
        }
    }

    fun deleteNote(id: String): Boolean {
        return notes.removeAll { it.id == id }
    }

    fun toggleFavorite(id: String) {
        getNoteById(id)?.let { note ->
            note.isFavorite = !note.isFavorite
            updateNote(note)
        }
    }

    fun searchNotes(query: String): List<Note> {
        return notes.filter {
            it.title.contains(query, true) || it.content.contains(query, true)
        }
    }
}