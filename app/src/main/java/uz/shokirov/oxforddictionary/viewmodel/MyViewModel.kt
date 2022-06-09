package uz.shokirov.oxforddictionary.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.shokirov.oxforddictionary.model.ListItem
import uz.shokirov.oxforddictionary.model.Words
import java.io.IOException

class MyViewModel(var context: Context):ViewModel() {
    private val TAG = "MyViewModels"
    private var liveDouble = MutableLiveData<List<ListItem>>()

    fun getLists():MutableLiveData<List<ListItem>>{

        val jsonFileString = getJsonDataFromAsset(context.applicationContext, "data.json")!!
        //Log.i("data", jsonFileString)
        val gson = Gson()
        val lisType = object : TypeToken<Words>() {}.type
        val wordsList: Words = gson.fromJson(jsonFileString, lisType)

        liveDouble.value = wordsList


        return liveDouble
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