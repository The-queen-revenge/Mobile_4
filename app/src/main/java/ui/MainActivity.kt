package com.example.zaebalsay.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zaebalsay.R
import com.example.zaebalsay.adapter.NotesAdapter
import com.example.zaebalsay.data.Note
import com.example.zaebalsay.data.NotesRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter
    private lateinit var emptyState: View
    private lateinit var tvTitle: TextView
    private lateinit var ivSearch: ImageView
    private lateinit var ivSort: ImageView
    private lateinit var fabAdd: FloatingActionButton

    private val repository = NotesRepository()
    private var isShowingFavorites = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupClickListeners()
        loadNotes()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        emptyState = findViewById(R.id.emptyState)
        tvTitle = findViewById(R.id.tvTitle)
        ivSearch = findViewById(R.id.ivSearch)
        ivSort = findViewById(R.id.ivSort)
        fabAdd = findViewById(R.id.fabAdd)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = NotesAdapter(
            onNoteClick = { note ->
                openNoteDetail(note)
            },
            onNoteLongClick = { note ->
                repository.toggleFavorite(note.id)
                loadNotes()
            }
        )

        recyclerView.adapter = adapter
    }

    private fun setupClickListeners() {
        fabAdd.setOnClickListener {
            openNoteDetail(null)
        }

        ivSearch.setOnClickListener {
            // Поиск (можно добавить позже)
            tvTitle.text = "Поиск заметок"
        }

        ivSort.setOnClickListener {
            isShowingFavorites = !isShowingFavorites
            if (isShowingFavorites) {
                tvTitle.text = "Избранное"
                val favorites = repository.getAllNotes().filter { it.isFavorite }
                adapter.updateNotes(favorites)
            } else {
                tvTitle.text = "Все заметки"
                loadNotes()
            }
        }
    }

    private fun loadNotes() {
        val notes = repository.getAllNotes()
        adapter.updateNotes(notes)

        // Показываем/скрываем состояние "нет заметок"
        if (notes.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyState.visibility = View.GONE
        }
    }

    private fun openNoteDetail(note: Note?) {
        val intent = Intent(this, NoteDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
        tvTitle.text = "Мои заметки"
        isShowingFavorites = false
    }
}