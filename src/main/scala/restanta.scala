package restanta

import com.cra.figaro.language.{Select, Apply, Constant, Element, Chain, Universe, Flip}
import com.cra.figaro.library.compound.{If, CPD, RichCPD, OneOf, *, ^^}
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.algorithm.sampling.{Importance, MetropolisHastings, ProposalScheme, MetropolisHastingsAnnealer, Schedule}
import com.cra.figaro.algorithm.filtering.ParticleFilter

object Ex1 {

    abstract class State {
        val pret:Element[String]
        val vanzari:Element[String]
    }

    class Initial extends State {
        val pret = Select(0.3 -> "scade", 0.3 -> "stagneaza", 0.3 -> "creste")
        val vanzari = 
        CPD(pret,
        "scade" -> Select(0.5 -> "ridicat", 0.2 -> "mediu", 0.15 -> "scazut", 0.05 -> "foarte scazut"),
        "stagneaza" -> Select(0.3 -> "ridicat", 0.3 -> "mediu", 0.2 -> "scazut", 0.1 -> "foarte scazut"),
        "creste" -> Select(0.2 -> "ridicat", 0.2 -> "mediu", 0.2 -> "scazut", 0.4 -> "foarte scazut")
        )
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
	}
}
