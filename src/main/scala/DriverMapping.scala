import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ProvenShape, ForeignKeyQuery}

case class Driver(name: String, teamId: Int, birthYear: Int, weight: Int)

class Drivers(tag: Tag) extends Table[Driver](tag, "drivers") {
  def name: Column[String] = column[String]("name", O.PrimaryKey)
  def teamId: Column[Int] = column[Int]("team_id")
  def birthYear: Column[Int] = column[Int]("birth_year")
  def weight: Column[Int] = column[Int]("weight")

  def * = (name, teamId, birthYear, weight) <>(Driver.tupled, Driver.unapply)

  // A reified foreign key relation that can be navigated to create a join
  def team: ForeignKeyQuery[Teams, Team] =
    foreignKey("team_fk", teamId, TableQuery[Teams])(_.id)

}