import java.io.{BufferedWriter, File, FileWriter}
import doobie.*
import doobie.implicits.*
import cats.*
import cats.effect.*
import cats.implicits.*
import com.github.tototoshi.csv.{CSVReader, DefaultCSVFormat}
implicit object CustomFormat extends  DefaultCSVFormat {
  override  val delimiter: Char = ';'
}
object generarScripts {
  // @main
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

    def scriptProvincia(): Unit = {
      val nombreTXT = "provincia.sql"
      val insertFormat = s"INSERT INTO Provincia(codigo_provincia, nombre) VALUES('%s', '%s');"

      // val data0 = leerRutas(ruta2022)
      val data1 = leerRutas(ruta2023)
      val data2 = leerRutas(ruta2024)

      val data = (data1 ++ data2) // Unir todos las provincias y sus codigos desde que empezó a tomarse estos datos (2023)

      val value = data
        .map(x => (x("codigo_provincia").trim,
          x("nombre_provincia").trim))
        .distinct
        .sortBy(x => (x._1, x._2))
        .map(x => escribirDatosTXT(nombreTXT, insertFormat.formatLocal(java.util.Locale.US, x._1, x._2)))
      println("Script " + nombreTXT + " creado con éxito")
      // println(value.size) comprobar que están las 24 provincias, hay 26, dos veces Cañar 3 - 90 y mar territorial
    }

    // scriptProvincia()

    def scriptZona(): Unit = {
      val nombreTXT = "zona.sql"
      val insertFormat = s"INSERT INTO Zona(nombre_zona, nombre_subzona) VALUES('%s', '%s');"

      val data0 = leerRutas(ruta2016)
      val data1 = leerRutas(ruta2017)
      val data2 = leerRutas(ruta2018)
      val data3 = leerRutas(ruta2019)
      val data4 = leerRutas(ruta2020)
      val data5 = leerRutas(ruta2021)
      val data6 = leerRutas(ruta2022)
      val data7 = leerRutas(ruta2023)
      val data8 = leerRutas(ruta2024)

      val data = (data0 ++ data1 ++ data2 ++ data3 ++ data4 ++ data5 ++ data6 ++ data7 ++ data8)
      // Unir todos las zonas desde que empezó a tomarse estos datos (2016)

      val value = data
        .map(x => (x("nombre_zona").trim,
          x("nombre_subzona").trim))
        .distinct
        .sortBy(x => (x._1, x._2))
        .map(x => escribirDatosTXT(nombreTXT, insertFormat.formatLocal(java.util.Locale.US, x._1, x._2)))
      println("Script " + nombreTXT + " creado con éxito")
      // println(value.size)
    }

    // scriptZona()

    def scriptCanton(): Unit = {
      val nombreTXT = "canton.sql"
      val insertFormat = s"INSERT INTO Canton(codigo_canton, nombre_subzona, codigo_provincia, nombre_canton) " +
        s"VALUES('%s', '%s', '%s', '%s');"

      // val data0 = leerRutas(ruta2022)
      val data1 = leerRutas(ruta2023)
      val data2 = leerRutas(ruta2024)

      val data = (data1 ++ data2) // Unir todos los cantones y sus codigos desde que empezó a tomarse estos datos (2023)

      val value = data
        .map(x => (x("codigo_canton").trim,
          x("nombre_subzona").trim,
          x("codigo_provincia").trim,
          x("nombre_canton").trim))
        .distinct
        .sortBy(x => (x._1, x._2))
        .map(x => escribirDatosTXT(nombreTXT, insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, x._3, x._4)))
      println("Script " + nombreTXT + " creado con éxito")
      println(value.size)
    }

    scriptCanton()

    def scriptParroquia(): Unit = {
      val nombreTXT = "parroquia.sql"
      val insertFormat = s"INSERT INTO Parroquia(codigo_parroquia, codigo_canton, nombre) VALUES('%s', '%s', '%s');"

      // val data0 = leerRutas(ruta2022)
      val data1 = leerRutas(ruta2023)
      val data2 = leerRutas(ruta2024)

      val data = (data1 ++ data2) // Unir todos las parroquias y sus codigos desde que empezó a tomarse estos datos (2023)

      val value = data
        .map(x => (x("codigo_parroquia").trim,
          x("codigo_canton").trim,
          x("nombre_parroquia").trim))
        .distinct
        .sortBy(x => (x._1, x._2))
        .map(x => escribirDatosTXT(nombreTXT, insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, x._3)))
      println("Script " + nombreTXT + " creado con éxito")
    }

    // scriptParroquia()

    def scriptDistrito(): Unit = {
      val nombreTXT = "distrito.sql"
      val insertFormat = s"INSERT INTO Distrito(codigo_distrito, nombre_subzona, nombre) VALUES('%s', '%s', '%s');"

      val data0 = leerRutas(ruta2022)
      val data1 = leerRutas(ruta2023)
      val data2 = leerRutas(ruta2024)

      val data = (data0 ++ data1 ++ data2) // Unir todos los distritos desde que empezó a tomarse estos datos (2022)

      val value = data
        .map(x => (x("codigo_distrito").trim,
          x("nombre_subzona").trim,
          x("nombre_distrito").trim))
        .distinct
        .sortBy(x => (x._1, x._2))
        .map(x => escribirDatosTXT(nombreTXT, insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, x._3)))
      println("Script " + nombreTXT + " creado con éxito")
    }

    // scriptDistrito()

    def scriptCircuito(): Unit = {
      val nombreTXT = "circuito.sql"
      val insertFormat = s"INSERT INTO Circuito(codigo_circuito, codigo_distrito, nombre) VALUES('%s', '%s', '%s');"

      val data0 = leerRutas(ruta2022)
      val data1 = leerRutas(ruta2023)
      val data2 = leerRutas(ruta2024)

      val data = (data0 ++ data1 ++ data2) // Unir todos los circuitos desde que empezó a tomarse estos datos (2022)

      val value = data
        .map(x => (x("codigo_circuito").trim,
          x("codigo_distrito").trim,
          x("nombre_circuito").trim))
        .distinct
        .sortBy(x => (x._1, x._2))
        .map(x => escribirDatosTXT(nombreTXT, insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, x._3)))
      println("Script " + nombreTXT + " creado con éxito")
    }

    // scriptCircuito()

    def scriptSubcircuito(): Unit = {
      val nombreTXT = "subcircuito.sql"
      val insertFormat = s"INSERT INTO Subcircuito(codigo_subcircuito, codigo_circuito, nombre) VALUES('%s', '%s', '%s');"

      val data0 = leerRutas(ruta2022)
      val data1 = leerRutas(ruta2023)
      val data2 = leerRutas(ruta2024)

      val data = (data0 ++ data1 ++ data2) // Unir todos los subcircuitos desde que empezó a tomarse estos datos

      val value = data
        .map(x => (x("codigo_subcircuito").trim,
          x("codigo_circuito").trim,
          x("nombre_subcircuito").trim))
        .distinct
        .sortBy(x => (x._1, x._2))
        .map(x => escribirDatosTXT(nombreTXT, insertFormat.formatLocal(java.util.Locale.US, x._1, x._2, x._3)))
      println("Script " + nombreTXT + " creado con éxito")
    }

    // scriptSubcircuito()
  }
}
