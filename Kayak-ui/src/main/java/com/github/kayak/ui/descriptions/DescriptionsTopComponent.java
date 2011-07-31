/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kayak.ui.descriptions;

import java.util.List;
import javax.swing.Action;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//com.github.kayak.ui.descriptions//Descriptions//EN",
autostore = false)
@TopComponent.Description(preferredID = "DescriptionsTopComponent",
iconBase="org/freedesktop/tango/16x16/mimetypes/text-x-generic.png", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "management", openAtStartup = true)
@ActionID(category = "Window", id = "com.github.kayak.ui.descriptions.DescriptionsTopComponent")
@ActionReference(path = "Menu/Window", position = 10)
@TopComponent.OpenActionRegistration(displayName = "#CTL_DescriptionsAction",
preferredID = "DescriptionsTopComponent")
public final class DescriptionsTopComponent extends TopComponent implements ExplorerManager.Provider {

    ExplorerManager manager = new ExplorerManager();
    DescriptionsNodeFactory factory = new DescriptionsNodeFactory();

    public DescriptionsTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(DescriptionsTopComponent.class, "CTL_DescriptionsTopComponent"));
        setToolTipText(NbBundle.getMessage(DescriptionsTopComponent.class, "HINT_DescriptionsTopComponent"));
        
        AbstractNode rootNode = new AbstractNode(Children.create(factory, false));
        manager.setRootContext(rootNode);
        associateLookup(ExplorerUtils.createLookup(manager, getActionMap()));
        initToolbar();
    }

    private void initToolbar() {
        List<? extends Action> actions = Utilities.actionsForPath("Actions/Descriptions");
        
        for(Action a : actions) {
            jToolBar1.add(a);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                beanTreeView1 = new org.openide.explorer.view.BeanTreeView();
                jToolBar1 = new javax.swing.JToolBar();

                beanTreeView1.setRootVisible(false);

                jToolBar1.setFloatable(false);
                jToolBar1.setRollover(true);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                        .addComponent(beanTreeView1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(beanTreeView1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE))
                );
        }// </editor-fold>//GEN-END:initComponents

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private org.openide.explorer.view.BeanTreeView beanTreeView1;
        private javax.swing.JToolBar jToolBar1;
        // End of variables declaration//GEN-END:variables

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }
}
