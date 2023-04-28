
data class Nota(
    val id: Int = -1,
    val titulo: String = "",
    val descripcion: String = "",
)

data class NotasList(val results: List<Nota> = listOf())