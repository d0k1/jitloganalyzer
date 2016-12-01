/**
 * This sample walks through the parsed JIT log and collects method's compilation data.
 * After that, script sorts result by compilations count in descending mode.
 *
 * It means that at the end, script returns a map, that contains method names that starts with filter('ru/naumen')
 * and first entry is the most compiled method.
 *
 * There are some variables available in script to make more complex analyze:
 *  *  ttylog - list that contains all tty log events
 *  *  classloading - list of all com.focusit.jitloganalyzer.tty.model.ClassLoadEvent
 *  *  jitcompilations - map that has method compilation id as a key, and a list of events associated with specific compilation id as a value
 *  *  sweeping - list of all com.focusit.jitloganalyzer.tty.model.SweeperEvent
 *  *  methodCompilations - map that has method name as a key and list of compilation id associated with specific methd as a value
 */

def result = [:]
def filter = "ru/naumen"

methodCompilations.each { entry ->
    if (entry.getKey().startsWith(filter) && entry.getValue().size() > 4) {
        result.put(entry.getKey(), entry.getValue())
    }
}

result = result.sort { -it.value.size() };

return result

