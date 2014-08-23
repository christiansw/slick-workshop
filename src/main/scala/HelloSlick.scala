import scala.slick.driver.H2Driver.simple._

// The main application
object HelloSlick extends App {

  // The query interface for the teams table
  val teams: TableQuery[Teams] = TableQuery[Teams]

  // the query interface for the drivers table
  val drivers: TableQuery[Drivers] = TableQuery[Drivers]
  
  // Create a connection (called a "session") to an in-memory H2 database
  val db = Database.forURL("jdbc:h2:mem:formula1;DATABASE_TO_UPPER=false", driver = "org.h2.Driver")
  db.withSession { implicit session =>

    // Create the schema by combining the DDLs for the teams and drivers
    // tables using the query interfaces
    (teams.ddl ++ drivers.ddl).create

  
    /* Create / Insert */
  
    // Insert some teams
    teams += Team(Some(1), "Red Bull", "Renault", 425, 710)
    teams += Team(Some(2), "Mercedes", "Mercedes", 300, 610)
    teams += Team(Some(3), "Ferrari", "Ferrari", 410, 700)
    teams += Team(Some(4), "McLaren", "Mercedes", 160, 500)

    // Insert some drivers (using JDBC's batch insert feature)
    val driversInsertResult: Option[Int] = drivers ++= Seq (
      Driver("Nico Rosberg", 2, 1985, 71),
      Driver("Sebastian Vettel", 1, 1987, 58),
      Driver("Fernando Alonso", 3, 1981, 68),
      Driver("Jenson Button",   4, 1980, 72)
    )
  
    val allTeams: List[Team] =
      teams.list

    // Print the number of rows inserted
    driversInsertResult foreach { numRows =>
      println(s"Inserted $numRows rows into the drivers table")
    }

  
    /* Read / Query / Select */
  
    // Print the SQL for the drivers query
    println("Generated SQL for base drivers query:\n" + drivers.selectStatement)

    // Query the drivers table using a foreach and print each row
    drivers foreach { case Driver(name, teamId, birthYear, weight) =>
      println("  " + name + "\t" + teamId + "\t" + birthYear + "\t" + weight)
    }


    /* Filtering / Where */

    // Construct a query where the weight of drivers is > 70
    val filterQuery: Query[Drivers, Driver, Seq] =
      drivers.filter(_.weight > 70)

    println("Generated SQL for filter query:\n" + filterQuery.selectStatement)

    // Execute the query
    println(filterQuery.list)
  

    /* Update */
  
    // Construct an update query with the sales column being the one to update
    val updateQuery: Query[Column[Int], Int, Seq] = drivers.map(_.weight)

    // Print the SQL for the drivers update query
    println("Generated SQL for drivers update:\n" + updateQuery.updateStatement)
  
    // Perform the update
    val numUpdatedRows = updateQuery.update(1)
  
    println(s"Updated $numUpdatedRows rows")


    /* Delete */

    // Construct a delete query that deletes drivers with a weight less than 70
    val deleteQuery: Query[Drivers, Driver, Seq] =
      drivers.filter(_.weight < 70)

    // Print the SQL for the drivers delete query
    println("Generated SQL for drivers delete:\n" + deleteQuery.deleteStatement)

    // Perform the delete
    val numDeletedRows = deleteQuery.delete

    println(s"Deleted $numDeletedRows rows")
  
  
    /* Selecting Specific Columns */
  
    // Construct a new drivers query that just selects the name
    val justNameQuery: Query[Column[String], String, Seq] = drivers.map(_.name)
  
    println("Generated SQL for query returning just the name:\n" +
      justNameQuery.selectStatement)
  
    // Execute the query
    println(justNameQuery.list)
  
  
    /* Sorting / Order By */
  
    val sortByPriceQuery: Query[Drivers, Driver, Seq] =
      drivers.sortBy(_.birthYear)
  
    println("Generated SQL for query sorted by birth year:\n" +
      sortByPriceQuery.selectStatement)
  
    // Execute the query
    println(sortByPriceQuery.list)
  
  
    /* Query Composition */
  
    val composedQuery: Query[Column[String], String, Seq] =
      drivers.sortBy(_.name).take(3).filter(_.birthYear < 1982).map(_.name)
  
    println("Generated SQL for composed query:\n" +
      composedQuery.selectStatement)
  
    // Execute the composed query
    println(composedQuery.list)
  
  
    /* Joins */
  
    // Join the tables using the relationship defined in the drivers table
    val joinQuery: Query[(Column[String], Column[String]), (String, String), Seq] = for {
      c <- drivers if c.birthYear < 1982
      s <- c.team
    } yield (c.name, s.name)

    println("Generated SQL for the join query:\n" + joinQuery.selectStatement)

    // Print the rows which contain the driver name and the team name
    println(joinQuery.list)
    
    
    /* Computed Values */
    
    // Create a new computed column that calculates the max weight
    val maxWeightColumn: Column[Option[Int]] = drivers.map(_.weight).max
    
    println("Generated SQL for max weight column:\n" + maxWeightColumn.selectStatement)
    
    // Execute the computed value query
    println(maxWeightColumn.run)
    
    
    /* Manual SQL / String Interpolation */

    // Required import for the sql interpolator
    import scala.slick.jdbc.StaticQuery.interpolation
  
    // A value to insert into the statement
    val engineBrand = "Mercedes"
  
    // Construct a SQL statement manually with an interpolated value
    val plainQuery = sql"select name from teams where engine_brand = $engineBrand".as[String]
    
    println("Generated SQL for plain query:\n" + plainQuery.getStatement)
    
    // Execute the query
    println(plainQuery.list)
    
  }
}
