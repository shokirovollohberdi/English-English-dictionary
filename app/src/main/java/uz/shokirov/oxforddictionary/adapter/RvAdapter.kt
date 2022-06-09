package uz.shokirov.oxforddictionary.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import uz.shokirov.oxforddictionary.InfoActivity
import uz.shokirov.oxforddictionary.R
import uz.shokirov.oxforddictionary.databinding.ItemRvBinding
import uz.shokirov.oxforddictionary.model.ListItem
import uz.shokirov.oxforddictionary.utls.MySharedPrefences
import java.util.*
import kotlin.collections.ArrayList

class RvAdapter(var list: ArrayList<ListItem>, var context: Context) :
    RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
        private lateinit var mTTsFrom: TextToSpeech
        fun onBind(listItem: ListItem, position: Int) {

            var first = listItem.word.subSequence(0,1)
            var more = listItem.word.subSequence(1,listItem.word.length).toString().toLowerCase()

            itemRv.tvWord.text = "$first$more"


            itemRv.imageVoice.setOnClickListener {
                mTTsFrom = TextToSpeech(context) { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        var result = mTTsFrom.setLanguage(Locale.ENGLISH)
                        var pitch = 0.8f
                        var speed = 1.1f
                        mTTsFrom.setPitch(pitch)
                        mTTsFrom.setSpeechRate(speed)
                        mTTsFrom.speak(listItem.word, TextToSpeech.QUEUE_FLUSH, null)
                    }
                }
            }
            itemRv.root.setOnClickListener {
                var intent = Intent(context, InfoActivity::class.java)
                intent.putExtra("key", listItem)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun searchList(filteredList: ArrayList<ListItem>) {
        list = filteredList
        notifyDataSetChanged()
    }
}
