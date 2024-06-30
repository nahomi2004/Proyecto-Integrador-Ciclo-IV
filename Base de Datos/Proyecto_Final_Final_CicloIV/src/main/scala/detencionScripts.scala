import java.io.{BufferedWriter, File, FileWriter}
import doobie.*
import doobie.implicits.*
import cats.*
import cats.effect.*
import cats.implicits.*
import com.github.tototoshi.csv.{CSVReader, DefaultCSVFormat}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object detencionScripts {
  @main
  def ejecutable(): Unit = {
    val ruta2016 = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/COPIA - BD_2016.csv"
    val ruta2017 = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/COPIA - BD_2017.csv"
    val ruta2018 = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/COPIA - BD_2018.csv"
    val ruta2019 = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/COPIA - BD_2019.csv"
    val ruta2020 = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/COPIA - BD_2020.csv"
    val ruta2021 = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/COPIA - BD_2021.csv"
    val ruta2022 = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/COPIA - BD_2022.csv"
    val ruta2023 = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/COPIA - BD_2023.csv"
    val ruta2024 = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/COPIA - BD_2024.csv"

    def leerRutas(ruta: String): List[Map[String, String]] = {
      val reader = CSVReader.open(new File(ruta))
      val contentFile: List[Map[String, String]] =
        reader.allWithHeaders()

      contentFile
    }

    def escribirDatosTXT(nombreTXT: String, archivo: String): Unit = {
      val rutaTXT = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Base de Datos/Script/"
      val rutaFinal = rutaTXT + nombreTXT

      val escritor = new BufferedWriter(new FileWriter(rutaFinal, true))
      try {
        escritor.write(archivo)
        escritor.newLine()
      } finally {
        escritor.close()
      }
    }

    def valoresDoBuedos(s: String) =
      if (s.isEmpty) 0 else s.toInt

    def definirFecha(dateStr: String): String = {
      val inputFormat = DateTimeFormatter.ofPattern("d-M-yy")
      val outputFormat = DateTimeFormatter.ofPattern("d/M/yyyy")

      // Comprobar si ya está en el formato de salida
      try {
        // Parsear y formatear para validar el formato de salida
        LocalDate.parse(dateStr, outputFormat).format(outputFormat)
        // Si no lanza una excepción, devolver la fecha sin cambios
        return dateStr
      } catch {
        case _: Exception => // Ignorar la excepción y continuar con el formato de entrada
      }

      // Parsear la fecha en el formato original y formatearla al nuevo formato
      try {
        val date = LocalDate.parse(dateStr, inputFormat)
        val formattedDate = date.format(outputFormat)
        // Devolver la fecha formateada
        formattedDate
      } catch {
        case e: Exception =>
          throw new IllegalArgumentException(s"Error al parsear la fecha: $dateStr", e)
      }
    }

    def scriptDetencion(): Unit = {
      val nombreTXT = "detencion.sql"
      val insertFormat = s"INSERT INTO Detencion(anio, id_detencion, zona, subzona, tipo_arma, nombre_arma, tipo, " +
        s"estado_civil, estatus_migratorio, edad, sexo, genero, nacionalidad, autoidentificacion_etnica, " +
        s"numero_detenciones, nivel_instruccion, condicion, movilizacion, fecha_detencion_aprehension, " +
        s"hora_detencion, lugar, tipo_lugar, presunta_flagrancia) VALUES(%d, %d, '%s', '%s', '%s', '%s', '%s', '%s', " +
        s"'%s', %d, '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', STR_TO_DATE('%s', '%%d/%%m/%%Y'), '%s', '%s', '%s', '%s');"

      val data7 = leerRutas(ruta2023)
      val data8 = leerRutas(ruta2024)

      val data = data7 ++ data8

      val value = data
        .map { x =>
          (
            x("anio").trim.toInt,
            x("id").trim.toInt,
            x("nombre_zona").trim,
            x("nombre_subzona").trim,
            x("tipo_arma").trim,
            x("arma").trim,
            x("tipo").trim,
            x("estado_civil").trim,
            x("estatus_migratorio").trim,
            x("edad").trim.toInt,
            x("sexo").trim,
            x("genero").trim,
            x("Nacionalidad").trim,
            x("autoidentificacion_etnica").trim,
            x("numero_detenciones").trim.toInt,
            x("nivel_de_instruccion").trim,
            x("condicion").trim,
            x("movilizacion").trim,
            definirFecha(x("fecha_detencion_aprehension").trim),
            x("hora_detencion_aprehension").trim,
            x("lugar").trim,
            x("tipo_lugar").trim,
            x("presunta_flagrancia").trim
          )
        }
        .map { case (
          anio, id, nombre_zona, nombre_subzona, tipo_arma, arma, tipo, estado_civil, estatus_migratorio, edad,
          sexo, genero, nacionalidad, autoidentificacion_etnica, numero_detenciones, nivel_de_instruccion,
          condicion, movilizacion, fecha_detencion_apreh, hora_detencion_apreh, lugar, tipo_lugar,
          presunta_flagrancia
          ) =>
          escribirDatosTXT(
            nombreTXT,
            insertFormat.formatLocal(
              java.util.Locale.US, anio, id, nombre_zona, nombre_subzona, tipo_arma, arma, tipo, estado_civil,
              estatus_migratorio, edad, sexo, genero, nacionalidad, autoidentificacion_etnica, numero_detenciones,
              nivel_de_instruccion, condicion, movilizacion, fecha_detencion_apreh, hora_detencion_apreh, lugar,
              tipo_lugar, presunta_flagrancia
            )
          )
        }
      println("Script " + nombreTXT + " creado con éxito")
    }

    def scriptDetencion22(): Unit = {
      val nombreTXT = "detencion22.sql"
      val insertFormat = s"INSERT INTO Detencion(anio, id_detencion, zona, subzona, tipo_arma, nombre_arma, tipo, " +
        s"estado_civil, estatus_migratorio, edad, sexo, genero, nacionalidad, autoidentificacion_etnica, " +
        s"numero_detenciones, nivel_instruccion, condicion, movilizacion, fecha_detencion_aprehension, " +
        s"hora_detencion, lugar, tipo_lugar, presunta_flagrancia) VALUES(%d, %d, '%s', '%s', '%s', '%s', '%s', '%s', " +
        s"'%s', %d, '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', STR_TO_DATE('%s', '%%d/%%m/%%Y'), '%s', '%s', '%s', '%s');"
      val data = leerRutas(ruta2022)

      val value = data
        .map { x =>
          (
            x("anio").trim.toInt,
            x("id").trim.toInt,
            x("nombre_zona").trim,
            x("nombre_subzona").trim,
            x("tipo").trim,
            x("edad").trim.toInt,
            x("sexo").trim,
            x("Nacionalidad").trim,
            x("autoidentificacion_etnica").trim,
            x("numero_detenciones").trim.toInt,
            definirFecha(x("fecha_detencion_aprehension").trim),
            x("hora_detencion_aprehension").trim,
            x("lugar").trim,
            x("presunta_flagrancia").trim
          )
        }
        .map(x => escribirDatosTXT(nombreTXT,
            insertFormat.formatLocal(
              java.util.Locale.US, x._1, x._2, x._3, x._4, null, null, x._5, null, null, x._6, x._7, null, x._8,
              x._9, x._10, null, null, null, x._11, x._12, x._13, null, x._14)))
      println("Script " + nombreTXT + " creado con éxito")
    }

    def scriptDetencion1921(): Unit = {
      val nombreTXT = "detencion1921.sql"
      val insertFormat = s"INSERT INTO Detencion(anio, id_detencion, zona, subzona, tipo_arma, nombre_arma, tipo, " +
        s"estado_civil, estatus_migratorio, edad, sexo, genero, nacionalidad, autoidentificacion_etnica, " +
        s"numero_detenciones, nivel_instruccion, condicion, movilizacion, fecha_detencion_aprehension, " +
        s"hora_detencion, lugar, tipo_lugar, presunta_flagrancia) VALUES(%d, %d, '%s', '%s', '%s', '%s', '%s', '%s', " +
        s"'%s', %d, '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', STR_TO_DATE('%s', '%%d/%%m/%%Y'), '%s', '%s', '%s', '%s');"

      val data3 = leerRutas(ruta2019)
      val data4 = leerRutas(ruta2020)
      val data5 = leerRutas(ruta2021)

      val data = data3 ++ data4 ++ data5

      val value = data
        .map { x =>
          (
            x("anio").trim.toInt,
            x("id").trim.toInt,
            x("nombre_zona").trim,
            x("nombre_subzona").trim,
            x("Edad").trim.toInt,
            x("Sexo").trim,
            x("Nacionalidad").trim,
            definirFecha(x("Fecha de Detencion").trim)
          )
        }
        .map (x =>
          escribirDatosTXT(nombreTXT, insertFormat.formatLocal(
              java.util.Locale.US, x._1, x._2, x._3, x._4, null, null, null, null, null, x._5, x._6, null, x._7, null,
            null, null, null, null, x._8, null, null, null, null)))

      println("Script " + nombreTXT + " creado con éxito")
    }

    // INSERT INTO tu_tabla (columna_fecha)
    //VALUES (STR_TO_DATE('11/9/2023', '%d/%m/%Y'));
    def scriptDetencion1618(): Unit = {
      val nombreTXT = "detencion1618.sql"
      val insertFormat = s"INSERT INTO Detencion(anio, id_detencion, zona, subzona, tipo_arma, nombre_arma, tipo, " +
        s"estado_civil, estatus_migratorio, edad, sexo, genero, nacionalidad, autoidentificacion_etnica, " +
        s"numero_detenciones, nivel_instruccion, condicion, movilizacion, fecha_detencion_aprehension, " +
        s"hora_detencion, lugar, tipo_lugar, presunta_flagrancia) VALUES(%d, %d, '%s', '%s', '%s', '%s', '%s', '%s', " +
        s"'%s', %d, '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', STR_TO_DATE('%s', '%%d/%%m/%%Y') , '%s', '%s', '%s', '%s');"

      val data3 = leerRutas(ruta2016)
      val data4 = leerRutas(ruta2017)
      val data5 = leerRutas(ruta2018)

      val data = data3 ++ data4 ++ data5

      val value = data
        .map { x =>
          (
            valoresDoBuedos(x("anio").trim),
            valoresDoBuedos(x("id").trim),
            x("nombre_zona").trim,
            x("nombre_subzona").trim,
            valoresDoBuedos(x("Edad").trim),
            x("Sexo").trim,
            x("Nacionalidad").trim,
            definirFecha(x("Fecha de Detencion").trim),
            x("Flagrante/Boleta").trim
          )
        }
        .map(x =>
          escribirDatosTXT(nombreTXT, insertFormat.formatLocal(
            java.util.Locale.US, x._1, x._2, x._3, x._4, null, null, null, null, null, x._5, x._6, null, x._7, null,
            null, null, null, null, x._8, null, null, null, x._9)))

      println("Script " + nombreTXT + " creado con éxito")
    }

    // scriptDetencion1618()
    scriptDetencion1921()
    scriptDetencion22()
    scriptDetencion()
  }
}
