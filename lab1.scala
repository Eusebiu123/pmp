import com.cra.figaro.algorithm.factored.
import com.cra.figaro.language.
import com.cra.figaro.library.compound._
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.library.compound.CPD
import com.cra.figaro.algorithm.sampling._

object produs {

     def main(args: Array[String]) {

      
        val nevoie=Flip(0.5)
         val reducere = Flip(0.5)

         val result = CPD(nevoie, reducere,
        (false, false) ->Flip(0.2),
        (false, true) ->Flip(0.5),
        (true, false) -> Flip(0.8),
        (true, true) -> Flip(1.0)
        )
         val algorithm=Importance(1000,nevoie)
         algorithm.start()
         val rez=alg.probability(nevoie,true)+alg.probability(nevoie,false);
        println("Probabilitatea ca cumparatorul sa aiba nevoie urgenta: " + rez)
       
        }
}