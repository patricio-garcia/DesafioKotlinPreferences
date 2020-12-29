package cl.serlitoral.desafiokotlinpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    val sharedPrefFile = "cl.serlitoral.desafiokotlinpreferences"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    val nicknameKey = "nickname_key"
    val ageKey = "age_key"
    var languagesKey = ""

    private lateinit var userName: String
    private lateinit var homeTitle: TextView
    private lateinit var nickname: TextView
    private lateinit var nicknameTitle: TextView
    private lateinit var spanishCheckBox: CheckBox
    private lateinit var englishCheckBox: CheckBox
    private lateinit var germanCheckBox: CheckBox
    private lateinit var otherCheckBox: CheckBox
    private lateinit var otherTextInput: TextInputEditText
    private lateinit var nickNameInput: TextInputEditText
    private lateinit var ageInput: TextInputEditText
    private lateinit var save: Button
    private lateinit var container: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor =  sharedPreferences.edit()


        setUpViews()
        loadData()
        save.setOnClickListener {
           saveLanguages()
            saveNickNameAndAge()
            Snackbar.make(container, "Datos guardados", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpViews() {
        homeTitle = findViewById(R.id.home_title)
        nickname = findViewById(R.id.nickname_title)
        nicknameTitle = findViewById(R.id.nickname_input)
        spanishCheckBox = findViewById(R.id.language_one)
        englishCheckBox = findViewById(R.id.language_two)
        germanCheckBox = findViewById(R.id.language_three)
        otherCheckBox = findViewById(R.id.language_other)
        otherTextInput = findViewById(R.id.other_language_input)
        nickNameInput = findViewById(R.id.nickname_input)
        ageInput = findViewById(R.id.age_input)
        save = findViewById(R.id.save_button)
        container = findViewById(R.id.container)
    }
    private fun loadData() {
        userName = sharedPreferences.getString("name_key", " ").toString()
        val title = "BienvenidoEsta es la pantalla inicial, aquí $userName podrá ver todas sus preferencias"
        homeTitle.text = title
        nickname.text = sharedPreferences.getString(nicknameKey, userName)


            handleLanguages()
            loadProfile()
    }

    private fun loadProfile() {
        //crear las claves para buscar y almacenar los datos, recuerde asociar las mismas al usuario de alguna manera

        val nickNameString = sharedPreferences.getString(nicknameKey, "")
        nickNameInput.setText(nickNameString)
        nicknameTitle.text = nickNameString
        val ageString = sharedPreferences.getInt(ageKey, 0)
        ageInput.setText(ageString.toString())
    }

    private fun handleLanguages() {
        languagesKey = "language_$userName"
        val languages = sharedPreferences.getStringSet(languagesKey, mutableSetOf<String>())

        if (languages != null) {
            for(language: String in languages) {
                resolveLanguage(language)
            }
        }
    }

    private fun resolveLanguage(language: String) {
        if (language.isNotEmpty() && language == "Spanish") {
            spanishCheckBox.isChecked = true
        }
        if (language.isNotEmpty() && language == "English") {
            englishCheckBox.isChecked = true
        }
        if (language.isNotEmpty() && language == "German") {
            germanCheckBox.isChecked = true
        }
        if (language.isNotEmpty() && language.contains("Other")) {
            otherCheckBox.isChecked = true
            otherTextInput.setText(language.split("*")[1]) // ocupar el mismo delimitador para almacenar el valor de este campo
        }
    }

    private fun saveLanguages() {
        val languages = mutableSetOf<String>()
        if (spanishCheckBox.isChecked) {
            languages.add("Spanish")
        }
        if (englishCheckBox.isChecked) {
            languages.add("English")
        }
        if (germanCheckBox.isChecked) {
            languages.add("German")
        }
        if (otherCheckBox.isChecked) {
            languages.add("Other*${otherTextInput.text.toString()}")
        }

        editor.putStringSet(languagesKey, languages)
        editor.apply()

    }

    private fun saveNickNameAndAge() {

        if (nickNameInput.text!!.isNotEmpty()) {
            editor.putString(nicknameKey, nickname_input.text.toString())
            editor.apply()
        }
        if (ageInput.text!!.isNotEmpty()) {
            val ageInt = ageInput.text.toString().toInt()
            editor.putInt(ageKey, ageInt)
            editor.apply()
        }
    }
}