//: Compare.java
//Interface for sorting callback:
package WordSort;

interface Compare {
	boolean lessThan(Object lhs, Object rhs);

	boolean lessThanOrEqual(Object lhs, Object rhs);
} ///:~
