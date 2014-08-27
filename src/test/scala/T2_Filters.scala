import ObjectMother._
import org.scalatest.Matchers
import scala.Some

class T2_Filters extends BaseFormula1RepositoryTest with Matchers {

  test("Should return none when team not found by name") {
    sut.insertTeam(teamWithName("Lotus"))

    val emptyResult = sut.findTeamWithName("Daihatsu")
    assert(emptyResult === None)
  }

  test("Should find team by name") {
    sut.insertTeam(teamWithName("Red Bull"))
    val mercedes = sut.insertTeam(teamWithName("Mercedes"))
    sut.insertTeam(teamWithName("Toro Rosso"))

    val result = sut.findTeamWithName("Mercedes")
    assert(result === Some(mercedes))
  }

  test("Should filter by minimum budget") {
    val redBull = sut.insertTeam(teamWithBudget("Red Bull", 425))
    sut.insertTeam(teamWithBudget("Mercedes", 300))
    sut.insertTeam(teamWithBudget("McLaren", 160))
    val ferrari = sut.insertTeam(teamWithBudget("Ferrari", 410))

    val results = sut.listTeamsWithBudgetAbove(400)

    results should contain theSameElementsAs List(redBull, ferrari)
  }

}