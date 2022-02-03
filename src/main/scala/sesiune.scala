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

	class Album(val p: Autor) {
		val calitate=Select(0.27 -> "mica", 0.6 -> "medie",0.13 ->"mare")
	}
    class Nominalizare(val p: Album) {
		def getProb()
        {
            
        }
	}

	
	def main(args: Array[String]) {
		val rd = new ResearchAndDevelopment()
		val hr = new HumanResources()
		val p = new Production(rd, hr)
		val s = new Sales(p)
		val f = new Finance(hr, s)
		val firm = new Firm(rd, hr, p, s, f)
        
        val algorithm = Importance(10000, firm.health)
        rd.state.setCondition((s: Double) => s >= 90)
        algorithm.start()
        algorithm.stop()
        println("Mean(Firm.health|ResearchAndDevelopment.state >= 90) = " + algorithm.mean(firm.health))
        rd.state.unobserve()
        algorithm.kill()

        val algorithm2 = Importance(10000, firm.health)
        rd.state.setCondition((s: Double) => s >= 90)
        hr.state.setCondition((s: Double) => s >= 95)
        algorithm2.start()
        algorithm2.stop()
        println("Mean(Firm.health|ResearchAndDevelopment.state >= 90 & HumanResources.state >= 95) = " + algorithm2.mean(firm.health))
        rd.state.unobserve()
        hr.state.unobserve()
        algorithm2.kill()

        val algorithm3 = Importance(10000, firm.health, hr.state, s.state)
        f.state.setCondition((f: Double) => f <= 20)
        algorithm3.start()
        algorithm3.stop()
        println("Mean(Firm.health|Finance.state <= 20) = " + algorithm3.mean(firm.health))
        println("Mean(HumanResources.state|Finance.state <= 20) = " + algorithm3.mean(hr.state))
        println("Mean(Sales.state|Finance.state <= 20) = " + algorithm3.mean(s.state))
        f.state.unobserve()
        hr.state.unobserve()
        algorithm3.kill()

        // ... more queries to go
	}
}
