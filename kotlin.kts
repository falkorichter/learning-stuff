import kotlin.properties.Delegates
import kotlin.reflect.KProperty

//https://kotlinlang.org/docs/tutorials/quick-run.html on how to run scratches

open class AClass {
    open fun someFunction() {
        println("SomeClass : some Function\n\n\n")
    }
}

class UsageClass {

    fun useSomeClass(someClassObj: AClass) {
        someClassObj.someFunction()
    }
}
val usageClass = UsageClass()

usageClass.useSomeClass(AClass())

usageClass.useSomeClass(object : AClass() {

    override fun someFunction() {
        super.someFunction();
        System.out.println("UsageClass : overridden some Function");
    }
})


abstract class AAbstractClass {
    internal abstract fun abstractDoSomething()

    fun iAmDoingSomething() {
        println("AAbstractClass : I am doing something")
    }
}

interface IInterface {
    fun canIDoSomethingInInterface()
}

class UsageOfAAbstractClass {

    fun useAbstractClass(abstractClassObj: AAbstractClass) {

        abstractClassObj.abstractDoSomething()
        abstractClassObj.iAmDoingSomething()
    }

    fun useInterface(iInterfaceObject: IInterface) {
        iInterfaceObject.canIDoSomethingInInterface()
    }

    fun useSomeClass(someClassObj: AClass) {
        someClassObj.someFunction()
    }
}

val usageOfAbstractClass = UsageOfAAbstractClass()
usageOfAbstractClass.useAbstractClass(object : AAbstractClass() {
    override fun abstractDoSomething() {
        println("UsageClass : I am doing something which was not defined in the abstract class")
    }
})

usageOfAbstractClass.useInterface(object : IInterface {
    override fun canIDoSomethingInInterface() {
        println("UsageClass : can I Do Something which was not defined in the interface")
    }
})

val justAnObject = object {
    var x = 10
    var y = 20
    fun sum(): Int = x + y
}

//doesn`t work
//println(justAnObject.sum())

open class BClass(val x: Int){
    open val y = 20
}


val bClassObj : BClass = object : BClass(10), IInterface {

    override val y: Int = 30

    override fun canIDoSomethingInInterface() {
        println("bClassObj : can I do something")
    }

}

println("bClass has a y with value: $bClassObj.y")

//this doesn´t work (yet) of course
//bClassObj.anExtentionFunction()

fun BClass.anExtentionFunction(){
    println("I can print my y value from within: $y")
}

bClassObj.anExtentionFunction()

fun String.splitCamelCase(): String {
    return replace(
            String.format("%s|%s|%s",
                    "(?<=[A-Z])(?=[A-Z][a-z])",
                    "(?<=[^A-Z])(?=[A-Z])",
                    "(?<=[A-Za-z])(?=[^A-Za-z])"
            ).toRegex(),
            " "
    )
}

println("ConvertThisCamelCase".splitCamelCase())


println("something elseFoo bar".splitCamelCase())

println("something entirely")


class Delegate {
    var localProperty : String = ""
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return localProperty
        //return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
        localProperty = value
    }
}

class Example {
    var p: String by Delegate()
}

val example = Example()

println("example to string ${example}")

println("p is set to \"${example.p}\"")

example.p = "over 100"

println("p is set to \"${example.p}\" now")


val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}


println(lazyValue)
println(lazyValue)


class User {
    var name: String by Delegates.observable("<no name>") {
        prop, old, new ->
        println("$old -> $new")
    }
}
val user = User()
user.name = "first"
user.name = "second"

var max: Int by Delegates.notNull()

// println(max) // will fail with IllegalStateException

max = 10
println(max) // 10
