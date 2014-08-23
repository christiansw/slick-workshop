import org.scalatest._
import scala.slick.driver.H2Driver.simple._


class BaseFormula1Suite extends FunSuite with BeforeAndAfterEach {
  implicit var session: Session = _

  override def beforeEach {
    session = Database.forURL("jdbc:h2:mem:formula1", driver = "org.h2.Driver").createSession()
  }

  override def afterEach {
    session.close()
  }

}