package no.jmheiberg.jonma.tictactoe

import android.support.v7.app.AlertDialog
import android.view.View

/**
 * Util class for creating Alerts
 */

class Alert(private val msg: String, private val view: View) {

    fun generate() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setTitle("There was an error")
        builder.setMessage(msg)
        builder.setNeutralButton("OK") { dialog, _ ->
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }

}