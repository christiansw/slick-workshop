import ObjectMother.{teamWithEmployees, teamWithBudget}
import org.scalatest.Matchers

class T3_Aggregations extends BaseFormula1RepositoryTest with Matchers {

  test("Should sum all budgets") {
    sut.insertTeam(teamWithBudget("Red Bull", 425))
    sut.insertTeam(teamWithBudget("Mercedes", 300))
    sut.insertTeam(teamWithBudget("Ferrari", 410))
    sut.insertTeam(teamWithBudget("McLaren", 160))

    val result = sut.sumBudgets()

    assert((425 + 300 + 410 + 160) == result.get)
  }

  test("Should get average number of employees"){
    sut.insertTeam(teamWithEmployees("Red Bull", 710))
    sut.insertTeam(teamWithEmployees("Mercedes", 610))
    sut.insertTeam(teamWithEmployees("Ferrari", 700))
    sut.insertTeam(teamWithEmployees("McLaren", 500))

    val result = sut.averageEmployees()

    assert(((710 + 610 + 700 + 500) / 4) == result.get)
  }

  test("Should get number of drivers per team") {
    sut.insertTeam(Team(1, "Red Bull", "Renault", 425, 710))
    sut.insertDriver(Driver("Sebastian Vettel", 1, 1987, 58))
    sut.insertDriver(Driver("Daniel Ricciardo", 1, 1989, 65))

    sut.insertTeam(Team(2, "Mercedes", "Mercedes", 600, 845))
    sut.insertDriver(Driver("Nico Rosberg", 2, 1985, 71))

    val results = sut.listNumberOfDriversPerTeam()
    assert(results.size === 2)

    results should contain theSameElementsAs List(("Red Bull", 2), ("Mercedes", 1))
  }

}