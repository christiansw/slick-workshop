import scala.slick.driver.H2Driver.simple._

class Formula1Repository(implicit s: Session) {

  val teams = TableQuery[Teams]
  val drivers = TableQuery[Drivers]

  def createSchema() {
    (teams.ddl ++ drivers.ddl).create
  }

  // T1_InsertAndQuery

  def insertTeam(team: Team) = teams += team
  def insertDriver(driver: Driver) = drivers += driver

  def listTeams(): List[Team] = teams.list
  def listTeamnames(): List[String] = teams.map(_.name).list
  def listDrivers(): List[Driver] = drivers.list
  def driversOrderedByAgeAndWeight(): List[Driver] = drivers.sortBy(d => (d.birthYear, d.weight.desc)).list

  // T2_Filters

  def findTeamWithName(name: String): Option[Team] = teams.filter(_.name === name).firstOption
  def listTeamsWithBudgetAbove(minimumBudget: Int): List[Team] = {
    val filterQuery: Query[Teams, Team, Seq] = teams.filter(_.budget > minimumBudget)
    filterQuery.list
  }
  /*
   * Alternative implementation using for comprehension. TODO: add to presentation
    {
      (for {
        t <- teams if t.budget > minimumBudget
      } yield t).list
    }
  */

  // T3_Aggregations

  def sumBudgets(): Option[Int] = teams.map(_.budget).sum.run
  def averageEmployees(): Option[Int] = teams.map(_.employees).avg.run
  def listNumberOfDriversPerTeam(): List[(String, Int)] = {
    (for {
      d <- drivers
      t <- d.team
    } yield (d, t)).groupBy(_._2.id).map {
      case (_, grouped) => (grouped.map(_._2.name).max.get, grouped.length)
    }.list
  }

  // T4_UpdateAndDelete

  def update(team: Team): Int = teams.filter(_.id === team.id).update(team)
  def updateEmployees(id: Int, employees: Int): Int =
    teams.filter(_.id === id)
      .map(_.employees)
      .update(employees)
  def deleteTeam(id: Int): Int = teams.filter(_.id === id).delete
  def findTeamById(id: Int): Option[Team] = teams.filter(_.id === id).firstOption

  // T5_Joins

  def listDriversWithTeam(): List[(Driver, Team)] = (drivers join teams on (_.teamId === _.id)).list
}
