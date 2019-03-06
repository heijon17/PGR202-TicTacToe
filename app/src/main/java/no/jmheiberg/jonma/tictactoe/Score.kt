package no.jmheiberg.jonma.tictactoe

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class Score: Fragment() {

    private var players: ArrayList<PlayerScore> = ArrayList()
    private lateinit var recyclerView: RecyclerView




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_score, container, false)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataset()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_score)

        recyclerView.layoutManager = LinearLayoutManager(this.context)

        val adapter = ScoreAdapter(this.context, players)
        recyclerView.adapter = adapter
    }


    private fun initDataset() {
        var json = ""

        //Read file, not writeable. (testdata)
        try {
            val input = resources.openRawResource(R.raw.highscores)
            json = input.bufferedReader().use { it.readText() }
            Log.d("Highscore - loaded", json)

        }catch (e: Exception) {
            Log.d("Highscore", e.toString())
        }


        //convert from string to object
        if(json.isNotEmpty()) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            players = gson.fromJson(json, object : TypeToken<List<PlayerScore>>() {}.type)


            Log.d("jsonparse", players.toString())
        }



    }
}