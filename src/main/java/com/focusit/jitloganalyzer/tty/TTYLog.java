package com.focusit.jitloganalyzer.tty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.focusit.jitloganalyzer.tty.model.TTYEvent;
import com.focusit.jitloganalyzer.tty.model.TTYEventFactory;

/**
 * Created by doki on 08.11.16.
 */
public class TTYLog
{
    private boolean started = false;

    private List<TTYEvent> events = new ArrayList<>();

    public void parseLog(String filename) throws IOException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
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
                    events.add(event);
                }

                if (started && line.toLowerCase().equals("</tty>"))
                {
                    break;
                }

            }
        }
    }

    public Collection<TTYEvent> getEventLog()
    {
        return Collections.unmodifiableList(events);
    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("JIT LOG TTY Events");

        TTYLog log = new TTYLog();

        log.parseLog(args[0]);

        System.out.println("Done. Got " + log.getEventLog().size() + " events");
    }
}
