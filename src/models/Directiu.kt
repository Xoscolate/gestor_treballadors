package models

class Directiu(
    nom: String,
    apellido: String,
    nif: String,
    salariBase: Double,
    jornadaCompleta: Boolean,
    val area: String,
    val bonus: Double
) : Treballador(nom, apellido, nif, salariBase, jornadaCompleta, TipusTreballador.DIRECTIU) {

}