
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WebService {

    @GET("api/Note")
    suspend fun getNotas(@Query ("idUser") idUser: Int): Response<Any>

    @GET("login")
    suspend fun login(
        @Query ("username") username: String,
        @Query ("password") password: String): Response<Any>


    @POST("api/User")
    suspend fun registro(
        @Field("id") id: Int,
        @Field("username") username: String,
        @Field("password") password: String): Response<Any>




    /*@GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query ("api_key") apiKey: String): MovieList

    @GET("movie/popular")
    suspend fun getPopulardMovies(@Query ("api_key") apiKey: String): MovieList*/
}


