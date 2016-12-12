def result = [:]
def filter = "ru/naumen"

methodCompilations.each { entry ->
    if (entry.getKey().startsWith(filter)) {
        result.put(entry.getKey(), entry.getValue().get(entry.getValue().size() - 1))
    }
}

def notEntrant = [:]

result.each { entry ->
    def comps = jitcompilations.get(entry.getValue())
    if (comps.get(comps.size() - 1).getClass().getName().contains("MakeNotEntrantEvent")) {
        notEntrant.put(entry.getKey(), entry.getValue())
    }
}

return notEntrant
