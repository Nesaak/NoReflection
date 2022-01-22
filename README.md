__**NoReflection**__

**What is it?**
NoReflection is a library available to use that aims to make reflective java operations just as fast as inline java code. This means that you can convert expensive reflection calls into NoReflection calls which will use much less resources.

**Why Use it?**
Java Reflection is expensive.  Calls in the Reflection API can cost anywhere from 2x to 50x more resources than compiled java code. Reflection is super useful however, especially when working inside of Bukkit and you need to access methods not accessible via the Bukkit API. NoReflection provides you an alternative to give you the abilities of reflection without the high cost of traditional reflection. 

**(Rough) Benchmarks**
All benchmarks achieved using GraalVM, OpenJDK 13 on OSX.
```java
Running 100000000 trials to benchmark "method invoke speed"
Comparing method points: ["Inline", "Reflection", "NoReflection"]
Inline took 41ms, average: 0.41722375ns/call
Reflection took 471ms, average: 4.7178881ns/call
NoReflection took 34ms, average: 0.34594272ns/call

Running 100000000 trials to benchmark "constructor invoke speed"
Comparing method points: ["Inline", "Reflection", "NoReflection"]
Inline took 318ms, average: 3.18014419ns/call
Reflection took 1438ms, average: 14.38783877ns/call
NoReflection took 244ms, average: 2.44636244ns/call

Running 100000000 trials to benchmark "field get speed"
Comparing method points: ["Inline", "Reflection", "NoReflection"]
Inline took 10ms, average: 0.10023419ns/call
Reflection took 427ms, average: 4.27518689ns/call
NoReflection took 4ms, average: 0.04495969ns/call

Running 100000000 trials to benchmark "field set speed"
Comparing method points: ["Inline", "Reflection", "NoReflection"]
Inline took 134ms, average: 1.3494477ns/call
Reflection took 698ms, average: 6.9854564ns/call
NoReflection took 157ms, average: 1.57177489ns/call
```

**How To Use It**

**Step 1: Depend on NoReflection**
You can use JitPack in order to use NoReflection in your projects. Here is an example of how to do that using maven:

```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>```

```xml
<dependency>
  <groupId>com.github.Nesaak</groupId>
  <artifactId>NoReflection</artifactId>
  <version>b9fd2940dc <!-- Replace the version with the latest commit on github--> </version>
</dependency>
```

You may also clone the github repo and install NoReflection to your local m2.

**Step 2: Use NoReflection**
The starting point to use NoReflection is the NoReflection class, you can use the shared instance by doing `NoReflection.shared()` or create your own instance. You do not need to create your own instance unless you are having collision problems with other services, this will not be a problem for *most* people

Once you have your instance of NoReflection, you may then start using it to generate accessors by providing it with Reflection objects. You can use NoReflection similar to how you would use Reflection, except you must first pass the class member objects through a NoReflection instance.

NoReflection supports 3 members at the moment:
```
java.util.reflect.Field => com.nesaak.noreflection.access.FieldAccess
java.util.reflect.Method => com.nesaak.noreflection.access.DynamicCaller
java.util.reflect.Constructor => com.nesaak.noreflection.access.DynamicCaller
```
The method NoReflection#get provides you with these NoReflection access objects, you simply call the #get() method inputting a Constructor, Method or Field and NoReflection will return the appropriate access object.

**Examples:**

Here is some example reflection code and how you would write it with NoReflection:
Given:
```java
Class CRAFT_PLAYER_CLASS = getBukkitClass("CraftPlayer");
Class NMS_PLAYER_CLASS = getNMSClass("EntityPlayer");
Method GET_HANDLE = CRAFT_PLAYER_CLASS.getMethod("getHandle");
Field CONNECTION_FIELD = NMS_PLAYER_CLASS.getField("playerConnection");
```

Reflection:
```java
private Object getConnection(Player player) {
  Object nmsPlayer = GET_HANDLE.invoke(player);
  return CONNECTION_FIELD.get(nmsPlayer);
}
```

NoReflection:
```java
DynamicCaller NF_GET_HANDLE = NoReflection.shared().get(GET_HANDLE);
FieldAccess NF_CONNECTION_FIELD = NoReflection.shared().get(CONNECTION_FIELD);

private Object getConnection(Player player) {
  Object nmsPlayer = NF_GET_HANDLE.call(player);
  return NF_CONNECTION_FIELD.get(nmsPlayer);
}
```

See? There is almost no change, NoReflection is designed to be used similarly to normal reflection.

**Notes**
- If you would like NoReflection to be performant you **MUST** cache the access instances it gives to you. So if you would like to reflect a particular method/field/constructor, get the access object once and store it in a static final field for whenever you need it.
- NoReflection does **NOT** modify final fields, this is the one thing it cannot do that traditional reflection can. 
- NoReflection performs illegal reflection operations, this is not uncommon for spigot plugins but newer versions of java with certain security settings may prevent NoReflection from working (however, traditional reflection would also fail too)
- NoReflection is a public project and may be unstable, it makes significant use of `Unsafe` which is, like the name implies, unsafe. 
- NoReflection welcomes contributions, feel free to make a PR on our github or report an issue
