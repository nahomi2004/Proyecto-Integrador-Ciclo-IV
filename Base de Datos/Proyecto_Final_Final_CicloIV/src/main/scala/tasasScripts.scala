import java.io.{BufferedWriter, File, FileWriter}
import doobie.*
import doobie.implicits.*
import cats.*
import cats.effect.*
import cats.implicits.*
import com.github.tototoshi.csv.{CSVReader, DefaultCSVFormat}
import java.time.LocalDate
import java.time.format.DateTimeFormatter
object tasasScripts {
  // @main
  def ejecutable(): Unit = {
    val ruta = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Excel/CSV Tasa Anual Victimas.csv"

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

    def escribirDatosTXT2(archivo: String): Unit =
      val rutaTXT = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Base de Datos/Script/scriptInsertIntos.sql"

      val escritor = new BufferedWriter(new FileWriter(rutaTXT, true))
      try {
        escritor.write(archivo)
        escritor.newLine()
      } finally {
        escritor.close()
      }

    def scriptTasasVictimas(): Unit = {
      val nombreTXT = "tasasVictimas.sql"
      val insertFormat = s"INSERT INTO VictimasHomicidio(anioVictimas, feminicidio, sicaritio, homicidio, asesinato) " +
        s"VALUES(%d, %d, %d, %d, %d);"

      val data = leerRutas(ruta)

      // val data = (data1 ++ data2) // Unir todos los cantones y sus codigos desde que empezó a tomarse estos datos (2023)

      val value = data
        .map(x => (x("anio").trim.toInt,
          x("femicidio").trim.toInt,
          x("sicariato").trim.toInt,
          x(" homicidio").trim.toInt,
          x("asesinato").trim.toInt))
        .distinct
        .sortBy(x => x._1)
        .map(x => escribirDatosTXT2(insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, x._3, x._4, x._5)))
      println("Script " + nombreTXT + " creado con éxito")
    }

    def scriptTasas(): Unit = {
      val nombreTXT = "tasasAdicionales.sql"
      val insertFormat = s"INSERT INTO Tasas_Porcentaje(anioTasas, tasa_PoblacionE, tasa_Inmigrantes, tasa_Desempleo, tasa_Crimen, tasa_Desnutricion) " +
        s"VALUES(%d, %d, %.2f, %.2f, %.2f);"

      val data = leerRutas(ruta)

      // val data = (data1 ++ data2) // Unir todos los cantones y sus codigos desde que empezó a tomarse estos datos (2023)

      val value = data
        .map(x => (x("anio").trim.toInt,
          x("tasa_PoblacionE").trim.toLong,
          x("tasa_Inmigrantes").trim.toDouble,
          x("tasa_Desempleo").trim.toDouble,
          x("tasa_Crimen").trim.toDouble,
          x("tasa_Desnutricion").trim.toDouble))
        .distinct
        .sortBy(x => x._1)
        .map(x => escribirDatosTXT2(insertFormat.formatLocal(java.util.Locale.US, x._1, x._2: java.lang.Long, x._3, x._4, x._5, x._6)))
      println("Script " + nombreTXT + " creado con éxito")
      println(value.size)
    }

    scriptTasasVictimas()
    scriptTasas()
  }
}
