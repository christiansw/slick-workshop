import ObjectMother._
import scala.Some

class T2_Filters extends BaseFormula1RepositoryTest {

  test("Should find team by name") {
    sut.insertTeam(teamWithName("Red Bull"))
    val mercedes = sut.insertTeam(teamWithName("Mercedes"))

    val emptyResult = sut.findTeamWithName("Daihatsu")
    assert(emptyResult === None)

    val result = sut.findTeamWithName("Mercedes")
    assert(result === Some(mercedes))
  }

  test("Should filter by minimum budget") {
    val redBull = sut.insertTeam(teamWithBudget("Red Bull", 425))
    val ferrari = sut.insertTeam(teamWithBudget("Ferrari", 410))
    sut.insertTeam(teamWithBudget("Mercedes", 300))
    sut.insertTeam(teamWithBudget("McLaren", 160))

    val results = sut.listTeamsWithBudgetAbove(400)

    assert(results.size == 2)
    assert(results(0) == redBull)
    assert(results(1) == ferrari)
  }

}