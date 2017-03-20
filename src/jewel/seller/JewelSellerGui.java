package jewel.seller;

import jade.gui.TimeChooser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.Date;

/**
 * This is the GUI of the agent that tries to sell books on behalf of its user
 */
public class JewelSellerGui extends JFrame implements JewelSellerGuiInterface {

    private JewelSellerAgent myAgent;

    private JTextField desiredPriceTF, PriceTF, deadlineTF;
    private JButton setDeadlineB;
    private JButton setCCB, sellB, resetB, exitB;
    private JTextArea logTA;
     private String selectedType,selectedMaterial,selectedGem;

    private Date deadline;

    public void setAgent(JewelSellerAgent a) {
        myAgent = a;
        setTitle(myAgent.getName());
    }

    public JewelSellerGui() {
        super();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myAgent.doDelete();
            }
        });

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new GridBagLayout());
        rootPanel.setMinimumSize(new Dimension(430, 180));
        rootPanel.setPreferredSize(new Dimension(430, 180));

       
        //Combo box with jewel type
        JLabel l = new JLabel("Jewellery type:");
        l.setHorizontalAlignment(SwingConstants.LEFT);
        l.setMinimumSize(new Dimension(90, 20));
        l.setPreferredSize(new Dimension(90, 20));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
        rootPanel.add(l, gridBagConstraints);

        String[] JewelType = new String[] {"Necklace","Ring","Bracelet"};
        JComboBox TypeList = new JComboBox(JewelType);
        TypeList.setSelectedItem(null);
        TypeList.setMinimumSize(new Dimension(130, 20));
        TypeList.setPreferredSize(new Dimension(130, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(5, 3, 0, 3);
        rootPanel.add(TypeList, gridBagConstraints);
        //Listener to get the selected Type
        TypeList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                 if (e.getStateChange() == ItemEvent.SELECTED) {
                 Object item = e.getItem();  
                 selectedType = item.toString();
                 }}});
        
        //Combo box with jewel material
        l = new JLabel("Jewellery material:");
        l.setHorizontalAlignment(SwingConstants.LEFT);
        l.setMinimumSize(new Dimension(110, 20));
        l.setPreferredSize(new Dimension(110, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
        rootPanel.add(l, gridBagConstraints);

        String[] JewelMat = new String[] {"Gold","Silver","Platinum"};
        JComboBox MatList = new JComboBox(JewelMat);
        MatList.setSelectedItem(null);
        MatList.setMinimumSize(new Dimension(130, 20));
        MatList.setPreferredSize(new Dimension(130, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(5, 3, 0, 3);
        rootPanel.add(MatList, gridBagConstraints);
        //Listener to get the selected Material
        MatList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                 if (e.getStateChange() == ItemEvent.SELECTED) {
                 Object item = e.getItem();  
                 selectedMaterial = item.toString();
                 }}});
        
        //Combo box with jewel gem
        l = new JLabel("Jewellery gem:");
        l.setHorizontalAlignment(SwingConstants.LEFT);
        l.setMinimumSize(new Dimension(95, 20));
        l.setPreferredSize(new Dimension(95, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
        rootPanel.add(l, gridBagConstraints);

        String[] JewelGem = new String[] {"Diamond","Ruby","Sapphire","Emerald"};
        JComboBox GemList = new JComboBox(JewelGem);
        GemList.setSelectedItem(null);
        GemList.setMinimumSize(new Dimension(130, 20));
        GemList.setPreferredSize(new Dimension(130, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(5, 3, 0, 3);
        rootPanel.add(GemList, gridBagConstraints);
        //Listener to get the selected Gem
        GemList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                 if (e.getStateChange() == ItemEvent.SELECTED) {
                 Object item = e.getItem();  
                 selectedGem = item.toString();
                 }}});
      
        l = new JLabel("Price:");
        l.setHorizontalAlignment(SwingConstants.LEFT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
        rootPanel.add(l, gridBagConstraints);

        PriceTF = new JTextField(64);
        PriceTF.setMinimumSize(new Dimension(70, 20));
        PriceTF.setPreferredSize(new Dimension(70, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(5, 3, 0, 3);
        rootPanel.add(PriceTF, gridBagConstraints);

        l = new JLabel("Deadline:");
        l.setHorizontalAlignment(SwingConstants.LEFT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
        rootPanel.add(l, gridBagConstraints);

        deadlineTF = new JTextField(64);
        deadlineTF.setMinimumSize(new Dimension(146, 20));
        deadlineTF.setPreferredSize(new Dimension(146, 20));
        deadlineTF.setEnabled(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(5, 3, 0, 3);
        rootPanel.add(deadlineTF, gridBagConstraints);

        setDeadlineB = new JButton("Set");
        setDeadlineB.setMinimumSize(new Dimension(70, 20));
        setDeadlineB.setPreferredSize(new Dimension(70, 20));
        
        
        setDeadlineB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date d = deadline;
                if (d == null) {
                    d = new Date();
                }
                TimeChooser tc = new TimeChooser(d);
                if (tc.showEditTimeDlg(JewelSellerGui.this) == TimeChooser.OK) {
                    deadline = tc.getDate();
                    deadlineTF.setText(deadline.toString());
                }
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(5, 3, 0, 3);
        rootPanel.add(setDeadlineB, gridBagConstraints);

        rootPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        getContentPane().add(rootPanel, BorderLayout.NORTH);

        logTA = new JTextArea();
        logTA.setEnabled(false);
        JScrollPane jsp = new JScrollPane(logTA);
        jsp.setMinimumSize(new Dimension(360, 300));
        jsp.setPreferredSize(new Dimension(360, 300));
        JPanel p = new JPanel();
        p.setBorder(new BevelBorder(BevelBorder.LOWERED));
        p.add(jsp);
        getContentPane().add(p, BorderLayout.CENTER);

        p = new JPanel();
        sellB = new JButton("Sell");
        sellB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                
                //final jewellery name to sell e.x Golden Ruby Ring
                String title = selectedMaterial+" "+selectedGem+" "+selectedType; 
                //int desiredPrice = -1;
                int price;
                 if ((TypeList.getSelectedIndex() != -1)&&
                        (MatList.getSelectedIndex() != -1)&&(GemList.getSelectedIndex() != -1)) {
                    if (deadline != null && deadline.getTime() > System.currentTimeMillis()) {
                        
                            try {
                                price = Integer.parseInt(PriceTF.getText());
                                
                                    myAgent.putForSale(title, price,  deadline);
                                    notifyUser("PUT FOR SALE: " + title + " in Price "+price+" by \n" + deadline);
                                }
                                 catch (Exception ex1) {
                                // Invalid max cost
                                JOptionPane.showMessageDialog(JewelSellerGui.this, "Invalid price", "WARNING", JOptionPane.WARNING_MESSAGE);
                            }
                      
                        }else {
                        // No deadline specified
                        JOptionPane.showMessageDialog(JewelSellerGui.this, "Invalid deadline", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    // No jewellery specified
                    JOptionPane.showMessageDialog(JewelSellerGui.this, "No jewellery specified", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        resetB = new JButton("Reset");
        resetB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TypeList.setSelectedItem(null);
                MatList.setSelectedItem(null);
                GemList.setSelectedItem(null);
                PriceTF.setText("");
                deadlineTF.setText("");
                deadline = null;
            }
        });
        exitB = new JButton("Exit");
        exitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myAgent.doDelete();
            }
        });

        sellB.setPreferredSize(resetB.getPreferredSize());
        exitB.setPreferredSize(resetB.getPreferredSize());

        p.add(sellB);
        p.add(resetB);
        p.add(exitB);

        p.setBorder(new BevelBorder(BevelBorder.LOWERED));
        getContentPane().add(p, BorderLayout.SOUTH);

        pack();

        setResizable(false);
    }

    public void notifyUser(String message) {
        logTA.append(message + "\n");
    }
}
