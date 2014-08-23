import scala.slick.jdbc.meta.MTable


class T0_Schema extends BaseFormula1RepositorySuite {

  test("Should create database schema") {
    val tables = MTable.getTables.list

    assert(tables.size == 2)
    assert(tables.count(_.name.name.equalsIgnoreCase("teams")) == 1)
    assert(tables.count(_.name.name.equalsIgnoreCase("drivers")) == 1)
  }

}