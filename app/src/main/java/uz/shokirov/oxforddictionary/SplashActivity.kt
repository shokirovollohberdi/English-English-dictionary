package uz.shokirov.oxforddictionary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.shokirov.oxforddictionary.model.ListItem
import uz.shokirov.oxforddictionary.model.Words
import uz.shokirov.oxforddictionary.utls.GlobalList
import java.io.IOException

class SplashActivity : AppCompatActivity() {
    lateinit var listItem: ArrayList<ListItem>
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        handler = Handler()

        handler.postDelayed({
            loading()
        }, 3000)

    }

    fun loading() {
        val jsonFileString = getJsonDataFromAsset(applicationContext, "data.json")!!
        //Log.i("data", jsonFileString)
        val gson = Gson()
        val lisType = object : TypeToken<Words>() {}.type
        val wordsList: Words = gson.fromJson(jsonFileString, lisType)
        listItem = ArrayList()

        wordsList.forEach {
            listItem.add(it)
        }
        GlobalList.listItem = wordsList
        startActivity(Intent(this, MainActivity::class.java))

    }


    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}