package org.fife.rsta.ac.js.ecma.api.ecma5;

import org.fife.rsta.ac.js.ecma.api.ecma3.JSFunction;
import org.fife.rsta.ac.js.ecma.api.ecma3.JSString;
import org.fife.rsta.ac.js.ecma.api.ecma5.functions.JS5FunctionFunctions;

/**
 * Object Function
 * @since Standard ECMA-262 3rd. Edition
 */
public abstract class JS5Function extends JSFunction implements JS5FunctionFunctions {

	/**
     * Object Function(argument_names..., body)
     * @constructor
     * @extends Object
     * @param argument_names Any number of string arguments, each naming one or more arguments of the Function object to be created.
     * @param body A string that represents the body of the function. It may contain an number of JavaScript statements, separated by semicolons.
     * @since Standard ECMA-262 3rd. Edition
     * @since Level 2 Document Object Model Core Definition.
    */
    public JS5Function(JSString argument_names, JSString body) {
    	super(argument_names, body);
    }
}
