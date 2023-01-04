package com.github.secretx33.codebench

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Threads
import org.openjdk.jmh.annotations.Warmup
import java.util.concurrent.TimeUnit

@Fork(1, jvmArgsAppend = ["-XX:+UseG1GC", "-XX:+AlwaysPreTouch", "-Xms4G", "-Xmx4G"])
@Warmup(iterations = 10, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
class Benchmarks {

    private val snowflake = Snowflake()

    @Threads(1)
    @Benchmark
    fun testSnowflakePerformance1(): Long = snowflake.nextLong()

    @Threads(4)
    @Benchmark
    fun testSnowflakePerformance4(): Long = snowflake.nextLong()

    @Threads(8)
    @Benchmark
    fun testSnowflakePerformance8(): Long = snowflake.nextLong()

    @Threads(12)
    @Benchmark
    fun testSnowflakePerformance12(): Long = snowflake.nextLong()

    @Threads(16)
    @Benchmark
    fun testSnowflakePerformance16(): Long = snowflake.nextLong()

    @Threads(20)
    @Benchmark
    fun testSnowflakePerformance20(): Long = snowflake.nextLong()

    @Threads(30)
    @Benchmark
    fun testSnowflakePerformance30(): Long = snowflake.nextLong()

    @Threads(50)
    @Benchmark
    fun testSnowflakePerformance50(): Long = snowflake.nextLong()

    @Threads(70)
    @Benchmark
    fun testSnowflakePerformance70(): Long = snowflake.nextLong()

    @Threads(100)
    @Benchmark
    fun testSnowflakePerformance100(): Long = snowflake.nextLong()

    @Threads(120)
    @Benchmark
    fun testSnowflakePerformance120(): Long = snowflake.nextLong()

    @Threads(180)
    @Benchmark
    fun testSnowflakePerformance180(): Long = snowflake.nextLong()

    @Threads(240)
    @Benchmark
    fun testSnowflakePerformance240(): Long = snowflake.nextLong()

    @Threads(300)
    @Benchmark
    fun testSnowflakePerformance300(): Long = snowflake.nextLong()

    @Threads(500)
    @Benchmark
    fun testSnowflakePerformance500(): Long = snowflake.nextLong()

}
