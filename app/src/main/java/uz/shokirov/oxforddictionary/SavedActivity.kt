package uz.shokirov.oxforddictionary

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.core.widget.addTextChangedListener
import uz.shokirov.oxforddictionary.adapter.RvAdapter
import uz.shokirov.oxforddictionary.databinding.ActivitySavedBinding
import uz.shokirov.oxforddictionary.model.ListItem
import uz.shokirov.oxforddictionary.utls.MySharedPrefences
import java.util.*
import kotlin.collections.ArrayList

class SavedActivity : AppCompatActivity() {
    lateinit var binding: ActivitySavedBinding
    lateinit var list: ArrayList<ListItem>
    lateinit var adapter: RvAdapter
    private lateinit var mTTsFrom: TextToSpeech

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setAdapter()
        binding.imageBack.setOnClickListener {
            finish()
        }
        binding.searchView.addTextChangedListener {
            var listSearch = ArrayList<ListItem>()
            for (word in list) {
                for (i in 0 until word.word.length) {
                    if (word.word.subSequence(0, i).toString()
                            .lowercase(Locale.getDefault()) == binding.searchView.text.toString()
                            .toLowerCase(Locale.getDefault())
                    ) {
                        listSearch.add(word)
                    }
                }
            }
            binding.rv.adapter = RvAdapter(listSearch, this)
        }


    }

    override fun onStart() {
        adapter.notifyDataSetChanged()
        super.onStart()
    }


    private fun setAdapter() {
        MySharedPrefences.init(this)
        list = MySharedPrefences.userListCash!!
        adapter = RvAdapter(list, this)
        binding.rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}