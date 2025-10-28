package controlador
import java.util.Scanner
import models.Treballador
import exceptions.exceptions
import models.Seus

val scanner = Scanner(System.`in`)
private val treballadors = mutableListOf<Treballador>() //la quiero mutable para añadir cambios
private val seus = mutableListOf<Seus>()

fun altaTreballador() {
    var salir = false
    while(!salir){
    try {
        println(" -- Alta del treballador --")
        print("Nombre: ")

        val nom = scanner.nextLine()
        if (nom.isEmpty()) {
            throw exceptions("Nombre no puede estar en blanco.")
        }
        println("")
        print("Apellido: ")
        val apellido = scanner.nextLine()
        if (apellido.isEmpty()) {
            throw exceptions("Apellido no puede estar en blanco")
        }
        println("")
        print("Nif: ")
        val nif = scanner.nextLine()
        var nifExsiste = validarNif(nif)

        if (nif.length != 9) {
            throw exceptions("El nif no es correcto.")

        } else if(!nifExsiste){
            throw exceptions("Ya exsiste un trbajador con este nif")

        }

        println("")
        print("Salario base:")
        val salarioBaseInput = scanner.nextLine()
        val salarioBase = salarioBaseInput.toDoubleOrNull()
        if(salarioBase == null){
            throw exceptions("salario invalido")
        }

        if (salarioBase < 1200.0 || salarioBase > 3000.0) {
            throw exceptions("El salario debe estar entre 1200 y 3000.")
        }




        // hacer la excepcion

        println("")
        print("Jornada Completa C / Jornada Parcial P? (C/P) ")
        var jornadaCompleta = scanner.nextLine().uppercase()
        if (jornadaCompleta != "C" && jornadaCompleta != "P") {
            throw exceptions("La jornada completa solo es C o P.")


       }


            val jornada: Boolean = jornadaCompleta == "C"  // Completa es true, y P es false


            val treballador = Treballador(nom, apellido, nif, salarioBase, jornada)
           treballadors.add(treballador)
            println("Trabajador registrado correctamente")
            println("")
            println("")
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
                println("No hay trabajadores ahora mismo")
                salir = true

            } else {
                println("Lista de trabajadores")
                for (t in treballadors) {
                    print(t.nom + " ")
                    print(t.apellido + " ")
                    print(t.nif + " ")
                    println("")

                }
                println("Escoge cual quieres modificar (pon su nif) : ")
                val nif = scanner.nextLine()
                var exsisteNif = mismoNif(nif)
                if (!exsisteNif) {
                    throw exceptions("Ese nif no exsiste en nuestra base de datos")
                }
                val trabajadorModifcar = cogerTrabajador(nif)

                println("Que desea modificar:")
                print("1:Nombre / 2: Apellido / 3:Nif / 4:Salario Base / 5: Jornada : ")
                val eleccion = scanner.nextLine()
                if (eleccion != "1" && eleccion != "2" && eleccion != "3" && eleccion != "4" && eleccion != "5") {
                    throw exceptions("No exsiste esa opcion")
                } else if (eleccion == "1") { //Actualizacion del nombre
                    print("Nuevo nombre (no dejar vacio): ")
                    val nuevoNombre = scanner.nextLine()
                    if (nuevoNombre.isEmpty()) {
                        throw exceptions("No puede estar vacio")
                    } else {
                        trabajadorModifcar.nom = nuevoNombre
                        println("Se ha modificado correctamente")

                    }


                } else if (eleccion == "2") { //Actualizacion del Apellido
                    print("Nuevo apellido (no dejar vacio): ")
                    val nuevoApellido = scanner.nextLine()
                    if (nuevoApellido.isEmpty()) {
                        throw exceptions("No puede estar vacio")
                    } else {
                        trabajadorModifcar.apellido = nuevoApellido
                        println("Se ha modificado correctamente")
                    }
                } else if (eleccion == "3") { // modificacion del nif
                    print("Nuevo Nif : ")
                    val nuevoNif = scanner.nextLine()
                    if (!validarNif(nuevoNif)) {
                        throw exceptions("ese nif no es correcto")
                    } else {
                        trabajadorModifcar.nif = nuevoNif
                        println("Se ha modificado correctamente")

                    }

                } else if (eleccion == "4") {
                    print("Nuevo salario (entre 1200 y 3000): ")
                    val nuevoSalario = scanner.nextLine()
                    val salarioBase = nuevoSalario.toDoubleOrNull()
                    if (salarioBase == null) {
                        throw exceptions("No puede no tener ningun tipo de salario")
                    } else if (salarioBase < 1200 || salarioBase > 3000) {
                        throw exceptions("El salario debe ser mayor que 1200 y menor que 3000")

                    } else {
                        trabajadorModifcar.salariBase = salarioBase
                        println("Se ha modificado correctamente")
                    }


                } else {
                    var tipoJornada = trabajadorModifcar.jornadaCompleta
                    var jornadaReal = ""
                    if (!tipoJornada) {
                        jornadaReal = "Partida"
                    } else {
                        jornadaReal = "Completa"
                    }
                    print("Desea cambiar de jornada " + jornadaReal + " ? (Si/No)")
                    var respuesta = scanner.nextLine().lowercase()
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
                println("Desea seguir modificando a los trabajadores")
                var respuesta = scanner.nextLine().lowercase()
                if (respuesta != "si" && respuesta != "no") {

                    println("Error de respuesta, saliendo...")
                    salir = true
                } else if (respuesta == "si") {
                    println("Perfecto.")
                } else {
                    println("Saliendo...")
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

fun despatxarTreballador (){
    println("-- Eliminar Trabajador --")
    var salir = false
    while (!salir) {
        try {
            if (!exsistenTrabajadores()) {
                println("No hay trabajadores ahora mismo")
                salir = true

            } else {
                println("Lista de trabajadores")
                for (t in treballadors) {
                    print(t.nom + " ")
                    print(t.apellido + " ")
                    print(t.nif + " ")
                    println("")

                }
                println("Escoge cual quieres eliminar (pon su nif) : ")
                val nif = scanner.nextLine()
                var exsisteNif = mismoNif(nif)
                if (!exsisteNif) {
                    throw exceptions("Ese nif no exsiste en nuestra base de datos")
                }
                val trabajadorModifcar = cogerTrabajador(nif)
                eliminacionNif(trabajadorModifcar.nif)
                println(" -- Se elimino correctamente --")
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

fun pagarTreballador () {
    println("-- Pagar Trabajador --")
    var salir = false
    while (!salir) {
        try {
            if (!exsistenTrabajadores()) {
                println("No hay trabajadores ahora mismo")
                salir = true

            } else {
                println("Lista de trabajadores")
                for (t in treballadors) {
                    print(t.nom + " ")
                    print(t.apellido + " ")
                    print(t.nif + " ")
                    println("")

                }
                println("Escoge cual a quien quieres pagar (pon su nif) : ")
                val nif = scanner.nextLine()
                var exsisteNif = mismoNif(nif)
                if (!exsisteNif) {
                    throw exceptions("Ese nif no exsiste en nuestra base de datos")
                }
                val trabajadorPagar = cogerTrabajador(nif)
                var tipoJornada = trabajadorPagar.jornadaCompleta
                var salario = trabajadorPagar.salariBase
                if (tipoJornada == true) {
                    println("El pago a " + trabajadorPagar.nom + " " + trabajadorPagar.apellido + " Nif: " + trabajadorPagar.nif + "es de:")
                    print(salario)
                    print(" euros")
                    salir = true

                } else {
                    salario = salario / 2
                    println("El pago a " + trabajadorPagar.nom + " " + trabajadorPagar.apellido + " Nif: " + trabajadorPagar.nif + "es de:")
                    print(salario)
                    println(" euros")
                    println("")
                    salir = true

                }

            }
        } catch (e: exceptions) {
            println("Error de datos: ${e.message}")
            println("Desea reiniciar el pago del trabajador? ")
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

    fun exsisteSeu(nom: String): Boolean {
        var exsiste = false
        for (i in seus) {
            if (i.nomSeu == nom) {
                exsiste = true
            }
        }
        return exsiste
    }

    fun crearSeu() {
        var salir = false
        while (!salir) {
            try {
                println(" -- Alta de seu --")
                print("Nombre: ")

                val nom = scanner.nextLine().lowercase()
                if (exsisteSeu(nom)) {
                    throw exceptions("Este seu ya exsiste")
                } else {
                    println("")
                    print("Ubicacio: ")
                    val ubicacion = scanner.nextLine().lowercase()
                    if (ubicacion.isEmpty()) {
                        throw exceptions("No puedes no tener ubicacion")
                    }
                    println("")
                    print("Cantidad (número decimal): ")
                    val cantidadInput = scanner.nextLine()
                    val cantidad = cantidadInput.toIntOrNull()
                    if (cantidad == null) {
                        throw exceptions("No puede no tener cantidad")
                    }

                    val seus = Seus(nom, ubicacion, cantidad)
                    controlador.seus.add(seus)
                    println("Seu registrado")
                    println("")
                    println("")
                    salir = true

                }
            } catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar la alta del trabajador? ")
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
}
