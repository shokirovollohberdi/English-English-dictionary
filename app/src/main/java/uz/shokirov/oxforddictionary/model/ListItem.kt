package uz.shokirov.oxforddictionary.model

import java.io.Serializable

data class ListItem(
    val __v: Int,
    val _id: Id,
    val definition: String,
    val word: String
):Serializable