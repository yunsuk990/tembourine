package com.example.tem

import java.io.Serializable

class Shop: Serializable {

    private var image: Int = 0

    fun getImage(): Int {return image}
    fun setImage(image: Int){this.image = image}

}