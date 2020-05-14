package fr.piotrfleury.simpleapi.models

import java.util.*

data class Skill(
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val rating: Int,
        val ratingMax: Int = 5
)