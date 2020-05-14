package fr.piotrfleury.simpleapi.models

import java.util.*

data class TeamMember(
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val firstName: String,
        val age: Int,
        val role: Role,
        val skills: MutableList<Skill>
)