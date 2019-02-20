package no.jmheiberg.jonma.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.register_names.*

class RegisterNames : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_names)

        val btnStart: Button = findViewById(R.id.btn_start)
        btnStart.setOnClickListener{
            launchActivity(etxt_p1.text.toString(), etxt_p2.text.toString())
        }
    }

    private fun launchActivity(p1Name: String, p2Name: String) {
        val intent = Intent(this@RegisterNames, Game::class.java )
        intent.putExtra("p1", p1Name)
        intent.putExtra("p2", p2Name)
        startActivity(intent)
    }
}
