import org.scalatest._
import scala.slick.driver.H2Driver.simple._
import scala.slick.jdbc.meta._


class TablesSuite extends FunSuite with BeforeAndAfter {

  //get complete f1 table (regex it)
  //filter based on weight

  //filter

  //crud

  //define result

  //sum result


  val teams = TableQuery[Teams]
  val drivers = TableQuery[Drivers]
  
  implicit var session: Session = _

  def createSchema() = (teams.ddl ++ drivers.ddl).create
  
  def insertTeam(): Int = teams += Team(Some(101), "Red Bull", "Renault", 425, 710)
  
  before {
    session = Database.forURL("jdbc:h2:mem:formula1", driver = "org.h2.Driver").createSession()
    createSchema()
  }

  test("Creating the Schema works") {
    val tables = MTable.getTables.list

    assert(tables.size == 2)
    assert(tables.count(_.name.name.equalsIgnoreCase("teams")) == 1)
    assert(tables.count(_.name.name.equalsIgnoreCase("drivers")) == 1)
  }

  test("Inserting a team works") {
    val insertCount = insertTeam()
    assert(insertCount == 1)
  }
  
  test("Should return inserted team") {
    insertTeam()
    val results = teams.list
    assert(results.size == 1)
    assert(results.head.id == Some(101))
  }

  after {
    session.close()
  }

}