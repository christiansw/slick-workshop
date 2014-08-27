import org.h2.jdbc.JdbcSQLException
import org.scalatest.Matchers

class T1_InsertAndQuery extends BaseFormula1RepositoryTest with Matchers {

  test("Should return inserted team") {
    sut.insertTeam(Team(1, "Red Bull", "Renault", 425, 710))

    val results = sut.listTeams()

    assert(results.size == 1)
    assert(results.head == Team(1, "Red Bull", "Renault", 425, 710))
  }

  test("Should return name of inserted teams") {
    sut.insertTeam(Team(1, "Red Bull", "Renault", 425, 710))
    sut.insertTeam(Team(2, "Mercedes", "Mercedes", 300, 610))

    val results = sut.listTeamnames()

    assert(results.size == 2)
    results should contain theSameElementsAs List("Red Bull", "Mercedes")
  }

  test("Should not allow two teams with the same name") {
    sut.insertTeam(Team(1, "Red Bull", "Renault", 425, 710))
    val e = intercept[JdbcSQLException] {
      sut.insertTeam(Team(2, "Red Bull", "Mercedes", 600, 845))
    }
    assert(e.getMessage contains "Unique index or primary key violation")
  }

  test("Should return inserted driver") {
    sut.insertTeam(Team(2, "Mercedes", "Mercedes", 300, 610))
    sut.insertDriver(Driver("Nico Rosberg", 2, 1985, 71))

    val results = sut.listDrivers()

    assert(results.size == 1)
    assert(results.head == Driver("Nico Rosberg", 2, 1985, 71))
  }

  test("Should order drivers by age (high to low), then weight (high to low)") {
    val sebastianVettel = Driver("Sebastian Vettel", 1, 1987, 58)
    val danielRicciardo = Driver("Daniel Ricciardo", 1, 1989, 65)
    val nicoRosberg = Driver("Nico Rosberg", 2, 1985, 71)
    val lewisHamilton = Driver("Lewis Hamilton", 2, 1985, 68)

    sut.insertTeam(Team(1, "Red Bull", "Renault", 425, 710))
    sut.insertDriver(sebastianVettel)
    sut.insertDriver(danielRicciardo)

    sut.insertTeam(Team(2, "Mercedes", "Mercedes", 600, 845))
    sut.insertDriver(nicoRosberg)
    sut.insertDriver(lewisHamilton)

    val results = sut.driversOrderedByAgeAndWeight()

    assert(results.size === 4)
    results should contain theSameElementsInOrderAs List(nicoRosberg, lewisHamilton, sebastianVettel, danielRicciardo)

  }

}