import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}

case class Team(tag: Tag)
  extends Table[(Int, String, String, Int, Int)](tag, "teams") {

  // This is the primary key column:
  def id: Column[Int] = column[Int]("team_id", O.PrimaryKey)
  def name: Column[String] = column[String]("name")
  def engineBrand: Column[String] = column[String]("engine_brand")
  def budget: Column[Int] = column[Int]("budget")
  def employees: Column[Int] = column[Int]("employees")
  
  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[(Int, String, String, Int, Int)] =
    (id, name, engineBrand, budget, employees)
}

// A drivers table with 5 columns: name, team id, price, sales, total
case class Driver(tag: Tag)
  extends Table[(String, Int, Int, Int)](tag, "drivers") {

  def name: Column[String] = column[String]("name", O.PrimaryKey)
  def teamId: Column[Int] = column[Int]("team_id")
  def birthYear: Column[Int] = column[Int]("birth_year")
  def weight: Column[Int] = column[Int]("weight")

  def * : ProvenShape[(String, Int, Int, Int)] =
    (name, teamId, birthYear, weight)
  
  // A reified foreign key relation that can be navigated to create a join
  def team: ForeignKeyQuery[Team, (Int, String, String, Int, Int)] =
    foreignKey("team_fk", teamId, TableQuery[Team])(_.id)
}
