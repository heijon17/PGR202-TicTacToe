package no.jmheiberg.jonma.tictactoe



class AI {




    fun getScore(board: Array<Player>, player: Player): Int {

        if(isWinning(board, Player.Human)) return -10
        if(isWinning(board,Player.Computer)) return 10

        var move = -1
        var score = -2


        for(i in 0..8) {
            if (board[i] == Player.Blank) {
                var newBoard = board.copyOf()
                newBoard[i] = player
                var newScore = - this.getScore(newBoard, oppositePlayer(player))
                if(newScore > score) {
                    score = newScore
                    move = i
                }
            }
        }
        if (move == -1) return 0
        return score
    }

    private fun oppositePlayer(player: Player): Player{
        return if(player == Player.Computer) Player.Human
        else Player.Computer

    }

    private fun isWinning(board: Array<Player>, player: Player) : Boolean {

        if(board[0] == player && board[1] == player && board[2] == player) return true
        if(board[3] == player && board[4] == player && board[5] == player) return true
        if(board[6] == player && board[7] == player && board[8] == player) return true
        else return false






//        if (xPos.contains(1) && xPos.contains(2) && xPos.contains(3)) winner(1)
//        if (xPos.contains(4) && xPos.contains(5) && xPos.contains(6)) winner(1)
//        if (xPos.contains(7) && xPos.contains(8) && xPos.contains(9)) winner(1)
//
//        if (xPos.contains(1) && xPos.contains(4) && xPos.contains(7)) winner(1)
//        if (xPos.contains(2) && xPos.contains(5) && xPos.contains(8)) winner(1)
//        if (xPos.contains(3) && xPos.contains(6) && xPos.contains(9)) winner(1)
//
//        if (xPos.contains(1) && xPos.contains(5) && xPos.contains(9)) winner(1)
//        if (xPos.contains(3) && xPos.contains(5) && xPos.contains(7)) winner(1)
//
//        if (oPos.contains(1) && oPos.contains(2) && oPos.contains(3)) winner(2)
//        if (oPos.contains(4) && oPos.contains(5) && oPos.contains(6)) winner(2)
//        if (oPos.contains(7) && oPos.contains(8) && oPos.contains(9)) winner(2)
//
//        if (oPos.contains(1) && oPos.contains(4) && oPos.contains(7)) winner(2)
//        if (oPos.contains(2) && oPos.contains(5) && oPos.contains(8)) winner(2)
//        if (oPos.contains(3) && oPos.contains(6) && oPos.contains(9)) winner(2)
//
//        if (oPos.contains(1) && oPos.contains(5) && oPos.contains(9)) winner(2)
//        if (oPos.contains(3) && oPos.contains(5) && oPos.contains(7)) winner(2)

    }


//
//
//
//    private fun minimax(board: Array<Player>, player: Player): Int {
//        if (var winner = self . getWinner (board) as Player) {
//            return winner.rawValue * player.rawValue // -1 * -1 || 1 * 1
//        }
//
//        var move = -1
//        var score = -2
//
//        for var i = 0; i < 9; ++i {
//            // For all moves
//            if board[i] == Player.Blank {
//                // Only possible moves
//                var boardWithNewMove = board // Copy board to make it mutable
//                boardWithNewMove[i] = player // Try the move
//                let scoreForTheMove = - self.minimax(
//                    boardWithNewMove,
//                    player: self. getOponnentFor (player)) // Count negative score for oponnent
//                if scoreForTheMove > score {
//                    score = scoreForTheMove
//                    move = i
//                } // Picking move that gives oponnent the worst score
//            }
//        }
//        if move == -1 {
//            return 0 // No move - it's a draw
//        }
//        return score
//    }
}

enum class Player(score: Int){
    Human(-1),
    Blank(0),
    Computer(1)


}




