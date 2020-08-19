import java.sql.Timestamp
import java.text.SimpleDateFormat

val formatOne = new SimpleDateFormat("M/dd/yyyy")
val formatDateOne = formatOne.parse("12/02/2020")
val dateOne = new Timestamp(formatDateOne.getTime)