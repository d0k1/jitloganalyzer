package com.focusit.jitloganalyzer.tty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.focusit.jitloganalyzer.tty.model.*;

/**
 * Created by doki on 08.11.16.
 */
public class TTYLog
{
    public Map<Class, Set<String>> eventAttributes = new HashMap<>();
    public List<TTYEvent> events = new ArrayList<>();
    public List<ClassLoadEvent> classLoading = new ArrayList<>();
    public HashMap<Long, List<TTYEvent>> queued_tasks = new HashMap<>();
    public List<SweeperEvent> sweeping = new ArrayList<>();
    public HashMap<String, List<Long>> methodCompilations = new HashMap<>();
    private boolean started = false;
    private Comparator<TTYEvent> ttyEventComparatorByStampAsc = (o1, o2) -> {
        if (o1 instanceof HasStamp && o2 instanceof HasStamp)
        {
            double diff = (((HasStamp)o2).getStamp() - ((HasStamp)o1).getStamp());
            int result = 0;
            if (diff > 0)
                result = -1;
            if (diff < 0)
                result = +1;

            return result;
        }
        return -1;
    };

    public void parseLog(String filename) throws IOException
    {
        events.clear();
        eventAttributes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename), 64 * 1024 * 1024))
        {
            TTYEvent prevEvent = null;
            for (String line; (line = br.readLine()) != null;)
            {
                if (!started && line.toLowerCase().equals("<tty>"))
                {
                    started = true;
                    continue;
                }

                TTYEvent event = TTYEventFactory.getEventForString(line);

                if (event != null)
                {
                    event.processLine(line);
                    if (prevEvent != null)
                    {
                        event.setPreviousEvent(prevEvent);
                    }
                    if (event instanceof AbstractTTYEvent)
                    {
                        Set<String> attrs = eventAttributes.get(event.getClass());
                        if (attrs == null)
                        {
                            attrs = new HashSet<>();
                            eventAttributes.put(event.getClass(), attrs);
                        }
                        attrs.addAll(((AbstractTTYEvent)event).getAttributes(line).keySet());
                    }
                    events.add(event);
                    prevEvent = event;
                }

                if (started && line.toLowerCase().equals("</tty>"))
                {
                    break;
                }

            }
        }
    }

    public void fillClassLoading()
    {
        classLoading.clear();
        events.forEach(event -> {
            if (event instanceof ClassLoadEvent)
            {
                classLoading.add((ClassLoadEvent)event);
            }
        });

        classLoading.sort(ttyEventComparatorByStampAsc);
    }

    public void fillJitCompilations()
    {
        queued_tasks.clear();
        events.forEach(event -> {
            if (event instanceof NMethodEvent || event instanceof TaskQueuedEvent || event instanceof UncommonTrapEvent
                    || event instanceof MakeNotEntrantEvent)
            {
                HasCompileId hci = (HasCompileId)event;
                if (queued_tasks.get(hci.getCompileId()) == null)
                {
                    queued_tasks.put(hci.getCompileId(), new ArrayList<>());
                }
                List<TTYEvent> compilationEvents = queued_tasks.get(hci.getCompileId());
                compilationEvents.add(event);
                compilationEvents.sort(ttyEventComparatorByStampAsc);
            }
        });
    }

    public void fillSweeping()
    {
        sweeping.clear();
        events.forEach(event -> {
            if (event instanceof SweeperEvent)
            {
                sweeping.add((SweeperEvent)event);
            }
        });
        sweeping.sort(ttyEventComparatorByStampAsc);
    }

    public void fillMethodCompilations()
    {
        methodCompilations.clear();
        events.forEach(event -> {
            if (event instanceof TaskQueuedEvent)
            {
                String method = ((HasMethod)event).getMethod();
                List<Long> compilations = methodCompilations.get(method);
                if (compilations == null)
                {
                    compilations = new ArrayList<>();
                    methodCompilations.put(method, compilations);
                }
                compilations.add(((HasCompileId)event).getCompileId());
            }
        });
    }
}
