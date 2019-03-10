package no.jmheiberg.jonma.tictactoe



import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class ScoreAdapter(context: Context?) : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {


    private val mContext = context
    var players: List<PlayerScore> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext)
            .inflate(R.layout.score_row_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }



    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val player = players[position]

        viewHolder.txtName.text = "${position + 1}) ${player.name}:"
        viewHolder.txtScore.text = "${player.score}"
        viewHolder.txtTimePlayed.text = "${player.getTimePlayed()}"
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtName: TextView = itemView.findViewById(R.id.txt_score_name)
        val txtScore: TextView = itemView.findViewById(R.id.txt_score)
        val txtTimePlayed: TextView = itemView.findViewById(R.id.txt_score_time_played)


    }

}