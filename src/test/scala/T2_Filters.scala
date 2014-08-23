import org.scalatest._
import scala.slick.driver.H2Driver.simple._


class T2_Filters extends FunSuite with BeforeAndAfter {
  implicit var session: Session = null
  val teams = TableQuery[Teams]
  val drivers = TableQuery[Drivers]

  before {
    session = Database.forURL("jdbc:h2:mem:formula1", driver = "org.h2.Driver").createSession()
    createSchema()
  }

  after {
    session.close()
  }

  test("Should return inserted team") {
    teams += Team(Some(1), "Red Bull", "Renault", 425, 710)

    val results = teams.list

    assert(results.size == 1)
    assert(results.head == Team(Some(1), "Red Bull", "Renault", 425, 710))
  }

  test("Should return inserted driver") {
    teams += Team(Some(2), "Mercedes", "Mercedes", 300, 610)
    drivers += Driver("Nico Rosberg", 2, 1985, 71)

    val results = drivers.list

    assert(results.size == 1)
    assert(results.head == Driver("Nico Rosberg", 2, 1985, 71))
  }

  def createSchema() = (teams.ddl ++ drivers.ddl).create

}