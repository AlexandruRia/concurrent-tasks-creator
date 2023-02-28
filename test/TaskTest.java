
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TaskTest {

    @Test
    public void test_task() {

        int number = (int) (Math.random() * 10);
        String result = null ;

        for (int i = 0; i < 100; i++) {
            number+= (Math.random()*(42000000 - 1000)+1000);

            if(number < Integer.MAX_VALUE) {
                result = "Completed";
            }
            else {
                result = "Failed";
            }
        }

        if(number < Integer.MAX_VALUE)
            assertTrue(result=="Completed");
        else {assertTrue(result == "Failed");}

    }

    @Test
    public void test_row_limit() {

        DefaultTableModel model;

        String[] text = {null,null,null};

        model = new DefaultTableModel(text,0);

        for (int i = 0; i < 50; i++) {
            if(model.getRowCount() < 5) {
                model.addRow(text);
            }
            else {model.removeRow(i%5);
                model.insertRow(i%5, text); }
        }

        assertTrue(model.getRowCount() == 5);
    }

    @Test
    public void test_model() {


        DefaultTableModel model;

        String[] text = {"Name","Status","Result"};

        model = new DefaultTableModel(text,0);
        JTable table = new JTable(model);
        table.getColumnName(0);

        assertEquals("Name", model.getColumnName(0));
        assertEquals("Status", model.getColumnName(1));
        assertEquals("Result", model.getColumnName(2));

        assertEquals(3, model.getColumnCount());

        model.addRow(new Object[] {15,"Test"});

        assertEquals(15, model.getValueAt(0, 0));
        assertEquals("Test", model.getValueAt(0, 1));
        assertEquals(1, model.getRowCount());

        model.addRow(new Object[] {16,"Test2"});
        assertEquals(2, model.getRowCount());


    }


    @Test
    public void test_task_run_time() {

        long start = System.currentTimeMillis();

        @SuppressWarnings("unused")
        int number = (int) (Math.random() * 10);

        for (int i = 0; i < 100; i++) {
            number+= (Math.random()*(42000000 - 1000)+1000);

            try {
                Thread.sleep((long) (Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        long time = end - start;

        assertTrue(time < 10000);
    }


    @Test
    void test_frame() {

        JFrame frame = new JFrame();
        JButton button = new JButton();

        frame.setSize(700, 600);
        frame.add(button);

        assertEquals(600,frame.getSize().height);
        assertEquals(700,frame.getSize().width);

        assertEquals(1,frame.getComponentCount());

        assertFalse(frame.isVisible());
    }

    @Test
    void test_button() {

        JButton button = new JButton();
        JButton button2 = new JButton();

        button.setBackground(Color.GREEN);

        button.setSize(100, 250);
        button.add(button2);

        assertEquals(250,button.getSize().height);
        assertEquals(100,button.getSize().width);

        assertEquals(Color.GREEN,button.getBackground());

        assertEquals(1,button.getComponentCount());

    }


}