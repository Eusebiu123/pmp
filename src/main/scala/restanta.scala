package restanta

import com.cra.figaro.language.{Select, Apply, Constant, Element, Chain, Universe, Flip}
import com.cra.figaro.library.compound.{If, CPD, RichCPD, OneOf, *, ^^}
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.algorithm.sampling.{Importance, MetropolisHastings, ProposalScheme, MetropolisHastingsAnnealer, Schedule}
import com.cra.figaro.algorithm.filtering.ParticleFilter

object Ex1 {

    abstract class State {
        val pret:Element[String]
        def vanzari() = CPD(pret,
        "scade" -> Select(0.5 -> "ridicat", 0.2 -> "mediu", 0.15 -> "scazut", 0.05 -> "foarte scazut"),
        "stagneaza" -> Select(0.3 -> "ridicat", 0.3 -> "mediu", 0.2 -> "scazut", 0.1 -> "foarte scazut"),
        "creste" -> Select(0.2 -> "ridicat", 0.2 -> "mediu", 0.2 -> "scazut", 0.4 -> "foarte scazut")
        )
    }

    class InitialState extends State {
        val pret = Select(0.3 -> "scade", 0.3 -> "stagneaza", 0.3 -> "creste")   
    }

    class NextState(current: State) extends State {
        val pret = 
        CPD(current.pret,
        "creste" -> Select(0.2 -> "scade", 0.8 -> "stagneaza"),
        "stagneaza" -> Select(0.3 -> "scade", 0.3 -> "stagneaza", 0.3 -> "creste"),
        "scade" -> Select(0.05 -> "scade", 0.6 -> "stagneaza", 0.35 -> "creste")
        )
    }

	def main(args: Array[String]) {
        val an = 12
        val vanzariLuni:Array[State] = Array.fill(an)(new InitialState());

        for { i <- 1 until an } {
            vanzariLuni(i) = new NextState(vanzariLuni(i-1));
        }

        vanzariLuni(0).vanzari.observe("ridicat")
        vanzariLuni(1).vanzari.observe("ridicat")
        vanzariLuni(2).vanzari.observe("mediu")

        println("Sansa ca in luna 4 sa scada " + VariableElimination.probability(vanzariLuni(3).pret, "scade"))
        println("Sansa ca in luna 4 sa stagneze " + VariableElimination.probability(vanzariLuni(3).pret, "stagneaza"))
        println("Sansa ca in luna 4 sa creasca " + VariableElimination.probability(vanzariLuni(3).pret, "creste"))

        /*
        Sansa ca in luna 4 sa scada 0.2511419083524646
        Sansa ca in luna 4 sa stagneze 0.48189445158210303
        Sansa ca in luna 4 sa creasca 0.26696364006543244
        */

        vanzariLuni(0).vanzari.unobserve()
        vanzariLuni(1).vanzari.unobserve()
        vanzariLuni(2).vanzari.unobserve()

        vanzariLuni(4).vanzari.observe("scazut")
        vanzariLuni(5).vanzari.observe("scazut")
        println("Sansa ca in luna 2 preturile sa scada " + Importance.probability(vanzariLuni(1).pret, "scade"))
        println("Sansa ca in luna 3 preturile sa scada " + Importance.probability(vanzariLuni(2).pret, "scade"))

        /*
        Sansa ca in luna 2 preturile sa scada 0.2909290625680987
        Sansa ca in luna 3 preturile sa scada 0.1920933474277721
        */
	}
}
