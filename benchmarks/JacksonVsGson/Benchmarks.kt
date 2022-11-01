package com.github.secretx33.codebench

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.util.concurrent.TimeUnit

@Fork(1, jvmArgsAppend = ["-XX:+UseG1GC", "-XX:+AlwaysPreTouch", "-Xms2G", "-Xmx2G"])
@Warmup(iterations = 15, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
open class Benchmarks {

    @Param
    lateinit var jsonSize: JsonSize
    private lateinit var holder: ConfigHolder

    private lateinit var jackson: ObjectMapper
    private lateinit var jacksonWithModules: ObjectMapper
    private lateinit var gson: Gson
    private lateinit var gsonFromBuilder: Gson
    private lateinit var gsonHtmlEscapeDisabled: Gson
    private lateinit var gsonDisableJdkUnsafe: Gson
    private lateinit var gsonHtmlEscapeDisabledAndDisableJdkUnsafe: Gson

    @Setup(Level.Trial)
    open fun initialize() {
        holder = ConfigHolder(jsonSize)

        jackson = ObjectMapper()
        jacksonWithModules = ObjectMapper().findAndRegisterModules()
        gson = Gson()
        gsonFromBuilder = GsonBuilder().create()
        gsonHtmlEscapeDisabled = GsonBuilder().disableHtmlEscaping().create()
        gsonDisableJdkUnsafe = GsonBuilder().disableJdkUnsafe().create()
        gsonHtmlEscapeDisabledAndDisableJdkUnsafe = GsonBuilder().disableHtmlEscaping().disableJdkUnsafe().create()
    }

    @Benchmark
    open fun jackson(): Map<String, Any> = jackson.readValue(holder.json)

    @Benchmark
    open fun jacksonWithCachedTypeRef(): Map<String, Any> = jackson.readValue(holder.json, JACKSON_TYPE_REF)

    @Benchmark
    open fun jacksonWithModules(): Map<String, Any> = jacksonWithModules.readValue(holder.json)

    @Benchmark
    open fun jacksonWithModulesWithCachedTypeRef(): Map<String, Any> = jacksonWithModules.readValue(holder.json, JACKSON_TYPE_REF)

    @Benchmark
    open fun gson(): Map<String, Any> = gson.fromJson(holder.json, gsonTypeRef())

    @Benchmark
    open fun gsonWithCachedTypeRef(): Map<String, Any> = gson.fromJson(holder.json, GSON_TYPE_REF)

    @Benchmark
    open fun gsonFromBuilder(): Map<String, Any> = gsonFromBuilder.fromJson(holder.json, gsonTypeRef())

    @Benchmark
    open fun gsonFromBuilderWithCachedTypeRef(): Map<String, Any> = gsonFromBuilder.fromJson(holder.json, GSON_TYPE_REF)

    @Benchmark
    open fun gsonHtmlEscapeDisabled(): Map<String, Any> = gsonHtmlEscapeDisabled.fromJson(holder.json, gsonTypeRef())

    @Benchmark
    open fun gsonHtmlEscapeDisabledWithCachedTypeRef(): Map<String, Any> = gsonHtmlEscapeDisabled.fromJson(holder.json, GSON_TYPE_REF)

    @Benchmark
    open fun gsonDisableJdkUnsafe(): Map<String, Any> = gsonDisableJdkUnsafe.fromJson(holder.json, gsonTypeRef())

    @Benchmark
    open fun gsonDisableJdkUnsafeWithCachedTypeRef(): Map<String, Any> = gsonDisableJdkUnsafe.fromJson(holder.json, GSON_TYPE_REF)

    @Benchmark
    open fun gsonHtmlEscapeDisabledAndDisableJdkUnsafe(): Map<String, Any> =
        gsonHtmlEscapeDisabledAndDisableJdkUnsafe.fromJson(holder.json, gsonTypeRef())

    @Benchmark
    open fun gsonHtmlEscapeDisabledAndDisableJdkUnsafeWithCachedTypeRef(): Map<String, Any> =
        gsonHtmlEscapeDisabledAndDisableJdkUnsafe.fromJson(holder.json, GSON_TYPE_REF)

    private data class ConfigHolder(val jsonSize: JsonSize) {
        val json: String = when (jsonSize) {
            JsonSize.SMALL -> getResourceAsString("small.json")
            JsonSize.MEDIUM -> getResourceAsString("medium.json")
            JsonSize.BIG ->  getResourceAsString("big.json")
        }
    }

    enum class JsonSize {
        SMALL,
        MEDIUM,
        BIG,
    }

    private companion object {
        val JACKSON_TYPE_REF = jacksonTypeRef<Map<String, Any>>()
        val GSON_TYPE_REF = gsonTypeRef<Map<String, Any>>()
    }
}

inline fun <reified T> gsonTypeRef(): TypeToken<T> = object : TypeToken<T>() {}
