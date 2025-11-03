package models

class Tecnic(
    nom: String,
    apellido: String,
    nif: String,
    salariBase: Double,
    jornadaCompleta: Boolean,
    var especialitat: String,
    var potFerGuardies: Boolean
) : Treballador(nom, apellido, nif, salariBase, jornadaCompleta, TipusTreballador.TECNIC)
