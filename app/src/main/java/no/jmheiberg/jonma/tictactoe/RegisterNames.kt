package no.jmheiberg.jonma.tictactoe


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import kotlinx.android.synthetic.main.register_names.*

class RegisterNames : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_names)

        val btnStart: Button = findViewById(R.id.btn_start)
        btnStart.setOnClickListener{
            launchActivity(etxt_p1.text.toString(), etxt_p2.text.toString())
        }

        val switchVsCpu: Switch = findViewById(R.id.switch_vsCpu)
        switchVsCpu.setOnCheckedChangeListener{ _, _ ->
            if(switchVsCpu.isChecked){
                etxt_p2.setText("TTTBot")
                etxt_p2.isEnabled = false
                switchVsCpu.text = "Yes"
            }
            else{
                etxt_p2.setText("")
                etxt_p2.isEnabled = true
                switchVsCpu.text = "No"
            }
        }
    }

    private fun launchActivity(p1Name: String, p2Name: String) {
        val intent = Intent(this@RegisterNames, Game::class.java )
        when {
            p1Name.isEmpty() -> alert("Player 1 needs a name")
            p2Name.isEmpty() -> alert("Player 2 needs a name")
            else -> {
                intent.putExtra("p1", p1Name)
                intent.putExtra("p2", p2Name)
                startActivity(intent)
            }
        }
    }

    private fun alert(msg: String) {

        val alert = Alert(msg, btn_start)
        alert.generate()
    }
}
