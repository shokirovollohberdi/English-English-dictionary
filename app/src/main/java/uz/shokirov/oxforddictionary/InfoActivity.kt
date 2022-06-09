package uz.shokirov.oxforddictionary

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import uz.shokirov.oxforddictionary.databinding.ActivityInfoBinding
import uz.shokirov.oxforddictionary.model.ListItem
import uz.shokirov.oxforddictionary.utls.MySharedPrefences

class InfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityInfoBinding
    lateinit var defination: String
    lateinit var word: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        var intentList = intent.getSerializableExtra("key") as ListItem

        word = intentList.word
        defination = intentList.definition

        binding.tvDefinition.text = intentList.definition
        binding.tvWord.text = intentList.word
        binding.imageBack.setOnClickListener {
            finish()
        }

        binding.imageCopy.setOnClickListener {
            copyText(word, defination)
        }
        binding.imageShare.setOnClickListener {
            shareText(word, defination)
        }
        checkImageSave(intentList)

        binding.imageSave.setOnClickListener {
            MySharedPrefences.init(this)
            var list = MySharedPrefences.userListCash
            if (!checkImageSave(intentList)) {
                list?.add(intentList)
                MySharedPrefences.userListCash = list
                binding.imageSave.setImageResource(R.drawable.ic_baseline_bookmark_24)
            } else {
                list?.remove(intentList)
                MySharedPrefences.userListCash = list
                binding.imageSave.setImageResource(R.drawable.ic_unsave)
            }
        }

    }

    private fun checkImageSave(intentList: ListItem): Boolean {
        MySharedPrefences.init(this)
        var isHave = false
        var listCash = MySharedPrefences.userListCash
        for (i in listCash!!.indices) {
            var example = listCash[i]
            if (example == intentList) {
                isHave = true
            }
        }
        if (!isHave) {
            binding.imageSave.setImageResource(R.drawable.ic_unsave)
        } else {
            binding.imageSave.setImageResource(R.drawable.ic_baseline_bookmark_24)
        }
        return isHave
    }


    private fun shareText(word: String?, definition: String?) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, "$word - $definition")
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Oxford Dictionary"))
    }

    private fun copyText(word: String?, definition: String?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = "$word - $definition"
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
        } else {
            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", "$word - $definition")
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
        }
    }
}