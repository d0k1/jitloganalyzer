package com.focusit.jitloganalyzer.tty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.focusit.jitloganalyzer.tty.model.TTYEvent;

/**
 * Created by doki on 08.11.16.
 */
public class TTYLog
{
    private List<TTYEvent> events = new ArrayList<>();

    public void parseLog(String filename)
    {

    }

    public static void main(String[] args) throws IOException
    {
        System.out.println("JIT LOG TTY Events");

        boolean started = false;

        try (BufferedReader br = new BufferedReader(new FileReader(args[0])))
        {
            for (String line; (line = br.readLine()) != null;)
            {
                if (!started && line.toLowerCase().equals("<tty>"))
                {
                    started = true;
                    continue;
                }

                if (started && line.toLowerCase().equals("</tty>"))
                {
                    break;
                }

            }
        }

        System.out.println("Done");

        Exception e = new Exception();
        e.printStackTrace(System.err);
    }
}
