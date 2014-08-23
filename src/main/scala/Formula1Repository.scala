import scala.slick.driver.H2Driver.simple._

class Formula1Repository {

  def listTeams(implicit s: Session): List[Team] = {
      return TableQuery[Teams].list
  }

  def listDrivers(implicit s: Session): List[Driver] = {
      return TableQuery[Drivers].list
  }
}
