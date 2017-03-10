package com.densev.multimodule.kot

import com.densev.multimodule.kot.army.HorseArchers
import com.google.common.base.Stopwatch
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Dzianis_Sevastseyenk on 01/03/2017.
 */
class App {

    companion object {

        val logger = LoggerFactory.getLogger(App::class.java)

        @JvmStatic fun main(args: Array<String>) {

            var stopwatch: Stopwatch = Stopwatch.createStarted()

            var kingdom: Kingdom = Kingdom("Orc kingdom")
            var ha: HorseArchers = HorseArchers()

            ha.counter = 10
            stopwatch.stop()
            logger.info("run for {} milliseconds", stopwatch.elapsed(TimeUnit.MILLISECONDS))
        }

    }

    fun task1(collection: Collection<Int>): String {
        val sb = StringBuilder()
        sb.append("{")
        val iterator = collection.iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            sb.append(element)
            if (iterator.hasNext()) {
                sb.append(", ")
            }
        }
        sb.append("}")
        return sb.toString()
    }
}


