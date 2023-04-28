
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WebService {

    @GET("api/Note")
    suspend fun getNotas(): Response<Any>

    @GET("login")
    suspend fun login(
        @Query ("username") username: String,
        @Query ("password") password: String): Response<Any>

    /*@GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query ("api_key") apiKey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopulardMovies(@Query ("api_key") apiKey: String): MovieList*/
}

