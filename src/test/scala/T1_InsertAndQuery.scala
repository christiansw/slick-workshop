import org.scalatest._
import scala.slick.driver.H2Driver.simple._
import scala.slick.jdbc.meta._


class T1_InsertAndQuery extends FunSuite with BeforeAndAfter {
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