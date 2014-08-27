

class T2_Filters extends BaseFormula1RepositoryTest {

  test("Should find team by name") {
    sut.insertTeam(Team(1, "Red Bull", "Renault", 425, 710))
    sut.insertTeam(Team(2, "Mercedes", "Mercedes", 300, 610))

    val emptyResult = sut.findTeamWithName("Daihatsu")
    assert(emptyResult === None)

    val result = sut.findTeamWithName("Mercedes")
    assert(result === Some(Team(2, "Mercedes", "Mercedes", 300, 610)))

  }

  test("Should filter by minimum budget") {
    sut.insertTeam(Team(1, "Red Bull", "Renault", 425, 710))
    sut.insertTeam(Team(2, "Mercedes", "Mercedes", 300, 610))
    sut.insertTeam(Team(3, "Ferrari", "Ferrari", 410, 700))
    sut.insertTeam(Team(4, "McLaren", "Mercedes", 160, 500))

    val results = sut.listTeamsWithBudgetAbove(400)

    assert(results.size == 2)
    assert(results(0) == Team(1, "Red Bull", "Renault", 425, 710))
    assert(results(1) == Team(3, "Ferrari", "Ferrari", 410, 700))
  }

}