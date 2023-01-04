package com.github.secretx33.codebench

import java.util.*

class RandomSeed {

    fun generateId(): Int {
        val timestamp = System.currentTimeMillis()

        val concatenatedValues = timestamp.toString() + DEFAULT_COMPANY_ID.toString()
        val seed = if (concatenatedValues.length > 18) {
            concatenatedValues
        } else {
            concatenatedValues.substring(0, 18)
        }

        val random = Random(seed.toLong())
        return random.nextInt()
    }

    private companion object {
        const val DEFAULT_COMPANY_ID = 500
    }

}
