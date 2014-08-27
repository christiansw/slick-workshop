import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.ForeignKeyQuery

case class Team(id: Int, name: String, engineBrand: String, budget: Int, employees: Int)

class Teams(tag: Tag) extends Table[Team](tag, "teams") {
  def id: Column[Int] = column[Int]("id", O.PrimaryKey)
  def name: Column[String] = column[String]("name", O.NotNull)
  def engineBrand: Column[String] = column[String]("engine_brand", O.NotNull)
  def budget: Column[Int] = column[Int]("budget")
  def employees: Column[Int] = column[Int]("employees")

  // the * projection (e.g. select * ...) auto-transforms the tupled column values to / from a Team
  def * = (id, name, engineBrand, budget, employees) <> (Team.tupled, Team.unapply)

  def nameIdx = index("team_name_idx", name, unique = true)
}
