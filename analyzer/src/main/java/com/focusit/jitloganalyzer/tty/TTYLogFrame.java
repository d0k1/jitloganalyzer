package com.focusit.jitloganalyzer.tty;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import javax.swing.*;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.focusit.jitloganalyzer.tty.model.ClassLoadEvent;
import com.focusit.jitloganalyzer.tty.model.SweeperEvent;
import com.focusit.jitloganalyzer.tty.model.TTYEvent;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * Created by dkirpichenkov on 30.11.16.
 */
public class TTYLogFrame extends JFrame
{
    private JPanel rootPanel;
    private JButton runButton;
    private org.fife.ui.rsyntaxtextarea.RSyntaxTextArea textArea1;
    private JSplitPane splitPane;
    private JPanel scriptPanel;
    private JPanel logPanel;
    private JTextArea logArea;
    private JButton parseButton;
    private RTextScrollPane scrollPane1;
    private GroovyShell shell = new GroovyShell();
    private List<TTYEvent> events;
    private List<ClassLoadEvent> classLoading;
    private HashMap<Long, List<TTYEvent>> jitCompilations;
    private List<SweeperEvent> sweeping;
    private HashMap<String, List<Long>> methodCompilations;
    private Function<Void, Void> parseFunction;

    public TTYLogFrame(java.util.List<TTYEvent> events, List<ClassLoadEvent> classLoading,
            HashMap<Long, List<TTYEvent>> jitCompilations, List<SweeperEvent> sweeping,
            HashMap<String, List<Long>> methodCompilations, Function<Void, Void> parseFunction) throws HeadlessException
    {
        super("Jitloganalyzer: TTY");
        this.events = events;
        this.classLoading = classLoading;
        this.jitCompilations = jitCompilations;
        this.sweeping = sweeping;
        this.methodCompilations = methodCompilations;
        this.parseFunction = parseFunction;
        setContentPane(rootPanel);
        createScriptArea();
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
        logArea.setFont(new Font("Hack", Font.PLAIN, 16));

        runButton.addActionListener(e -> {
            Binding binding = new Binding();
            binding.setVariable("ttylog", events);
            binding.setVariable("classloading", classLoading);
            binding.setVariable("jitcompilations", jitCompilations);
            binding.setVariable("sweeping", sweeping);
            binding.setVariable("methodCompilations", methodCompilations);
            Script script = shell.parse(textArea1.getText());
            script.setBinding(binding);
            try
            {
                Object result = script.run();
                if (result == null)
                {
                    logArea.setText("");
                }
                else
                {
                    logArea.setText(result.toString());
                }
            }
            catch (Throwable ex)
            {
                Writer out = new StringWriter();
                PrintWriter pw = new PrintWriter(out);
                ex.printStackTrace(pw);
                logArea.setText(ex.toString() + "\n\n" + out.toString());
                throw ex;
            }
        });
        parseButton.addActionListener(e -> parseFunction.apply(null));
    }

    private void createScriptArea()
    {
        scrollPane1 = new RTextScrollPane();
        textArea1 = new RSyntaxTextArea();
        scrollPane1.setViewportView(textArea1);

        textArea1.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY);
        textArea1.getFoldManager().setCodeFoldingEnabled(true);
        textArea1.setFont(new Font("Hack", Font.PLAIN, 16));
        textArea1.setRows(3);
        textArea1.setMarkOccurrences(true);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        scrollPane1.setLineNumbersEnabled(true);
        scrollPane1.setFoldIndicatorEnabled(true);
        scriptPanel.add(scrollPane1);
    }
}
