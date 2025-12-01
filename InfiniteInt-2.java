import java.util.*; 

public class InfiniteInt extends DLList<Integer> implements Comparable<InfiniteInt> {   
    /**
     * Constructs an InfiniteInt object from a string representation of an integer.
     * @param s The string representation of the integer.
     * @throws IllegalArgumentException if the input string contains non-digit characters.
     */
    public InfiniteInt(String s) {
        if (!s.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid input. Only digits are allowed.");  
        }
        for (int i = s.length(); i > 0; i = i - 3) { 
            String part; 
            if (i < 3) {
                part = s.substring(0, i); 
            } else { 
                part = s.substring(i - 3, i); 
            }
            this.addFirst(Integer.parseInt(part)); 
        }
    }

    /**
     * Constructs an InfiniteInt object with a default value of 0.
     */
    public InfiniteInt() {
        this.addFirst(0);
    }

    /**
     * Converts the InfiniteInt object to its string representation.
     * @return The string representation of the InfiniteInt object.
     */
    @Override
    public String toString() {  
        if (this.isEmpty() || (this.size() == 1 && this.getFirst() == 0)) {
            return "0";
        }
        StringBuilder sb = new StringBuilder(); 
        DLLNode<Integer> current = this.head;
        while (current != null) {
            sb.append(String.format("%03d", current.data)); 
            current = current.next;
        }
        String str = sb.toString().replaceAll("^(0+)", "");
        
        str = str.replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
        return str;
    }

    /**
     * Adds two InfiniteInt objects.
     * @param int1 The first InfiniteInt object.
     * @param int2 The second InfiniteInt object.
     * @return The sum of int1 and int2.
     */
    public static InfiniteInt add(InfiniteInt int1, InfiniteInt int2) {
        InfiniteInt result = new InfiniteInt();
        result.removeFirst(); 

        DLLNode<Integer> ptr1 = int1.tail;
        DLLNode<Integer> ptr2 = int2.tail;

        int carry = 0;
        while (ptr1 != null || ptr2 != null) { 
            int sum = carry;
            if (ptr1 != null) {
                sum = sum + ptr1.data;
                ptr1 = ptr1.prev; 
            }
            if (ptr2 != null) { 
                sum = sum + ptr2.data;
                ptr2 = ptr2.prev;
            }

            result.addFirst(sum % 1000); 
            carry = sum / 1000; 
        }

        if (carry > 0) {
            result.addFirst(carry); 
        }

        return result; 
    }

    /**
     * Compares this InfiniteInt with another InfiniteInt.
     * @param other The InfiniteInt to be compared.
     * @return A negative integer, zero, or a positive integer as this InfiniteInt is less than, equal to, or greater than the specified InfiniteInt.
     */
    @Override
    public int compareTo(InfiniteInt other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare InfiniteInt with null"); 
        }
        if (this.size() > other.size()) {
            return 1;
        } else if (this.size() < other.size()) {
            return -1;
        } else {
            DLLNode<Integer> thisPtr = this.head;
            DLLNode<Integer> otherPtr = other.head;
            while (thisPtr != null) {
                if (thisPtr.data > otherPtr.data) {
                    return 1;
                } else if (thisPtr.data < otherPtr.data) {
                    return -1;
                }
                thisPtr = thisPtr.next;
                otherPtr = otherPtr.next;
            }
            return 0;
        }
    }
    
    /**
     * Compares this InfiniteInt with an Integer.
     * @param other The Integer to be compared.
     * @return A negative integer, zero, or a positive integer as this InfiniteInt is less than, equal to, or greater than the specified Integer.
     */
    public int compareTo(Integer other) {
        throw new IllegalArgumentException("Cannot compare InfiniteInt with Integer"); 
    }

    /**
     * Checks whether this InfiniteInt is equal to another object.
     * @param o The object to compare with.
     * @return true if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Integer) {
            o = new InfiniteInt(o.toString());
        }
        if (!(o instanceof InfiniteInt)) {
            return false;
        }
        InfiniteInt other = (InfiniteInt) o;
        return this.compareTo(other) == 0;
    }

    /**
     * Converts the InfiniteInt object to its string representation, reverses it, and removes commas.
     * @return The reversed string representation of the InfiniteInt object without commas.
     */
    public String revAndRemoveToString() {
        String str = this.toString();
        str = str.replace(",", "");
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Converts the InfiniteInt object to its string representation and replaces commas with hyphens.
     * @return The string representation of the InfiniteInt object with commas replaced by hyphens.
     */
    public String replaceCommasWithHyphens() {
        return this.toString().replace(",", "-");
    }
    
    /**
     * Generates a hash for grading purposes.
     * @return The grader hash.
     */
    public String getGraderHash() {
        return "299043c07016e6b278cce56ec2ecc773";    
    }
}

