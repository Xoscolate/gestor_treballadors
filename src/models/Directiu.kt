package models

class Directiu(
    nom: String,
    apellido: String,
    nif: String,
    salariBase: Double,
    jornadaCompleta: Boolean,
    var area: String,
    var bonus: Double
) : Treballador(nom, apellido, nif, salariBase, jornadaCompleta, TipusTreballador.DIRECTIU) {

}