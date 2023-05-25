package com.example.rickmortyxml

data class Character (private val id: Int, private val name: String, private val image: String) {
    fun getId(): Int = id
    fun getName(): String = name
    fun getImage(): String = image
}