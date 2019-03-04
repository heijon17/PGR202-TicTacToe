package no.jmheiberg.jonma.tictactoe

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class Score: Fragment() {

    private lateinit var dataset: ArrayList<PlayerScore>
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

        val adapter = ScoreAdapter(this.context, dataset)
        recyclerView.adapter = adapter
    }


    private fun initDataset() {
        dataset = ArrayList()
        dataset.add(PlayerScore("Jon-Martin", 15, 150))
        dataset.add(PlayerScore("Tom", 5, 80))
        dataset.add(PlayerScore("TTTBot", 3, 45))
    }
}