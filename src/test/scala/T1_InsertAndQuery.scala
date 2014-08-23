import scala.slick.driver.H2Driver.simple._


class T1_InsertAndQuery extends BaseFormula1Suite {
  val teams = TableQuery[Teams]
  val drivers = TableQuery[Drivers]
  val sut = new Formula1Repository()

  override def beforeEach {
    super.beforeEach()

    //create schema
    (teams.ddl ++ drivers.ddl).create
  }

  test("Should return inserted team") {
    val redBull: Team = Team(Some(1), "Red Bull", "Renault", 425, 710)
    teams += redBull

    val results = sut.listTeams(session)

    assert(results.size == 1)
    assert(results.head == redBull)
  }

  test("Should return inserted driver") {
    teams += Team(Some(2), "Mercedes", "Mercedes", 300, 610)
    val nico: Driver = Driver("Nico Rosberg", 2, 1985, 71)
    drivers += nico

    val results = sut.listDrivers(session)

    assert(results.size == 1)
    assert(results.head == nico)
  }

}