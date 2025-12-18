package com.example.Dev.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.Dev.R
import com.example.Dev.data.Note
import java.text.SimpleDateFormat
import java.util.*


class NotesAdapter(
    private var notes: List<Note> = emptyList(),
    private val onNoteClick: (Note) -> Unit,
    private val onNoteLongClick: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    // Цвета для карточек
    private val cardColors = listOf(
        Color.parseColor("#FFD8E6"), // Розовый
        Color.parseColor("#D8F0FF"), // Голубой
        Color.parseColor("#E6FFD8"), // Зеленый
        Color.parseColor("#FFF8D8"), // Желтый
        Color.parseColor("#E8D8FF")  // Фиолетовый
    )

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvTag: TextView = itemView.findViewById(R.id.tvTag)
        val ivFavorite: View = itemView.findViewById(R.id.ivFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        holder.tvTitle.text = note.title.ifEmpty { "Без названия" }
        holder.tvContent.text = note.content
        holder.tvDate.text = dateFormat.format(note.createdAt)
        holder.tvTag.text = note.tag

        holder.ivFavorite.visibility = if (note.isFavorite) View.VISIBLE else View.GONE

        // Устанавливаем цвет карточки
        val colorIndex = note.getCardColorIndex()
        holder.cardView.setCardBackgroundColor(cardColors[colorIndex])

        // Клик по карточке
        holder.itemView.setOnClickListener {
            onNoteClick(note)
        }

        // Долгое нажатие
        holder.itemView.setOnLongClickListener {
            onNoteLongClick(note)
            true
        }
    }

    override fun getItemCount(): Int = notes.size

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}