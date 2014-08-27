import ObjectMother._
import org.scalatest.Matchers

class T5_Aggregations extends BaseFormula1RepositoryTest with Matchers {

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

  test("Should return heaviest driver's weight per team") {
    val redBull = sut.insertTeam(teamWithName("Red Bull"))
    sut.insertDriver(driverWithWeight("Sebastian Vettel", 58, redBull.id))
    sut.insertDriver(driverWithWeight("Daniel Ricciardo", 64, redBull.id))

    val mercedes = sut.insertTeam(teamWithName("Mercedes"))
    sut.insertDriver(driverWithWeight("Nico Rosberg", 71, mercedes.id))
    sut.insertDriver(driverWithWeight("Lewis Hamilton", 66, mercedes.id))

    val results = sut.listMaxDriverWeightPerTeam()
    assert(results.size === 2)

    results should contain theSameElementsAs List(
      (redBull.id, Some(64)),
      (mercedes.id, Some(71))
    )
  }

  test("BONUS TASK: Should get number of drivers per team") {
    val redBull = sut.insertTeam(teamWithName("Red Bull"))
    sut.insertDriver(driverWithName("Sebastian Vettel", redBull.id))
    sut.insertDriver(driverWithName("Daniel Ricciardo", redBull.id))

    val mercedes = sut.insertTeam(teamWithName("Mercedes"))
    sut.insertDriver(driverWithName("Nico Rosberg", mercedes.id))

    val results = sut.listNumberOfDriversPerTeam()
    assert(results.size === 2)

    results should contain theSameElementsAs List(
      ("Red Bull", 2),
      ("Mercedes", 1)
    )
  }

}