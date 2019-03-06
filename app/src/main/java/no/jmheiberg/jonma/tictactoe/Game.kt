package no.jmheiberg.jonma.tictactoe


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_game.*
import pl.droidsonroids.gif.GifDrawable
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer


class Game : AppCompatActivity() {

    var playersTurn = 1

    var secondsUsed = 0
    var minutesUsed = 0
    var timer = Timer()

    val btnList = ArrayList<ImageView>()
    var xPos = ArrayList<Int>()
    var oPos = ArrayList<Int>()

    var players: List<PlayerScore> = ArrayList()

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


        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.fr_container, Score())
        transaction.commit()

        readHighscores()




    }

    private fun readHighscores(){

        //CODE DUPLICATION
    var json = ""

        //Read file, not writeable. (testdata)
        try {
            val input = resources.openRawResource(R.raw.highscores)
            json = input.bufferedReader().use { it.readText() }
            Log.d("Highscore - loaded", json)

        }catch (e: Exception) {
            Log.d("Highscore", filesDir.toString())
        }


        //convert from string to object
        if(json.isNotEmpty()) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            players = gson.fromJson(json, object : TypeToken<List<PlayerScore>>() {}.type)


            Log.d("jsonparse", players.toString())
        }


        //Save file
        saveHighscore()

    }

    private fun saveHighscore() {
        try {
            val file = File(filesDir, "highscores.json")
            FileOutputStream(file).use {
                val gson = GsonBuilder().setPrettyPrinting().create()
                it.write(gson.toJson(players).toByteArray())
            }
        } catch (e: Exception) {
            Log.d("Highscore", e.toString())
        }
    }

    private fun updateHighscores(player: PlayerScore) {

        players.forEach {
            if(it.name == player.name){
                it.score++
                it.secondsplayed += player.secondsplayed
            }
        }
        saveHighscore()

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
            playersTurn = 2
        } else {
            btn.setImageDrawable(GifDrawable(resources, R.drawable.ttto))
            oPos.add(position)
            playersTurn = 1
        }

        calculateWinner()


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
    }

    private fun winner(player: Int) {

        pauseTimer()

        var winnerName = ""
        val bundle = Bundle()
        bundle.putInt("winner", player)
        if(player == 1) {
            bundle.putString("name", txtPlayer1.text.toString())
            winnerName = txtPlayer1.text.toString()
        }
        if(player == 2) {
            bundle.putString("name", txtPlayer2.text.toString())
            winnerName = txtPlayer2.text.toString()
        }

        for(btn in btnList) btn.isClickable = false

        players.forEach {
            if(winnerName == it.name){
                updateHighscores(it)
            } else {
                val newPlayer = PlayerScore("winnerName", 1, secondsUsed)
                updateHighscores(newPlayer)
            }
        }

        val gameover = GameOver()
        gameover.arguments = bundle

        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.fr_container, gameover)

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
