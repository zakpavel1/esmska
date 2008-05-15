/*
 * @(#) MacIntegration.java     10-05-2008  18:59
 */
package esmska.integration;

import java.awt.event.ActionEvent;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationEvent;
import com.apple.eawt.ApplicationListener;

/**
 * Integration for Mac OS X.
 * 
 * @author Marian Boucek
 * @version 1.0
 */
public class MacIntegration extends IntegrationAdapter implements ApplicationListener {
  
  private Application app;
  
  /**
   * @see esmska.utils.mac.IntegrationAdapter#initialize()
   */
  @Override
  protected void initialize() {
    app = new Application();
    
    app.setEnabledAboutMenu(true);
    app.setEnabledPreferencesMenu(true);
    
    app.addApplicationListener(this);
  }
  
  // public interface -------------------------------------------------------
  /**
   * @see esmska.utils.mac.IntegrationAdapter#setSMSCount(Integer)
   */
  @Override
  public void setSMSCount(Integer count) {
      if (count != null) {
        app.setDockIconBadge(count.toString());
      } else {
        app.setDockIconBadge(null);
      }
  }
  
  // implementation of app interface ------------------------------------------
  /**
   * @see com.apple.eawt.ApplicationListener#handleAbout(com.apple.eawt.ApplicationEvent)
   */
  @Override
  public void handleAbout(ApplicationEvent e) {
    e.setHandled(true);
    bean.getAboutAction().actionPerformed(new ActionEvent(e.getSource(), 
            ActionEvent.ACTION_PERFORMED, "aboutSelected"));
  }
  
  /**
   * @see com.apple.eawt.ApplicationListener#handleOpenApplication(com.apple.eawt.ApplicationEvent)
   */
  @Override
  public void handleOpenApplication(ApplicationEvent e) {
    e.setHandled(false);
  }
  
  /**
   * @see com.apple.eawt.ApplicationListener#handleOpenFile(com.apple.eawt.ApplicationEvent)
   */
  @Override
  public void handleOpenFile(ApplicationEvent e) {
    e.setHandled(false);
  }
  
  /**
   * @see com.apple.eawt.ApplicationListener#handlePreferences(com.apple.eawt.ApplicationEvent)
   */
  @Override
  public void handlePreferences(ApplicationEvent e) {
    e.setHandled(true);
    bean.getConfigAction().actionPerformed(new ActionEvent(e.getSource(),
            ActionEvent.ACTION_PERFORMED, "configSelected"));
  }
  
  /**
   * @see com.apple.eawt.ApplicationListener#handlePrintFile(com.apple.eawt.ApplicationEvent)
   */
  @Override
  public void handlePrintFile(ApplicationEvent e) {
    e.setHandled(false);
  }
  
  /**
   * @see com.apple.eawt.ApplicationListener#handleQuit(com.apple.eawt.ApplicationEvent)
   */
  @Override
  public void handleQuit(ApplicationEvent e) {
    e.setHandled(true);
    bean.getQuitAction().actionPerformed(new ActionEvent(e.getSource(),
            ActionEvent.ACTION_PERFORMED, "quitSelected"));
  }
  
  /**
   * @see com.apple.eawt.ApplicationListener#handleReOpenApplication(com.apple.eawt.ApplicationEvent)
   */
  @Override
  public void handleReOpenApplication(ApplicationEvent e) {
    e.setHandled(false);
  }
}