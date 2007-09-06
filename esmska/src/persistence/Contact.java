/*
 * Contact.java
 *
 * Created on 21. červenec 2007, 0:57
 */

package persistence;

import java.beans.*;
import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import operators.Operator;

/** SMS Contact
 * @author ripper
 */
public class Contact extends Object implements Serializable, Comparable<Contact> {
    
    private String name;
    private String number;
    private Operator operator;
    
    private PropertyChangeSupport propertySupport;
    
    public Contact() {
        this(null,null,null);
    }
    
    public Contact(String name, String number, Operator operator) {
        this.name = name;
        this.number = number;
        this.operator = operator;
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertySupport.firePropertyChange("name", oldName, name);
    }
    
    /**
     * Getter for property number.
     * @return Value of property number.
     */
    public String getNumber() {
        return this.number;
    }
    
    /**
     * Setter for property number.
     * @param number New value of property number.
     */
    public void setNumber(String number) {
        String oldNumber = this.number;
        this.number = number;
        propertySupport.firePropertyChange("number", oldNumber, number);
    }
    
    /**
     * Getter for property operator.
     * @return Value of property operator.
     */
    public Operator getOperator() {
        return this.operator;
    }
    
    /**
     * Setter for property operator.
     * @param operator New value of property operator.
     */
    public void setOperator(Operator operator) {
        Operator oldOperator = this.operator;
        this.operator = operator;
        propertySupport.firePropertyChange("operator", oldOperator, operator);
    }
    
    public int compareTo(Contact c) {
        int result = 0;
        Collator collator = Collator.getInstance();
        
        //name
        result = collator.compare(this.getName(), c.getName());
        if (result != 0)
            return result;
        //number
            result = collator.compare(this.getNumber(), c.getNumber());
        if (result != 0)
            return result;
        //operator
        if (this.getOperator() == null) {
            if (c.getOperator() != null)
                result = -1;
        } else {
            result = this.getOperator().toString().compareTo(c.getOperator().toString());
        }
        
        return result;
    }
    
    public String toString() {
        return getName();
    }
    
}
