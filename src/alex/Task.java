package alex;

import javax.swing.table.DefaultTableModel;

public class Task implements Runnable {

    int number;
    int result;

    public Task(int number) {
        this.number = number;
    }

    @Override
    public void run() {

        DefaultTableModel model = TableUI.model;

        model.setValueAt("Running", number%20, 1);
        model.fireTableDataChanged();

        int result = (int) (Math.random() * 10);

        for (int i = 0; i < 100; i++) {
            result+= (Math.random()*(42000000 - 1000)+1000);

            try {
                Thread.sleep((long) (Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(result < Integer.MAX_VALUE) {
            model.setValueAt("Completed",number%20, 1);
            model.setValueAt(result,number%20, 2);
            model.fireTableDataChanged();

        }
        else {
            model.setValueAt("Failed",number%20, 1);
            model.fireTableDataChanged();

        }
    }
}
