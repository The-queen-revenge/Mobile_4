package com.example.Dev.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Dev.R
import com.example.Dev.data.Note
import com.example.Dev.data.NotesRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class NoteDetailActivity : AppCompatActivity() {
    private lateinit var etTitle: TextInputEditText
    private lateinit var etContent: TextInputEditText
    private lateinit var btnSave: MaterialButton
    private lateinit var btnFavorite: MaterialButton
    private lateinit var ivBack: ImageView
    private lateinit var ivDelete: ImageView
    private lateinit var tvTitle: MaterialTextView

    private val repository = NotesRepository()
    private var currentNote: Note? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        initViews()
        loadNoteData()
        setupClickListeners()
    }

    private fun initViews() {
        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        btnSave = findViewById(R.id.btnSave)
        btnFavorite = findViewById(R.id.btnFavorite)
        ivBack = findViewById(R.id.ivBack)
        ivDelete = findViewById(R.id.ivDelete)
        tvTitle = findViewById(R.id.tvTitle)
    }

    private fun loadNoteData() {
        val noteId = intent.getStringExtra("note_id")

        if (noteId != null) {
            // Редактирование существующей заметки
            currentNote = repository.getNoteById(noteId)
            currentNote?.let { note ->
                etTitle.setText(note.title)
                etContent.setText(note.content)
                isFavorite = note.isFavorite
                updateFavoriteButton()
                tvTitle.text = "Редактировать"
            }
        } else {
            // Создание новой заметки
            currentNote = Note()
            tvTitle.text = "Новая заметка"
        }
    }

    private fun setupClickListeners() {
        ivBack.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            saveNote()
        }

        btnFavorite.setOnClickListener {
            toggleFavorite()
        }

        ivDelete.setOnClickListener {
            deleteNote()
        }
    }

    private fun saveNote() {
        val title = etTitle.text.toString().trim()
        val content = etContent.text.toString().trim()

        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, "Заметка не может быть пустой", Toast.LENGTH_SHORT).show()
            return
        }

        currentNote?.let { note ->
            note.title = title
            note.content = content
            note.isFavorite = isFavorite

            if (repository.getNoteById(note.id) != null) {
                // Обновление существующей заметки
                repository.updateNote(note)
                Toast.makeText(this, "Заметка обновлена", Toast.LENGTH_SHORT).show()
            } else {
                // Добавление новой заметки
                repository.addNote(note)
                Toast.makeText(this, "Заметка сохранена", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }

    private fun toggleFavorite() {
        isFavorite = !isFavorite
        updateFavoriteButton()
    }

    private fun updateFavoriteButton() {
        if (isFavorite) {
            btnFavorite.icon = getDrawable(android.R.drawable.btn_star_big_on)
            btnFavorite.text = "В избранном"
        } else {
            btnFavorite.icon = getDrawable(android.R.drawable.btn_star_big_off)
            btnFavorite.text = "В избранное"
        }
    }

    private fun deleteNote() {
        currentNote?.let { note ->
            if (repository.getNoteById(note.id) != null) {
                repository.deleteNote(note.id)
                Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
