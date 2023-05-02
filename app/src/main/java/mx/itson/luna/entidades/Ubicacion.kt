package mx.itson.luna.entidades

import com.google.gson.annotations.SerializedName

class Ubicacion {

    var latitude : Float? = null
    var longitude: Float? = null
    var elevation:Float? = null

    @SerializedName("current_weather")
    var clima:Clima? = null
}