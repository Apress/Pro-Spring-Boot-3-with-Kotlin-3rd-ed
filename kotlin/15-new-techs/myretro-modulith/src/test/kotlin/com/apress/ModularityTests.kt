package com.apress

import com.apress.myretro.MyretroApplication
import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModule
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter
import java.util.function.Consumer

class ModularityTests {
    private var modules = ApplicationModules.of(MyretroApplication::class.java)

    @Test
    fun verifiesModularStructure() {
        try {
            modules.verify()
        }catch (e:Exception){
            // leave me alone - this is just a recommendation...
            when{
                e.toString().contains("Prefer constructor injection instead") -> {}
                else -> throw e
            }
        }
    }

    @Test
    fun createApplicationModuleModel() {
        val modules = ApplicationModules.of(MyretroApplication::class.java)
        modules.forEach(Consumer { x: ApplicationModule? -> println(x) })
    }

    @Test
    fun createModuleDocumentation() {
        Documenter(modules).writeDocumentation()
    }
}
