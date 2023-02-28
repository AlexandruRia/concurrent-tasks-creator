package alex;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TableUI extends JFrame {

    private static final long serialVersionUID = 1L;

    public static DefaultTableModel model;
    JTable table;

    public void createUI() {
        JFrame frame = new JFrame();

        String[] text = {"Name","Status","Result"};

        model = new DefaultTableModel(text,0);

        table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

                Component c = super.prepareRenderer(renderer, row, column);
                Object type = getModel().getValueAt(row, column);

                if(type =="Pending") {
                    c.setBackground(Color.YELLOW);
                    c.setForeground(Color.BLACK); }

                else if (type == "Running") {
                    c.setBackground(Color.blue);
                    c.setForeground(Color.WHITE);}

                else if (type == "Failed") {
                    c.setBackground(Color.red);
                    c.setForeground(Color.WHITE);}

                else if(type == "Completed"){
                    c.setBackground(Color.GREEN);
                    c.setForeground(Color.BLACK);
                }
                else {
                    c.setBackground(Color.white);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        };


        JPanel btnPnl = new JPanel(new BorderLayout());

        JPanel bottombtnPnl = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));

        JButton button = new JButton("Add");

        ThreadPoolExecutor exec = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        button.addActionListener(new ActionListener() {
            public int i = 0;
            @Override
            public void actionPerformed(ActionEvent e) {

                if(model.getRowCount() > 19) {

                    for (int k = 0; k < 20; k++) {
                        if(model.getValueAt(k, 1) =="Completed" || model.getValueAt(k, 1) == "Failed") {

                            model.removeRow(k);

                            Task task = new Task(k);
                            model.insertRow(k%20, new Object[]{"task " + i});
                            model.setValueAt("Pending",k, 1);
                            model.fireTableDataChanged();

                            exec.execute(task);
                            i++;
                            break;

                        }
                    }
                }

                else {
                    Task task = new Task(i);

                    model.insertRow(i, new Object[]{"task " + task.number});
                    model.setValueAt("Pending",i, 1);
                    model.fireTableDataChanged();
                    exec.execute(task);

                    i++;
                }
            }
        });

        bottombtnPnl.add(button);

        btnPnl.add(bottombtnPnl, BorderLayout.CENTER);

        frame.add(table.getTableHeader(), BorderLayout.NORTH);
        frame.add(table, BorderLayout.CENTER);
        frame.add(btnPnl, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(300, 413);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new TableUI().createUI();

    }
}
