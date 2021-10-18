package Lab3

import com.cra.figaro.library.atomic._
import com.cra.figaro.language._
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.library.compound._


object laborator3 {
	def main(args: Array[String]) {
        val sideOfBed = Flip(0.6)
        val sunnyToday = Flip(0.2)
            
            val greetingToday = If(sideOfBed,
                If(sunnyToday,
                   Select(0.6 -> "Hello, world!", 0.4 -> "Howdy, universe!"),
                   Select(0.2 -> "Hello, world!", 0.8 -> "Oh no, not again!")),
                Select(1.0 -> "Oh no, not again!"))

         val sunnyTomorrow = If(sunnyToday, Flip(0.8), Flip(0.05))

         val greetingTomorrow = If(sideOfBed,
              If(sunnyTomorrow,
             Select(0.6 -> "Hello, world!", 0.4 -> "Howdy, universe!"),
             Select(0.2 -> "Hello, world!", 0.8 -> "Oh no, not again!")),
             Select(1.0 -> "Oh no, not again!"))

  greetingToday.observe("Oh no, not again!")
 println(VariableElimination.probability(sunnyToday,true))


            ////////////////////////3
            val a = Flip(0.4)
            val b = Flip(0.4)
            val d = b
            val e = a === d
            println(VariableElimination.probability(d,true))  // 0.52 pt ca probabilitatea ca si a si c sa fie false este  0.32 si sa fie adevarate 0.16 
                                                            

            val x = Flip(0.4)
            val y = Flip(0.4)
            val z = x
            val w = x === z
            println(VariableElimination.probability(w,true)) // 1.0 deoarece x === z e mereu adevarata

            // val die1 = FromRange(1, 7)
            // val die2 = FromRange(1, 7)
            // val total = Apply(die1, die2, (i1: Int, i2: Int) => i1 + i2)
            // println(VariableElimination.probability(total, 11))
        }
	}
    //ex4 -calculeaza probabilitatea ca suma celor 3 sa fie 11
        //ex5-

            //ex6

                //ex7


                    //ex8
   