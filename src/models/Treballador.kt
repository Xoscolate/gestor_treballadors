package models

open class Treballador(
    var nom: String,
    var apellido: String,
    var nif: String,
    var salariBase: Double,
    var jornadaCompleta: Boolean,
    var tipus: TipusTreballador

)

