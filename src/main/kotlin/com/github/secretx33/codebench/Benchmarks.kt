package com.github.secretx33.codebench

import org.bukkit.configuration.MemoryConfiguration
import org.bukkit.configuration.MemorySection
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.lang.invoke.ConstantCallSite
import java.lang.invoke.MethodHandles
import java.lang.invoke.VarHandle
import java.util.concurrent.TimeUnit

@Fork(1, jvmArgsAppend = ["-XX:+UnlockExperimentalVMOptions", "-XX:+UseEpsilonGC", "-XX:+AlwaysPreTouch", "-Xms9G", "-Xmx9G"])
@Warmup(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 20, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
open class Benchmarks {

    @Benchmark
    open fun memoryConfigToMapConvertionRecursive(holder: ConfigHolder): Map<String, Any> {
        return memoryToMapRec(holder.config)
    }

    @Benchmark
    open fun memoryConfigToMapConvertionIterative_reflection(holder: ConfigHolder): Map<String, Any> {
        return memoryToMapIter(holder.config, MemoryConfiguration::innerMapReflection)
    }

    @Benchmark
    open fun memoryConfigToMapConvertionIterative_cachedReflection(holder: ConfigHolder): Map<String, Any> {
        return memoryToMapIter(holder.config, MemoryConfiguration::innerMapCachedReflection)
    }

    @Benchmark
    open fun memoryConfigToMapConvertionIterative_methodHandle(holder: ConfigHolder): Map<String, Any> {
        return memoryToMapIter(holder.config, MemoryConfiguration::innerMapMethodHandleInvokeExact)
    }

    @Benchmark
    open fun memoryConfigToMapConvertionIterative_methodHandleUnreflect(holder: ConfigHolder): Map<String, Any> {
        return memoryToMapIter(holder.config, MemoryConfiguration::innerMapMethodHandleUnreflectInvokeExact)
    }

    @Benchmark
    open fun memoryConfigToMapConvertionIterative_cachedMethodHandleInvoke(holder: ConfigHolder): Map<String, Any> {
        return memoryToMapIter(holder.config, MemoryConfiguration::innerMapCachedMethodHandleInvoke)
    }

    @Benchmark
    open fun memoryConfigToMapConvertionIterative_cachedMethodHandleInvokeExact(holder: ConfigHolder): Map<String, Any> {
        return memoryToMapIter(holder.config, MemoryConfiguration::innerMapCachedMethodHandleInvokeExact)
    }

    @Benchmark
    open fun memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvoke(holder: ConfigHolder): Map<String, Any> {
        return memoryToMapIter(holder.config, MemoryConfiguration::innerMapCachedMethodHandleUnreflectInvoke)
    }

    @Benchmark
    open fun memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvokeExact(holder: ConfigHolder): Map<String, Any> {
        return memoryToMapIter(holder.config, MemoryConfiguration::innerMapCachedMethodHandleUnreflectInvokeExact)
    }

    @State(Scope.Thread)
    open class ConfigHolder {

        lateinit var config: MemoryConfiguration

        @Setup(Level.Iteration)
        fun shuffleConfig() {
            config = newShuffledMap(15)
        }

        private fun newShuffledMap(nestedLevel: Int): MemoryConfiguration {
            val map = newShuffledMap()
            repeat(nestedLevel) { outerLvl ->
                val newShuffledMap2 = newShuffledMap()
                map.apply { set("outer$outerLvl", newShuffledMap2) }
                var currentNestedMap = newShuffledMap2
                repeat(nestedLevel) { innerLvl ->
                    val newNestedMap = newShuffledMap()
                    currentNestedMap.set(innerLvl.toString(), newNestedMap)
                    currentNestedMap = newNestedMap
                }
            }
            return map
        }

        private fun newShuffledMap(): MemoryConfiguration = mapOf(
            "number" to 1,
            "test" to "one",
            "nested" to mapOf(
                "2nd" to "yes",
                "number" to 1,
                "emptyMap" to emptyMap<String, String>(),
                "nested" to mapOf(
                    "number" to 1,
                    "final" to "aaaaaaa",
                    "list" to listOf("a", "b", "c"),
                    "emptyMap" to emptyMap<String, String>(),
                ).shuffled()
            ).shuffled(),
            "someList" to listOf("", "a", "b", "c"),
            "emptyMap" to emptyMap<String, String>(),
        ).shuffled()
            .let(::mapToMemoryRec)
    }
}

fun <K, V> Map<K, V>.shuffled(): Map<K, V> = toList().shuffled().toMap()

fun mapToMemoryRec(map: Map<*, *>): MemoryConfiguration {
    val config = MemoryConfiguration()
    map.forEach { (key, value) ->
        if (value == null) return@forEach
        config.set(key.toString(), if (value is Map<*, *>) mapToMemoryRec(value) else value)
    }
    return config
}

private fun memoryToMapRec(config: MemoryConfiguration): Map<String, Any> {
    val map = mutableMapOf<String, Any>()
    for (key in config.getKeys(false)) {
        val value = config.get(key) ?: continue
        map[key] = if (value is MemoryConfiguration) memoryToMapRec(value) else value
    }
    return map
}

private fun memoryToMapIter(config: MemoryConfiguration, innerMap: MemoryConfiguration.() -> Map<String, Any?>): Map<String, Any> {
    var map = mutableMapOf<String, Any>()
    var iterator = config.innerMap().iterator()
    val stack = ArrayDeque<StackEntry<String, Any>>()
    while (iterator.hasNext()) {
        val entry = iterator.next()
        val value = entry.value ?: continue
        if (value is MemoryConfiguration) {
            // suspend current iteration
            stack += StackEntry(entry.key, map, iterator)
            // reset iteration
            map = mutableMapOf()
            iterator = value.innerMapReflection.iterator()
        } else {
            map[entry.key] = value
        }
        // resume last iteration from where it stopped
        while (!iterator.hasNext() && stack.isNotEmpty()) {
            val stackEntry = stack.removeLast()
            val currentMap = map
            map = stackEntry.map.also { it[stackEntry.key] = currentMap }
            iterator = stackEntry.iterator
        }
    }
    return map
}

data class StackEntry<K, V>(
    val key: String,
    val map: MutableMap<K, V>,
    val iterator: Iterator<Map.Entry<K, V?>>,
)

@Suppress("UNCHECKED_CAST")
private val MemoryConfiguration.innerMapReflection: Map<String, Any?>
    get() = MemorySection::class.java.getDeclaredField("map")
        .apply { isAccessible = true }
        .get(this) as Map<String, Any?>

private val MEMORY_SECTION_MAP = MemorySection::class.java.getDeclaredField("map")
    .apply { isAccessible = true }

@Suppress("UNCHECKED_CAST")
private val MemoryConfiguration.innerMapCachedReflection: Map<String, Any?>
    get() = MEMORY_SECTION_MAP.get(this) as Map<String, Any?>

private val lookup = MethodHandles.lookup()

private fun getMapMethodHandleDirect(): ConstantCallSite {
    val handle = MethodHandles.privateLookupIn(MemorySection::class.java, lookup)
        .findVarHandle(MemorySection::class.java, "map", Map::class.java)
        .toMethodHandle(VarHandle.AccessMode.GET)
    return ConstantCallSite(handle)
}

private fun getMapMethodHandleUnreflect(): ConstantCallSite {
    val handle = MethodHandles.privateLookupIn(MemorySection::class.java, lookup)
        .unreflectVarHandle(MEMORY_SECTION_MAP)
        .toMethodHandle(VarHandle.AccessMode.GET)
    return ConstantCallSite(handle)
}

@Suppress("UNCHECKED_CAST")
private val MemoryConfiguration.innerMapMethodHandleInvokeExact: Map<String, Any?>
    get() = getMapMethodHandleDirect().target.bindTo(this).invokeExact() as Map<String, Any?>

@Suppress("UNCHECKED_CAST")
private val MemoryConfiguration.innerMapMethodHandleUnreflectInvokeExact: Map<String, Any?>
    get() = getMapMethodHandleUnreflect().target.bindTo(this).invokeExact() as Map<String, Any?>

private val METHOD_HANDLE_MAP_DIRECT = getMapMethodHandleDirect()

@Suppress("UNCHECKED_CAST")
private val MemoryConfiguration.innerMapCachedMethodHandleInvoke: Map<String, Any?>
    get() = METHOD_HANDLE_MAP_DIRECT.target.bindTo(this).invoke() as Map<String, Any?>

@Suppress("UNCHECKED_CAST")
private val MemoryConfiguration.innerMapCachedMethodHandleInvokeExact: Map<String, Any?>
    get() = METHOD_HANDLE_MAP_DIRECT.target.bindTo(this).invokeExact() as Map<String, Any?>

private val METHOD_HANDLE_MAP_UNREFLECTED = getMapMethodHandleUnreflect()

@Suppress("UNCHECKED_CAST")
private val MemoryConfiguration.innerMapCachedMethodHandleUnreflectInvoke: Map<String, Any?>
    get() = METHOD_HANDLE_MAP_UNREFLECTED.target.bindTo(this).invoke() as Map<String, Any?>

@Suppress("UNCHECKED_CAST")
private val MemoryConfiguration.innerMapCachedMethodHandleUnreflectInvokeExact: Map<String, Any?>
    get() = METHOD_HANDLE_MAP_UNREFLECTED.target.bindTo(this).invokeExact() as Map<String, Any?>
