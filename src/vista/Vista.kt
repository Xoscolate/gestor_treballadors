package vista

import models.Administratiu
import models.Directiu
import models.Seus
import models.Tecnic
import models.TipusTreballador
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
        println("F: Reformas de un seu")
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
                    if(t.tipus == TipusTreballador.ADMINISTRATIU) {
                        if (t is Administratiu) {
                            print(jornada + " - Especialidad: " +t.tipus + " - Departament: "+ t.departament + "- Horas extras: "+t.hores_extras)
                            println("")
                            print("--- Total : " + seu.nifsTreballadors.size + " trebajadores ---")
                            println("")
                        }

                    }else if(t.tipus == TipusTreballador.TECNIC) {
                        if (t is Tecnic) {
                            print(jornada + " - Especialidad: " + t.tipus + " - Especialitat: " + t.especialitat)
                            if (!t.potFerGuardies) {


                                print("Guardias: No ")
                            } else {
                                print("Guardias: Si ")

                            }

                        }
                    }else {
                        if (t is Directiu) {
                            print(jornada + " - Especialidad: " + t.tipus + " - Area: " + t.area + "- Bonus: " + t.bonus)

                        }
                    }

                    println("")
                    print ("--- Total : "+seu.nifsTreballadors.size + " trebajadores ---")
                    println("")

                }
            }
        }

    }




}