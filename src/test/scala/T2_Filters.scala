

class T2_Filters extends BaseFormula1RepositorySuite {

  test("Should filter by minimum budget") {
    sut.insertTeam(Team(Some(1), "Red Bull", "Renault", 425, 710))
    sut.insertTeam(Team(Some(2), "Mercedes", "Mercedes", 300, 610))
    sut.insertTeam(Team(Some(3), "Ferrari", "Ferrari", 410, 700))
    sut.insertTeam(Team(Some(4), "McLaren", "Mercedes", 160, 500))

    val results = sut.listTeamsWithBudgetAbove(400)(session)

    assert(results.size == 2)
    assert(results(0) == Team(Some(1), "Red Bull", "Renault", 425, 710))
    assert(results(1) == Team(Some(3), "Ferrari", "Ferrari", 410, 700))
  }

}