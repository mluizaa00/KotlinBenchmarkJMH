## Java 17 (OpenJDK)

### Run 1

| Benchmark                                                                   | Mode | Cnt | Score      | Error       | Units | Best |
|-----------------------------------------------------------------------------|------|-----|------------|-------------|-------|:----:|
| memoryConfigToMapConvertionIterative_cachedMethodHandleInvoke               | avgt | 20  | 175457.378 | ± 11689.468 | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedMethodHandleInvokeExact          | avgt | 20  | 167097.182 | ± 9783.022  | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvoke      | avgt | 20  | 169639.728 | ± 6237.830  | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvokeExact | avgt | 20  | 166409.804 | ± 6704.688  | ns/op |  ⭐   |
| memoryConfigToMapConvertionIterative_cachedReflection                       | avgt | 20  | 163747.453 | ± 11568.175 | ns/op |      |
| memoryConfigToMapConvertionIterative_methodHandle                           | avgt | 20  | 196151.632 | ± 25711.382 | ns/op |      |
| memoryConfigToMapConvertionIterative_methodHandleUnreflect                  | avgt | 20  | 180189.502 | ± 4593.218  | ns/op |      |
| memoryConfigToMapConvertionIterative_reflection                             | avgt | 20  | 169185.900 | ± 3308.274  | ns/op |      |
| memoryConfigToMapConvertionRecursive                                        | avgt | 20  | 506558.556 | ± 12931.354 | ns/op |      |

### Run 2

| Benchmark                                                                   | Mode | Cnt | Score      | Error       | Units | Best |
|-----------------------------------------------------------------------------|------|-----|------------|-------------|-------|:----:|
| memoryConfigToMapConvertionIterative_cachedMethodHandleInvoke               | avgt | 15  | 168515.522 | ± 10004.364 | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedMethodHandleInvokeExact          | avgt | 15  | 169025.042 | ± 6196.142  | ns/op |  ⭐   |
| memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvoke      | avgt | 15  | 172541.367 | ± 9696.825  | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvokeExact | avgt | 15  | 189092.083 | ± 18305.132 | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedReflection                       | avgt | 15  | 182279.441 | ± 10714.146 | ns/op |      |
| memoryConfigToMapConvertionIterative_methodHandle                           | avgt | 15  | 180641.404 | ± 15033.021 | ns/op |      |
| memoryConfigToMapConvertionIterative_methodHandleUnreflect                  | avgt | 15  | 189940.452 | ± 18943.954 | ns/op |      |
| memoryConfigToMapConvertionIterative_reflection                             | avgt | 15  | 186615.716 | ± 4042.300  | ns/op |      |
| memoryConfigToMapConvertionRecursive                                        | avgt | 15  | 522933.864 | ± 59566.008 | ns/op |      |


## Java 17 (GraalVM):

### Run 1

| Benchmark                                                                   | Mode | Cnt | Score      | Error       | Units | Best |
|-----------------------------------------------------------------------------|------|-----|------------|-------------|-------|:----:|
| memoryConfigToMapConvertionIterative_cachedMethodHandleInvoke               | avgt | 15  | 175180.765 | ± 8172.567  | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedMethodHandleInvokeExact          | avgt | 15  | 164590.022 | ± 5907.877  | ns/op |  ⭐   |
| memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvoke      | avgt | 15  | 175134.670 | ± 7318.562  | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvokeExact | avgt | 15  | 174628.625 | ± 7446.566  | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedReflection                       | avgt | 15  | 165792.819 | ± 5837.541  | ns/op |      |
| memoryConfigToMapConvertionIterative_methodHandle                           | avgt | 15  | 168716.172 | ± 7732.671  | ns/op |      |
| memoryConfigToMapConvertionIterative_methodHandleUnreflect                  | avgt | 15  | 168881.662 | ± 6921.324  | ns/op |      |
| memoryConfigToMapConvertionIterative_reflection                             | avgt | 15  | 171802.140 | ± 7927.458  | ns/op |      |
| memoryConfigToMapConvertionRecursive                                        | avgt | 15  | 467991.905 | ± 20480.451 | ns/op |      |

### Run 2

| Benchmark                                                                   | Mode | Cnt | Score      | Error       | Units | Best |
|-----------------------------------------------------------------------------|------|-----|------------|-------------|-------|:----:|
| memoryConfigToMapConvertionIterative_cachedMethodHandleInvoke               | avgt | 15  | 175180.765 | ± 8172.567  | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedMethodHandleInvokeExact          | avgt | 15  | 164590.022 | ± 5907.877  | ns/op |  ⭐   |
| memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvoke      | avgt | 15  | 175134.670 | ± 7318.562  | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedMethodHandleUnreflectInvokeExact | avgt | 15  | 174628.625 | ± 7446.566  | ns/op |      |
| memoryConfigToMapConvertionIterative_cachedReflection                       | avgt | 15  | 165792.819 | ± 5837.541  | ns/op |      |
| memoryConfigToMapConvertionIterative_methodHandle                           | avgt | 15  | 168716.172 | ± 7732.671  | ns/op |      |
| memoryConfigToMapConvertionIterative_methodHandleUnreflect                  | avgt | 15  | 168881.662 | ± 6921.324  | ns/op |      |
| memoryConfigToMapConvertionIterative_reflection                             | avgt | 15  | 171802.140 | ± 7927.458  | ns/op |      |
| memoryConfigToMapConvertionRecursive                                        | avgt | 15  | 467991.905 | ± 20480.451 | ns/op |      |

## Summary

This benchmark was initially done to compare performance of a recursive vs iterable algorithm, but it ended up being much more than that since when I realized I would need to use reflection to perform access a specific field of the `MemoryConfiguration` class, which was when I realized I would use this to benchmark the various methods of accessing a private field of an object.

On OpenJDK JVM, the timing were fluctuating a bit as can be seen on the two runs I recorded, so conclusion can be taken from these tests except iterative version is faster than the recursive one.  On GraalVM though the timings were much more consistent, with `Iterative` `cachedMethodHandleInvokeExact` variant almost always coming on top.

## Versions

| Dependency | Version           |
|------------|-------------------|
| Kotlin     | 1.7.20            |
| Paper      | 1.17.1 (rev. 397) |
