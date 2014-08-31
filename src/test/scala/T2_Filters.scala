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

  test("Should list top 3 lightest drivers born after specified year") {
    val team: Team = sut.insertTeam(teamWithName("Team A"))

    sut.insertDriver(Driver("Nico Rosberg", team.id, 1985, 71))
    sut.insertDriver(Driver("Sebastian Vettel", team.id, 1987, 58))
    sut.insertDriver(Driver("Fernando Alonso", team.id, 1981, 68))
    sut.insertDriver(Driver("Jenson Button", team.id,   1980, 72))
    sut.insertDriver(Driver("Daniel Ricciardo", team.id, 1989, 64))

    val results = sut.listTop3LightestDriversBornAfter(1980);

    results should contain theSameElementsInOrderAs List("Sebastian Vettel", "Daniel Ricciardo", "Fernando Alonso")

  }

}