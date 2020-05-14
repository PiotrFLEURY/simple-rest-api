package fr.piotrfleury.simpleapi.services

import fr.piotrfleury.simpleapi.models.Skill
import fr.piotrfleury.simpleapi.models.Team
import fr.piotrfleury.simpleapi.models.TeamMember
import fr.piotrfleury.simpleapi.models.Teams

interface TeamService {

    fun createTeam(team: Team)

    fun getTeam(id: String): Team?

    fun updateTeam(team: Team): Team?

    fun deleteTeam(id: String): Team?

    fun getAllTeams(): Teams

    fun addMember(teamId: String, teamMember: TeamMember): TeamMember?

    fun editMember(teamId: String, teamMember: TeamMember): TeamMember?

    fun getMember(teamId: String, memberId: String): TeamMember?

    fun deleteMember(teamId: String, memberId: String): TeamMember?

    fun addSkill(teamId: String, memberId: String, skill: Skill): Skill?

    fun getSkill(teamId: String, memberId: String, skillId: String): Skill?

    fun updateSkill(teamId: String, memberId: String, skill: Skill): Skill?

    fun deleteSkill(teamId: String, memberId: String, skillId: String): Skill?

}