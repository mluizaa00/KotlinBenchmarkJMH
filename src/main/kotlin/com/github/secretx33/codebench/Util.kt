package com.github.secretx33.codebench

fun Any.getResourceAsString(name: String): String = this::class.java.classLoader.getResourceAsStream(name)!!
    .bufferedReader()
    .use { it.readText() }
