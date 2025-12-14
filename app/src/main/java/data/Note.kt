package com.example.zaebalsay.data

import java.util.*

data class Note(
    val id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var content: String = "",
    var createdAt: Date = Date(),
    var isFavorite: Boolean = false,
    var tag: String = "Личное"
) {
    // Безопасный метод для получения индекса цвета
    fun getCardColorIndex(totalColors: Int = 5): Int {
        // Используем положительный хэш-код и деление по модулю
        val hashCode = id.hashCode()
        val positiveHash = if (hashCode == Int.MIN_VALUE) 0 else Math.abs(hashCode)
        return positiveHash % totalColors
    }
}