package no.jmheiberg.jonma.tictactoe


import android.app.ActionBar
import android.app.Person
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_game.*
import pl.droidsonroids.gif.GifDrawable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.concurrent.timer


class Game : AppCompatActivity() {

    lateinit var board: Array<Player>

    var vsCpu = false

    var playersTurn = 1

    var secondsUsed = 0
    var minutesUsed = 0
    var timer = Timer()
    var score = Score()

    val btnList = ArrayList<ImageView>()
    var xPos = ArrayList<Int>()
    var oPos = ArrayList<Int>()

    var players: List<PlayerScore> = ArrayList()

    private lateinit var highscores: Highscores

    private lateinit var txtPlayer1: TextView
    private lateinit var txtPlayer2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val intent = intent

        txtPlayer1 = findViewById(R.id.txt_p1)
        txtPlayer2 = findViewById(R.id.txt_p2)

        txtPlayer1.text = intent.getStringExtra("p1")
        txtPlayer2.text = intent.getStringExtra("p2")


        btnList.add(findViewById(R.id.img_game_1))
        btnList.add(findViewById(R.id.img_game_2))
        btnList.add(findViewById(R.id.img_game_3))
        btnList.add(findViewById(R.id.img_game_4))
        btnList.add(findViewById(R.id.img_game_5))
        btnList.add(findViewById(R.id.img_game_6))
        btnList.add(findViewById(R.id.img_game_7))
        btnList.add(findViewById(R.id.img_game_8))
        btnList.add(findViewById(R.id.img_game_9))

        for (btn in btnList){
            btn.setOnClickListener{
                clickedImage(btn)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.fr_container, score)
        transaction.commit()

        if (txtPlayer2.text == "TTTBot") vsCpu = true

        setTurn()

        board = Array(9) { Player.Blank }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun setTurn() {
        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        anim.repeatCount = Animation.INFINITE
        anim.repeatMode = Animation.REVERSE
        if (playersTurn == 1) {
            txt_p1_turn.startAnimation(anim)
            txt_p2_turn.clearAnimation()
        }
        if (playersTurn == 2) {
            txt_p2_turn.startAnimation(anim)
            txt_p1_turn.clearAnimation()
        }
    }


    override fun onPause() {
        super.onPause()
        pauseTimer()
    }

    override fun onResume() {
        super.onResume()
        resumeTimer()
    }

    private fun clickedImage(btn: ImageView) {

        btn.isClickable = false
        var position = 0
        when(btn.id){
            R.id.img_game_1 -> position = 1
            R.id.img_game_2 -> position = 2
            R.id.img_game_3 -> position = 3
            R.id.img_game_4 -> position = 4
            R.id.img_game_5 -> position = 5
            R.id.img_game_6 -> position = 6
            R.id.img_game_7 -> position = 7
            R.id.img_game_8 -> position = 8
            R.id.img_game_9 -> position = 9
        }

        if(playersTurn == 1){
            btn.setImageDrawable(GifDrawable(resources, R.drawable.tttx))
            xPos.add(position)
            board[position-1] = Player.Human
            playersTurn = 2
        }
        else if(playersTurn == 2){
            btn.setImageDrawable(GifDrawable(resources, R.drawable.ttto))
            oPos.add(position)
            board[position-1] = Player.Computer
            playersTurn = 1
        }


        calculateWinner()
        setTurn()
        if (vsCpu && playersTurn == 2) nextMove()
    }

    private fun nextMove() {
        Handler().postDelayed ({

            var pos = (1..9).random()
            while (oPos.contains(pos) || xPos.contains(pos) || playersTurn == 0) {
                pos = (1..9).random()
            }
            clickedImage(btnList[pos-1])
        }, 1000)
    }


    private fun calculateWinner() {

        if (xPos.contains(1) && xPos.contains(2) && xPos.contains(3)) winner(1)
        if (xPos.contains(4) && xPos.contains(5) && xPos.contains(6)) winner(1)
        if (xPos.contains(7) && xPos.contains(8) && xPos.contains(9)) winner(1)

        if (xPos.contains(1) && xPos.contains(4) && xPos.contains(7)) winner(1)
        if (xPos.contains(2) && xPos.contains(5) && xPos.contains(8)) winner(1)
        if (xPos.contains(3) && xPos.contains(6) && xPos.contains(9)) winner(1)

        if (xPos.contains(1) && xPos.contains(5) && xPos.contains(9)) winner(1)
        if (xPos.contains(3) && xPos.contains(5) && xPos.contains(7)) winner(1)

        if (oPos.contains(1) && oPos.contains(2) && oPos.contains(3)) winner(2)
        if (oPos.contains(4) && oPos.contains(5) && oPos.contains(6)) winner(2)
        if (oPos.contains(7) && oPos.contains(8) && oPos.contains(9)) winner(2)

        if (oPos.contains(1) && oPos.contains(4) && oPos.contains(7)) winner(2)
        if (oPos.contains(2) && oPos.contains(5) && oPos.contains(8)) winner(2)
        if (oPos.contains(3) && oPos.contains(6) && oPos.contains(9)) winner(2)

        if (oPos.contains(1) && oPos.contains(5) && oPos.contains(9)) winner(2)
        if (oPos.contains(3) && oPos.contains(5) && oPos.contains(7)) winner(2)

        if (oPos.size + xPos.size == 9) winner(0)

    }

    private fun winner(player: Int) {

        pauseTimer()
        playersTurn = 0

        var winnerName = ""
        val gameOverBundle = Bundle()
        gameOverBundle.putInt("winner", player)
        if(player == 1) {
            gameOverBundle.putString("name", txtPlayer1.text.toString())
            winnerName = txtPlayer1.text.toString()
        }
        if(player == 2) {
            gameOverBundle.putString("name", txtPlayer2.text.toString())
            winnerName = txtPlayer2.text.toString()
        }

        for(btn in btnList) btn.isClickable = false

        //pass winner to score
        val scoreBundle = Bundle()

        if (player != 0) {
            scoreBundle.putParcelable("winner", PlayerScore(winnerName, 1, secondsUsed))
            score.arguments = scoreBundle
            score.update()
        }


        //show gameover
        val gameover = GameOver()
        gameover.arguments = gameOverBundle
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.fr_container, gameover)
        transaction.commit()
    }

    private fun resumeTimer(){
        initTimer()
    }


    private fun pauseTimer(){
        this.timer.cancel()
    }

    private fun initTimer() {
        this.timer = timer("timeCounter", false, 1000, 1000) {
            secondsUsed++
            if (secondsUsed % 60 == 0) {
                minutesUsed++
                secondsUsed = 0
            }
            val timeUsed = String.format("%d:%02d", minutesUsed, secondsUsed)
            this@Game.runOnUiThread {
                txt_time.text = "Time played: $timeUsed"
            }
        }

    }

}
