package vista

import controlador.scanner
import models.Seus
import models.Treballador
import java.util.Scanner


class Vista (private val scanner: Scanner, private val listaSeus: MutableList<Seus>, private val listaTrabajadors: MutableList<Treballador>){
    fun Menu(){
        println("-- Gestor de trbajadores: Tech Nova Solutions --")
        println("A: Alta del trabajador")
        println("B: Modificar trabajador")
        println("C: Eliminar trabajador")
        println("D: Pagar a un trabajador")
        print("Escoge una de las opciones:")

    }
    fun showMessage(message: String) {
        println(message)
    }
    fun strings () : String {

        val nom = scanner.nextLine()
        return nom

    }

    fun listaDeSeus (){
        println("Lista de Seus")
        for (s in listaSeus) {
            print(s.nomSeu + " ")
            print(s.ubicacio + " ")
            print(s.nombreMaxim)
            println("")

        }
    }

    fun listaTrabajadores() {
        println("Lista de trabajadores")
        for (t in listaTrabajadors) {
            print(t.nom + " ")
            print(t.apellido + " ")
            print(t.nif + " ")
            println("")
        }
    }





}