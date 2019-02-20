package no.jmheiberg.jonma.tictactoe


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import pl.droidsonroids.gif.GifDrawable


class Game : AppCompatActivity() {

    var playersTurn = 1

    val btnList = ArrayList<ImageView>()
    var xPos = ArrayList<Int>()
    var oPos = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val intent = intent

        val txtPlayer1: TextView = findViewById(R.id.txt_p1)
        val txtPlayer2: TextView = findViewById(R.id.txt_p2)

        txtPlayer1.text = intent.getStringExtra("p1")
        txtPlayer2.text = intent.getStringExtra("p2")



//        val imgTest: ImageView  = findViewById(R.id.img_game_1)
//
//        imgTest.setOnClickListener{
//            val drawable = GifDrawable(resources, R.drawable.ttto)
//            imgTest.setImageDrawable(drawable)
//
//        }

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
        println(xPos.toString())

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
        println("winner is player $player")
        for(btn in btnList) btn.isClickable = false
    }

}
