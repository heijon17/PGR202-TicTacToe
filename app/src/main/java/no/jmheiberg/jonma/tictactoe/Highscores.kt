package no.jmheiberg.jonma.tictactoe

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class Highscores(private val context: Context?, private val adapter: ScoreAdapter) {

    var players: ArrayList<PlayerScore> = ArrayList()

    init {
        initHighscores()
    }

    private fun initHighscores() {
        var json = ""

        //Read file
        try {
            val file = File(context?.filesDir, "highscores.json")
            json = FileInputStream(file).bufferedReader().use { it.readText() }


        } catch (e: Exception) {
            Log.d("Highscore", e.toString())
        }


        //convert from string to object
        if (json.isNotEmpty()) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            players = gson.fromJson(json, object : TypeToken<List<PlayerScore>>() {}.type)
        }
        sortByWins()
    }

    fun updateHighscores(player: PlayerScore) {

        var existingPlayer = false
        for (item in players) {
            if (item.name == player.name) {
                item.score++
                item.secondsplayed += player.secondsplayed
                existingPlayer = true
                break
            }
        }
        if (!existingPlayer) players.add(player)
        sortByWins()
        adapter.notifyDataSetChanged()
        saveHighscore()

    }

    private fun saveHighscore() {
        try {
            val file = File(context?.filesDir, "highscores.json")
            FileOutputStream(file).use {
                val gson = GsonBuilder().setPrettyPrinting().create()
                it.write(gson.toJson(players).toByteArray())
            }
        } catch (e: Exception) {
            Log.d("Highscore", e.toString())
        }
    }

    private fun sortByWins() {
        players.sortByDescending { it.score }
    }
}