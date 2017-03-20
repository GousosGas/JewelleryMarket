package jewel.buyer;

import jade.gui.TimeChooser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


import java.util.Date;


public class JewelBuyerGui extends JFrame implements JewelBuyerGuiInterface {

    private JewelBuyerAgent myAgent;

    private JTextField maxCostTF, deadlineTF;
    private JButton setDeadlineB;
    private JButton buyB, resetB, exitB;
    private JTextArea logTA;
    private String selectedType,selectedMaterial,selectedGem;

    private Date deadline;

    public JewelBuyerGui() {
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

        
        //List with jewel type
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
        TypeList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                 if (e.getStateChange() == ItemEvent.SELECTED) {
                 Object item = e.getItem();  
                 selectedType = item.toString();
                 }}});
        
         //List with jewel material
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
        MatList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                 if (e.getStateChange() == ItemEvent.SELECTED) {
                 Object item = e.getItem();  
                 selectedMaterial = item.toString();
                 }}});
        
         //List with jewel gem
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
        GemList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                 if (e.getStateChange() == ItemEvent.SELECTED) {
                 Object item = e.getItem();  
                 selectedGem = item.toString();
                 }}});
        
        l = new JLabel("Max Cost:");
        l.setHorizontalAlignment(SwingConstants.LEFT);
        l.setMinimumSize(new Dimension(70, 20));
        l.setPreferredSize(new Dimension(70, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
        rootPanel.add(l, gridBagConstraints);

        maxCostTF = new JTextField(64);
        maxCostTF.setMinimumSize(new Dimension(70, 20));
        maxCostTF.setPreferredSize(new Dimension(70, 20));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(5, 3, 0, 3);
        rootPanel.add(maxCostTF, gridBagConstraints);//max cost text field

     
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
                if (tc.showEditTimeDlg(JewelBuyerGui.this) == TimeChooser.OK) {
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
        buyB = new JButton("Buy");
        
        buyB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //final jewellery to buy e.x Golden Ruby Ring
                String title = selectedMaterial+" "+selectedGem+" "+selectedType;
                //int desiredCost = -1;
                int maxCost = -1;
                //If every characteristic of the jewel has been selected
                if ((TypeList.getSelectedIndex() != -1)&&
                        (MatList.getSelectedIndex() != -1)&&(GemList.getSelectedIndex() != -1)) {
                    //If time of availability has been set
                    if (deadline != null && deadline.getTime() > System.currentTimeMillis()) {

                            try {
                                maxCost = Integer.parseInt(maxCostTF.getText());
                                
                                myAgent.purchase(title, maxCost, deadline);
                                notifyUser("PUT FOR BUY: " + title + " at max " + maxCost + "by \n" + deadline);
                               					  			
                            } catch (Exception ex1) {
                                // Invalid max cost
                                JOptionPane.showMessageDialog(JewelBuyerGui.this, "Invalid max cost", "WARNING", JOptionPane.WARNING_MESSAGE);
                            }

                    } else {
                        // No deadline specified
                        JOptionPane.showMessageDialog(JewelBuyerGui.this, "Invalid deadline", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    // No jewellery title specified
                    JOptionPane.showMessageDialog(JewelBuyerGui.this, "No jewellery specified", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        resetB = new JButton("Reset");
        resetB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TypeList.setSelectedItem(null);
                MatList.setSelectedItem(null);
                GemList.setSelectedItem(null);
                maxCostTF.setText("");
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

        buyB.setPreferredSize(resetB.getPreferredSize());
        exitB.setPreferredSize(resetB.getPreferredSize());

        p.add(buyB);
        p.add(resetB);
        p.add(exitB);

        p.setBorder(new BevelBorder(BevelBorder.LOWERED));
        getContentPane().add(p, BorderLayout.SOUTH);

        pack();

        setResizable(false);
    }

    public void setAgent(JewelBuyerAgent a) {
        myAgent = a;
        setTitle(myAgent.getName());
    }

    public void notifyUser(String message) {
        logTA.append(message + "\n");
    }

}

