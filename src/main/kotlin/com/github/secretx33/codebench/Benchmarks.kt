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

/**
 * Here is where all the action happens. Annotate your methods with [@Benchmark][Benchmark] to make them appear
 * on your benchmark results.
 *
 * Remember to always either return the result of the operation or blackhole it using a [Blackhole] so the JIT
 * compiler won't remove your code and make you benchmark an empty method instead ;).
 *
 * The test can be tweaked to your likings and benchmark case, but these are good start points for anybody
 * that has just got into benchmarking and know nothing about it.
 */
@Fork(1, jvmArgsAppend = ["-XX:+UseG1GC", "-XX:+AlwaysPreTouch", "-Xms2G", "-Xmx2G"])
@Warmup(iterations = 15, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
open class Benchmarks {

    // On this benchmark, the test is trying to figure out if it is worth to wrap Exception
    // instantiation with 'lazy' in scenarios where the exception is not always throw, and
    // also see if there is any difference in speed for creating an uninitialized empty 'lazy'
    // vs an uninitialized 'lazy' of an exception.
    //
    // Run it to see if that is worth doing on your code!

    @Benchmark
    open fun eagerException() = Exception()

    @Benchmark
    open fun lazyException() = lazy { Exception() }.value

    @Benchmark
    open fun uninitializedLazyException() = lazy { Exception() }

    @Benchmark
    open fun emptyLazy() = lazy { }

}
