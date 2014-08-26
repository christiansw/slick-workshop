import org.scalatest.Matchers

class T5_Joins extends BaseFormula1RepositoryTest with Matchers {

  test("Should get drivers and their team") {
    val redBull: Team = Team(Some(1), "Red Bull", "Renault", 425, 710)
    val sebastianVettel: Driver = Driver("Sebastian Vettel", 1, 1987, 58)
    val mercedes: Team = Team(Some(2), "Mercedes", "Mercedes", 600, 845)
    val nicoRosberg: Driver = Driver("Nico Rosberg", 2, 1985, 71)

    sut.insertTeam(redBull)
    sut.insertDriver(sebastianVettel)
    sut.insertTeam(mercedes)
    sut.insertDriver(nicoRosberg)

    val results = sut.listDriversWithTeam()

    assert(results.size === 2)
    results should contain theSameElementsAs List((sebastianVettel, redBull), (nicoRosberg, mercedes))

  }

}