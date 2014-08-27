import ObjectMother.{driverWithName, teamWithName}
import org.scalatest.Matchers

class T3_Joins extends BaseFormula1RepositoryTest with Matchers {

  test("Should get drivers and their team") {
    val redBull = sut.insertTeam(teamWithName("Red Bull"))
    val sebastianVettel = sut.insertDriver(driverWithName("Sebastian Vettel", redBull.id))

    val mercedes = sut.insertTeam(teamWithName("Mercedes"))
    val nicoRosberg = sut.insertDriver(driverWithName("Nico Rosberg", mercedes.id))

    val results = sut.listDriversWithTeam()

    assert(results.size === 2)
    results should contain theSameElementsAs List(
      (sebastianVettel, redBull),
      (nicoRosberg, mercedes)
    )

  }
}