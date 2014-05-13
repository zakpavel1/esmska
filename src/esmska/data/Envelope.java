package esmska.data;

import esmska.utils.MiscUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/** Class for preparing attributes of sms (single or multiple)
 *
 * @author ripper
 */
public class Envelope {
    private static final Config config = Config.getInstance();
    private static final Logger logger = Logger.getLogger(Envelope.class.getName());
    private static final Gateways gateways = Gateways.getInstance();
    private static final Signatures signatures = Signatures.getInstance();
    private String text;
    private Set<Contact> contacts = new HashSet<Contact>();

    // <editor-fold defaultstate="collapsed" desc="PropertyChange support">
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    // </editor-fold>
    
    /** get text of sms */
    public String getText() {
        return text;
    }
    
    /** set text of sms */
    public void setText(String text) {
        String oldText = this.text;
        if (config.isRemoveAccents()) {
            text = MiscUtils.removeAccents(text);
        }
        this.text = text;
        changeSupport.firePropertyChange("text", oldText, text);
    }
    
    /** get all recipients */
    public Set<Contact> getContacts() {
        return Collections.unmodifiableSet(contacts);
    }
    
    /** set all recipients */
    public void setContacts(Set<Contact> contacts) {
        Set<Contact> oldContacts = this.contacts;
        this.contacts = contacts;
        changeSupport.firePropertyChange("contacts", oldContacts, contacts);
    }
    
    /** get maximum length of sendable message */
    public int getMaxTextLength() {
        int min = Integer.MAX_VALUE;
        for (Contact c : contacts) {
            Gateway gateway = gateways.get(c.getGateway());
            if (gateway == null) {
                continue;
            }
            int value = gateway.getMaxChars() * gateway.getMaxParts();
            value -= getSignatureLength(c); //subtract signature length
            min = Math.min(min,value);
        }
        return min;
    }
    
    /** How many characters at the message start are occupied by the prefix
     * (i.e. sender name)
     */
    public int getPrefixLength() {
        int max = 0;
        for (Contact c : contacts) {
            Gateway gateway = gateways.get(c.getGateway());
            if (gateway == null) {
                continue;
            }
            max = Math.max(max, gateway.getSenderName().length());
        }
        return max;
    }
    
    /** get length of one sms
     * @return length of one sms or -1 when sms length is unspecified
     */
    public int getSMSLength() {
        int min = Integer.MAX_VALUE;
        for (Contact c : contacts) {
            Gateway gateway = gateways.get(c.getGateway());
            if (gateway == null) {
                continue;
            }
            min = Math.min(min, gateway.getSMSLength());
        }
        if (min <= 0) {
            //sms length is unspecified
            min = -1;
        }
        return min;
    }
    
    /** get number of sms from these characters 
     * @return resulting number of sms or -1 when length of sms is unspecified
     */
    public int getSMSCount(int chars) {
        int worstGateway = Integer.MAX_VALUE;
        for (Contact c : contacts) {
            Gateway gateway = gateways.get(c.getGateway());
            if (gateway == null) {
                continue;
            }
            worstGateway = Math.min(worstGateway,
                    gateway.getSMSLength());
        }

        if (worstGateway <= 0) {
            //sms length is unspecified
            return -1;
        }

        chars += getSignatureLength();
        int count = chars / worstGateway;
        if (chars % worstGateway != 0) {
            count++;
        }
        return count;
    }
    
    /** Get maximum signature length of the contact gateways in the envelope */
    public int getSignatureLength() {
        int worstSignature = 0;
        //find maximum signature length
        for (Contact c : contacts) {
            int length = getSignatureLength(c);
            worstSignature = Math.max(worstSignature, length);
        }
        return worstSignature;
    }
    
    /** generate list of sms's to send */
    public ArrayList<SMS> generate() {
        ArrayList<SMS> list = new ArrayList<SMS>();
        for (Contact c : contacts) {
            Gateway gateway = gateways.get(c.getGateway());
            int limit = (gateway != null ? gateway.getMaxChars() : Integer.MAX_VALUE);
            String msgText = text;
            // add user signature to the message
            if (gateway != null) {
                String signature = gateway.getSenderName();
                // only if signature is not already added
                if (!msgText.trim().toLowerCase().startsWith(signature.trim().toLowerCase())) {
                    msgText = signature + msgText;
                }
            }
            String messageId = SMS.generateID();
            // cut out the messages
            ArrayList<String> messages = cutOutMessages(msgText, limit);
            for (String cutText : messages) {
                SMS sms = new SMS(c.getNumber(), cutText, c.getGateway(), c.getName(), messageId);
                list.add(sms);
            }
        }
        logger.log(Level.FINE, "Envelope specified for {0} contact(s) generated {1} SMS(s)", 
                new Object[]{contacts.size(), list.size()});
        return list;
    }
    
    /**
     * Take a full message text and cut it out into pieces depending on max SMS
     * length limit. The pieces will be split by word boundaries, unless it
     * would take away more than 10% of the text - in that case it will be split
     * by characters (splitting the word).
     *
     * @param msgText full message text
     * @param limit max SMS length limit
     * @return pieces of msgText, sequence of pieces corresponds to the order of
     * pieces in the msgText
     */
    private ArrayList<String> cutOutMessages(String msgText, int limit) {
        ArrayList<String> list = new ArrayList<String>();

        //cutting msgText into sms's
        int currentLengthOfSMS = msgText.length(); //initial length of sms
        double deviation = 0.1; //deviation 10% from limit
        for (int i = 0; i < msgText.length(); i += currentLengthOfSMS) {
            int indexOfCut = findIndexOfCut(msgText, i, limit, deviation);

            String cutText = msgText.substring(i, indexOfCut);
            currentLengthOfSMS = cutText.length(); //lenght of cutText

            if (currentLengthOfSMS <= 0) {
                throw new IllegalStateException("Current lenght of message is <= 0, loop is infinite!");
            }

            //add cutText into list
            list.add(cutText);
        }

        return list;
    }

    /**
     * From beginnig index start(which is inclusive) find the ending index(which
     * is exclusive) depending on max SMS length limit. Ending index will be
     * searched by word boundaries, unless it would take away more than
     * deviation limit of the text - in that case it will be searched by
     * characters (splitting the word).
     *
     * @param msgText full message text
     * @param start the beginning index, inclusive
     * @param limit max SMS length limit
     * @param dev deviation limit, must be in interval from 0 to 1
     * @return the ending index, exclusive
     */
    private int findIndexOfCut(String msgText, int start, int limit, double dev) {
        //initial index of cut
        //meanwhile, cut message is in interval <i, i+limit)
        int indexOfCut = start + limit;

        if (indexOfCut >= msgText.length()) {
            //last part of the text, equal or shorter than the limit
            //return index of cut, cut message is in interval <i, msgText.length)
            return msgText.length();
        }

        //when the index of cut is in the middle of continuous text,
        //try to find white space
        if (!(Character.isWhitespace(msgText.charAt(indexOfCut)))
                && !(Character.isWhitespace(msgText.charAt(indexOfCut - 1)))) {

            //searching separator between words with dev deviation from intial index of cut
            int minIndexOfCut = start + ((int) (limit * (1 - dev)));
            while ((!Character.isWhitespace(msgText.charAt(indexOfCut)))
                    && (indexOfCut > minIndexOfCut)) {

                indexOfCut--;
            }

            if (indexOfCut <= minIndexOfCut) {
                //separator not found in the specified deviation 
                //return index of cut, cut message is in interval <i, i+limit)
                return (start + limit);
            }

            //inclusion gap into (left) message
            indexOfCut++;
        }

        //return index of cut, cut message is in interval <i, indexOfCut)
        return indexOfCut;
    }
    
    /** get length of signature needed to be subtracted from message length */
    private int getSignatureLength(Contact c) {
        Gateway gateway = gateways.get(c.getGateway());
        if (gateway != null) {
             Signature signature = signatures.get(gateway.getConfig().getSignature());
             if (signature != null && StringUtils.length(signature.getUserName()) > 0) {
                 return gateway.getSignatureExtraLength() + signature.getUserName().length();
             }
        }
        return 0;
    }
    
}
