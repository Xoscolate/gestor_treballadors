
import models.Treballador
import controlador.altaTreballador
import controlador.modificarTrabajador
import controlador.despatxarTreballador
import controlador.pagarTreballador
import controlador.Controller
import controlador.NifValidator
import models.Seus
import vista.Vista
import java.util.Scanner


fun main() {

    val scanner = Scanner(System.`in`)
    val seus = mutableListOf(Seus("cocacola","Barcelona",1,mutableListOf()))
    val treballadors = mutableListOf<Treballador>()
    val vista = Vista(scanner, seus, treballadors )
    val validator = NifValidator()
    val controlador = Controller(treballadors, seus, vista, validator )

    controlador.empezar()

}