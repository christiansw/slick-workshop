import scala.slick.driver.H2Driver.simple._
import scala.slick.jdbc.meta.MTable


class T0_Schema extends BaseFormula1Suite {

  test("Should create database schema") {
    val teams = TableQuery[Teams]
    val drivers = TableQuery[Drivers]

    //create schema
    (teams.ddl ++ drivers.ddl).create

    val tables = MTable.getTables.list
    assert(tables.size == 2)
    assert(tables.count(_.name.name.equalsIgnoreCase("teams")) == 1)
    assert(tables.count(_.name.name.equalsIgnoreCase("drivers")) == 1)
  }

}