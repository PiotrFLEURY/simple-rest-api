package fr.piotrfleury.simpleapi.services.impl

import fr.piotrfleury.simpleapi.models.*
import fr.piotrfleury.simpleapi.services.TeamService
import org.springframework.stereotype.Service

@Service
class TeamServiceImpl: TeamService {

    private final val teamSample = Team(
            name = "The best ones",
            members = mutableListOf(TeamMember(
                    name = "FLEURY",
                    firstName = "Piotr",
                    age = 33,
                    role = Role.TECH_LEAD,
                    skills = mutableListOf(Skill(
                            name = "Kotlin",
                            rating = 4
                    ))
            ))
    )

    val teams = mutableListOf(teamSample)

    override fun createTeam(team: Team) {
        teams.add(team)
    }

    override fun getAllTeams(): Teams {
        return Teams(teams)
    }

    override fun getTeam(id: String): Team? {
        return teams.firstOrNull { it.id == id }
    }

    override fun updateTeam(team: Team): Team? {
        teams.firstOrNull { it.id == team.id }?.let {
            teams.remove(it)
            teams.add(team)
            return team
        }
        return null
    }

    override fun deleteTeam(id: String): Team? {
        teams.firstOrNull { it.id == id }?.let {
            teams.remove(it)
            return it
        }
        return null
    }

    override fun addMember(teamId: String, teamMember: TeamMember): TeamMember? {
        return getTeam(teamId)?.members?.add(teamMember)?.let { teamMember }
    }

    override fun getMember(teamId: String, memberId: String): TeamMember? {
        return getTeam(teamId)?.members?.firstOrNull { it.id == memberId }
    }

    override fun editMember(teamId: String, teamMember: TeamMember): TeamMember? {
        getTeam(teamId)?.let {team ->
            team.members.firstOrNull { it.id == teamMember.id }?.let {
                team.members.remove(it)
                team.members.add(teamMember)
                return teamMember
            }
        }
        return null
    }

    override fun deleteMember(teamId: String, memberId: String): TeamMember? {
        getTeam(teamId)?.members?.let { members ->
            members.firstOrNull { it.id == memberId }?.let {
                members.remove(it)
                return it
            }
        }
        return null
    }

    override fun addSkill(teamId: String, memberId: String, skill: Skill): Skill? {
        getMember(teamId, memberId)?.let { member ->
            member.skills.add(skill)
            return skill
        }
        return null
    }

    override fun getSkill(teamId: String, memberId: String, skillId: String): Skill? {
        return getMember(teamId, memberId)?.skills?.firstOrNull { it.id == skillId }
    }

    override fun updateSkill(teamId: String, memberId: String, skill: Skill): Skill? {
        getMember(teamId, memberId)?.let { member ->
            member.skills.firstOrNull { it.id == skill.id }?.let {
                member.skills.remove(it)
                member.skills.add(skill)
                return skill
            }
        }
        return null
    }

    override fun deleteSkill(teamId: String, memberId: String, skillId: String): Skill? {
        getMember(teamId, memberId)?.let { member ->
            member.skills.firstOrNull { it.id == skillId }?.let {
                member.skills.remove(it)
                return it
            }
        }
        return null
    }
}