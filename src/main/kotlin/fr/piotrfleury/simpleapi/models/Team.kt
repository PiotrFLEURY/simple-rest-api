package fr.piotrfleury.simpleapi.models

import java.util.*

data class Team(
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val members: MutableList<TeamMember>
)