package models

enum class TipusTreballador(val codi: Char) {
    ADMINISTRATIU('A'),
    TECNIC('T'),
    DIRECTIU('D');

    companion object {
        fun from(codi: Char): TipusTreballador? = values().find { it.codi == codi }
    }
}