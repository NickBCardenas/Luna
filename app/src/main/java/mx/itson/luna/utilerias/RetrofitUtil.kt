package mx.itson.luna.utilerias

import com.google.gson.GsonBuilder
import mx.itson.luna.interfaz.OpenMeteo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitUtil {
    fun getApi() : OpenMeteo {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder().baseUrl("https://api.open-meteo.com/v1/").addConverterFactory(GsonConverterFactory.create(gson)).build()
        return retrofit.create(OpenMeteo::class.java)
    }

}