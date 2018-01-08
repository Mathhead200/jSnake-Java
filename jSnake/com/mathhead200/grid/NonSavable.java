package com.mathhead200.grid;

import java.lang.annotation.*;

/**
 * if used on a GridItem, cause that type of grid item not to save to file, <br />
 * if used on a Box cause it's griditem not to save to file, <br />
 * if used on a Grid causes the Grid to throw a NonSavableGridException
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NonSavable {
}