import scala.slick.driver.H2Driver.simple._

class Formula1Repository(implicit s: Session) {

  // T0_Schema:

  def createSchema()  = ???

  // T1_InsertAndQuery:

  def insertTeam(team: Team): Team = ???
  def insertDriver(driver: Driver): Driver = ???
  def listTeams(): List[Team] = ???
  def listTeamNames(): List[String] = ???
  def listDrivers(): List[Driver] = ???
  def driversOrderedByAgeAndWeight(): List[Driver] = ???

  // T2_Filters:

  def findTeamWithName(name: String): Option[Team] = ???
  def listTeamsWithBudgetAbove(minimumBudget: Int): List[Team] = ???
  def listTop3LightestDriversBornAfter(birthYear: Int) = ???

  // T3_Joins:

  def listDriversWithTeam(): List[(Driver, Team)] = ???
  def listEngineForDriversBornAfter(birthYear: Int) = ???

  // T4_UpdateAndDelete:

  def update(team: Team): Int = ???
  def deleteTeam(id: Int): Int = ???
  def findTeamById(id: Int): Option[Team] = ???
  def updateEmployees(id: Int, newEmployees: Int): Int = ???

  // T5_Aggregations:

  def sumBudgets(): Option[Int] = ???
  def averageEmployees(): Option[Int] = ???
  def listMaxDriverWeightPerTeam(): List[(Int, Option[Int])] = ???

  // Bonus task:
  def listNumberOfDriversPerTeam(): List[(String, Int)] = ???

}
