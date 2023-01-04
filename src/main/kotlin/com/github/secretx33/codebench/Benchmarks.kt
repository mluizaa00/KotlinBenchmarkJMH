package com.github.secretx33.codebench

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@Fork(1, jvmArgsAppend = ["-XX:+UseG1GC", "-XX:+AlwaysPreTouch", "-Xms4G", "-Xmx4G"])
@Warmup(iterations = 10, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
class Benchmarks {

    private val randomSeed = RandomSeed()

    @Threads(1)
    @Benchmark
    fun testSnowflakePerformance1(): Int = randomSeed.generateId()

    @Threads(4)
    @Benchmark
    fun testSnowflakePerformance4(): Int = randomSeed.generateId()

    @Threads(8)
    @Benchmark
    fun testSnowflakePerformance8(): Int = randomSeed.generateId()

    @Threads(12)
    @Benchmark
    fun testSnowflakePerformance12(): Int = randomSeed.generateId()

    @Threads(16)
    @Benchmark
    fun testSnowflakePerformance16(): Int = randomSeed.generateId()

    @Threads(20)
    @Benchmark
    fun testSnowflakePerformance20(): Int = randomSeed.generateId()

    @Threads(30)
    @Benchmark
    fun testSnowflakePerformance30(): Int = randomSeed.generateId()

    @Threads(50)
    @Benchmark
    fun testSnowflakePerformance50(): Int = randomSeed.generateId()

    @Threads(70)
    @Benchmark
    fun testSnowflakePerformance70(): Int = randomSeed.generateId()

    @Threads(100)
    @Benchmark
    fun testSnowflakePerformance100(): Int = randomSeed.generateId()

//    @Threads(120)
//    @Benchmark
//    fun testSnowflakePerformance120(): Int = randomSeed.generateId()
//
//    @Threads(180)
//    @Benchmark
//    fun testSnowflakePerformance180(): Int = randomSeed.generateId()
//
//    @Threads(240)
//    @Benchmark
//    fun testSnowflakePerformance240(): Int = randomSeed.generateId()

//    @Threads(300)
//    @Benchmark
//    fun testSnowflakePerformance300(): Int = randomSeed.generateId()
//
//    @Threads(500)
//    @Benchmark
//    fun testSnowflakePerformance500(): Int = randomSeed.generateId()

}
