

class T4_UpdateAndDelete extends BaseFormula1RepositoryTest {

  test("Should update team") {
    val teamId = 1

    sut.insertTeam(Team(Some(teamId), "Red Bull", "Renault", 425, 710))

    val updatedRows = sut.update(Team(Some(teamId), "Red Bull", "Mazda", 350, 600))
    assert(updatedRows === 1)

    val updatedTeam = sut.findTeamById(teamId)
    assert(updatedTeam === Some(Team(Some(teamId), "Red Bull", "Mazda", 350, 600)))
  }

  test("Should update single value on team") {
    val teamId = 1

    sut.insertTeam(Team(Some(teamId), "Red Bull", "Renault", 425, 710))

    val updatedRows = sut.updateEmployees(teamId, 800)
    assert(updatedRows === 1)

    val updatedTeam = sut.findTeamById(teamId)
    assert(updatedTeam === Some(Team(Some(teamId), "Red Bull", "Renault", 425, 800)))
  }

  test("Should delete team") {
    val teamId = 1

    sut.insertTeam(Team(Some(teamId), "Red Bull", "Renault", 425, 710))

    val deletedRows = sut.deleteTeam(teamId)
    assert(deletedRows === 1)

    assert(sut.findTeamById(teamId) === None)
  }
}