package controlador

import exceptions.exceptions
import models.Seus
import models.Treballador
import vista.Vista


class Controller (private val treballadors: MutableList<Treballador> = mutableListOf(),   private val llistaSeus: MutableList<Seus>, private val vista: Vista) {


    fun empezar() {
        val scanner = java.util.Scanner(System.`in`)
        var salir = false;
        while (!salir) {
            vista.Menu()
            val eleccion = scanner.nextLine().uppercase()
            if (eleccion == "A") {
                altaTreballador()

            } else if (eleccion == "S") {
                salir = true
            } else if (eleccion == "B") {
                modificarTrabajador()


            } else if (eleccion == "C") {
                despatxarTreballador()

            } else if (eleccion == "D") {
                pagarTreballador()
            } else {
                vista.showMessage("Esa opcion no exsiste")

            }


        }
        vista.showMessage("ADEW...")


    }

    fun altaTreballador() {
        var salir = false
        while(!salir){
            try {
                vista.showMessage("-- Alta de trabajador --")
                vista.showMessage("")
                vista.showMessage("Nom: ")
                var nom = vista.strings()
                if (nom.isEmpty()) {
                    throw exceptions("Nombre no puede estar en blanco.")
                }
                vista.showMessage("")
                vista.showMessage("Apellido: ")
                val apellido = vista.strings()
                if (apellido.isEmpty()) {
                    throw exceptions("Apellido no puede estar en blanco")
                }
                vista.showMessage("")
                vista.showMessage("Nif: ")
                val nif = vista.strings().lowercase()
                var nifExsiste = validarNif(nif)
                if (nif.length != 9) {
                    throw exceptions("El nif no es correcto.")

                } else if(!validarNif(nif)){
                    throw exceptions("Ya exsiste un trbajador con este nif")

                }

                vista.showMessage("")
                vista.showMessage("Salario: ")
                val salarioBaseInput = vista.strings()
                val salarioBase = salarioBaseInput.toDoubleOrNull()
                if(salarioBase == null){
                    throw exceptions("salario invalido")
                }

                if (salarioBase < 1200.0 || salarioBase > 3000.0) {
                    throw exceptions("El salario debe estar entre 1200 y 3000.")
                }

                vista.showMessage("")
                vista.showMessage("Jornada Completa o Parcial (C o P): ")
                var jornadaCompleta = vista.strings().uppercase()
                if (jornadaCompleta != "C" && jornadaCompleta != "P") {
                    throw exceptions("La jornada completa solo es C o P.")


                }


                val jornada: Boolean = jornadaCompleta == "C"  // Completa es true, y P es false

                vista.showMessage("")
                vista.listaDeSeus()
                val seu = vista.strings().lowercase()

                if(!exsisteSeu(seu)){
                    throw exceptions("Ese seu no exsiste")
                }

                if(cantidadSeus(seu)){
                    throw exceptions("El seu esta completa")
                }




                val treballador = Treballador(nom, apellido, nif, salarioBase, jornada)
                for(s in llistaSeus){
                    if (s.nomSeu== seu){
                        s.nifsTreballadors.add(nif)
                    }
                }
                treballadors.add(treballador)
                vista.showMessage("Trabajador registrado /n")
                salir = true








            } catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar la alta del trabajador? ")
                val respuesta = scanner.nextLine().lowercase()
                if (respuesta == "si"){
                    salir = false
                }else{
                    println("saliendo...")
                    salir = true
                }
            } catch (e: Exception) {
                println("Error inesperado: ${e.message}")
                salir = true;
            }
        }



    }

    fun validarNif (nif : String) : Boolean{
        var exsiste = true
        for (t in treballadors){
            if (t.nif == nif){
                exsiste = false
            }
        }

        return exsiste
    }

    fun mismoNif (nif : String): Boolean {
        for (t in treballadors){
            if (t.nif == nif){
                return true
            }
        }
        return false
    }
    fun cogerTrabajador(nif: String): Treballador {
        for (t in treballadors) {
            if (t.nif == nif) {
                return t
            }
        }
        throw exceptions("No se encontró ningún trabajador con ese NIF.")
    }






    fun modificarTrabajador() {
        println("-- Modificar Trabajador --")
        var salir = false
        while (!salir) {
            try {
                if (!exsistenTrabajadores()) {
                    vista.showMessage("No hay trabajadores ahora mismo")
                    salir = true

                } else {

                    vista.listaTrabajadores()

                    vista.showMessage("Escoge cual quieres modificar (pon su nif) : ")
                    val nif = vista.strings()
                    var exsisteNif = mismoNif(nif)
                    if (!exsisteNif) {
                        throw exceptions("Ese nif no exsiste en nuestra base de datos")
                    }
                    val trabajadorModifcar = cogerTrabajador(nif)

                    vista.showMessage("Que desea modificar:")
                    vista.showMessage("1:Nombre / 2: Apellido / 3:Nif / 4:Salario Base / 5: Jornada : ")
                    val eleccion = vista.strings()
                    if (eleccion != "1" && eleccion != "2" && eleccion != "3" && eleccion != "4" && eleccion != "5") {
                        throw exceptions("No exsiste esa opcion")
                    } else if (eleccion == "1") { //Actualizacion del nombre
                        vista.showMessage("Nuevo nombre (no dejar vacio): ")
                        val nuevoNombre = vista.strings()
                        if (nuevoNombre.isEmpty()) {
                            throw exceptions("No puede estar vacio")
                        } else {
                            trabajadorModifcar.nom = nuevoNombre
                            vista.showMessage("Se ha modificado correctamente")

                        }


                    } else if (eleccion == "2") { //Actualizacion del Apellido
                        vista.showMessage("Nuevo apellido (no dejar vacio): ")
                        val nuevoApellido = vista.strings()
                        if (nuevoApellido.isEmpty()) {
                            throw exceptions("No puede estar vacio")
                        } else {
                            trabajadorModifcar.apellido = nuevoApellido
                            vista.showMessage("Se ha modificado correctamente")
                        }
                    } else if (eleccion == "3") { // modificacion del nif
                        vista.showMessage("Nuevo Nif : ")
                        val nuevoNif = scanner.nextLine()
                        if (!validarNif(nuevoNif)) {
                            throw exceptions("ese nif no es correcto")
                        } else {
                            trabajadorModifcar.nif = nuevoNif
                            for (s in llistaSeus) {
                                val index = s.nifsTreballadors.indexOf(trabajadorModifcar.nif)
                                if (index != -1) {
                                    s.nifsTreballadors[index] = nuevoNif
                                }
                            }

                            vista.showMessage("Se ha modificado correctamente")

                        }

                    } else if (eleccion == "4") {
                        vista.showMessage("Nuevo salario (entre 1200 y 3000): ")
                        val nuevoSalario = vista.strings()
                        val salarioBase = nuevoSalario.toDoubleOrNull()
                        if (salarioBase == null) {
                            throw exceptions("No puede no tener ningun tipo de salario")
                        } else if (salarioBase < 1200 || salarioBase > 3000) {
                            throw exceptions("El salario debe ser mayor que 1200 y menor que 3000")

                        } else {
                            trabajadorModifcar.salariBase = salarioBase
                            vista.showMessage("Se ha modificado correctamente")
                        }


                    } else {
                        var tipoJornada = trabajadorModifcar.jornadaCompleta
                        var jornadaReal = ""
                        if (!tipoJornada) {
                            jornadaReal = "Partida"
                        } else {
                            jornadaReal = "Completa"
                        }
                        vista.showMessage("Desea cambiar de jornada " + jornadaReal + " ? (Si/No)")
                        var respuesta = vista.strings().lowercase()
                        if (respuesta != "si" && respuesta != "no") {
                            throw exceptions("La respuesta solo puede ser si o no")
                        } else if (respuesta == "si") {
                            if (tipoJornada == true) {
                                tipoJornada = false
                            } else {
                                tipoJornada = true
                            }
                            trabajadorModifcar.jornadaCompleta = tipoJornada
                        }

                    }
                    vista.showMessage("Desea seguir modificando a los trabajadores")
                    var respuesta = vista.strings().lowercase()
                    if (respuesta != "si" && respuesta != "no") {

                        vista.showMessage("Error de respuesta, saliendo...")
                        salir = true
                    } else if (respuesta == "si") {
                        vista.showMessage("Perfecto.")
                    } else {
                        vista.showMessage("Saliendo...")
                        salir = true
                    }
                }
            } catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar la modificacion del trabajador? ")
                val respuesta = scanner.nextLine().lowercase()
                if (respuesta == "si") {
                    salir = false
                } else {
                    println("saliendo...")
                    salir = true
                }
            } catch (e: Exception) {
                println("Error inesperado: ${e.message}")
                salir = true;
            }
        }


    }

    fun exsistenTrabajadores () : Boolean{
        var exsisteTrabajador = true
        if(treballadors.isEmpty()){
            exsisteTrabajador = false
        }
        return exsisteTrabajador
    }

    fun exsisteSeu (nombre : String) : Boolean{
        var exsiste = false
        for (s in llistaSeus){
            if (s.nomSeu == nombre){
                exsiste = true
            }
        }
        return exsiste

    }

    fun cantidadSeus(seu : String) : Boolean{
        var cantidad = 0
        var completo = false
        for (s in llistaSeus){
            if (s.nomSeu == seu){
                cantidad = s.nombreMaxim
                val ocupado = s.nifsTreballadors.size
                if (ocupado >= cantidad){
                    completo = true
                }

            }
        }
return completo
    }

    fun eliminarTreballadorSeu(nif : String){
        for(s in llistaSeus){
            for(n in s.nifsTreballadors){
                if(n==nif){
                    s.nifsTreballadors.remove(n)

                }
            }

        }
    }



    fun despatxarTreballador (){
        vista.showMessage("-- Eliminar Trabajador --")
        var salir = false
        while (!salir) {
            try {
                if (!exsistenTrabajadores()) {
                    vista.showMessage("No hay trabajadores ahora mismo")
                    salir = true

                } else {
                    vista.listaTrabajadores()
                    vista.showMessage("Escoge cual quieres eliminar (pon su nif) : ")
                    var nif = vista.strings().lowercase()
                    var exsisteNif = mismoNif(nif)
                    if (!exsisteNif) {
                        throw exceptions("Ese nif no exsiste en nuestra base de datos")
                    }
                    val trabajadorModifcar = cogerTrabajador(nif)
                    eliminacionNif(trabajadorModifcar.nif)
                    eliminarTreballadorSeu(trabajadorModifcar.nif)
                    vista.showMessage(" Se elimino correctamente ")
                    salir = true




                }


            }catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar la modificacion del trabajador? ")
                val respuesta = scanner.nextLine().lowercase()
                if (respuesta == "si") {
                    salir = false
                } else {
                    println("saliendo...")
                    salir = true
                }
            } catch (e: Exception) {
                println("Error inesperado: ${e.message}")
                salir = true;
            }
        }
    }
    fun eliminacionNif(nif : String) {
        for (i in treballadors.indices) {
            if (treballadors[i].nif == nif) {
                treballadors.removeAt(i)

            }else{
                throw exceptions("No se elimino Nif.")
            }
        }
    }


}