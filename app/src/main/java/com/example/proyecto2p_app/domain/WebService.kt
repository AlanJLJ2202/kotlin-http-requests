
import retrofit2.Response
import retrofit2.http.*

interface WebService {

    @GET("api/Note")
    suspend fun getNotas(@Query ("idUser") idUser: Int): Response<Any>

    @GET("login")
    suspend fun login(
        @Query ("username") username: String,
        @Query ("password") password: String): Response<Any>

    @GET("registro")
    suspend fun registro(
        @Query("id") id: Int,
        @Query("username") username: String,
        @Query("password") password: String): Response<Any>


    @GET("savenote")
    suspend fun registroNota(
        @Query("title") title: String,
        @Query("descripcion") descripcion: String,
        @Query("iduser") iduser: Int): Response<Any>

    /*@GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query ("api_key") apiKey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopulardMovies(@Query ("api_key") apiKey: String): MovieList*/
}


