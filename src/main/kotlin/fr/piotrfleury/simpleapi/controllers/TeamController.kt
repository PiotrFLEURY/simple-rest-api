package fr.piotrfleury.simpleapi.controllers

import fr.piotrfleury.simpleapi.models.Skill
import fr.piotrfleury.simpleapi.models.Team
import fr.piotrfleury.simpleapi.models.TeamMember
import fr.piotrfleury.simpleapi.models.Teams
import fr.piotrfleury.simpleapi.services.TeamService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/team-api/teams")
@Tag(name = "Team management API", description = "Manage team members, skills and informations")
class TeamController {

    @Autowired
    lateinit var teamService: TeamService

    // TEAM MANAGEMENT

    @Operation(summary = "creates a new team", description = "save the team in the data repository")
    @ApiResponses(ApiResponse(responseCode = "201", description = "Team created"))
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewTeam(@RequestBody team: Team) {
        teamService.createTeam(team)
    }

    @Operation(summary = "get all teams", description = "return all existing teams")
    @ApiResponses(
            ApiResponse(
                    responseCode = "200",
                    description = "Teams returned"
            ))
    @GetMapping("/", produces = ["application/json"])
    fun getAllTeams(): Teams {
        return teamService.getAllTeams()
    }

    @Operation(summary = "get a team", description = "search team by id and return it if exists")
    @ApiResponses(
            ApiResponse(
                    responseCode = "200",
                    description = "Team found"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "team not found"
            ))
    @GetMapping("/{id}/", produces = ["application/json"])
    fun getTeam(@PathVariable("id") id: String): Team {
        val team = teamService.getTeam(id)
        if (team != null) {
            return team
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No team found for id $id")
        }
    }

    @Operation(summary = "updaye a team", description = "search team by id update it and return it if exists")
    @ApiResponses(
            ApiResponse(
                    responseCode = "200",
                    description = "Team updated"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "team not found"
            ))
    @PutMapping("/", produces = ["application/json"])
    fun updateTeam(@RequestBody requestTeam: Team): Team {
        val team = teamService.updateTeam(requestTeam)
        if (team != null) {
            return team
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No team found for id ${requestTeam.id}")
        }
    }

    @Operation(summary = "delete a team", description = "search team by id and delete it if exists")
    @ApiResponses(
            ApiResponse(
                    responseCode = "202",
                    description = "Request accepted, Team deleted"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "team not found"
            ))
    @DeleteMapping("/{id}/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteTeam(@PathVariable("id") id: String) {
        teamService.deleteTeam(id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No team found for id $id")
    }

    // TEAM MEMBER MANAGEMENT

    @Operation(summary = "Add a member to a team", description = "Try to add a new member into an existing team")
    @ApiResponses(
            ApiResponse(
                    responseCode = "200",
                    description = "Team member added"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "Team not found"
            )
    )
    @PostMapping("/{id}/members/", produces = ["application/json"])
    fun addTeamMember(@PathVariable("id") teamId: String, @RequestBody teamMember: TeamMember): TeamMember {
        return teamService.addMember(teamId, teamMember) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No team found for id $teamId")
    }

    @Operation(summary = "Get a team member", description = "Try to find an existing team member")
    @ApiResponses(
            ApiResponse(
                    responseCode = "200",
                    description = "Team member found"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "Team member not found"
            )
    )
    @GetMapping("/{id}/members/{memberId}/", produces = ["application/json"])
    fun getTeamMember(@PathVariable("id") teamId: String, @PathVariable("memberId") memberId: String): TeamMember {
        return teamService.getMember(teamId, memberId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No team member found for team id $teamId and member id $memberId")
    }

    @Operation(summary = "Edit a team member", description = "Try to edit an existing team member")
    @ApiResponses(
            ApiResponse(
                    responseCode = "200",
                    description = "Team member edited"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "Team or team member not found"
            )
    )
    @PutMapping("/{id}/members/", produces = ["application/json"])
    fun editTeamMember(
            @PathVariable("id") teamId: String,
            @RequestBody teamMember: TeamMember
    ): TeamMember {
        return teamService.editMember(teamId, teamMember) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No team found for id $teamId")
    }

    @Operation(summary = "Delete a team member", description = "Try to delete an existing team member")
    @ApiResponses(
            ApiResponse(
                    responseCode = "202",
                    description = "Team member deleted"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "Team or team member not found"
            )
    )
    @DeleteMapping("/{id}/members/{memberId}/", produces = ["application/json"])
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteTeamMember(
            @PathVariable("id") teamId: String,
            @PathVariable("memberId") memberId: String
    ): TeamMember {
        return teamService.deleteMember(teamId, memberId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No team member found for team id $teamId and member id $memberId")
    }

    // SKILLS MANAGEMENT

    @Operation(summary = "Add a skill to a team member", description = "Try to add a new skil to an existing team member")
    @ApiResponses(
            ApiResponse(
                    responseCode = "200",
                    description = "Skill added"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "Team member not found"
            )
    )
    @PostMapping("/{id}/members/{memberId}/skills/", produces = ["application/json"])
    fun addTeamMember(@PathVariable("id") teamId: String, @PathVariable("memberId") memberId: String, @RequestBody skill: Skill): Skill {
        return teamService.addSkill(teamId, memberId, skill) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No team member found for team id $teamId and member id $memberId")
    }

    @Operation(summary = "Get a team member skill", description = "Try to get an existing team member skill")
    @ApiResponses(
            ApiResponse(
                    responseCode = "200",
                    description = "Skill found"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "Skill not found"
            )
    )
    @GetMapping("/{id}/members/{memberId}/skills/{skillId}/", produces = ["application/json"])
    fun getMemberSkill(@PathVariable("id") teamId: String, @PathVariable("memberId") memberId: String, @PathVariable("skillId") skillId: String): Skill {
        return teamService.getSkill(teamId, memberId, skillId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No skill found for team id $teamId, member id $memberId and skill id $skillId")
    }

    @Operation(summary = "Update a skill", description = "Try to update an existing team member skill")
    @ApiResponses(
            ApiResponse(
                    responseCode = "200",
                    description = "Skill updated"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "Skill not found"
            )
    )
    @PutMapping("/{id}/members/{memberId}/skills/", produces = ["application/json"])
    fun updateMemberSkill(@PathVariable("id") teamId: String, @PathVariable("memberId") memberId: String, @RequestBody skill: Skill): Skill {
        return teamService.updateSkill(teamId, memberId, skill) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No team member found for team id $teamId and member id $memberId")
    }

    @Operation(summary = "Delete a team member skill", description = "Try to delete an existing team member skill")
    @ApiResponses(
            ApiResponse(
                    responseCode = "202",
                    description = "Skill deleted"
            ),
            ApiResponse(
                    responseCode = "404",
                    description = "Skill not found"
            )
    )
    @DeleteMapping("/{id}/members/{memberId}/skills/{skillId}/", produces = ["application/json"])
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteMemberSkill(@PathVariable("id") teamId: String, @PathVariable("memberId") memberId: String, @PathVariable("skillId") skillId: String): Skill {
        return teamService.deleteSkill(teamId, memberId, skillId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "No skill found for team id $teamId, member id $memberId and skill id $skillId")
    }
}