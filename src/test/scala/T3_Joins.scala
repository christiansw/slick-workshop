import ObjectMother.{teamWithEngine, driverWithBirthYear, driverWithName, teamWithName}
import org.scalatest.Matchers

class T3_Joins extends BaseFormula1RepositoryTest with Matchers {

  test("Should get drivers and their team") {
    val redBull = sut.insertTeam(teamWithName("Red Bull"))
    val sebastianVettel = sut.insertDriver(driverWithName("Sebastian Vettel", redBull.id))

    val mercedes = sut.insertTeam(teamWithName("Mercedes"))
    val nicoRosberg = sut.insertDriver(driverWithName("Nico Rosberg", mercedes.id))

    val results = sut.listDriversWithTeam()

    results should contain theSameElementsAs List(
      (sebastianVettel, redBull),
      (nicoRosberg, mercedes)
    )
  }

  test("Should get engine brand of drivers born after specified year") {
    val ferrari = sut.insertTeam(teamWithEngine("Ferrari", "Ferrari"))
    sut.insertDriver(driverWithBirthYear("Kimi Räikkönen", 1979, ferrari.id))

    val mcLaren = sut.insertTeam(teamWithEngine("McLaren", "Mercedes"))
    sut.insertDriver(driverWithBirthYear("Kevin Magnussen", 1992, mcLaren.id))

    val results = sut.listEngineForDriversBornAfter(1985)

    results should contain theSameElementsAs List(
      ("Kevin Magnussen", "Mercedes")
    )
  }
}