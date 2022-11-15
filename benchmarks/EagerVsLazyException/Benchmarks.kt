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
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit

@Fork(1, jvmArgsAppend = ["-XX:+UseG1GC", "-XX:+AlwaysPreTouch", "-Xms2G", "-Xmx2G"])
@Warmup(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
open class Benchmarks {

    @Benchmark
    open fun eagerException() = Exception()

    @Benchmark
    open fun lazyException() = lazy { Exception() }.value

    @Benchmark
    open fun uninitializedLazyException() = lazy { Exception() }

    @Benchmark
    open fun emptyLazy() = lazy { }

}
