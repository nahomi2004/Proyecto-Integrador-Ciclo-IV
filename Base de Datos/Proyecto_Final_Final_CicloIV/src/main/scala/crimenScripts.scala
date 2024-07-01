import java.io.{BufferedWriter, File, FileWriter}
import doobie.*
import doobie.implicits.*
import cats.*
import cats.effect.*
import cats.implicits.*
import com.github.tototoshi.csv.{CSVReader, DefaultCSVFormat}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object crimenScripts {
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

    def escribirDatosTXT2(archivo: String): Unit =
      val rutaTXT = "C:/Users/D E L L/Documents/Git Proyecto Ciclo 4/Proyecto-Integrador-Ciclo-IV/Base de Datos/Script/scriptInsertIntos2.sql"

      val escritor = new BufferedWriter(new FileWriter(rutaTXT, true))
      try {
        escritor.write(archivo)
        escritor.newLine()
      } finally {
        escritor.close()
      }

    def valoresDoBuedos(s: String) =
      if (s.isEmpty) 0 else s.toInt

    def scriptCrimen(): Unit = {
      /*
      .map(x => {
            val presuntaSubinfraccion = x("Presunta_Subinfraccion").trim
            val truncatedSubinfraccion = if (presuntaSubinfraccion.length > 200) presuntaSubinfraccion.substring(0, 200) else presuntaSubinfraccion
            (x("anio").trim.toInt,
              x("id").trim.toInt,
              truncatedSubinfraccion)
          })
      * */
      val nombreTXT = "crimen2324.sql"
      val insertFormat = s"INSERT INTO Crimen(codigo_iccs, presunta_infraccion, presunta_subinfraccion, presunta_modalidad, presunta_subinfraccion_incom, presunta_modalidad_incom) " +
        s"VALUES('%s', '%s', '%s', '%s', '%s', '%s');"

      // val data0 = leerRutas(ruta2022)
      val data1 = leerRutas(ruta2023)
      val data2 = leerRutas(ruta2024)

      val data = (data1 ++ data2) // Unir todos los cantones y sus codigos desde que empezó a tomarse estos datos (2023)

      val value = data
        .map(x => {
          val presuntaSubinfraccion = x("presunta_subinfraccion").trim
          val truncatedSubinfraccion = if (presuntaSubinfraccion.length > 200) presuntaSubinfraccion.substring(0, 200) else presuntaSubinfraccion
          val presuntaModalidad = x("presunta_modalidad").trim
          val truncatedModalidad = if (presuntaModalidad.length > 200) presuntaModalidad.substring(0, 200) else presuntaModalidad
          (x("codigo_iccs").trim,
          x("presunta_infraccion").trim,
          x("presunta_subinfraccion").trim,
          x("presunta_modalidad").trim,
          truncatedSubinfraccion,
          truncatedModalidad)})
        .distinct
        .sortBy(x => (x._1, x._2))
        .map(x => escribirDatosTXT2(insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, x._3, x._4, x._5, x._6)))
      println("Script " + nombreTXT + " creado con éxito")
      // println(value.size)
    }

    def scriptCrimen22(): Unit = {
      val nombreTXT = "crimen22.sql"
      val insertFormat = s"INSERT INTO Crimen(codigo_iccs, presunta_infraccion, presunta_subinfraccion, presunta_modalidad, presunta_subinfraccion_incom, presunta_modalidad_incom) " +
        s"VALUES('%s', '%s', '%s', '%s', '%s', '%s');"

      val data = leerRutas(ruta2022)

      val value = data
        .map(x => {
          val presuntaSubinfraccion = x("presunta_subinfraccion").trim
          val truncatedSubinfraccion = if (presuntaSubinfraccion.length > 200) presuntaSubinfraccion.substring(0, 200) else presuntaSubinfraccion
          (x("presunta_infraccion").trim,
          x("presunta_subinfraccion").trim,
          truncatedSubinfraccion)})
        .distinct
        .sortBy(x => (x._1, x._2))
        .map(x => escribirDatosTXT2(insertFormat.formatLocal(java.util.Locale.US, "SIN DATO", x._1, x._2, null, x._3, "SIN DATO")))
      println("Script " + nombreTXT + " creado con éxito")
      println(value.size)
    }

    def scriptCrimen1621(): Unit = {
      val nombreTXT = "crimen1621.sql"
      val insertFormat = s"INSERT INTO Crimen(codigo_iccs, presunta_infraccion, presunta_subinfraccion, presunta_modalidad, presunta_subinfraccion_incom, presunta_modalidad_incom) " +
        s"VALUES('%s', '%s', '%s', '%s', '%s', '%s');"

      val data0 = leerRutas(ruta2016)
      val data1 = leerRutas(ruta2017)
      val data2 = leerRutas(ruta2018)
      val data3 = leerRutas(ruta2019)
      val data4 = leerRutas(ruta2020)
      val data5 = leerRutas(ruta2021)

      val data = data0 ++ data1 ++ data2 ++ data3 ++ data4 ++ data5

      val value = data
        .filter(x =>
          !x("Presunta_Subinfraccion").trim.isEmpty
        )
        .map(x => {
          val presuntaSubinfraccion = x("Presunta_Subinfraccion").trim
          val truncatedSubinfraccion = if (presuntaSubinfraccion.length > 200) presuntaSubinfraccion.substring(0, 200) else presuntaSubinfraccion
          (x("Presunta_Subinfraccion").trim,
          truncatedSubinfraccion)})
        .distinct
        .sorted
        .map(x => escribirDatosTXT2(insertFormat.formatLocal(java.util.Locale.US, "SIN DATO", "SIN DATO", x._1, null, x._2, "SIN DATO")))
      println("Script " + nombreTXT + " creado con éxito")
      println(value.size)
    }

    def scriptDetencionXCrimen1621(): Unit = {
      val nombreTXT = "DetencionXCrimen1621.sql"
      val insertFormat = s"INSERT INTO DetencionXCrimen(anio, id_detencion, codigo_iccs, presunta_infraccion, presunta_subinfraccion, presunta_modalidad) " +
        s"VALUES(%d, %d, '%s', '%s', '%s', '%s');"

      val data0 = leerRutas(ruta2016)
      val data1 = leerRutas(ruta2017)
      val data2 = leerRutas(ruta2018)
      val data3 = leerRutas(ruta2019)
      val data4 = leerRutas(ruta2020)
      val data5 = leerRutas(ruta2021)

      val data = data0 ++ data1 ++ data2 ++ data3 ++ data4 ++ data5

      val value = data
        .filter(x =>
          !x("anio").trim.isEmpty &&
          !x("id").trim.isEmpty &&
          !x("Presunta_Subinfraccion").trim.isEmpty
        )
        .map(x => {
          val presuntaSubinfraccion = x("Presunta_Subinfraccion").trim
          val truncatedSubinfraccion = if (presuntaSubinfraccion.length > 200) presuntaSubinfraccion.substring(0, 200) else presuntaSubinfraccion
          (x("anio").trim.toInt,
          x("id").trim.toInt,
          truncatedSubinfraccion)
        })
        .map(x => escribirDatosTXT2(insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, "SIN DATO", "SIN DATO", x._3, "SIN DATO")))
      println("Script " + nombreTXT + " creado con éxito")
      println(value.size)
    }

    def scriptDetencionXCrimen22(): Unit = {
      val nombreTXT = "DetencionXCrimen22.sql"
      val insertFormat = s"INSERT INTO DetencionXCrimen(anio, id_detencion, codigo_iccs, presunta_infraccion, presunta_subinfraccion, presunta_modalidad) " +
        s"VALUES(%d, %d, '%s', '%s', '%s', '%s');"

      val data = leerRutas(ruta2022)

      val value = data
        .map(x => {
          val presuntaSubinfraccion = x("presunta_subinfraccion").trim
          val truncatedSubinfraccion = if (presuntaSubinfraccion.length > 200) presuntaSubinfraccion.substring(0, 200) else presuntaSubinfraccion
          (x("anio").trim.toInt,
          x("id").trim.toInt,
          x("presunta_infraccion"),
          truncatedSubinfraccion)})
        .map(x => escribirDatosTXT2(insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, "SIN DATO", x._3, x._4, "SIN DATO")))
      println("Script " + nombreTXT + " creado con éxito")
      println(value.size)
    }

    def scriptDetencionXCrimen2324(): Unit = {
      val nombreTXT = "DetencionXCrimen2324.sql"
      val insertFormat = s"INSERT INTO DetencionXCrimen(anio, id_detencion, codigo_iccs, presunta_infraccion, presunta_subinfraccion, presunta_modalidad) " +
        s"VALUES(%d, %d, '%s', '%s', '%s', '%s');"

      val data1 = leerRutas(ruta2023)
      val data2 = leerRutas(ruta2024)

      val data = (data1 ++ data2)

      val value = data
        .map(x => {
          val presuntaSubinfraccion = x("presunta_subinfraccion").trim
          val truncatedSubinfraccion = if (presuntaSubinfraccion.length > 200) presuntaSubinfraccion.substring(0, 200) else presuntaSubinfraccion
          val presuntaModalidad = x("presunta_modalidad").trim
          val truncatedModalidad = if (presuntaModalidad.length > 200) presuntaModalidad.substring(0, 200) else presuntaModalidad
          (x("anio").trim.toInt,
          x("id").trim.toInt,
          x("codigo_iccs"),
          x("presunta_infraccion"),
          truncatedSubinfraccion,
          truncatedModalidad)})
        .map(x => escribirDatosTXT2(insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, x._3, x._4, x._5, x._6)))
      println("Script " + nombreTXT + " creado con éxito")
      println(value.size)
    }

    // scriptCrimen1621()
    // scriptCrimen22()
    // scriptCrimen()
    scriptDetencionXCrimen1621()
    scriptDetencionXCrimen22()
    scriptDetencionXCrimen2324()
  }
}
