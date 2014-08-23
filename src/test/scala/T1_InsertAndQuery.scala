import scala.slick.driver.H2Driver.simple._


class T1_InsertAndQuery extends BaseFormula1RepositorySuite {

  test("Should return inserted team") {
    teams += Team(Some(1), "Red Bull", "Renault", 425, 710)

    val results = sut.listTeams(session)

    assert(results.size == 1)
    assert(results.head == Team(Some(1), "Red Bull", "Renault", 425, 710))
  }

  test("Should return inserted driver") {
    teams += Team(Some(2), "Mercedes", "Mercedes", 300, 610)
    drivers += Driver("Nico Rosberg", 2, 1985, 71)

    val results = sut.listDrivers(session)

    assert(results.size == 1)
    assert(results.head == Driver("Nico Rosberg", 2, 1985, 71))
  }

}