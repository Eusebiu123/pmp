import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.discrete._
import com.cra.figaro.algorithm.factored._
object hpf{
  def main(args: Array[String]){
    // cele 2
    val die1 = FromRange(1, 7)
val die2 = FromRange(1, 7)
val total = Apply(die1, die2, (i1: Int, i2: Int) => i1 + i2)
println(VariableElimination.probability(total, 11))
    // relatia de comparatie
    def castiga(s1: String, s2: String) : Boolean =
      (s1 == 7 && s2 == 2) ||
      (s1 == 7 && s2 == 3) ||
      (s1 == 7 && s2 == 12) ||
      (s1 == 11 && s2 == 3) ||
      (s1 == 11 && s2 == 2) ||
      (s1 == 11 && s2 == 12) 
    
    // jucatorii
   val zar1p1 = Uniform (1, 2, 3, 4, 5, 6)
    val zar2p2 = Uniform (1, 2, 3, 4, 5, 6)
    // joc cu o singura runda
    val jocORunda = Apply(zar1p1, zar2p2, (v1:String, v2:String) =>
      if (v1 == v2) "egal"
      else if (castiga(v1, v2)) "jucator1"
      else "jucator2")
    // afisare probabilitate
    println("Joc intr-o runda: " + VariableElimination.probability(jocORunda, "jucator2"))
    
    
  }
}