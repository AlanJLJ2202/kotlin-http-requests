data class ApiResponse (
    val data: Any,
    val message: String = "",
    val errors: List<String>
)

