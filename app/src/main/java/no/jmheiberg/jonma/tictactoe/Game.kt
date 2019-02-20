package no.jmheiberg.jonma.tictactoe


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide


class Game : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val imgTest: ImageView  = findViewById(R.id.img_test)

        imgTest.setOnClickListener{
            Glide.with(this).load(R.drawable.ttto).into(imgTest)

        }

    }

}
