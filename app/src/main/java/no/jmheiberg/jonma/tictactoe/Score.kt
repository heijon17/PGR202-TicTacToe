package no.jmheiberg.jonma.tictactoe

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class Score: Fragment() {


    private lateinit var recyclerView: RecyclerView

    lateinit var highscores: Highscores
    lateinit var adapter: ScoreAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_score)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        adapter = ScoreAdapter(this.context)
        recyclerView.adapter = adapter

        highscores = Highscores(this.context, adapter)
        adapter.players = highscores.players
    }

    fun update(){
        val winner = arguments?.getParcelable<PlayerScore>("winner")
        if(winner != null){
            highscores.updateHighscores(winner)
            adapter.notifyDataSetChanged()
        }
    }


}