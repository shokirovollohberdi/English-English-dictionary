package uz.shokirov.oxforddictionary.utls

import uz.shokirov.oxforddictionary.model.ListItem

object GlobalList {
    var listItem = ArrayList<ListItem>()
    fun getList():ArrayList<ListItem>{

        listItem.sortedBy {
            it.word
        }

        return listItem
    }
}