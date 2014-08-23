import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}

case class Team(id: Option[Int] = None, name: String, engineBrand: String, budget: Int, employees: Int)

class Teams(tag: Tag) extends Table[Team](tag, "teams") {
  def id: Column[Int] = column[Int]("team_id", O.PrimaryKey)
  def name: Column[String] = column[String]("name")
  def engineBrand: Column[String] = column[String]("engine_brand")
  def budget: Column[Int] = column[Int]("budget")
  def employees: Column[Int] = column[Int]("employees")
  
  def * = (id.?, name, engineBrand, budget, employees) <> (Team.tupled, Team.unapply)
}

case class Driver(name: String, teamId: Int, birthYear: Int, weight: Int)

class Drivers(tag: Tag) extends Table[Driver](tag, "drivers") {
  def name: Column[String] = column[String]("name", O.PrimaryKey)
  def teamId: Column[Int] = column[Int]("team_id")
  def birthYear: Column[Int] = column[Int]("birth_year")
  def weight: Column[Int] = column[Int]("weight")

  def * = (name, teamId, birthYear, weight) <> (Driver.tupled, Driver.unapply)

  // A reified foreign key relation that can be navigated to create a join
  def team: ForeignKeyQuery[Teams, Team] =
    foreignKey("team_fk", teamId, TableQuery[Teams])(_.id)
}
