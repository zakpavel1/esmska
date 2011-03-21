/*
 * AboutFrame.java
 *
 * Created on 8. červenec 2007, 13:18
 */

package esmska.gui;

import esmska.data.Config;
import esmska.data.Icons;
import esmska.utils.L10N;
import esmska.data.Links;
import esmska.utils.MiscUtils;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.apache.commons.io.IOUtils;
import org.jdesktop.swingx.JXHyperlink;
import org.openide.awt.Mnemonics;

/** About form
 *
 * @author  ripper
 */
public class AboutFrame extends javax.swing.JFrame {
    private static final Logger logger = Logger.getLogger(AboutFrame.class.getName());
    private static final String RES = "/esmska/resources/";
    private static final ResourceBundle l10n = L10N.l10nBundle;
    private static final Config config = Config.getInstance();
    
    /** Creates new form AboutFrame */
    public AboutFrame() {
        initComponents();
        closeButton.requestFocusInWindow();
        this.getRootPane().setDefaultButton(closeButton);

        //set window images
        ArrayList<Image> images = new ArrayList<Image>();
        images.add(Icons.get("about-16.png").getImage());
        images.add(Icons.get("about-22.png").getImage());
        images.add(Icons.get("about-32.png").getImage());
        images.add(Icons.get("about-48.png").getImage());
        setIconImages(images);

        //close on Ctrl+W
        String command = "close";
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(
                KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), command);
        getRootPane().getActionMap().put(command, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeButtonActionPerformed(e);
            }
        });
    }

    /** Same as JXHyperlink.setURI(), but don't throw exception */
    private void setURI(JXHyperlink jXHyperlink, URI uri) {
        try {
            jXHyperlink.setURI(uri);
        } catch (UnsupportedOperationException ex) {
            logger.log(Level.WARNING, "System does not support Desktop API, this URI won't work: " + uri, ex);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        creditsButton = new JButton();
        closeButton = new JButton();
        licenseButton = new JButton();
        jLabel5 = new JLabel();
        jLabel4 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        homeHyperlink = new JXHyperlink();
        supportHyperlink = new JXHyperlink();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ResourceBundle bundle = ResourceBundle.getBundle("esmska/resources/l10n"); // NOI18N
        setTitle(bundle.getString("AboutFrame.title")); // NOI18N
        setLocationByPlatform(true);

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/esmska/resources/esmska.png"))); // NOI18N
        jLabel1.setFocusable(false);

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | Font.BOLD, jLabel2.getFont().getSize()+22));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        Mnemonics.setLocalizedText(jLabel2, "Esmska " + config.getLatestVersion());
        jLabel2.setFocusable(false);

        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        Mnemonics.setLocalizedText(jLabel3, bundle.getString("AboutFrame.jLabel3.text")); // NOI18N
        jLabel3.setFocusable(false);

        creditsButton.setIcon(new ImageIcon(getClass().getResource("/esmska/resources/about-22.png"))); // NOI18N
        Mnemonics.setLocalizedText(creditsButton, bundle.getString("AboutFrame.creditsButton.text"));
        creditsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                creditsButtonActionPerformed(evt);
            }
        });

        closeButton.setIcon(new ImageIcon(getClass().getResource("/esmska/resources/close-22.png"))); // NOI18N
        Mnemonics.setLocalizedText(closeButton, bundle.getString("Close_"));
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        Mnemonics.setLocalizedText(licenseButton, bundle.getString("AboutFrame.licenseButton.text"));
        licenseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                licenseButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getSize()-2f));
        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setIcon(new ImageIcon(getClass().getResource("/esmska/resources/copyleft-12.png"))); // NOI18N
        Mnemonics.setLocalizedText(jLabel5, bundle.getString("AboutFrame.jLabel5.text")); // NOI18N
        jLabel5.setFocusable(false);



        setURI(homeHyperlink, Links.getURI(Links.HOMEPAGE));
        Mnemonics.setLocalizedText(homeHyperlink, l10n.getString("AboutFrame.homeHyperlink.text"));
        homeHyperlink.setToolTipText(l10n.getString("AboutFrame.homeHyperlink.toolTipText")); // NOI18N
        setURI(supportHyperlink, Links.getURI(Links.DONATE));
        Mnemonics.setLocalizedText(supportHyperlink, l10n.getString("AboutFrame.supportHyperlink.text"));
        supportHyperlink.setToolTipText(l10n.getString("AboutFrame.supportHyperlink.toolTipText")); // NOI18N
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jLabel5, Alignment.CENTER, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                    .addComponent(homeHyperlink, Alignment.CENTER, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(supportHyperlink, Alignment.CENTER, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, Alignment.CENTER, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                    .addComponent(jLabel2, Alignment.CENTER, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                    .addComponent(jLabel1, Alignment.CENTER, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(creditsButton)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(licenseButton)
                        .addPreferredGap(ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addComponent(closeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                        .addGap(108, 108, 108)
                        .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                        .addGap(138, 138, 138)
                        .addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {closeButton, creditsButton, licenseButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)))
                    .addComponent(homeHyperlink, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(supportHyperlink, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(creditsButton)
                    .addComponent(closeButton)
                    .addComponent(licenseButton))
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {jLabel4, jLabel6});

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {closeButton, creditsButton, licenseButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents
            
    private void licenseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_licenseButtonActionPerformed
        //show licence
        try {
            logger.fine("Showing license...");
            String license = IOUtils.toString(
                    getClass().getResourceAsStream(RES + "license.txt"), "UTF-8");
            final String agpl = IOUtils.toString(
                            getClass().getResourceAsStream(RES + "gnu-agpl.txt"), "UTF-8");
            license = MiscUtils.escapeHtml(license);
            license = license.replaceAll("GNU Affero General Public License", 
                    "<a href=\"agpl\">GNU Affero General Public License</a>");
            
            final JTextPane tp = new JTextPane();
            tp.setContentType("text/html; charset=UTF-8");
            tp.setText("<html><pre>" + license + "</pre></html>");
            tp.setEditable(false);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            tp.setPreferredSize(new Dimension((int)d.getWidth()/2,(int)d.getHeight()/2)); //reasonable size
            tp.setCaretPosition(0);
            //make links clickable
            tp.addHyperlinkListener(new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(final HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        logger.fine("Showing GNU AGPL...");
                        tp.setText(null);
                        tp.setContentType("text/plain");
                        tp.setText(agpl);
                        tp.setCaretPosition(0);
                    }
                }
            });
            
            String option = l10n.getString("AboutFrame.Acknowledge");
            JOptionPane op = new JOptionPane(new JScrollPane(tp),JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, new Object[]{option}, option);
            JDialog dialog = op.createDialog(this,l10n.getString("AboutFrame.License"));
            dialog.setResizable(true);
            dialog.pack();
            dialog.setVisible(true);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Could not show license", ex);
        }
    }//GEN-LAST:event_licenseButtonActionPerformed
    
    private void creditsButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_creditsButtonActionPerformed
        //show credits
        try {
            logger.fine("Showing credits...");
            String credits = IOUtils.toString(
                    getClass().getResourceAsStream(RES + "credits.html"), "UTF-8");
            String translators = l10n.getString("Translators");
            if ("translator-credits".equals(translators)) {
                //there are no translators mentioned
                translators = "";
            } else {
                translators = translators.replaceAll("\n", "<br>\n").
                    replaceAll("\n  ", "\n&nbsp;&nbsp;");
                //add hyperlinks to the Launchpad URLs
                translators = translators.replaceAll("(https://[^<]*)", 
                        "<a href=\"$1\">$1</a>");
            }
            
            String document = MessageFormat.format(credits, l10n.getString("Credits.authors"),
                    l10n.getString("Credits.contributors"), l10n.getString("Credits.graphics"),
                    l10n.getString("Credits.sponsors"), l10n.getString("Credits.translators"),
                    translators, Links.DONATORS, l10n.getString("Credits.moreDonators"),
                    MessageFormat.format(l10n.getString("Credits.packagers"), Links.DOWNLOAD));
            
            JTextPane tp = new JTextPane();
            tp.setContentType("text/html; charset=UTF-8");
            tp.setText(document);
            tp.setEditable(false);
            tp.setPreferredSize(new Dimension(450, 400));
            tp.setCaretPosition(0);
            //make links clickable
            tp.addHyperlinkListener(new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(final HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED &&
                            Desktop.isDesktopSupported()) {
                        try {
                            logger.fine("Browsing URL: " + e.getURL());
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (Exception ex) {
                            logger.log(Level.SEVERE, "Can't browse hyperlink: " + e.getURL(), ex);
                        }
                    }
                }
            });
            
            String option = l10n.getString("AboutFrame.Thank_you");
            JOptionPane op = new JOptionPane(new JScrollPane(tp),JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, new Object[]{option}, option);
            JDialog dialog = op.createDialog(this,l10n.getString("AboutFrame.Credits"));
            dialog.setResizable(true);
            dialog.pack();
            dialog.setVisible(true);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not show credits", e);
        }
    }//GEN-LAST:event_creditsButtonActionPerformed
    
    private void closeButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton closeButton;
    private JButton creditsButton;
    private JXHyperlink homeHyperlink;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JButton licenseButton;
    private JXHyperlink supportHyperlink;
    // End of variables declaration//GEN-END:variables
    
}
