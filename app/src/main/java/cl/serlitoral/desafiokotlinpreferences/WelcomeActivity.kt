package cl.serlitoral.desafiokotlinpreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class WelcomeActivity : AppCompatActivity() {

    val sharedPrefFile = "cl.serlitoral.desafiokotlinpreferences"
    lateinit var sharedPreferences: SharedPreferences

    lateinit var nameUser: TextView
    lateinit var advance: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        nameUser = findViewById(R.id.welcome_name)
        advance = findViewById(R.id.advance_button)

        sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        setUpViewsAndListener()
    }

    private fun setUpViewsAndListener() {

        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        val userName = sharedPreferences.getString("name_key", " ") // obtener el nombre de usuario de alguna manera y mostrarlo en nameUser
        nameUser.text = userName
        advance.setOnClickListener {
            //Agregar los pasos necesarios para manejar la persistencia
            //de cuando un usuario ve la pantalla de bienvenida la primera vez
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}