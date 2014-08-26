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

  def findTeamWithName(name: String): Option[Team] = teams.filter(_.name === name).firstOption
  def findTeamById(id: Int): Option[Team] = teams.filter(_.id === id).firstOption

  def getSumBudgets(implicit s: Session): Option[Int] = teams.map(_.budget).sum.run
  def getAverageEmployees(implicit s: Session): Option[Int] = teams.map(_.employees).avg.run

  def update(team: Team): Int = teams.filter(_.id === team.id).update(team)

  def updateEmployees(id: Int, employees: Int) =
    teams.filter(_.id === id)
      .map(_.employees)
      .update(employees)

  def deleteTeam(id: Int): Int = teams.filter(_.id === id).delete

  def driversOrderedByAgeAndWeight(): Seq[Driver] = drivers.sortBy(d => (d.birthYear, d.weight.desc)).run
  def getDriversWithTeam(): Seq[(Driver, Team)] = (drivers join teams on (_.teamId === _.id)).run
}
