package controlador

import exceptions.exceptions
import models.Seus
import models.Treballador
import vista.Vista
import controlador.NifValidator
import models.Administratiu
import models.Directiu
import models.Tecnic
import models.TipusTreballador
import java.util.Scanner


class Controller (private val treballadors: MutableList<Treballador> = mutableListOf(),   private val llistaSeus: MutableList<Seus>, private val vista: Vista, private val validator: NifValidator) {


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
            } else if (eleccion == "E") {
                crearSeu()
            }else if (eleccion == "F") {
                reformaSeu()
            }else if (eleccion == "G") {
                veureSeu()
            } else {
                vista.showMessage("Esa opcion no exsiste")

            }


        }
        vista.showMessage("ADEW...")


    }

    fun altaTreballador() {
        var salir = false
        while (!salir) {
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
                if (!nifExsiste) {
                    throw exceptions("El nif no es correcto.")

                } else if (validator.validateNif(nif)) {
                    throw exceptions("Ya exsiste un trbajador con este nif")

                }

                vista.showMessage("")
                vista.showMessage("Salario: ")
                val salarioBaseInput = vista.strings()
                val salarioBase = salarioBaseInput.toDoubleOrNull()
                if (salarioBase == null) {
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

                if (!exsisteSeu(seu)) {
                    throw exceptions("Ese seu no exsiste")
                }

                if (cantidadSeus(seu)) {
                    throw exceptions("El seu esta completa")
                }
                vista.showMessage("Escoge el tipo de tecnico: Directiu (D), Administratiu (A), Tecnic(T)")
                var tipoTecnico = vista.strings().uppercase()
                if(tipoTecnico != "D" && tipoTecnico != "A" && tipoTecnico!= "T"){
                    throw exceptions("Solo puede ser D, A o T")
                }else if(tipoTecnico == "A") {
                    vista.showMessage("")
                    vista.showMessage("Dime que tipo de departamento: Compatibilidad (C)/ RRHH (R) / Secretaria (S)")
                    var tipoDepartament = vista.strings().uppercase()
                    if (tipoDepartament != "C" && tipoDepartament != "R" && tipoDepartament != "S") {
                        throw exceptions("Solo puede ser C, R o S")
                    }else if(tipoDepartament == "C"){
                        val administrativo = Administratiu(nom, apellido, nif, salarioBase, jornada,"Compatibilidad",0)
                        for (s in llistaSeus) {
                            if (s.nomSeu == seu) {
                                s.nifsTreballadors.add(nif)

                            }
                        }
                        treballadors.add(administrativo)
                        vista.showMessage("Trabajador registrado /n")
                        salir = true

                    }else if (tipoDepartament == "R"){
                        val administrativo = Administratiu(nom, apellido, nif, salarioBase, jornada,"RRHH",0)
                        for (s in llistaSeus) {
                            if (s.nomSeu == seu) {
                                s.nifsTreballadors.add(nif)

                            }
                        }
                        treballadors.add(administrativo)
                        vista.showMessage("Trabajador registrado /n")
                        salir = true

                    }else if (tipoDepartament == "S"){
                        val administrativo = Administratiu(nom, apellido, nif, salarioBase, jornada,"Secretaria",0)
                        for (s in llistaSeus) {
                            if (s.nomSeu == seu) {
                                s.nifsTreballadors.add(nif)


                            }
                        }
                        treballadors.add(administrativo)
                        vista.showMessage("Trabajador registrado /n")
                        salir = true

                    }
                }else if (tipoTecnico == "T")
                {
                    vista.showMessage("")
                    vista.showMessage("Dime que tipo de especialidad: Desenvolupament (D)/ Manteniment (M) / Test (T)")
                    var tipoDepartament = vista.strings().uppercase()
                    if (tipoDepartament != "D" && tipoDepartament != "M" && tipoDepartament != "T") {
                        throw exceptions("Solo puede ser D, M, T")

                    }else if(tipoDepartament == "D"){
                        vista.showMessage("")
                        vista.showMessage("Puede hacer guardias: (SI / NO)")
                        var puedeHoras = vista.strings().uppercase()
                        var booleanHoras = false;

                        if(puedeHoras != "SI" && puedeHoras != "NO"){
                            throw exceptions("TIENE QUE SER SI O NO")
                        }else if(puedeHoras == "SI"){
                            booleanHoras = true
                        }

                        val tecnico = Tecnic(nom, apellido, nif, salarioBase, jornada,"Desenvolupament",booleanHoras)
                        for (s in llistaSeus) {
                            if (s.nomSeu == seu) {
                                s.nifsTreballadors.add(nif)


                            }
                        }
                        treballadors.add(tecnico)
                        vista.showMessage("Trabajador registrado /n")
                        salir = true

                    }else if (tipoDepartament == "M"){
                        vista.showMessage("")
                        vista.showMessage("Puede hacer guardias:")
                        var puedeHoras = vista.strings().uppercase()
                        var booleanHoras = false;

                        if(puedeHoras != "SI" && puedeHoras != "NO"){
                            throw exceptions("TIENE QUE SER SI O NO")
                        }else if(puedeHoras == "SI"){
                            booleanHoras = true
                        }

                        val tecnico = Tecnic(nom, apellido, nif, salarioBase, jornada,"Manteniment",booleanHoras)
                        for (s in llistaSeus) {
                            if (s.nomSeu == seu) {
                                s.nifsTreballadors.add(nif)


                            }
                        }
                        treballadors.add(tecnico)
                        vista.showMessage("Trabajador registrado /n")
                        salir = true

                    }else if (tipoDepartament == "T"){
                        vista.showMessage("")
                        vista.showMessage("Puede hacer guardias:")
                        var puedeHoras = vista.strings().uppercase()
                        var booleanHoras = false;

                        if(puedeHoras != "SI" && puedeHoras != "NO"){
                            throw exceptions("TIENE QUE SER SI O NO")
                        }else if(puedeHoras == "SI"){
                            booleanHoras = true
                        }

                        val tecnico = Tecnic(nom, apellido, nif, salarioBase, jornada,"Test",booleanHoras)
                        for (s in llistaSeus) {
                            if (s.nomSeu == seu) {
                                s.nifsTreballadors.add(nif)


                            }
                        }
                        treballadors.add(tecnico)
                        vista.showMessage("Trabajador registrado /n")
                        salir = true

                    }
                }else{
                    vista.showMessage("")
                    vista.showMessage("Dime el bonus que tiene el trabajador: ")
                    var bonus = vista.strings()
                    val bonusT = bonus.toDoubleOrNull()


                    if(bonusT == null){
                        throw exceptions("El bonus debe ser algo")

                    }else if(bonusT <= 0 || bonusT >= 10000){
                        throw exceptions ("Tiene que ser menor que 0 o mayor 100000")
                    }else{
                        val treballador = Directiu(nom, apellido, nif, salarioBase, jornada,"",bonusT)
                        for (s in llistaSeus) {
                            if (s.nomSeu == seu) {
                                s.nifsTreballadors.add(nif)


                            }
                        }
                        treballadors.add(treballador)
                        vista.showMessage("Trabajador registrado /n")
                        salir = true

                    }

                }






            } catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar la alta del t" +
                        "rabajador? ")
                val respuesta = vista.strings().lowercase()
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

    fun validarNif(nif: String): Boolean {
        var exsiste = true
        for (t in treballadors) {
            if (t.nif == nif) {
                exsiste = false
            }
        }

        return exsiste
    }

    fun mismoNif(nif: String): Boolean {
        for (t in treballadors) {
            if (t.nif == nif) {
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
                    val nif = vista.strings().lowercase()
                    var exsisteNif = mismoNif(nif)
                    if (!exsisteNif) {
                        throw exceptions("Ese nif no exsiste en nuestra base de datos")
                    }
                    val trabajadorModifcar = cogerTrabajador(nif)

                    vista.showMessage("Que desea modificar:")
                    vista.showMessage("1:Nombre / 2: Apellido / 3:Nif / 4:Salario Base / 5: Jornada / 6: Cambiar de seu / 7: Horas extras, Bonus o Guardias")
                    val eleccion = vista.strings()
                    if (eleccion != "1" && eleccion != "2" && eleccion != "3" && eleccion != "4" && eleccion != "5" && eleccion != "6" && eleccion != "7") {
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
                        val nuevoNif = vista.strings()
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


                    } else if (eleccion == "5") {
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

                    } else if (eleccion == "6") {
                        var nombreSeu2 = buscarPorSeu(nif)
                        vista.showMessage("Actualmente esta en: " + nombreSeu2)
                        vista.showMessage("Desea cambiarlo?")
                        var respuesta = vista.strings()
                        if (respuesta != "si" && respuesta != "no") {
                            throw exceptions("La respuesta solo puede ser si o no")
                        } else if (respuesta == "si") {
                            vista.showMessage("")
                            vista.showMessage("Lista de seus")
                            vista.listaDeSeus()
                            vista.showMessage("")
                            vista.showMessage("Dime cual quieres cambiar (indicar por el nombre):")
                            var nombreSeu = vista.strings()
                            if (!exsisteSeu(nombreSeu)) {
                                throw exceptions("Ese seu no exsiste actualmente")
                            }
                            if (nombreSeu.equals(nombreSeu2)){
                                throw exceptions("Es el mismo seu en el que esta actualmente")
                            }
                            if (cantidadSeus(nombreSeu)) {
                                throw exceptions("Ahora mimso el seu esta completo")
                            }

                            eliminarTreballadorSeu(nif)
                            for (s in llistaSeus) {
                                if (s.nomSeu == nombreSeu) {
                                    s.nifsTreballadors.add(nif)
                                }
                            }

                            vista.showMessage("")
                            vista.showMessage("Creado correctamente")

                        }else {
                            vista.showMessage("")
                        }
                    }else if(eleccion == "7") {
                        if (trabajadorModifcar.tipus == TipusTreballador.ADMINISTRATIU){
                            vista.showMessage("Dime cuantas horas extras quieres:")
                            var horas = vista.strings().toIntOrNull();
                            if(horas == null){
                                throw exceptions("Las horas deben ser un numero entero")
                            }
                            if (trabajadorModifcar is Administratiu) {
                                trabajadorModifcar.hores_extras = horas
                            }

                        }else if (trabajadorModifcar.tipus == TipusTreballador.TECNIC){
                            if(trabajadorModifcar is Tecnic){
                                if(! trabajadorModifcar.potFerGuardies){
                                    vista.showMessage("Quieres que pueda hacer guardias? (Si / No)")
                                    var respuesta = vista.strings().lowercase()
                                    if (respuesta != "si" && respuesta != "no") {
                                        throw exceptions("Solo puede ser si o no")
                                    }else if (respuesta == "si"){
                                        trabajadorModifcar.potFerGuardies = true
                                        vista.showMessage("Se ha modificado perfectamente")
                                    }else{
                                        vista.showMessage("No se ha podido modificar")
                                    }

                                }else{
                                    vista.showMessage("Quieres que no pueda hacer guardias? (Si / No)")
                                    var respuesta = vista.strings().lowercase()
                                    if (respuesta != "si" && respuesta != "no") {
                                        throw exceptions("Solo puede ser si o no")
                                    }else if (respuesta == "si"){
                                        trabajadorModifcar.potFerGuardies = false
                                        vista.showMessage("Se ha modificado perfectamente")
                                    }else{
                                        vista.showMessage("No se ha modificado")
                                    }

                                }
                            }
                        }else{
                            if(trabajadorModifcar is Directiu){
                                vista.showMessage("Ahora mismo los bonus son: "+trabajadorModifcar.bonus+ " euros")
                                vista.showMessage("Desea modificarlo? (Si / No)")
                                var respuesta = vista.strings().lowercase()
                                if (respuesta != "si" && respuesta != "no") {
                                    throw exceptions("Solo puede ser si o no")
                                }else if (respuesta == "si"){

                                    vista.showMessage("Cual quiere que sea la cantidad (Maximo 10.000): ")
                                    var dinero = vista.strings().toDoubleOrNull()
                                    if(dinero == null){
                                        throw exceptions ("Solo puede ser un numero")
                                    }
                                    trabajadorModifcar.bonus = dinero
                                    vista.showMessage("Se ha modificado correctamente")

                                }else{
                                    vista.showMessage("No se ha modificado")
                                }
                            }

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
                val respuesta = vista.strings().lowercase()
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

    fun exsistenTrabajadores(): Boolean {
        var exsisteTrabajador = true
        if (treballadors.isEmpty()) {
            exsisteTrabajador = false
        }
        return exsisteTrabajador
    }


    fun cantidadSeus(seu: String): Boolean {
        var cantidad = 0
        var completo = false
        for (s in llistaSeus) {
            if (s.nomSeu == seu) {
                cantidad = s.nombreMaxim
                val ocupado = s.nifsTreballadors.size
                if (ocupado >= cantidad) {
                    completo = true
                }

            }
        }
        return completo
    }

    fun eliminarTreballadorSeu(nif: String) {
        for (s in llistaSeus) {
            val copia = s.nifsTreballadors.toList()
            for (n in copia) {
                if (n == nif) {
                    s.nifsTreballadors.remove(n)
                }
            }
        }
    }


    fun buscarPorSeu(nif: String): String {
        var nombreSeu = ""
        for (s in llistaSeus) {
            for (n in s.nifsTreballadors) {
                if (n == nif) {
                    nombreSeu = s.nomSeu

                }
            }

        }
        return nombreSeu
    }


    fun despatxarTreballador() {
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


            } catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar la modificacion del trabajador? ")
                val respuesta = vista.strings().lowercase()
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

    fun eliminacionNif(nif: String) {
        val removed = treballadors.removeIf { it.nif == nif }
        if (!removed) {
            throw exceptions("No se encontró el NIF para eliminar.")
        }
    }

    fun pagarTreballador() {
        vista.showMessage("-- Pagar Trabajador --")
        var salir = false
        while (!salir) {
            try {
                if (!exsistenTrabajadores()) {
                    vista.showMessage("No hay trabajadores ahora mismo")
                    salir = true

                } else {
                    vista.listaTrabajadores()
                    vista.showMessage("Escoge cual a quien quieres pagar (pon su nif) : ")
                    val nif = vista.strings().lowercase()
                    val exsisteNif = mismoNif(nif)
                    if (!exsisteNif) {
                        throw exceptions("Ese nif no exsiste en nuestra base de datos")
                    }

                    val trabajadorPagar = cogerTrabajador(nif)
                    var salario = trabajadorPagar.salariBase

                    if (!trabajadorPagar.jornadaCompleta) {
                        salario /= 2
                    }

                    if (trabajadorPagar is Administratiu) {
                        salario += trabajadorPagar.hores_extras * 35
                        trabajadorPagar.hores_extras= 0
                    } else if (trabajadorPagar is Tecnic) {
                        if (trabajadorPagar.potFerGuardies) {
                            salario += 600
                        }
                    } else if (trabajadorPagar is Directiu) {
                        salario += trabajadorPagar.bonus / 12
                    }

                    vista.showMessage("El pago a " + trabajadorPagar.nom + " " + trabajadorPagar.apellido +" Nif: " + trabajadorPagar.nif + " es de " + salario + " euros")
                    salir = true
                }
            } catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar el pago del trabajador? ")
                val respuesta = vista.strings().lowercase()
                salir = respuesta != "si"
                if (salir) println("saliendo...")
            } catch (e: Exception) {
                println("Error inesperado: ${e.message}")
                salir = true
            }
        }
    }

    fun reformaSeu() {
        vista.showMessage("-- REFORMAS DE LA SEU --")
        var salir = false
        while (!salir) {
            try {
                if (!ahiSeu()) {
                    throw exceptions("No exsisten seus actualmente")
                }
                vista.listaDeSeus()
                vista.showMessage("Escoge la seu que quieres modificar: ")
                var seu = vista.strings().lowercase()
                var seuSeleccionada = cogerSeu(seu)
                if (!exsisteSeu(seu)) {
                    throw exceptions("No exsiste ese seu")
                }

                vista.showMessage("Dime que quieres modificar: 1:Nombre / 2:Numero de empleados")
                var opcion = vista.strings().toIntOrNull()
                if (opcion != 1 && opcion != 2){
                    throw exceptions("Solo puede ser 1 o 2")
                }else if (opcion == 1){
                    vista.showMessage("Que nombre quieres que tenga")
                    var nombre = vista.strings().lowercase()
                    if (nombre == seuSeleccionada.nomSeu){
                        throw exceptions("No puede ser el mismo nombre")

                    }else if (exsisteSeu(nombre)){
                        throw exceptions("No puedes cambiar a un nombre ya exsistente")
                    }
                    seuSeleccionada.nomSeu = nombre
                    vista.showMessage("Ya se ha modificado correctamente")
                    salir = true

                }else if (opcion == 2){
                    vista.showMessage("Cual quieres que sea el numero de empleados: ")
                    var numeroEmpleados = vista.strings().toIntOrNull()
                    if (numeroEmpleados == null){
                        throw exceptions("No puede no ser un numero")
                    }
                    var tamaño =  seuSeleccionada.nifsTreballadors.size
                    if (tamaño > numeroEmpleados){
                        throw exceptions("No puede tener un tamaño menor a los empleados que ya hay")
                    }
                    seuSeleccionada.nombreMaxim = numeroEmpleados
                    vista.showMessage("Modificado correctamente")
                    salir = true
                }


            }catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar la modificacion del seu? ")
                val respuesta = vista.strings().lowercase()
                if (respuesta == "si") {
                    salir = false
                } else {
                    println("saliendo...")
                    salir = true
                }
            }  catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar el pago del trabajador? ")
                val respuesta = vista.strings().lowercase()
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


    fun exsisteSeu(nom: String): Boolean {
        for (i in llistaSeus) {
            if (i.nomSeu.equals(nom, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    fun crearSeu() {
        var salir = false
        while (!salir) {
            try {
                vista.showMessage(" -- Alta de seu --")
                vista.showMessage("Nombre (no se pueden repetir): ")

                val nom = vista.strings().lowercase()
                if (exsisteSeu(nom)) {
                    throw exceptions("Este seu ya exsiste")
                } else {
                    vista.showMessage("")
                    vista.showMessage("Ubicacio: ")
                    val ubicacion = vista.strings().lowercase()
                    if (ubicacion.isEmpty()) {
                        throw exceptions("No puedes no tener ubicacion")
                    }
                    vista.showMessage("")
                    vista.showMessage("Cantidad (número decimal): ")
                    val cantidadInput = vista.strings()
                    val cantidad = cantidadInput.toIntOrNull()
                    if (cantidad == null) {
                        throw exceptions("No puede no tener cantidad")
                    }

                    val seus = Seus(nom, ubicacion, cantidad)
                    llistaSeus.add(seus)
                    vista.showMessage("Seu registrado")
                    vista.showMessage("")
                    vista.showMessage("")
                    salir = true

                }
            } catch (e: exceptions) {
                println("Error de datos: ${e.message}")
                println("Desea reiniciar la alta del seu? ")
                val respuesta = vista.strings().lowercase()
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

    fun ahiSeu(): Boolean {
        var exsisteSeu = true
        if (llistaSeus.isEmpty()) {
            exsisteSeu = false
        }
        return exsisteSeu
    }

    fun cogerSeu(nombre: String): Seus {
        for (seu in llistaSeus) {
            if (seu.nomSeu.equals(nombre, ignoreCase = true)) {
                return seu
            }
        }
        throw Exception("No se ha encontrado ninguna seu con ese nombre")
    }

    fun veureSeu() {
        var salir = false
        while (!salir) {
            try {
                if (!ahiSeu()) {
                    vista.showMessage("No hay seus ahora mismo.")
                    salir = true

                } else {
                    vista.listaDeSeus()
                    vista.showMessage("Escoge el seu:")
                    var seleccionSeu = vista.strings().lowercase()
                    if(!exsisteSeu(seleccionSeu)){
                        throw exceptions("Este seu no exsiste")
                    }

                    var seuNuevo: Seus? = null

                    for (s in llistaSeus) {
                        if (s.nomSeu == seleccionSeu) {
                            seuNuevo = s
                        }
                    }
                    if (seuNuevo != null) {
                        vista.vistaSeu(seuNuevo,treballadors)

                    } else {
                        throw exceptions("No exsiste este seu")
                    }

                    vista.showMessage("Desea mirar otro seu")
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
                println("Desea reiniciar la observacion de seus? ")
                val respuesta = vista.strings().lowercase()
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




