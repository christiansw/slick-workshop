import ObjectMother.{driverWithName, teamWithName}
import org.h2.jdbc.JdbcSQLException
import org.scalatest.Matchers

class T1_InsertAndQuery extends BaseFormula1RepositoryTest with Matchers {

  test("Should list inserted teams") {
    val redBull = sut.insertTeam(teamWithName("Red Bull"))
    val lotus = sut.insertTeam(teamWithName("Lotus"))

    val results = sut.listTeams()

    results should contain theSameElementsAs List(redBull, lotus)
  }

  test("Should list names of inserted teams") {
    sut.insertTeam(teamWithName("Williams"))
    sut.insertTeam(teamWithName("Sauber"))

    val results = sut.listTeamNames()

    results should contain theSameElementsAs List("Williams", "Sauber")
  }

  test("Should not allow two teams with the same name") {
    sut.insertTeam(teamWithName("Red Bull"))

    val e = intercept[JdbcSQLException] {
      sut.insertTeam(teamWithName("Red Bull"))
    }

    assert(e.getMessage contains "Unique index or primary key violation")
  }

  test("Should list inserted driver") {
    val mercedes = sut.insertTeam(teamWithName("Mercedes"))
    val nicoRosberg = sut.insertDriver(driverWithName("Nico Rosberg", mercedes.id))

    val results = sut.listDrivers()

    assert(results.size == 1)
    assert(results.head == nicoRosberg)
  }

  test("Should order drivers by age (high to low), then weight (high to low)") {
    val redBull = sut.insertTeam(teamWithName("Red Bull"))
    val mercedes = sut.insertTeam(teamWithName("Mercedes"))

    val sebastianVettel = sut.insertDriver(Driver("Sebastian Vettel", redBull.id, 1987, 58))
    val danielRicciardo = sut.insertDriver(Driver("Daniel Ricciardo", redBull.id, 1989, 65))
    val nicoRosberg = sut.insertDriver(Driver("Nico Rosberg", mercedes.id, 1985, 71))
    val lewisHamilton = sut.insertDriver(Driver("Lewis Hamilton", mercedes.id, 1985, 68))

    val results = sut.driversOrderedByAgeAndWeight()

    results should contain theSameElementsInOrderAs
      List(nicoRosberg, lewisHamilton, sebastianVettel, danielRicciardo)
  }

}