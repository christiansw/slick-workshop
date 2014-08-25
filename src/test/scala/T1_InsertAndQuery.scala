import org.h2.jdbc.JdbcSQLException

class T1_InsertAndQuery extends BaseFormula1RepositoryTest {

  test("Should return inserted team") {
    sut.insertTeam(Team(Some(1), "Red Bull", "Renault", 425, 710))

    val results = sut.listTeams(session)

    assert(results.size == 1)
    assert(results.head == Team(Some(1), "Red Bull", "Renault", 425, 710))
  }

  test("Should not allow two teams with the same name") {
    sut.insertTeam(Team(Some(1), "Red Bull", "Renault", 425, 710))
    val e = intercept[JdbcSQLException] {
      sut.insertTeam(Team(Some(2), "Red Bull", "Mercedes", 600, 845))
    }
    assert(e.getMessage contains "Unique index or primary key violation")
  }

  test("Should return inserted driver") {
    sut.insertTeam(Team(Some(2), "Mercedes", "Mercedes", 300, 610))
    sut.insertDriver(Driver("Nico Rosberg", 2, 1985, 71))

    val results = sut.listDrivers(session)

    assert(results.size == 1)
    assert(results.head == Driver("Nico Rosberg", 2, 1985, 71))
  }

  //TODO: Test join of team into driver

}