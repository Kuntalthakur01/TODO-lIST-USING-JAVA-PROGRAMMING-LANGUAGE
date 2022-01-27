import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.font.TextAttribute;

public class ToDo extends JFrame implements ActionListener {
    JFrame frame;
    JPanel headerPanel;
    JPanel addItemPanel;
    JTextField addItemField;
    private JButton addItemButton;
    JScrollPane itemsContainer;
    JPanel tasksPanel;
    ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
    ArrayList<JButton> deleteButtons = new ArrayList<>();
    ArrayList<JLabel> tasksLabel = new ArrayList<>();
    ArrayList<Boolean> markAsDone = new ArrayList<>(20);
    JPanel emptySubPanel;

    GridBagConstraints gbc = new GridBagConstraints();

    ArrayList list = new ArrayList();

    public ToDo() {
        header();
        tasks();
        addItemSection();

        frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(500, 600);
        frame.setTitle("To Do List");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(addItemPanel, BorderLayout.SOUTH);
        frame.add(itemsContainer, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ToDo obj = new ToDo();
    }

    public void header() {
        // JLabel header = new JLabel();

        JLabel header;
        headerPanel = new JPanel();
        header = new JLabel();
        header.setText("To Do list");
        ImageIcon bulb = new ImageIcon(
                new ImageIcon("idea_light_bulb_T.png").getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));

        header.setIcon(bulb);
        header.setHorizontalTextPosition(JLabel.RIGHT);
        header.setVerticalTextPosition(JLabel.CENTER);
        header.setFont(new Font("MV Boli", Font.BOLD, 35));
        header.setVerticalAlignment(JLabel.TOP);
        header.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(header);

    }

    public void addItemSection() {

        addItemPanel = new JPanel();
        addItemPanel.setPreferredSize(new Dimension(100, 100));


        addItemField = new JTextField();
        addItemField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        addItemField.setPreferredSize(new Dimension(250, 33));
        addItemField.addActionListener(this);
        addItemPanel.add(addItemField);
        addItemPanel.setLayout(new GridBagLayout());

        addItemButton = new JButton("Add Task");
        ImageIcon plusImg = new ImageIcon(
                new ImageIcon("R.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        addItemButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        addItemButton.setIcon(plusImg);
        addItemButton.setFocusable(false);
        addItemButton.setBackground(Color.WHITE);
        addItemButton.addActionListener(this);
        addItemPanel.add(addItemButton);
    }

    public void tasks() {

        tasksPanel = new JPanel();

        renderTasks("intitial");
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.PAGE_AXIS));
        itemsContainer = new JScrollPane(tasksPanel);
    }

    public JPanel item(String s, int index, String action) {
        JPanel subPanel = new JPanel();

        if (action == "add" && index == list.size() - 1) {
            tasksLabel.add(new JLabel());
            deleteButtons.add(new JButton());
            markAsDone.add(false);
            checkBoxes.add(new JCheckBox());
            deleteButtons.get(index).addActionListener(this);
            checkBoxes.get(index).addActionListener(this);

        }

        tasksLabel.get(index).setText(s);

        if (markAsDone.get(index) == true) {

            Font font = new Font("Comic Sans MS", Font.PLAIN, 18);
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            Font newFont = new Font(attributes);
            tasksLabel.get(index).setFont(newFont);

        } else {
            markAsDone.set(index, false);
            tasksLabel.get(index).setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        }

        ImageIcon trashImg = new ImageIcon(
                new ImageIcon("/Users/kuntal/Documents/java mini project/trash2.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

        deleteButtons.get(index).setIcon(trashImg);
        deleteButtons.get(index).setBackground(Color.white);
        deleteButtons.get(index).setFocusable(false);



        subPanel.setLayout(new BorderLayout());



        tasksLabel.get(index).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        subPanel.add(checkBoxes.get(index), BorderLayout.WEST);
        subPanel.add(tasksLabel.get(index), BorderLayout.CENTER);
        subPanel.add(deleteButtons.get(index), BorderLayout.AFTER_LINE_ENDS);

        subPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return subPanel;
    }

    public void renderTasks(String action) {
        tasksPanel.removeAll();

        JPanel emptySubPanel = new JPanel();

        if (list.size() == 0) {
            JLabel empty = new JLabel();
            empty.setText("No Tasks Remaining!!");
            empty.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            emptySubPanel.add(empty);
            tasksPanel.add(emptySubPanel);
            tasksPanel.validate();

        } else {
            emptySubPanel.setVisible(false);
            for (int i = 0; i < list.size(); i++) {
                tasksPanel.add(item(i + 1 + ") " + list.get(i), i, action), BorderLayout.SOUTH);
            }
            tasksPanel.setLayout(new GridLayout(list.size() < 10 ? 10 : list.size(), 3));


            tasksPanel.validate();
            tasksPanel.repaint();
            itemsContainer.revalidate();

        }
    }

    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == addItemButton || e.getSource() == addItemField) {
            // add

            list.add(addItemField.getText());
            addItemField.setText("");
            // System.out.println(list.size());

            renderTasks("add");

        } else if (deleteButtons.contains(e.getSource())) {
            // remove

            int index = deleteButtons.indexOf(e.getSource());
            list.remove(index);
            checkBoxes.remove(index);
            deleteButtons.remove(index);
            tasksLabel.remove(index);
            markAsDone.remove(index);

            renderTasks("delete");

        } else if (checkBoxes.contains(e.getSource())) {
            // Checkbox

            int index = checkBoxes.indexOf(e.getSource());

            markAsDone.set(index, !markAsDone.get(index));

            renderTasks("checkbox");

        }
    }

}