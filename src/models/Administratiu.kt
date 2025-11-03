package models

class Administratiu(
    nom: String,
    apellido: String,
    nif: String,
    salariBase: Double,
    jornadaCompleta: Boolean,
    var departament: String,
    var hores_extras: Int
) : Treballador(nom, apellido, nif, salariBase, jornadaCompleta, TipusTreballador.ADMINISTRATIU) {
    var horesExtres: Int = 0
}


