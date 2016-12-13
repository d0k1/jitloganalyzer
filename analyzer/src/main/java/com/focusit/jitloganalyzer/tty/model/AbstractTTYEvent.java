package com.focusit.jitloganalyzer.tty.model;

import java.util.*;

/**
 * Created by dkirpichenkov on 30.11.16.
 */
public abstract class AbstractTTYEvent implements TTYEvent
{
    protected Map<String, Object> attributes = new HashMap<>();

    @Override
    public void setPreviousEvent(TTYEvent event)
    {

    }

    public double getStamp()
    {
        return Double.parseDouble(((String)attributes.get("stamp")).replace(",", "."));
    }

    public Map<String, Object> getAttributes(String line)
    {
        line = line.replace("<", "");
        line = line.replace("/>", "");
        line = line.replace(">", "");
        HashMap<String, Object> map = new HashMap<>();

        List<String> list = new ArrayList<>(Arrays.asList(line.split(" ")));
        list.remove(0);

        List<String> attr = new ArrayList<>();

        while (!list.isEmpty())
        {
            String item = list.get(0);
            if (item.endsWith("'"))
            {
                attr.add(item);
                list.remove(item);
            }
            else
            {
                int pos = 0;
                list.remove(item);
                while (!item.endsWith("'") && !list.isEmpty())
                {
                    item += " " + list.get(pos);
                    list.remove(0);
                }
                attr.add(item);
            }
        }

        attr.forEach(item -> {
            String[] attrs = item.split("=");
            if (attrs.length > 1)
                map.put(sanitizeAttr(attrs[0]), sanitizeValue(attrs[1]));
        });
        return map;
    }

    private String sanitizeValue(String value)
    {
        String result = value;
        if (result.startsWith("'"))
        {
            result = result.substring(1);
        }

        if (result.endsWith("'"))
        {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private String sanitizeAttr(String attr)
    {
        return attr;
    }

    @Override
    public void processLine(String line)
    {
        this.attributes = getAttributes(line);
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + "{" + "attributes=" + attributes + '}';
    }
}
