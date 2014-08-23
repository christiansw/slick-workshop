import scala.slick.driver.H2Driver.simple._


class T1_InsertAndQuery extends BaseFormula1Suite {
  val teams = TableQuery[Teams]
  val drivers = TableQuery[Drivers]

  override def beforeEach {
    super.beforeEach()
    createSchema()
  }

  test("Should return inserted team") {
    val redBull: Team = Team(Some(1), "Red Bull", "Renault", 425, 710)
    teams += redBull

    val results = teams.list

    assert(results.size == 1)
    assert(results.head == redBull)
  }

  test("Should return inserted driver") {
    teams += Team(Some(2), "Mercedes", "Mercedes", 300, 610)
    val nico: Driver = Driver("Nico Rosberg", 2, 1985, 71)
    drivers += nico

    val results = drivers.list

    assert(results.size == 1)
    assert(results.head == nico)
  }

  def createSchema() = (teams.ddl ++ drivers.ddl).create

}