import scala.slick.driver.H2Driver.simple._

class Formula1Repository(implicit s: Session) {

  val teams = TableQuery[Teams]
  val drivers = TableQuery[Drivers]
  (teams.ddl ++ drivers.ddl).create

  def insertTeam(team: Team) = teams += team

  def insertDriver(driver: Driver) = drivers += driver

  def listTeams(implicit s: Session): List[Team] = teams.list

  def listDrivers(implicit s: Session): List[Driver] = drivers.list

  def listTeamsWithBudgetAbove(minimumBudget: Int)(implicit s: Session) = {
    val filterQuery: Query[Teams, Team, Seq] = teams.filter(_.budget > minimumBudget)
    filterQuery.list
  }
}
