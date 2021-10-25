// To keep the code simple, I just make the cards an integer
        val cards = List(5, 4, 3, 2, 1)
        // The discrete uniform distribution chooses uniformly from a fixed
        // set of possibilities
        val player1Card1 = discrete.Uniform(cards:_*)
        val player1Card2 = Chain(player1Card, (card: Int) =>
            // Player 2 can get any card except the first player’s card
            discrete.Uniform(cards.filter(_ != card):_*)
        )
        val player2Card1 = Chain(player1Card, player1Card2, (card1: Int, card2: Int) =>
            // Player 2 can get any card except the first player’s card
            discrete.Uniform(cards.filter(_ != (card1 || card2)):_*)
        )
         val player2Card2 = Chain(player1Card,player1Card2, player2Card, (card1: Int, card2: Int, card3: Int) =>
          
            discrete.Uniform(cards.filter(_ != (card1 || card2 || card3)):_*)
        )
        val player1Bet1 = RichCPD(player1Card,player1Card2,
            // Player 1 is more likely to bet with a higher card,
            // but will sometimes bet with a lower card to bluff
            (OneOf(5, 4, 3),OneOf(5,4,3) )-> Flip(0.9),
            (OneOf(5, 4, 3),* )-> Flip(0.6)
            (*,OneOf(5,4,3) )-> Flip(0.6)
            (, )-> Flip(0.4) // ×××Change this for part (c)×××
        )
        
        val player2Bet = RichCPD(player2Card1,player1Card2 player1Bet1,
           (OneOf(5, 4), OneOf(5,4), *) -> Flip(0.9),
            (,, OneOf(false)) -> Flip(0.6),
            (, *,) -> Flip(0.1)
        )
        
        val player1Bet2 =
        Apply(player1Card1,player1Card2, player1Bet1, player2Bet,
            (card1: Int, card2: Int, bet11: Boolean, bet2: Boolean) =>
            // Player 1’s second bet is only relevant if she passed the
            // first time and player 2 bet
            !bet11 && bet2 && ((card1 == 5 || card1 == 4) || (card2 == 5 || card2 == 4))
        
        )

// EX 3


//  a) si b) Sunt 5 variabile: cartea primului jucator care nu depinde de nimic, cartea celui de-al doilea jucator care 
// depinde de cartea primului, decizia primului jucator care depinde de cartea lui, 
// decizia jucatorului 2 care depinde de cartea lui si de decizia jucatorului 1, 
// decizia 2 a primului jucator care depinde de prima lui decizie, cartea lui si 
// decizia jucatorului 2.

// c) Cartile celor 2 jucatori sunt alese uniform, iar actiunile lor depind de cartile lor si de actiunile anterioare deci 
// forma functionala a actiunilor este de Select sau Flip.

// d) Parametrii variabilelor care reprezinta cartile sunt cunoscute, iar cei care reprezinta actiunile 
// trebuie sa ii estimam sau sa ii invatam din experienta.

//ex5
val seq1 = Apply(player1Card, player1Bet1, player2Bet, player1Bet2,
                         (card1: Int, bet11: Boolean, bet2: Boolean, bet12: Boolean) =>
                         card1 == 4 && bet11 && !bet2 && !bet12)