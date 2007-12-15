/*
 * MainFrame.java
 *
 * Created on 6. červenec 2007, 15:37
 */

package esmska.gui;

import esmska.data.Envelope;
import esmska.persistence.ExportManager;
import esmska.transfer.SMSSender;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import esmska.data.Config;
import esmska.data.Contact;
import esmska.persistence.PersistenceManager;
import esmska.data.SMS;
import esmska.UpdateChecker;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 * MainFrame form
 *
 * @author ripper
 */
public class MainFrame extends javax.swing.JFrame {
    private static MainFrame instance;
    private static final String RES = "/esmska/resources/";
    
    private Action quitAction = new QuitAction();
    private Action aboutAction = new AboutAction();
    private Action configAction = new ConfigAction();
    private ImportAction importAction = new ImportAction();
    private Action exportAction = new ExportAction();
    private Action compressAction = new CompressAction();
    
    /** actual queue of sms's */
    private List<SMS> smsQueue = PersistenceManager.getQueue();
    /** sender of sms */
    private SMSSender smsSender;
    /** box for messages */
    private Envelope envelope;
    /** timer to send another sms after defined delay */
    private Timer smsDelayTimer = new Timer(1000,new SMSDelayActionListener());
    /** manager of persistence data */
    private PersistenceManager persistenceManager;
    /** program configuration */
    private Config config = PersistenceManager.getConfig();
    /** sms contacts */
    private TreeSet<Contact> contacts = PersistenceManager.getContacs();
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        instance = this;
        initComponents();
        
        //set tooltip delay
        ToolTipManager.sharedInstance().setInitialDelay(750);
        ToolTipManager.sharedInstance().setDismissDelay(60000);
        
        //init custom components
        smsSender = new SMSSender(smsQueue);
        envelope = new Envelope();
        smsPanel.setEnvelope(envelope);
        
        //load config
        try {
            persistenceManager = PersistenceManager.getInstance();
        } catch (IOException ex) {
            ex.printStackTrace();
            printStatusMessage("Nepovedlo se vytvořit adresář s nastavením programu!");
        }
        loadConfig();
        if (smsQueue.size() > 0)
            pauseSMSQueue(true);
        
        //setup components
        smsDelayTimer.setInitialDelay(0);
        
        //check for updates
        if (config.isCheckForUpdates()) {
            UpdateChecker updateChecker = new UpdateChecker();
            updateChecker.addActionListener(new UpdateListener());
            updateChecker.checkForUpdates();
        }
    }
    
    public static MainFrame getInstance() {
        if (instance == null)
            instance = new MainFrame();
        return instance;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        statusPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        smsDelayProgressBar = new javax.swing.JProgressBar();
        horizontalSplitPane = new javax.swing.JSplitPane();
        verticalSplitPane = new javax.swing.JSplitPane();
        smsPanel = new esmska.gui.SMSPanel();
        queuePanel = new esmska.gui.QueuePanel();
        contactPanel = new esmska.gui.ContactPanel();
        menuBar = new javax.swing.JMenuBar();
        programMenu = new javax.swing.JMenu();
        configMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        importMenuItem = new javax.swing.JMenuItem();
        exportMenuItem = new javax.swing.JMenuItem();
        compressMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Esmska");
        setIconImage(new ImageIcon(getClass().getResource(RES + "esmska.png")).getImage());
        setLocationByPlatform(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        statusPanel.setFocusable(false);

        statusMessageLabel.setText("V\u00edtejte");
        statusMessageLabel.setFocusable(false);

        statusAnimationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/esmska/resources/task-idle.png")));
        statusAnimationLabel.setFocusable(false);

        smsDelayProgressBar.setMaximum(15);
        smsDelayProgressBar.setFocusable(false);
        smsDelayProgressBar.setString("Dal\u0161\u00ed sms za: ");
        smsDelayProgressBar.setStringPainted(true);
        smsDelayProgressBar.setVisible(false);

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 380, Short.MAX_VALUE)
                .addComponent(smsDelayProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(statusAnimationLabel)
                    .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(smsDelayProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(statusMessageLabel))))
        );

        horizontalSplitPane.setBorder(null);
        horizontalSplitPane.setResizeWeight(0.5);
        horizontalSplitPane.setContinuousLayout(true);
        horizontalSplitPane.setOneTouchExpandable(true);
        horizontalSplitPane.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);
        verticalSplitPane.setBorder(null);
        verticalSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setResizeWeight(1.0);
        verticalSplitPane.setContinuousLayout(true);
        verticalSplitPane.setOneTouchExpandable(true);
        verticalSplitPane.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);
        smsPanel.addActionListener(new SMSListener());
        smsPanel.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.FALSE);
        verticalSplitPane.setLeftComponent(smsPanel);

        queuePanel.addActionListener(new QueueListener());
        queuePanel.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.FALSE);
        verticalSplitPane.setRightComponent(queuePanel);

        horizontalSplitPane.setLeftComponent(verticalSplitPane);

        contactPanel.addActionListener(new ContactListener());
        contactPanel.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.FALSE);
        horizontalSplitPane.setRightComponent(contactPanel);

        programMenu.setMnemonic('r');
        programMenu.setText("Program");
        configMenuItem.setAction(configAction);
        programMenu.add(configMenuItem);

        aboutMenuItem.setAction(aboutAction);
        programMenu.add(aboutMenuItem);

        exitMenuItem.setAction(quitAction);
        programMenu.add(exitMenuItem);

        menuBar.add(programMenu);

        toolsMenu.setMnemonic('n');
        toolsMenu.setText("N\u00e1stroje");
        importMenuItem.setAction(importAction);
        toolsMenu.add(importMenuItem);

        exportMenuItem.setAction(exportAction);
        toolsMenu.add(exportMenuItem);

        compressMenuItem.setAction(compressAction);
        toolsMenu.add(compressMenuItem);

        menuBar.add(toolsMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(horizontalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(horizontalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        saveConfig();
        saveContacts();
        saveQueue();
    }//GEN-LAST:event_formWindowClosing
    
    /** Prints message to status bar */
    public void printStatusMessage(String message) {
        statusMessageLabel.setText(message);
    }
    
    /** Tells main form whether it should display task busy icon */
    public void setTaskRunning(boolean b) {
        if (b == false)
            statusAnimationLabel.setIcon(new ImageIcon(getClass().getResource(RES + "task-idle.png")));
        else
            statusAnimationLabel.setIcon(new ImageIcon(getClass().getResource(RES + "task-busy.gif")));
    }
    
    /** Notifies about change in sms queue */
    public void smsProcessed(SMS sms) {
        queuePanel.smsProcessed(sms);
    }
    
    /** Pauses sms queue */
    public void pauseSMSQueue(boolean pause) {
        queuePanel.setPaused(pause);
    }
    
    /** Forces delay before sending another sms */
    public void setSMSDelay() {
        smsSender.setDelayed(true);
        smsDelayTimer.start();
    }
    
    /** Import additional contacts */
    public void importContacts(Collection<Contact> contacts) {
        contactPanel.clearSelection();
        contactPanel.addContacts(contacts);
    }
    
    /** save program configuration */
    private void saveConfig() {
        //save frame layout
        config.setMainDimension(this.getSize());
        config.setHorizontalSplitPaneLocation(horizontalSplitPane.getDividerLocation());
        config.setVerticalSplitPaneLocation(verticalSplitPane.getDividerLocation());
        
        try {
            persistenceManager.saveConfig();
        } catch (Exception ex) {
            ex.printStackTrace();
            printStatusMessage("Nepodařilo se uložit nastavení!");
        }
    }
    
    /** load program configuration */
    private void loadConfig() {
        if (config.isRememberLayout()) { //set frame layout
            Dimension mainDimension = config.getMainDimension();
            Integer horizontalSplitPaneLocation = config.getHorizontalSplitPaneLocation();
            Integer verticalSplitPaneLocation = config.getVerticalSplitPaneLocation();
            if (mainDimension != null)
                this.setSize(mainDimension);
            if (horizontalSplitPaneLocation != null)
                horizontalSplitPane.setDividerLocation(horizontalSplitPaneLocation);
            if (verticalSplitPaneLocation != null)
                verticalSplitPane.setDividerLocation(verticalSplitPaneLocation);
        }
    }
    
    /** save contacts */
    private void saveContacts() {
        try {
            persistenceManager.saveContacts();
        } catch (Exception ex) {
            ex.printStackTrace();
            printStatusMessage("Nepodařilo se uložit kontakty!");
        }
    }
    
    /** save sms queue */
    private void saveQueue() {
        try {
            persistenceManager.saveQueue();
        } catch (IOException ex) {
            ex.printStackTrace();
            printStatusMessage("Nepodařilo se uložit frontu sms!");
        }
    }
    
    /** Show about frame */
    private class AboutAction extends AbstractAction {
        AboutFrame aboutFrame;
        public AboutAction() {
            super("O programu", new ImageIcon(MainFrame.class.getResource(RES + "about-small.png")));
            this.putValue(MNEMONIC_KEY,KeyEvent.VK_O);
        }
        public void actionPerformed(ActionEvent e) {
            if (aboutFrame != null && aboutFrame.isVisible()) {
                aboutFrame.requestFocus();
            } else {
                aboutFrame = new AboutFrame();
                aboutFrame.setLocationRelativeTo(MainFrame.this);
                aboutFrame.setVisible(true);
            }
        }
    }
    
    /** Quit the program */
    private class QuitAction extends AbstractAction {
        public QuitAction() {
            super("Ukončit", new ImageIcon(MainFrame.class.getResource(RES + "exit-small.png")));
            this.putValue(MNEMONIC_KEY,KeyEvent.VK_U);
        }
        public void actionPerformed(ActionEvent e) {
            MainFrame.this.formWindowClosing(null);
            System.exit(0);
        }
    }
    
    /** Show config frame */
    private class ConfigAction extends AbstractAction {
        private ConfigFrame configFrame;
        public ConfigAction() {
            super("Nastavení", new ImageIcon(MainFrame.class.getResource(RES + "config-small.png")));
            this.putValue(MNEMONIC_KEY,KeyEvent.VK_N);
        }
        public void actionPerformed(ActionEvent e) {
            if (configFrame != null && configFrame.isVisible()) {
                configFrame.requestFocus();
            } else {
                configFrame = new ConfigFrame();
                configFrame.setLocationRelativeTo(MainFrame.this);
                configFrame.setVisible(true);
            }
        }
    }
    
    /** import data from other programs */
    private class ImportAction extends AbstractAction {
        private ImportFrame importFrame;
        public ImportAction() {
            super("Import kontaktů", new ImageIcon(MainFrame.class.getResource(RES + "contact-small.png")));
            this.putValue(SHORT_DESCRIPTION,"Importovat kontakty z jiných aplikací");
            putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        }
        public void actionPerformed(ActionEvent e) {
            if (importFrame != null && importFrame.isVisible()) {
                importFrame.requestFocus();
            } else {
                importFrame = new ImportFrame();
                importFrame.setLocationRelativeTo(MainFrame.this);
                importFrame.setVisible(true);
            }
        }
    }
    
    /** export data for other programs */
    private class ExportAction extends AbstractAction {
        public ExportAction() {
            super("Export kontaktů", new ImageIcon(MainFrame.class.getResource(RES + "contact-small.png")));
            this.putValue(SHORT_DESCRIPTION,"Exportovat kontakty do souboru");
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        }
        public void actionPerformed(ActionEvent e) {
            ExportManager.exportContacts(MainFrame.this,contacts);
        }
    }
    
    /** compress current sms text by rewriting it to CamelCase */
    private class CompressAction extends AbstractAction {
        public CompressAction() {
            super("Zkomprimovat zprávu", new ImageIcon(MainFrame.class.getResource(RES + "compress.png")));
            this.putValue(SHORT_DESCRIPTION,"Vynechat z aktuální zprávy bílé znaky a přepsat ji do tvaru \"CamelCase\".");
            putValue(MNEMONIC_KEY, KeyEvent.VK_K);
        }
        public void actionPerformed(ActionEvent e) {
            smsPanel.compressSMS();
        }
    }
    
    /** Progress bar action listener after sending sms */
    private class SMSDelayActionListener implements ActionListener {
        private final int DELAY = 15;
        private int seconds = 0;
        public void actionPerformed(ActionEvent e) {
            if (seconds <= DELAY) { //still wainting
                smsDelayProgressBar.setValue(seconds);
                smsDelayProgressBar.setString("Další sms za: " + (DELAY-seconds) + "s");
                if (seconds == 0)
                    smsDelayProgressBar.setVisible(true);
                seconds++;
            } else { //delay finished
                smsDelayTimer.stop();
                smsDelayProgressBar.setVisible(false);
                seconds = 0;
                smsSender.setDelayed(false);
                smsSender.announceNewSMS();
            }
        }
    }
    
    /** Listens for events from sms queue */
    private class QueueListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (e.getID()) {
                //edit sms in queue
                case QueuePanel.ACTION_REQUEST_EDIT_SMS:
                    SMS sms = queuePanel.getEditRequestedSMS();
                    if (sms == null)
                        return;
                    contactPanel.clearSelection();
                    smsPanel.setSMS(sms);
                    break;
                case QueuePanel.ACTION_QUEUE_PAUSE_CHANGED:
                    smsSender.setPaused(queuePanel.isPaused());
                    break;
                default: System.err.println("Uknown queue event type: " + e.getID());
            }
        }
    }
    
    /** Listens for changes in contact list */
    private class ContactListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            smsPanel.setContacts(contactPanel.getSelectedContacts());
        }
    }
    
    /** Listens for changes in sms panel */
    private class SMSListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (e.getID()) {
                case SMSPanel.ACTION_REQUEST_CLEAR_CONTACT_SELECTION:
                    contactPanel.clearSelection();
                    break;
                case SMSPanel.ACTION_REQUEST_SELECT_CONTACT:
                    contactPanel.setSelectedContact(smsPanel.getRequestedContactSelection());
                    break;
                case SMSPanel.ACTION_SEND_SMS:
                    for (SMS sms : envelope.send()) {
                        queuePanel.addSMS(sms);
                        smsSender.announceNewSMS();
                    }
                    break;
                default: System.err.println("Uknown sms event type: " + e.getID());
            }
        }
    }
    
    private class UpdateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            printStatusMessage("Byla vydána nová verze programu!");
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem compressMenuItem;
    private javax.swing.JMenuItem configMenuItem;
    private esmska.gui.ContactPanel contactPanel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem exportMenuItem;
    private javax.swing.JSplitPane horizontalSplitPane;
    private javax.swing.JMenuItem importMenuItem;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu programMenu;
    private esmska.gui.QueuePanel queuePanel;
    private javax.swing.JProgressBar smsDelayProgressBar;
    private esmska.gui.SMSPanel smsPanel;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JSplitPane verticalSplitPane;
    // End of variables declaration//GEN-END:variables
}
