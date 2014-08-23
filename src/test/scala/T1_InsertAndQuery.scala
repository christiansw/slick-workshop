

class T1_InsertAndQuery extends BaseFormula1RepositorySuite {

  test("Should return inserted team") {
    sut.insertTeam(Team(Some(1), "Red Bull", "Renault", 425, 710))

    val results = sut.listTeams(session)

    assert(results.size == 1)
    assert(results.head == Team(Some(1), "Red Bull", "Renault", 425, 710))
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