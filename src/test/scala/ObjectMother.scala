import scala.util.Random

object ObjectMother {

  var idCounter: Int = 0

  def teamWithName(name: String) = new Team(Random.nextInt(Integer.MAX_VALUE), name, "Engine brand", 987, 123);
  def teamWithBudget(name: String, budget: Int) = new Team(Random.nextInt(Integer.MAX_VALUE), name, "Engine Brand", budget, 123);
  def teamWithEmployees(name: String, employees: Int) = new Team(Random.nextInt(Integer.MAX_VALUE), name, "Engine Brand", 987, employees);

  def driverWithName(name: String, teamId: Int) = new Driver(name, teamId, 1979, 80);
  def driverWithWeight(name: String, weight: Int, teamId: Int) = new Driver(name, teamId, 1979, weight);
}
