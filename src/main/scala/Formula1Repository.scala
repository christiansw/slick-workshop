import scala.slick.driver.H2Driver.simple._

class Formula1Repository(implicit s: Session) {
  val teams = TableQuery[Teams]
  val drivers = TableQuery[Drivers]

  // T0_Schema:

  def createSchema() {
    (teams.ddl ++ drivers.ddl).create
  }

  // T1_InsertAndQuery:

  def insertTeam(team: Team): Team = {
    teams += team
    team
  }
  def insertDriver(driver: Driver): Driver = {
    drivers += driver
    driver
  }
  def listTeams(): List[Team] = teams.list
  def listTeamNames(): List[String] = teams.map(_.name).list
  def listDrivers(): List[Driver] = drivers.list
  def driversOrderedByAgeAndWeight(): List[Driver] = {
    drivers.sortBy(d => (d.birthYear, d.weight.desc)).list
  }

  // T2_Filters:

  def findTeamWithName(name: String): Option[Team] = {
    teams.filter(_.name === name).firstOption
  }
  def listTeamsWithBudgetAbove(minimumBudget: Int): List[Team] = {
    teams.filter(_.budget > minimumBudget).list
  }
  /*
    Alternative implementation using for comprehension:
    {
      (for {
        team <- teams if team.budget > minimumBudget
      } yield team).list
    }
  */
  def listTop3LightestDriversBornAfter(birthYear: Int): List[String] = {
    drivers.filter(_.birthYear > birthYear).sortBy(_.weight).take(3).map(_.name).list
  }

  // T3_Joins:

  def listDriversWithTeam(): List[(Driver, Team)] = {
    (drivers join teams on (_.teamId === _.id)).list
  }

  def listEngineForDriversBornAfter(birthYear: Int): List[(String, String)] = {
    val query = for {
      driver <- drivers if driver.birthYear > birthYear
      team <- driver.team
    } yield (driver.name, team.engineBrand)

    query.list
  }

  // T4_UpdateAndDelete:

  def update(team: Team): Int = teams.update(team)
  def deleteTeam(id: Int): Int = teams.filter(_.id === id).delete
  def findTeamById(id: Int): Option[Team] = teams.filter(_.id === id).firstOption
  def updateEmployees(id: Int, newEmployees: Int): Int =
    teams.filter(_.id === id)
      .map(_.employees)
      .update(newEmployees)

  // T5_Aggregations:

  def sumBudgets(): Option[Int] = teams.map(_.budget).sum.run
  def averageEmployees(): Option[Int] = teams.map(_.employees).avg.run

  def listMaxDriverWeightPerTeam(): List[(Int, Option[Int])] = {
    drivers.groupBy(_.teamId).map {
      case (teamId, drivers) => (teamId, drivers.map(_.weight).max)
    }.list
  }

}
