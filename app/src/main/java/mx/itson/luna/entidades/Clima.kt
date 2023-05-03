package mx.itson.luna.entidades

import com.google.gson.annotations.SerializedName

class Clima {
    @SerializedName ("temperature")
    var temperatura: Float? = null

    var windspeed: Float? = null

    var winddirection: Float? = null

    var elevation: Float? = null

    var weathercode : Float? = null     

}