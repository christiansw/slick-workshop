import org.scalatest._
import scala.slick.driver.H2Driver.simple._


class BaseFormula1RepositorySuite extends FunSuite with BeforeAndAfterEach {
  implicit var session: Session = _

  val teams = TableQuery[Teams]
  val drivers = TableQuery[Drivers]

  val sut = new Formula1Repository()

  override def beforeEach {
    val db = Database.forURL("jdbc:h2:mem:formula1", driver = "org.h2.Driver")
    session = db.createSession()
    (teams.ddl ++ drivers.ddl).create
  }

  override def afterEach {
    session.close()
  }

}