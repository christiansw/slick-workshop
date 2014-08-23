import org.scalatest._
import scala.slick.driver.H2Driver.simple._
import scala.slick.jdbc.meta.MTable


class T0_Schema extends FunSuite with BeforeAndAfter {
  implicit var session: Session = null

  before {
    session = Database.forURL("jdbc:h2:mem:formula1", driver = "org.h2.Driver").createSession()
  }

  after {
    session.close()
  }

  test("Should create database schema") {
    val teams = TableQuery[Teams]
    val drivers = TableQuery[Drivers]
    (teams.ddl ++ drivers.ddl).create

    val tables = MTable.getTables.list

    assert(tables.size == 2)
    assert(tables.count(_.name.name.equalsIgnoreCase("teams")) == 1)
    assert(tables.count(_.name.name.equalsIgnoreCase("drivers")) == 1)
  }

}