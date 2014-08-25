

class T3_Aggregations extends BaseFormula1RepositoryTest {

  test("Should sum all budgets") {
    sut.insertTeam(Team(Some(1), "Red Bull", "Renault", 425, 710))
    sut.insertTeam(Team(Some(2), "Mercedes", "Mercedes", 300, 610))
    sut.insertTeam(Team(Some(3), "Ferrari", "Ferrari", 410, 700))
    sut.insertTeam(Team(Some(4), "McLaren", "Mercedes", 160, 500))

    val result = sut.getSumBudgets(session)

    assert((425 + 300 + 410 + 160) == result.get)
  }

  test("Should get average number of employees"){
    sut.insertTeam(Team(Some(1), "Red Bull", "Renault", 425, 710))
    sut.insertTeam(Team(Some(2), "Mercedes", "Mercedes", 300, 610))
    sut.insertTeam(Team(Some(3), "Ferrari", "Ferrari", 410, 700))
    sut.insertTeam(Team(Some(4), "McLaren", "Mercedes", 160, 500))

    val result = sut.getAverageEmployees(session)

    assert(((710 + 610 + 700 + 500) / 4) == result.get)
  }

}