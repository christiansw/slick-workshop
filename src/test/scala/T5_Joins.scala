import org.scalatest.Matchers

class T5_Joins extends BaseFormula1RepositoryTest with Matchers {

  test("Should get drivers and their team") {
    val redBull = Team(1, "Red Bull", "Renault", 425, 710)
    sut.insertTeam(redBull)

    val sebastianVettel = Driver("Sebastian Vettel", 1, 1987, 58)
    sut.insertDriver(sebastianVettel)

    val mercedes = Team(2, "Mercedes", "Mercedes", 600, 845)
    sut.insertTeam(mercedes)

    val nicoRosberg = Driver("Nico Rosberg", 2, 1985, 71)
    sut.insertDriver(nicoRosberg)

    val results = sut.listDriversWithTeam()

    assert(results.size === 2)
    results should contain theSameElementsAs List(
      (sebastianVettel, redBull),
      (nicoRosberg, mercedes)
    )

  }

}