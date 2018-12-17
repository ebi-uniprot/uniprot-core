package uk.ac.ebi.uniprot.domain.util.property;

/*
 Copyright (c) 2002 JSON.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 The Software shall be used for Good, not Evil.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * A JSONArray is an ordered sequence of values. Its external text form is a
 * string wrapped in square brackets with commas separating the values. The
 * internal form is an object having <code>get</code> and <code>opt</code>
 * methods for accessing the values by index, and <code>put</code> methods for
 * adding or replacing values. The values can be any of these types:
 * <code>Boolean</code>, <code>JSONArray</code>, <code>JSONObject</code>,
 * <code>Number</code>, <code>String</code>, or the
 * <code>JSONObject.NULL object</code>.
 * <p>
 * The constructor can convert a JSON text into a Java object. The
 * <code>toString</code> method converts to JSON text.
 * <p>
 * A <code>get</code> method returns a value if one can be found, and throws an
 * exception if one cannot be found. An <code>opt</code> method returns a
 * default value instead of throwing an exception, and so is useful for
 * obtaining optional values.
 * <p>
 * The generic <code>get()</code> and <code>opt()</code> methods return an
 * object which you can cast or query for type. There are also typed
 * <code>get</code> and <code>opt</code> methods that do type checking and type
 * coercion for you.
 * <p>
 * The texts produced by the <code>toString</code> methods strictly conform to
 * JSON syntax rules. The constructors are more forgiving in the texts they will
 * accept:
 * <ul>
 * <li>An extra <code>,</code>&nbsp;<small>(comma)</small> may appear just
 * before the closing bracket.</li>
 * <li>The <code>null</code> value will be inserted when there is <code>,</code>
 * &nbsp;<small>(comma)</small> elision.</li>
 * <li>Strings may be quoted with <code>'</code>&nbsp;<small>(single
 * quote)</small>.</li>
 * <li>Strings do not need to be quoted at all if they do not begin with a quote
 * or single quote, and if they do not contain leading or trailing spaces, and
 * if they do not contain any of these characters:
 * <code>{ } [ ] / \ : , #</code> and if they do not look like numbers and
 * if they are not the reserved words <code>true</code>, <code>false</code>, or
 * <code>null</code>.</li>
 * </ul>
 *
 * @author JSON.org
 * @version 2016-08/15
 */
public class PropertyArray implements Iterable<Object> {

    /**
     * The arrayList where the JSONArray's properties are kept.
     */
    private final ArrayList<Object> myArrayList;

    /**
     * Construct an empty JSONArray.
     */
    public PropertyArray() {
        this.myArrayList = new ArrayList<Object>();
    }

    /**
     * Construct a JSONArray from a JSONTokener.
     *
     * @param x
     *            A JSONTokener
     * @throws PropertyException
     *             If there is a syntax error.
     */
    public PropertyArray(PropertyTokener x) throws PropertyException {
        this();
        if (x.nextClean() != '[') {
            throw x.syntaxError("A JSONArray text must start with '['");
        }
        
        char nextChar = x.nextClean();
        if (nextChar == 0) {
            // array is unclosed. No ']' found, instead EOF
            throw x.syntaxError("Expected a ',' or ']'");
        }
        if (nextChar != ']') {
            x.back();
            for (;;) {
                if (x.nextClean() == ',') {
                    x.back();
                    this.myArrayList.add(PropertyObject.NULL);
                } else {
                    x.back();
                    this.myArrayList.add(x.nextValue());
                }
                switch (x.nextClean()) {
                case 0:
                    // array is unclosed. No ']' found, instead EOF
                    throw x.syntaxError("Expected a ',' or ']'");
                case ',':
                    nextChar = x.nextClean();
                    if (nextChar == 0) {
                        // array is unclosed. No ']' found, instead EOF
                        throw x.syntaxError("Expected a ',' or ']'");
                    }
                    if (nextChar == ']') {
                        return;
                    }
                    x.back();
                    break;
                case ']':
                    return;
                default:
                    throw x.syntaxError("Expected a ',' or ']'");
                }
            }
        }
    }

    /**
     * Construct a JSONArray from a source JSON text.
     *
     * @param source
     *            A string that begins with <code>[</code>&nbsp;<small>(left
     *            bracket)</small> and ends with <code>]</code>
     *            &nbsp;<small>(right bracket)</small>.
     * @throws PropertyException
     *             If there is a syntax error.
     */
    public PropertyArray(String source) throws PropertyException {
        this(new PropertyTokener(source));
    }

    /**
     * Construct a JSONArray from a Collection.
     *
     * @param collection
     *            A Collection.
     */
    public PropertyArray(Collection<?> collection) {
        if (collection == null) {
            this.myArrayList = new ArrayList<Object>();
        } else {
            this.myArrayList = new ArrayList<Object>(collection.size());
        	for (Object o: collection){
        		this.myArrayList.add(PropertyObject.wrap(o));
        	}
        }
    }

    /**
     * Construct a JSONArray from an array.
     *
     * @param array
     *            Array. If the parameter passed is null, or not an array, an
     *            exception will be thrown.
     *
     * @throws PropertyException
     *            If not an array or if an array value is non-finite number.
     * @throws NullPointerException
     *            Thrown if the array parameter is null.
     */
    public PropertyArray(Object array) throws PropertyException {
        this();
        if (array.getClass().isArray()) {
            int length = Array.getLength(array);
            this.myArrayList.ensureCapacity(length);
            for (int i = 0; i < length; i += 1) {
                this.put(PropertyObject.wrap(Array.get(array, i)));
            }
        } else {
            throw new PropertyException(
                    "JSONArray initial value should be a string or collection or array.");
        }
    }

    @Override
    public Iterator<Object> iterator() {
        return this.myArrayList.iterator();
    }

    /**
     * Get the object value associated with an index.
     *
     * @param index
     *            The index must be between 0 and length() - 1.
     * @return An object value.
     * @throws PropertyException
     *             If there is no value for the index.
     */
    public Object get(int index) throws PropertyException {
        Object object = this.opt(index);
        if (object == null) {
            throw new PropertyException("JSONArray[" + index + "] not found.");
        }
        return object;
    }

    /**
     * Get the boolean value associated with an index. The string values "true"
     * and "false" are converted to boolean.
     *
     * @param index
     *            The index must be between 0 and length() - 1.
     * @return The truth.
     * @throws PropertyException
     *             If there is no value for the index or if the value is not
     *             convertible to boolean.
     */
    public boolean getBoolean(int index) throws PropertyException {
        Object object = this.get(index);
        if (object.equals(Boolean.FALSE)
                || (object instanceof String && ((String) object)
                        .equalsIgnoreCase("false"))) {
            return false;
        } else if (object.equals(Boolean.TRUE)
                || (object instanceof String && ((String) object)
                        .equalsIgnoreCase("true"))) {
            return true;
        }
        throw new PropertyException("JSONArray[" + index + "] is not a boolean.");
    }

    /**
     * Get the Number value associated with a key.
     *
     * @param index
     *            The index must be between 0 and length() - 1.
     * @return The numeric value.
     * @throws PropertyException
     *             if the key is not found or if the value is not a Number
     *             object and cannot be converted to a number.
     */
    public Number getNumber(int index) throws PropertyException {
        Object object = this.get(index);
        try {
            if (object instanceof Number) {
                return (Number)object;
            }
            return PropertyObject.stringToNumber(object.toString());
        } catch (Exception e) {
            throw new PropertyException("JSONArray[" + index + "] is not a number.", e);
        }
    }

    /**
     * Get the int value associated with an index.
     *
     * @param index
     *            The index must be between 0 and length() - 1.
     * @return The value.
     * @throws PropertyException
     *             If the key is not found or if the value is not a number.
     */
    public int getInt(int index) throws PropertyException {
        return this.getNumber(index).intValue();
    }

    /**
     * Get the JSONArray associated with an index.
     *
     * @param index
     *            The index must be between 0 and length() - 1.
     * @return A JSONArray value.
     * @throws PropertyException
     *             If there is no value for the index. or if the value is not a
     *             JSONArray
     */
    public PropertyArray getJSONArray(int index) throws PropertyException {
        Object object = this.get(index);
        if (object instanceof PropertyArray) {
            return (PropertyArray) object;
        }
        throw new PropertyException("JSONArray[" + index + "] is not a JSONArray.");
    }

    /**
     * Get the JSONObject associated with an index.
     *
     * @param index
     *            subscript
     * @return A JSONObject value.
     * @throws PropertyException
     *             If there is no value for the index or if the value is not a
     *             JSONObject
     */
    public PropertyObject getJSONObject(int index) throws PropertyException {
        Object object = this.get(index);
        if (object instanceof PropertyObject) {
            return (PropertyObject) object;
        }
        throw new PropertyException("JSONArray[" + index + "] is not a JSONObject.");
    }

    /**
     * Get the string associated with an index.
     *
     * @param index
     *            The index must be between 0 and length() - 1.
     * @return A string value.
     * @throws PropertyException
     *             If there is no string value for the index.
     */
    public String getString(int index) throws PropertyException {
        Object object = this.get(index);
        if (object instanceof String) {
            return (String) object;
        }
        throw new PropertyException("JSONArray[" + index + "] not a string.");
    }

    /**
     * Get the number of elements in the JSONArray, included nulls.
     *
     * @return The length (or size).
     */
    public int length() {
        return this.myArrayList.size();
    }

    /**
     * Get the optional object value associated with an index.
     *
     * @param index
     *            The index must be between 0 and length() - 1. If not, null is returned.
     * @return An object value, or null if there is no object at that index.
     */
    public Object opt(int index) {
        return (index < 0 || index >= this.length()) ? null : this.myArrayList
                .get(index);
    }

    /**
     * Get the optional boolean value associated with an index. It returns the
     * defaultValue if there is no value at that index or if it is not a Boolean
     * or the String "true" or "false" (case insensitive).
     *
     * @param index
     *            The index must be between 0 and length() - 1.
     * @param defaultValue
     *            A boolean default.
     * @return The truth.
     */
    public boolean optBoolean(int index, boolean defaultValue) {
        try {
            return this.getBoolean(index);
        } catch (Exception e) {
            return defaultValue;
        }
    }




    /**
     * Get the optional string value associated with an index. It returns an
     * empty string if there is no value at that index. If the value is not a
     * string and is not null, then it is converted to a string.
     *
     * @param index
     *            The index must be between 0 and length() - 1.
     * @return A String value.
     */
    public String optString(int index) {
        return this.optString(index, "");
    }

    /**
     * Get the optional string associated with an index. The defaultValue is
     * returned if the key is not found.
     *
     * @param index
     *            The index must be between 0 and length() - 1.
     * @param defaultValue
     *            The default value.
     * @return A String value.
     */
    public String optString(int index, String defaultValue) {
        Object object = this.opt(index);
        return PropertyObject.NULL.equals(object) ? defaultValue : object
                .toString();
    }


    /**
     * Append an object value. This increases the array's length by one.
     *
     * @param value
     *            An object value. The value should be a Boolean, Double,
     *            Integer, JSONArray, JSONObject, Long, or String, or the
     *            JSONObject.NULL object.
     * @return this.
     * @throws PropertyException
     *            If the value is non-finite number.
     */
    public PropertyArray put(Object value) {
        PropertyObject.testValidity(value);
        this.myArrayList.add(value);
        return this;
    }

    /**
     * Put or replace an object value in the JSONArray. If the index is greater
     * than the length of the JSONArray, then null elements will be added as
     * necessary to pad it out.
     *
     * @param index
     *            The subscript.
     * @param value
     *            The value to put into the array. The value should be a
     *            Boolean, Double, Integer, JSONArray, JSONObject, Long, or
     *            String, or the JSONObject.NULL object.
     * @return this.
     * @throws PropertyException
     *             If the index is negative or if the the value is an invalid
     *             number.
     */
    public PropertyArray put(int index, Object value) throws PropertyException {
        if (index < 0) {
            throw new PropertyException("JSONArray[" + index + "] not found.");
        }
        if (index < this.length()) {
            PropertyObject.testValidity(value);
            this.myArrayList.set(index, value);
            return this;
        }
        if(index == this.length()){
            // simple append
            return this.put(value);
        }
        // if we are inserting past the length, we want to grow the array all at once
        // instead of incrementally.
        this.myArrayList.ensureCapacity(index + 1);
        while (index != this.length()) {
            // we don't need to test validity of NULL objects
            this.myArrayList.add(PropertyObject.NULL);
        }
        return this.put(value);
    }


    /**
     * Remove an index and close the hole.
     *
     * @param index
     *            The index of the element to be removed.
     * @return The value that was associated with the index, or null if there
     *         was no value.
     */
    public Object remove(int index) {
        return index >= 0 && index < this.length()
            ? this.myArrayList.remove(index)
            : null;
    }

    /**
     * Determine if two JSONArrays are similar.
     * They must contain similar sequences.
     *
     * @param other The other JSONArray
     * @return true if they are equal
     */
    public boolean similar(Object other) {
        if (!(other instanceof PropertyArray)) {
            return false;
        }
        int len = this.length();
        if (len != ((PropertyArray)other).length()) {
            return false;
        }
        for (int i = 0; i < len; i += 1) {
            Object valueThis = this.myArrayList.get(i);
            Object valueOther = ((PropertyArray)other).myArrayList.get(i);
            if(valueThis == valueOther) {
            	continue;
            }
            if(valueThis == null) {
            	return false;
            }
            if (valueThis instanceof PropertyObject) {
                if (!((PropertyObject)valueThis).similar(valueOther)) {
                    return false;
                }
            } else if (valueThis instanceof PropertyArray) {
                if (!((PropertyArray)valueThis).similar(valueOther)) {
                    return false;
                }
            } else if (!valueThis.equals(valueOther)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Produce a JSONObject by combining a JSONArray of names with the values of
     * this JSONArray.
     *
     * @param names
     *            A JSONArray containing a list of key strings. These will be
     *            paired with the values.
     * @return A JSONObject, or null if there are no names or if this JSONArray
     *         has no values.
     * @throws PropertyException
     *             If any of the names are null.
     */
    public PropertyObject toJSONObject(PropertyArray names) throws PropertyException {
        if (names == null || names.isEmpty() || this.isEmpty()) {
            return null;
        }
        PropertyObject jo = new PropertyObject(names.length());
        for (int i = 0; i < names.length(); i += 1) {
            jo.put(names.getString(i), this.opt(i));
        }
        return jo;
    }

    /**
     * Returns a java.util.List containing all of the elements in this array.
     * If an element in the array is a JSONArray or JSONObject it will also
     * be converted.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @return a java.util.List containing the elements of this array
     */
    public List<Object> toList() {
        List<Object> results = new ArrayList<Object>(this.myArrayList.size());
        for (Object element : this.myArrayList) {
            if (element == null || PropertyObject.NULL.equals(element)) {
                results.add(null);
            } else if (element instanceof PropertyArray) {
                results.add(((PropertyArray) element).toList());
            } else if (element instanceof PropertyObject) {
                results.add(((PropertyObject) element).toMap());
            } else {
                results.add(element);
            }
        }
        return results;
    }

    /**
     * Check if JSONArray is empty.
     *
     * @return true if JSONArray is empty, otherwise false.
     */
    public boolean isEmpty() {
        return this.myArrayList.isEmpty();
    }

}
