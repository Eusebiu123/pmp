package Lab7

import com.cra.figaro.language.{Element, Select, Flip, Apply, Chain}
import com.cra.figaro.library.compound.{^^}
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.library.atomic.continuous.{Uniform}
import com.cra.figaro.algorithm.sampling.MetropolisHastings

object Grammy {

	class Autor {
		val popular = Flip(0.2)
	}

	class Album(val autor: Autor) {
		val calitate=Select(0.27 -> "mica", 0.6 -> "medie",0.13 ->"mare")
	}
    class Nominalizare(val p: Album) {
		def getProb() {
            val albumNominalizat = CPD(p.calitate, p.autor.popular,
                ('mica', false) -> Flip(0.003)
                ('mica', true) -> Flip(0.014)
                ('medie', false) -> Flip(0.016)
                ('medie', true) -> Flip(0.043)
                ('mare', false) -> Flip(0.047)
                ('mare', true) -> Flip(0.18)
        } 
	}

	
	def main(args: Array[String]) {
		val Autori: Array[Autor] = Array.fill(5)(new Autor())
        val Album: Array[Album] = Array.fill(10)(new Album())
        val c: Array[Album] = Array.fill(10)(new Album())
    
	}
}
