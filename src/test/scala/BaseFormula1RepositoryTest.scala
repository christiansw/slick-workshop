import org.scalatest._
import scala.slick.driver.H2Driver.simple._


class BaseFormula1RepositoryTest extends FunSuite with BeforeAndAfterEach {
  implicit var session: Session = _
  var sut: Formula1Repository = _

  override def beforeEach {
    val db = Database.forURL("jdbc:h2:mem:formula1", driver = "org.h2.Driver")
    session = db.createSession()
    sut = new Formula1Repository()
  }

  override def afterEach {
    session.close()
  }

}