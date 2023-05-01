package com.f33dir.lab4
import kotlinx.serialization.Serializable

@Serializable
data class Package(val content : String, val type: Int) ;