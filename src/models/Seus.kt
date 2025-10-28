package models

data class Seus(
    var nomSeu: String,
    var ubicacio: String,
    var nombreMaxim: Int,
    var nifsTreballadors: MutableList<String> = mutableListOf()


)

