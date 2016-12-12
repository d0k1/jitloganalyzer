package com.focusit.jitloganalyzer.compilation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by doki on 11.12.16.
 */
public class CompilationLog
{
    public static void main(String[] args)
    {

    }

    public void parseLog(String filename) throws IOException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            for (String line; (line = br.readLine()) != null;)
            {
            }
        }
    }
}
