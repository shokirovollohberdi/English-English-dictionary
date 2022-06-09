package uz.shokirov.oxforddictionary

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import uz.shokirov.oxforddictionary.adapter.RvAdapter
import uz.shokirov.oxforddictionary.databinding.ActivityMainBinding
import uz.shokirov.oxforddictionary.model.ListItem
import uz.shokirov.oxforddictionary.utls.GlobalList
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var list: ArrayList<ListItem>
    lateinit var adapter: RvAdapter
    private lateinit var mTTsFrom: TextToSpeech
    lateinit var filteredList: ArrayList<ListItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setAdapter()
        binding.imageSave.setOnClickListener {
            startActivity(Intent(this, SavedActivity::class.java))
        }

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filteredList = ArrayList()
                for (item in list) {
                    if (item.word.lowercase(Locale.getDefault()).contains(
                            binding.searchView.text.toString().toLowerCase(Locale.getDefault())
                        )
                    ) {
                        filteredList.add(item)
                    }
                }
                binding.rv.adapter = RvAdapter(filteredList, this@MainActivity)
            }
        }

        )
    }


    private fun setAdapter() {
        list = ArrayList()
        list = GlobalList.getList()
        Log.d("size", "setAdapter: ${list.size}")
        list.sortBy {
            it.word
        }
        adapter = RvAdapter(list, this)
        binding.rv.adapter = adapter
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }


}