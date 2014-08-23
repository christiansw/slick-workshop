import org.scalatest._
import scala.slick.driver.H2Driver.simple._
import scala.slick.jdbc.meta._


class TablesSuite extends FunSuite with BeforeAndAfter {

  val teams = TableQuery[Team]
  val drivers = TableQuery[Driver]
  
  implicit var session: Session = _

  def createSchema() = (teams.ddl ++ drivers.ddl).create
  
  def insertTeam(): Int = teams += (101, "Red Bull", "Renault", 425, 710)
  
  before {
    session = Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver").createSession()
  }
  
  test("Creating the Schema works") {
    createSchema()
    
    val tables = MTable.getTables.list

    assert(tables.size == 2)
    assert(tables.count(_.name.name.equalsIgnoreCase("teams")) == 1)
    assert(tables.count(_.name.name.equalsIgnoreCase("drivers")) == 1)
  }

  test("Inserting a team works") {
    createSchema()
    
    val insertCount = insertTeam()
    assert(insertCount == 1)
  }
  
  test("Query teams works") {
    createSchema()
    insertTeam()
    val results = teams.list
    assert(results.size == 1)
    assert(results.head._1 == 101)
  }
  
  after {
    session.close()
  }

}