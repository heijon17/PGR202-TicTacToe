package no.jmheiberg.jonma.tictactoe


data class PlayerScore(val name: String, val score: Int, val timePlayed: Int){
    fun getTimePlayed() : String{
        return "3 min 42 sec"
    }
}