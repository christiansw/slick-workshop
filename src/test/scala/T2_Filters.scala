import scala.slick.driver.H2Driver.simple._


class T2_Filters extends BaseFormula1RepositorySuite {

  test("Should filter by minimum budget") {
    teams += Team(Some(1), "Red Bull", "Renault", 425, 710)
    teams += Team(Some(2), "Mercedes", "Mercedes", 300, 610)
    teams += Team(Some(3), "Ferrari", "Ferrari", 410, 700)
    teams += Team(Some(4), "McLaren", "Mercedes", 160, 500)

    val results = sut.listTeamsWithBudgetAbove(420)(session)

    assert(results.size == 1)
    assert(results.head == Team(Some(1), "Red Bull", "Renault", 425, 710))
  }

}