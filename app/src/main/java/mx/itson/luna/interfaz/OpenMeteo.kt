package mx.itson.luna.interfaz

import mx.itson.luna.entidades.Ubicacion
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteo {

    @GET("forecast")
    fun getClima(@Query("latitude") lat:String,
                 @Query("longitude") lon:String,
                 @Query("current_weather") current: Boolean): Call<Ubicacion>
}