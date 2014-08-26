import org.scalatest.Matchers

class T3_Aggregations extends BaseFormula1RepositoryTest with Matchers {

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

  test("Should get number of drivers per team") {
    sut.insertTeam(Team(Some(1), "Red Bull", "Renault", 425, 710))
    sut.insertDriver(Driver("Sebastian Vettel", 1, 1987, 58))
    sut.insertDriver(Driver("Daniel Ricciardo", 1, 1989, 65))

    sut.insertTeam(Team(Some(2), "Mercedes", "Mercedes", 600, 845))
    sut.insertDriver(Driver("Nico Rosberg", 2, 1985, 71))

    val results = sut.listNumberOfDriversPerTeam()
    assert(results.size === 2)

    results should contain theSameElementsAs Seq(("Red Bull", 2), ("Mercedes", 1))
  }

}