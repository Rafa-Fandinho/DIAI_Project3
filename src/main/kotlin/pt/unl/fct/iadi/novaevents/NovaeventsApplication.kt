package pt.unl.fct.iadi.novaevents

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NovaeventsApplication

fun main(args: Array<String>) {
	runApplication<NovaeventsApplication>(*args)
}

//		spring.mvc.hiddenmethod.filter.enabled=true Slide 41 says to add this to application.properties