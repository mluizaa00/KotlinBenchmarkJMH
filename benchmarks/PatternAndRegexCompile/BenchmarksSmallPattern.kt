package com.github.secretx33.codebench

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

@Fork(1, jvmArgsAppend = ["-XX:+UseG1GC", "-XX:+AlwaysPreTouch", "-Xms4G", "-Xmx4G"])
@Warmup(iterations = 15, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
class Benchmarks {

    @Benchmark
    fun pattern(): Pattern = Pattern.compile(DATE_STRING_PATTERN)

    @Benchmark
    fun patternExtension(): Pattern = DATE_STRING_PATTERN.toPattern()

    @Benchmark
    fun regex() = Regex(DATE_STRING_PATTERN)

    @Benchmark
    fun regexExtension() = DATE_STRING_PATTERN.toRegex()

    private companion object {
        const val DATE_STRING_PATTERN = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])\$"
    }
}
