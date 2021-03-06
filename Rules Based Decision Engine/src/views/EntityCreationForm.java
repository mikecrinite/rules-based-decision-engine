package views;

import controllers.EntityController;
import services.EntityCollectionService;
import services.InputParserService;
import services.ObjectCollectionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

/**
 * A form which allows a user to create an Entity by hand.
 * The parts of an Entity are
 * -Name
 * -Map of keys which correspond to datapoints
 * @see models.Entity
 *
 * A user can create an Entity so that an existing Rule, which may otherwise not be fired, will compare to
 * the created Entity and fire.
 *
 * @author Michael Crinite
 * @version 1.0 12/07/2016
 */
public class EntityCreationForm extends JDialog
{
    //Components
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtEntityName;
    private JTable tblFields;
    private JButton addFieldButton;

    //Fields
    private DefaultTableModel fieldModel;

    /**
     * Calls an EntityCreationForm
     */
    public EntityCreationForm()
    {
        $$$setupUI$$$();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //Action Listeners
        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onSave();
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //Add Field Action Listener
        addFieldButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DefaultTableModel model = (DefaultTableModel) tblFields.getModel();
                //model.setRowCount(model.getRowCount() + 1);
                String[] row = {"", ""};
                model.addRow(row);
            }
        });

        pack();
        setVisible(true);
    }

    /**
     * Store keys and vals in their respective arrays, and pass those
     * and the name to an Entity creator
     *
     * Adds this Entity to a temporary collection so that the user can
     * select the Entity for comparison in a Condition of a Rule, but the object
     * will not be added to the collection.
     */
    private void onSave()
    {
        //TODO: Surround with try/catch(NumberFormatException) block
        /*
        Create Entity object
        Add object to collection
         */
        String name = txtEntityName.getText().toUpperCase();
        int rows = fieldModel.getRowCount();
        if (name.equals("") || name.replaceAll("\\s+", "").equals(""))
        {
            //Name field is blank
            JOptionPane.showMessageDialog(null, "The name field cannot be empty", "Empty name field", JOptionPane.ERROR_MESSAGE);

        }
        else if (rows == 0)
        {
            JOptionPane.showMessageDialog(null, "Your Entity has no fields", "", JOptionPane.ERROR_MESSAGE);

        }
        else
        {
            String[] tableKeys = new String[rows];
            String[] tableVals = new String[rows];
            HashSet<String> set = new HashSet<>();
            String key = "";
            for (int i = 0; i < rows; i++)
            {
                //TODO: Check for invalid input
                tableKeys[i] = (String) fieldModel.getValueAt(i, 0);
                tableVals[i] = (String) fieldModel.getValueAt(i, 1);
                key = name + "." + fieldModel.getValueAt(i, 0);
                //key = "\"" + name + "." + fieldModel.getValueAt(i, 0) + "\"";
                key = key.toUpperCase();
                set.add(key);
                InputParserService ips = InputParserService.getInstance();
                Object o = ips.dataConversion(fieldModel.getValueAt(i, 1));
                ObjectCollectionService.getInstance().putFromActions(key, o);
            }



            EntityController.getINSTANCE().createEntity(name, tableKeys, set, tableVals);

            String action = "";
            action += "Entity e = EntityCollectionService.getInstance().getFromActions(\"" + name + "\");\n";

            PlusNewActionDialog.actionString = action;

            PlusNewActionDialog.entityCreated = true;

            //TODO: Fix stupid exception where you have to click out of the box first
            dispose();
        }
    }

    /**
     * If an Entity is not created, uncheck the box in PlusActionDialog and allow the description
     * field to be edited.
     */
    private void onCancel()
    {
        PlusNewActionDialog.entityCreated = false;
        dispose();
    }

    /**
     * RuleTable must be custom-created
     */
    private void createUIComponents()
    {
        //Properties for ruleTable
        fieldModel = new DefaultTableModel(0, 2)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return true;
            }
        };
        fieldModel.setColumnIdentifiers(new String[]{"Field", "Value"});

        tblFields = new JTable(fieldModel)
        {
            public boolean isCellEditable(int row, int column)
            {
                return true;
            }
        };
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("Save");
        panel2.add(buttonOK, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Entity Name");
        panel3.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtEntityName = new JTextField();
        panel3.add(txtEntityName, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Fields");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(tblFields);
        addFieldButton = new JButton();
        addFieldButton.setText("Add Field");
        panel3.add(addFieldButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return contentPane;
    }
}
