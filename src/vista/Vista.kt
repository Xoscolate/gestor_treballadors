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
        println("E: Alta de un seu")
        println("G: Mirar seu")

        println("S: Salir del programa")


        println("Escoge una de las opciones:")

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


    fun vistaSeu (seu: Seus, treballador: MutableList<Treballador>){
        var jornada = ""
        println("Nombre de la seu: "+ seu.nomSeu + "         Ciudad: "+ seu.ubicacio + "          Capacidad: "+ seu.nombreMaxim)
        for (s in seu.nifsTreballadors){
            for (t in treballador){
                if(t.nif == s){
                    print("NIF: " + t.nif + " - Nombre:" + t.nom + " - Salario base: " + t.salariBase + " - Jornada:")
                    if (t.jornadaCompleta){
                        jornada = "Completa"

                    }else{
                        jornada = "Parcial"
                    }
                    print(jornada + " - PENDEIENTE - PENDIENTE ")
                    println("")
                    print ("--- Total : "+seu.nifsTreballadors.size + " trebajadores ---")
                    println("")

                }
            }
        }

    }




}