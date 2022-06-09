package uz.shokirov.oxforddictionary.utls

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.shokirov.oxforddictionary.model.ListItem

object MySharedPrefences {
    private const val NAME = "cash"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences
    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var userListCash: ArrayList<ListItem>?
        get() = gsonToArrayList(preferences.getString("keyText","[]")!!)
        set(value) = preferences.edit {
            if (value != null) {
                it.putString("keyText", arrayToGson(value))
            }
        }

    fun gsonToArrayList(gsonString: String): ArrayList<ListItem> {
        var arrayList = ArrayList<ListItem>()
        val type = object : TypeToken<ArrayList<ListItem>>() {}.type
        arrayList = Gson().fromJson(gsonString, type)
        return arrayList
    }
    fun arrayToGson(arrayList: ArrayList<ListItem>):String{
        return Gson().toJson(arrayList)
    }


}