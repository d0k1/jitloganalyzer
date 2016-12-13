package com.focusit.experiments.fqn;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dkirpichenkov on 13.12.16.
 */
public final class FastFqn implements Comparable<FastFqn>, Serializable
{

    /**
     * Separator between FQN elements.
     */
    public static final String SEPARATOR = "/";
    /**
     * Immutable root Fqn.
     */
    public static final FastFqn ROOT = new FastFqn();
    private static final Object[] EMPTY_ARRAY = new Object[0];
    private final Object[] elements;
    /**
     * A cached string representation of this Fqn, used by toString to it isn't calculated again every time.
     */
    protected String stringRepresentation;
    private transient int hash_code = 0;

    // ----------------- START: Private constructors for use by factory methods only. ----------------------

    private FastFqn(Object... elements)
    {
        this.elements = elements;
    }

    /**
     * If safe is false, Collections.unmodifiableList() is used to wrap the list passed in.  This is an optimisation so
     * Fqn.fromString(), probably the most frequently used factory method, doesn't end up needing to use the
     * unmodifiableList() since it creates the list internally.
     *
     * @param names List of names
     */
    private FastFqn(List<?> names)
    {
        if (names != null)
            elements = names.toArray();
        else
            elements = EMPTY_ARRAY;
    }

    private FastFqn(FastFqn base, Object... relative)
    {
        elements = new Object[base.elements.length + relative.length];
        System.arraycopy(base.elements, 0, elements, 0, base.elements.length);
        System.arraycopy(relative, 0, elements, base.elements.length, relative.length);
    }

    // ----------------- END: Private constructors for use by factory methods only. ----------------------

    /**
     * Retrieves an Fqn that represents the list of elements passed in.
     *
     * @param names list of elements that comprise the Fqn
     * @return an Fqn
     * @since 4.0
     */
    public final static FastFqn fromList(List<?> names)
    {
        return new FastFqn(names);
    }

    /**
     * Retrieves an Fqn that represents the array of elements passed in.
     *
     * @param elements array of elements that comprise the Fqn
     * @return an Fqn
     * @since 4.0
     */
    public final static FastFqn fromElements(Object... elements)
    {
        Object[] copy = new Object[elements.length];
        System.arraycopy(elements, 0, copy, 0, elements.length);
        return new FastFqn(copy);
    }

    /**
     * Retrieves an Fqn that represents the absolute Fqn of the relative Fqn passed in.
     *
     * @param base     base Fqn
     * @param relative relative Fqn
     * @return an Fqn
     * @since 4.0
     */
    public final static FastFqn fromRelativeFqn(FastFqn base, FastFqn relative)
    {
        return new FastFqn(base, relative.elements);
    }

    /**
     * Retrieves an Fqn that represents the List<Object> of elements passed in, relative to the base Fqn.
     *
     * @param base             base Fqn
     * @param relativeElements relative List<Object> of elements
     * @return an Fqn
     * @since 4.0
     */
    public final static FastFqn fromRelativeList(Fqn base, List<?> relativeElements)
    {
        return new FastFqn(base, relativeElements.toArray());
    }

    /**
     * Retrieves an Fqn that represents the array of elements passed in, relative to the base Fqn.
     *
     * @param base             base Fqn
     * @param relativeElements relative elements
     * @return an Fqn
     * @since 4.0
     */
    public final static FastFqn fromRelativeElements(Fqn base, Object... relativeElements)
    {
        return new FastFqn(base, relativeElements);
    }

    /**
     * Returns a new Fqn from a string, where the elements are deliminated by one or more separator ({@link #SEPARATOR})
     * characters.<br><br> Example use:<br>
     * <pre>
     * Fqn.fromString("/a/b/c/");
     * </pre><br>
     * is equivalent to:<br>
     * <pre>
     * Fqn.fromElements("a", "b", "c");
     * </pre>
     *
     * @param stringRepresentation String representation of the Fqn
     * @return an Fqn<String> constructed from the string representation passed in
     */
    public final static FastFqn fromString(String stringRepresentation)
    {
        if (stringRepresentation == null || stringRepresentation.equals(SEPARATOR)
                || stringRepresentation.length() == 0)
            return root();

        String toMatch = stringRepresentation.startsWith(SEPARATOR) ? stringRepresentation.substring(1)
                : stringRepresentation;
        Object[] el = toMatch.split(SEPARATOR);
        return new FastFqn(el);
    }

    public final static FastFqn root() // declared final so compilers can optimise and in-line.
    {
        return ROOT;
    }

    /**
     * Obtains an ancestor of the current Fqn.  Literally performs <code>elements.subList(0, generation)</code> such that
     * if <code> generation == Fqn.size() </code> then the return value is the Fqn itself (current generation), and if
     * <code> generation == Fqn.size() - 1 </code> then the return value is the same as <code> Fqn.getParent() </code>
     * i.e., just one generation behind the current generation. <code> generation == 0 </code> would return Fqn.ROOT.
     *
     * @param generation the generation of the ancestor to retrieve
     * @return an ancestor of the current Fqn
     */
    public final FastFqn getAncestor(int generation)
    {
        if (generation == 0)
            return root();
        return getSubFqn(0, generation);
    }

    /**
     * Obtains a sub-Fqn from the given Fqn.  Literally performs <code>elements.subList(startIndex, endIndex)</code>
     *
     * @param startIndex starting index
     * @param endIndex   end index
     * @return a subFqn
     */
    public final FastFqn getSubFqn(int startIndex, int endIndex)
    {
        if (endIndex < startIndex)
            throw new IllegalArgumentException("End index cannot be less than the start index!");
        int len = endIndex - startIndex;
        Object[] el = new Object[len];
        System.arraycopy(elements, startIndex, el, 0, len);
        return new FastFqn(el);
    }

    /**
     * @return the number of elements in the Fqn.  The root node contains zero.
     */
    public final int size()
    {
        return elements.length;
    }

    /**
     * @param n index of the element to return
     * @return Returns the nth element in the Fqn.
     */
    public final Object get(int n)
    {
        return elements[n];
    }

    /**
     * @return the last element in the Fqn.
     * @see #getLastElementAsString
     */
    public final Object getLastElement()
    {
        if (isRoot())
            return null;
        return elements[elements.length - 1];
    }

    /**
     * @param element element to find
     * @return true if the Fqn contains this element, false otherwise.
     */
    public final boolean hasElement(Object element)
    {
        return indexOf(element) != -1;
    }

    private int indexOf(Object element)
    {
        if (element == null)
        {
            for (int i = 0; i < elements.length; i++)
            {
                if (elements[i] == null)
                    return i;
            }
        }
        else
        {
            for (int i = 0; i < elements.length; i++)
            {
                if (element.equals(elements[i]))
                    return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if obj is a Fqn with the same elements.
     */
    @Override
    public final boolean equals(Object obj)
    {
        Boolean x = checkObjectClassEquality(obj);
        if (x != null)
            return x;

        if (!isSameItems((FastFqn)obj))
            return false;

        return true;
    }

    private Boolean checkObjectClassEquality(Object obj)
    {
        boolean sameObj = isSameObject(obj);
        boolean sameClass = isSameClass(obj);

        if (sameObj)
            return true;

        if (!sameClass)
        {
            return false;
        }
        return null;
    }

    private boolean isSameItems(FastFqn obj)
    {
        FastFqn other = obj;

        if (!isSameLength(other))
            return false;

        if (!isItemsEqual(other))
            return false;

        return true;
    }

    private boolean isItemsEqual(FastFqn other)
    {
        for (int i = elements.length - 1; i >= 0; i--)
        {
            if (!compareElements(other, i))
                return false;
        }
        return true;
    }

    private boolean compareElements(FastFqn other, int i)
    {
        return get(i).equals(other.get(i));
    }

    private boolean isSameLength(FastFqn other)
    {
        if (elements.length != other.elements.length)
            return false;
        return true;
    }

    private boolean isSameClass(Object obj)
    {
        return obj instanceof FastFqn;
    }

    private boolean isSameObject(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        return false;
    }

    /**
     * Returns a hash code with Fqn elements.
     */
    @Override
    public final int hashCode()
    {
        if (hash_code == 0)
        {
            hash_code = calculateHashCode();
        }
        return hash_code;
    }

    /**
     * Returns this Fqn as a string, prefixing the first element with a {@link Fqn#SEPARATOR} and joining each subsequent
     * element with a {@link Fqn#SEPARATOR}. If this is the root Fqn, returns {@link Fqn#SEPARATOR}. Example:
     * <pre>
     * new Fqn(new Object[] { "a", "b", "c" }).toString(); // "/a/b/c"
     * Fqn.ROOT.toString(); // "/"
     * </pre>
     */
    @Override
    public final String toString()
    {
        if (stringRepresentation == null)
        {
            stringRepresentation = getStringRepresentation(elements);
        }
        return stringRepresentation;
    }

    /**
     * Returns true if this Fqn is child of parentFqn. Example usage:
     * <pre>
     * Fqn<String> f1 = Fqn.fromString("/a/b");
     * Fqn<String> f2 = Fqn.fromString("/a/b/c");
     * assertTrue(f1.isChildOf(f2));
     * assertFalse(f1.isChildOf(f1));
     * assertFalse(f2.isChildOf(f1));
     * </pre>
     *
     * @param parentFqn candidate parent to test against
     * @return true if the target is a child of parentFqn
     */
    public final boolean isChildOf(FastFqn parentFqn)
    {
        return parentFqn.elements.length != elements.length && isChildOrEquals(parentFqn);
    }

    /**
     * Returns true if this Fqn is a <i>direct</i> child of a given Fqn.
     *
     * @param parentFqn parentFqn to compare with
     * @return true if this is a direct child, false otherwise.
     */
    public final boolean isDirectChildOf(FastFqn parentFqn)
    {
        return elements.length == parentFqn.elements.length + 1 && isChildOf(parentFqn);
    }

    /**
     * Returns true if this Fqn is equals or the child of parentFqn. Example usage:
     * <pre>
     * Fqn<String> f1 = Fqn.fromString("/a/b");
     * Fqn<String> f2 = Fqn.fromString("/a/b/c");
     * assertTrue(f1.isChildOrEquals(f2));
     * assertTrue(f1.isChildOrEquals(f1));
     * assertFalse(f2.isChildOrEquals(f1));
     * </pre>
     *
     * @param parentFqn candidate parent to test against
     * @return true if this Fqn is equals or the child of parentFqn.
     */
    public final boolean isChildOrEquals(FastFqn parentFqn)
    {
        Object[] parentEl = parentFqn.elements;
        if (parentEl.length > elements.length)
        {
            return false;
        }
        for (int i = parentEl.length - 1; i >= 0; i--)
        {
            if (!parentEl[i].equals(elements[i]))
                return false;
        }
        return true;
    }

    /**
     * Calculates a hash code by summing the hash code of all elements.
     *
     * @return a calculated hashcode
     */
    protected final int calculateHashCode()
    {
        int hashCode = 0;

        hashCode = itemsHashCode();

        if (hashCode == 0)
        {
            return 0xDEADBEEF; // degenerate case
        }
        return hashCode;
    }

    private int itemsHashCode()
    {
        int hashCode = 0;
        for (int i = 0; i < elements.length; i++)
        {
            hashCode = itemHashCode(19, elements[i]);
        }
        return hashCode;
    }

    private int itemHashCode(int hashCode, Object o)
    {
        if (o == null)
        {
            return itemNullHashCode(hashCode, o);
        }
        else
        {
            return 31 * hashCode + o.hashCode();
        }
    }

    private int itemNullHashCode(int hashCode, Object o)
    {
        return 31 * hashCode;
    }

    protected final String getStringRepresentation(Object[] elements)
    {
        StringBuilder builder = new StringBuilder();
        for (Object e : elements)
        {
            // incase user element 'e' does not implement equals() properly, don't rely on their implementation.
            if (!SEPARATOR.equals(e) && !"".equals(e))
            {
                builder.append(SEPARATOR);
                builder.append(e);
            }
        }
        return builder.length() == 0 ? SEPARATOR : builder.toString();
    }

    /**
     * Returns the parent of this Fqn. The parent of the root node is {@link #ROOT}. Examples:
     * <pre>
     * Fqn<String> f1 = Fqn.fromString("/a");
     * Fqn<String> f2 = Fqn.fromString("/a/b");
     * assertEquals(f1, f2.getParent());
     * assertEquals(Fqn.ROOT, f1.getParent().getParent());
     * assertEquals(Fqn.ROOT, Fqn.ROOT.getParent());
     * </pre>
     *
     * @return the parent Fqn
     */
    public final FastFqn getParent()
    {
        switch (elements.length)
        {
        case 0:
        case 1:
            return root();
        default:
            return getSubFqn(0, elements.length - 1);
        }
    }

    /**
     * Returns true if this is a root Fqn.
     *
     * @return true if the Fqn is Fqn.ROOT.
     */
    public final boolean isRoot()
    {
        return elements.length == 0;
    }

    /**
     * If this is the root, returns {@link Fqn#SEPARATOR}.
     *
     * @return a String representation of the last element that makes up this Fqn.
     */
    public final String getLastElementAsString()
    {
        if (isRoot())
        {
            return SEPARATOR;
        }
        else
        {
            Object last = getLastElement();
            if (last instanceof String)
                return (String)last;
            else
                return String.valueOf(getLastElement());
        }
    }

    /**
     * Peeks into the elements that build up this Fqn.  The list returned is read-only, to maintain the immutable nature
     * of Fqn.
     *
     * @return an unmodifiable list
     */
    public final List<Object> peekElements()
    {
        return Arrays.asList(elements);
    }

    /**
     * Compares this Fqn to another
     */
    @Override
    public final int compareTo(FastFqn fqn)
    {
        return FastFqnComparator.INSTANCE.compare(this, fqn);
    }

    /**
     * Creates a new Fqn whose ancestor has been replaced with the new ancestor passed in.
     *
     * @param oldAncestor old ancestor to replace
     * @param newAncestor nw ancestor to replace with
     * @return a new Fqn with ancestors replaced.
     */
    public final FastFqn replaceAncestor(FastFqn oldAncestor, FastFqn newAncestor)
    {
        if (!isChildOf(oldAncestor))
            throw new IllegalArgumentException("Old ancestor must be an ancestor of the current Fqn!");
        FastFqn subFqn = this.getSubFqn(oldAncestor.size(), size());
        return FastFqn.fromRelativeFqn(newAncestor, subFqn);
    }
}
