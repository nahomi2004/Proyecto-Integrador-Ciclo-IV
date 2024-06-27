import java.io.{BufferedWriter, File, FileWriter}
import doobie.*
import doobie.implicits.*
import cats.*
import cats.effect.*
import cats.implicits.*
import com.github.tototoshi.csv.{CSVReader, DefaultCSVFormat}

/*implicit object CustomFormat extends  DefaultCSVFormat {
  override  val delimiter: Char = ';'
}*/

object lecturaCSV {
  // @main
  def ejecutable(): Unit = {
    val ruta1 = "C:/Users/D E L L/Documents/gitpood/maquina-Virtual-Linux/COPIA - BD_2016.csv"
    val ruta2 = "C:/Users/D E L L/Documents/gitpood/maquina-Virtual-Linux/COPIA - BD_2017.csv"
    val ruta3 = "C:/Users/D E L L/Documents/gitpood/maquina-Virtual-Linux/COPIA - BD_2018.csv"
    val ruta4 = "C:/Users/D E L L/Documents/gitpood/maquina-Virtual-Linux/COPIA - BD_2019.csv"
    val ruta5 = "C:/Users/D E L L/Documents/gitpood/maquina-Virtual-Linux/COPIA - BD_2020.csv"
    val ruta6 = "C:/Users/D E L L/Documents/gitpood/maquina-Virtual-Linux/COPIA - BD_2021.csv"
    val ruta7 = "C:/Users/D E L L/Documents/gitpood/maquina-Virtual-Linux/COPIA - BD_2022.csv"
    val ruta8 = "C:/Users/D E L L/Documents/gitpood/maquina-Virtual-Linux/COPIA - BD_2023.csv"
    val ruta9 = "C:/Users/D E L L/Documents/gitpood/maquina-Virtual-Linux/COPIA - BD_2024.csv"

    def leerRutas(ruta: String): List[Map[String, String]] = {
      val reader = CSVReader.open(new File(ruta))
      val contentFile: List[Map[String, String]] =
        reader.allWithHeaders()

      contentFile
    }

    def leerNacionalidades(contentFile: List[Map[String, String]]) = {
      val nacionalidades = contentFile
        .map(x => x("Nacionalidad").trim)
        .distinct
        .sorted

      nacionalidades
    }

    val nacionalidades2016 = leerNacionalidades(leerRutas(ruta1))
    val nacionalidades2017 = leerNacionalidades(leerRutas(ruta2))
    val nacionalidades2018 = leerNacionalidades(leerRutas(ruta3))
    val nacionalidades2019 = leerNacionalidades(leerRutas(ruta4))
    val nacionalidades2020 = leerNacionalidades(leerRutas(ruta5))
    val nacionalidades2021 = leerNacionalidades(leerRutas(ruta6))
    val nacionalidades2022 = leerNacionalidades(leerRutas(ruta7))
    val nacionalidades2023 = leerNacionalidades(leerRutas(ruta8))
    val nacionalidades2024 = leerNacionalidades(leerRutas(ruta9))

    val nacionalidades = (nacionalidades2016 ++ nacionalidades2017 ++ nacionalidades2018 ++ nacionalidades2019 ++
      nacionalidades2020 ++ nacionalidades2021 ++ nacionalidades2022 ++ nacionalidades2023 ++ nacionalidades2024)
      .distinct
      .sorted



    def limpiarNacionalidades(nacionalidades: List[String]) = {
      val countryToNationality = Map(
        "AFRICA" -> "AFRICANA",
        "ALBANO" -> "ALBANESA",
        "ALBANES" -> "ALBANESA",
        "ALBANA" -> "ALBANESA",
        "ALEMANIA" -> "ALEMANA",
        "ARGELINO" -> "ARGELINA",
        // "ARGENTINO" -> "ALGENTINA",
        "ARMENIO" -> "ARMENIA",
        "AUTRALIANA" -> "AUSTRALIANA",
        "AZERBAIYAN" -> "AZERBAIYANA",
        "ASIA" -> "ASIATICA",
        "AUSTRIA" -> "AUSTRIACA",
        "BANGLADESHA" -> "BANGLADESE",
        "BANGLADESIA" -> "BANGLADESE",
        "BANGLADESI" -> "BANGLADESE",
        "BARBADENSEA" -> "BARBADENSE",
        // "BIELORUSO" -> "BIELORUSA",
        // "BISAUGINEANO" -> "BISAUGINEANA",
        "BOLIVIA" -> "BOLIVIANA",
        // "BOLIVIANO" -> "BOLIVIANA",
        "BRASIL" -> "BRASILEÑA",
        "BRASILA" -> "BRASILEÑA",
        // "BRITANICO" -> "BRITANICA",
        "CANADÁ" -> "CANADIENSE",
        "CHILEA" -> "CHILENA",
        "COREA" -> "COREANA",
        // "CHILENO" -> "CHILENA",
        "CHINA" -> "CHINA",
        "COLOMBIA" -> "COLOMBIANA",
        "COSTA DE MARFILA" -> "MARFILEÑA",
        "COSTA RICA" -> "COSTARRICENSE",
        "COSTARRICENSEA" -> "COSTARRICENSE",
        "CROACIA" -> "CROATA",
        "CUBA" -> "CUBANA",
        // "CAMERUNES" -> "CAMERUNESA",
        "CANADIENSEA" -> "CANADIENSE",
        "ECUADORA" -> "ECUATORIANA",
        "EGIPTA" -> "EGIPCIA",
        "EL SALVADORA" -> "SALVADOREÑA",
        "ESPAÑA" -> "ESPAÑOLA",
        "ESTADOS UNIDOSA" -> "ESTADOUNIDENSE",
        "ESTADOUNIDENCEA" -> "ESTADOUNIDENSE",
        "ESTADOUNIDENSEA" -> "ESTADOUNIDENSE",
        "FILIPINAS" -> "FILIPINA",
        "FRANCIA" -> "FRANCESA",
        "GHANA" -> "GHANESA",
        "GRECIA" -> "GRIEGA",
        "GUATEMALA" -> "GUATEMALTECA",
        "GUINEANA" -> "GUINEA",
        "HAITIA" -> "HAITIANA",
        "PAÍSES BAJOS" -> "HOLANDESA",
        "HONDURASA" -> "HONDUREÑA",
        "HOLANDA" -> "HOLANDESA",
        "INDIA" -> "INDIA",
        "INDIO/HINDUA" -> "INDIA",
        "INDUA" -> "INDIA",
        "INDONESIA" -> "INDONESIA",
        "REINO UNIDO" -> "BRITÁNICA",
        "IRANA" -> "IRANI",
        "IRANIA" -> "IRANI",
        "IRLANDA" -> "IRLANDESA",
        "ITALIA" -> "ITALIANA",
        "ISRAELIA" -> "ISRAELI",
        "JAPÓN" -> "JAPONESA",
        "JORDANIA" -> "JORDANA",
        "LITUANIA" -> "LITUANA",
        "LIBIA" -> "LIBANESA",
        "MALIENSEA" -> "MALIENSE",
        "MARROQUIA" -> "MARROQUI",
        "MARRUECOSA" -> "MARROQUI",
        "MEXICO" -> "MEXICANA",
        "MEXICA" -> "MEXICANA",
        "NICARAGUA" -> "NICARAGÜENSE",
        "NICARAGUENSEA" -> "NICARAGÜENSE",
        "NIGERIA" -> "NIGERIANA",
        "NIGERINA" -> "NIGERIANA",
        "PAKISTANIA" -> "PAKISTANI",
        "PAKISTANA" -> "PAKISTANI",
        "PANAMA" -> "PANAMEÑA",
        "PARAGUAY" -> "PARAGUAYA",
        "PAPUA" -> "PAPU",
        "PERUA" -> "PERUANA",
        "POLONIA" -> "POLACA",
        "PORTUGALA" -> "PORTUGUESA",
        "PORTUGUEÑA" -> "PORTUGUESA",
        "PUERTO RICA" -> "PUERTORIQUEÑA",
        "REINO UNIDA" -> "BRITANICA",
        "REPUBLICA DOMINICANA" -> "DOMINICANA",
        "RUMANIA" -> "RUMANA",
        "RUSIA" -> "RUSA",
        "SERBIA" -> "SERBIA",
        "SE DESCONOCEA" -> "SIN DATO",
        "SIN DATA" -> "SIN DATO",
        "SIRIA" -> "SIRIA",
        "SUDAFRICA" -> "SUDAFRICANA",
        "SINGAPURENSEA" -> "SINGAPURENSE",
        "SUECIA" -> "SUECA",
        "SUIZA" -> "SUIZA",
        "SRI LANKA" -> "ESRILANQUESA",
        "TAIWANA" -> "TAIWANESA",
        "TAILANDIA" -> "TAILANDESA",
        "TOGA" -> "TOGOLESA",
        "URUGUAY" -> "URUGUAYA",
        "UCRANIA" -> "UCRANIANA",
        "VENEZUELA" -> "VENEZOLANA",
        "VIETNAMA" -> "VIETNAMITA",
        "YEMEN" -> "YEMENÍ"
      )

      val sufijoFemenino = "A"

      val nationalitiesToFeminine = nacionalidades.map { nationality =>
        if (nationality.endsWith("O")) {
          nationality.dropRight(1) + sufijoFemenino
        } else if (!nationality.endsWith("A")) {
          nationality + sufijoFemenino
        } else {
          nationality
        }
      }

      val nationalitiesFeminine = nationalitiesToFeminine.map { country =>
        countryToNationality.getOrElse(country, country)
      }

      println(nationalitiesFeminine.distinct)
    }

    limpiarNacionalidades(nacionalidades)

    def unirCantonesProvincias() = {

    }
  }
}
