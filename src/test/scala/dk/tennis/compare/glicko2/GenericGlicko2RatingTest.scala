package dk.tennis.compare.glicko2

import org.junit._
import Assert._
import Glicko2Rating._
import java.util.Date

class GenericGlicko2RatingTest {

  val glicko2 = new GenericGlicko2Rating(0, 350d / 173.7178, 0.06)

  /**Single rating tests.*/
  /**On serve and return rating tests.*/
  @Test def calcOnServeReturnRatingsSingleResult {
    val ratings = glicko2.calcServeReturnRatings(Result("A", "B", 1, new Date(0)) :: Nil)

    //    assertEquals(1662.212, ratings("A").ratingOnServe.rating, 0.001)
    //    assertEquals(290.230, ratings("A").ratingOnServe.deviation, 0.001)
    //    assertEquals(1500, ratings("A").ratingOnReturn.rating, 0)
    //    assertEquals(350, ratings("A").ratingOnReturn.deviation, 0)
    //    assertEquals(1500, ratings("B").ratingOnServe.rating, 0)
    //    assertEquals(350, ratings("B").ratingOnServe.deviation, 0)
    //    assertEquals(1337.787, ratings("B").ratingOnReturn.rating, 0.001)
    //    assertEquals(290.230, ratings("B").ratingOnReturn.deviation, 0.001)

    assertEquals(0.798, GenericGlicko2Rating.E(ratings("A").ratingOnServe.rating, ratings("B").ratingOnReturn.rating, ratings("B").ratingOnReturn.deviation), 0.001)
    assertEquals(0.5, GenericGlicko2Rating.E(ratings("B").ratingOnServe.rating, ratings("A").ratingOnReturn.rating, ratings("A").ratingOnReturn.deviation), 0.001)
  }

  @Test def calcOnServeReturn_70_games_converge {

    val results = (1 to 12).map(i => Result("A", "B", 39d / 52, new Date(0))).toList
    val ratings = glicko2.calcServeReturnRatings(results)

    //    assertEquals(1662.212, ratings("A").ratingOnServe.rating, 0.001)
    //    assertEquals(290.230, ratings("A").ratingOnServe.deviation, 0.001)
    //    assertEquals(1500, ratings("A").ratingOnReturn.rating, 0)
    //    assertEquals(350, ratings("A").ratingOnReturn.deviation, 0)
    //    assertEquals(1500, ratings("B").ratingOnServe.rating, 0)
    //    assertEquals(350, ratings("B").ratingOnServe.deviation, 0)
    //    assertEquals(1337.787, ratings("B").ratingOnReturn.rating, 0.001)
    //    assertEquals(290.230, ratings("B").ratingOnReturn.deviation, 0.001)

    assertEquals(0.759, GenericGlicko2Rating.E(ratings("A").ratingOnServe.rating, ratings("B").ratingOnReturn.rating, ratings("B").ratingOnReturn.deviation), 0.001)
    assertEquals(0.5, GenericGlicko2Rating.E(ratings("B").ratingOnServe.rating, ratings("A").ratingOnReturn.rating, ratings("A").ratingOnReturn.deviation), 0.001)
  }

  /**Glicko 2 functions*/
  @Test def g = {
    assertEquals(0.99549, GenericGlicko2Rating.g(0.1727), 0.00001)
    assertEquals(0.95315, GenericGlicko2Rating.g(0.5756), 0.00001)
  }

  @Test def E = {
    assertEquals(0.6394, GenericGlicko2Rating.E(0, -0.5756, 0.1727), 0.0001)
    assertEquals(0.4318, GenericGlicko2Rating.E(0, 0.2878, 0.5756), 0.0001)
  }

  @Test def variance = {
    assertEquals(4.3767, GenericGlicko2Rating.variance(0.99549, 0.6394), 0.001)
    assertEquals(4.4861, GenericGlicko2Rating.variance(0.95315, 0.4318), 0.001)
  }

  @Test def delta = {
    assertEquals(1.5708, GenericGlicko2Rating.delta(4.3767, 0.99549, 1, 0.6394), 0.001)
    assertEquals(-2.7861, GenericGlicko2Rating.delta(4.3767, 0.99549, 0, 0.6394), 0.001)

    assertEquals(2.4295, GenericGlicko2Rating.delta(4.4861, 0.95315, 1, 0.4318), 0.0001)
    assertEquals(-1.846, GenericGlicko2Rating.delta(4.4861, 0.95315, 0, 0.4318), 0.001)
  }

  @Test def deviationGivenVolatility = assertEquals(1.152862, GenericGlicko2Rating.deviationGivenVolatility(1.1513, 0.05999), 0.000001)

  @Test def newDeviation = {
    assertEquals(1.0097, GenericGlicko2Rating.newDeviation(1.1529, 4.3767), 0.0001)
    assertEquals(0.8722, GenericGlicko2Rating.newDeviation(1.1529, 1.7785), 0.0001)

  }

  @Test def newRatingValue = assertEquals(0.27339, GenericGlicko2Rating.newRatingValue(0, 0.8722, 0.9955, 1, 0.639), 0.0001)
}