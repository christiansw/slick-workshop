import scala.slick.driver.H2Driver.simple._

class Formula1Repository {

  def listTeams(implicit s: Session): List[Team] = TableQuery[Teams].list

  def listDrivers(implicit s: Session): List[Driver] = TableQuery[Drivers].list

  def listTeamsWithBudgetAbove(minimumBudget: Int)(implicit s: Session) = {
    val teams = TableQuery[Teams]
    val filterQuery: Query[Teams, Team, Seq] = teams.filter(_.budget > minimumBudget)
    filterQuery.list
  }
}
