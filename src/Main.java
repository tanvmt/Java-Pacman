import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Window extends JPanel {
    protected final int MAX_X = 1366;
    protected final int MAX_Y = 768;
    protected final int BLOCK_SIZE = 24;
    protected int N_BLOCKS;
    protected int SCREEN_SIZE;
    protected short[] screenData; 
    
    protected JButton createButton(String text, int x, int y, int width, int height, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 40));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.RED);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(listener);
        return button;
    }

    protected JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.RED);
        // label.setBorder(BorderFactory.createBevelBorder(0));
        label.setBounds(x, y, 150, 50);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public List<String[]> readFileScoreV2(String filePath) {
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            
            while (line != null) {
                String[] value = line.split(",");
                dataList.add(value);  // Thêm mảng [name, score] vào danh sách
                line = br.readLine();
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(dataList, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                // Chuyển điểm từ String sang Double để so sánh
                double score1 = Double.parseDouble(o1[1]);
                double score2 = Double.parseDouble(o2[1]);
                return Double.compare(score2, score1); // Sắp xếp giảm dần
            }
        });

        return dataList;
    }
}

class MainPanel extends Window implements ActionListener {
    Menu menu;
    Instructions instructions;
    Highscores highscores;
    CardLayout cl;
    Image logo, icon1, icon2;
    GameEngine gameEngine;

    MainPanel() {
        initClasses();
        addPanels();
        setBackground(Color.BLACK);
    }

    private void initClasses(){
        cl = new CardLayout();
        setLayout(cl);
        menu = new Menu();
        instructions = new Instructions();
        highscores = new Highscores();
        gameEngine = new GameEngine();
    }

    private void addPanels() {
        this.add("Menu", menu);
        this.add("Instructions", instructions);
        this.add("Highscores", highscores);
        this.add("GameEngine", gameEngine);
    }

    private void loadImages() {
        logo = new ImageIcon("src/Image/logo.png").getImage();
        icon1 = new ImageIcon("src/Image/menu_icon.png").getImage();
        icon2 = new ImageIcon("src/Image/menu_icon_2.png").getImage();
    }

    private void drawLogo(Graphics2D g2D) {
        g2D.drawImage(logo, 251, 70, this);
        g2D.drawImage(icon1, 0, 0, this);
        g2D.drawImage(icon1, 0, 676, this);
        g2D.drawImage(icon2, 1296, 0, this);
        g2D.drawImage(icon2, 1296, 676, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLogo((Graphics2D) g);
    }

    class Menu extends Window {
        JButton[] menuButtons = new JButton[4];

        Menu() {
            initButtons();
            loadImages();
            setLayout(null);
            setBackground(Color.BLACK);
        }

        private void initButtons() {
            String[] labels = { "PLAY", "INSTRUCTIONS", "HIGHSCORES", "QUIT" };
            for (int i = 0; i < 4; i++) {
                menuButtons[i] = createButton(labels[i], MAX_X / 2 - 200, MAX_Y / 2 - 50 + 100 * i, 400, 50,
                        MainPanel.this);
                this.add(menuButtons[i]);
            }
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawLogo((Graphics2D) g);
        }
    }

    class Instructions extends Window {
        JButton backButton;
        JScrollPane instructionsArea;
        private JTextArea textArea;

        Instructions() {
            setLayout(null);
            setBackground(Color.BLACK);
            backButton = createButton("BACK", MAX_X / 2 - 200, MAX_Y / 2 + 250, 400, 50, MainPanel.this);

            String text = "src/Instructions.txt";
            instructionsArea = createTextArea(text, MAX_X / 2 - 300, MAX_Y / 2 - 100);

            this.add(backButton);
            this.add(instructionsArea);
        }

        public JScrollPane createTextArea(String text, int x, int y) {
            JTextArea area = new JTextArea(text, 16, 150);

            area.setBounds(x - 200, y - 200, 1000, 500);
            area.setForeground(Color.RED);
            area.setFont(new Font("Arial", Font.BOLD, 20));
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setEditable(false);
            area.setOpaque(false);
            String content = readFile(text);
            area.setText(content);


            JScrollPane scrollPane = new JScrollPane(area);
            scrollPane.setBounds(x - 200, y - 200, 1000, 500); // Thiết lập vị trí và kích thước
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Thanh cuộn dọc khi cần thiết
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Tắt thanh cuộn ngang

            return scrollPane;
        }


        private String readFile(String filePath) {
            StringBuilder content = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawLogo((Graphics2D) g);
        }
    }

    class Highscores extends Window {
        JButton backButton;
        JTextArea scoresArea;
        DefaultTableModel model;
        JTable table;

        public Highscores() {
            setLayout(null);
            setBackground(Color.BLACK);
            backButton = createButton("BACK", MAX_X / 2 - 200, MAX_Y / 2 + 250, 400, 50, MainPanel.this);
            this.add(backButton);
            
            createTable();  // Tạo và hiển thị bảng điểm cao
        }

        public void createTable(){
            String[] colHightScore = {"Top","Name","Score"};
            // model = new DefaultTableModel(colHightScore,0);
            model = new DefaultTableModel(colHightScore, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Trả về false để tất cả các ô đều không chỉnh sửa được
                    return false;
                }
            };
            table = new JTable(model);
            updateTable();
            table.setFillsViewportHeight(true);
            table.setRowHeight(30); // Chiều cao của mỗi hàng
            table.setBackground(Color.BLACK);   // Màu nền bảng
            table.setForeground(Color.WHITE); // Màu chữ bảng
            table.getTableHeader().setBackground(Color.GRAY);  // Màu nền tiêu đề cột
            table.getTableHeader().setForeground(Color.WHITE); // Màu chữ tiêu đề cột
            
            // Tùy chỉnh bảng (font, màu sắc)
            table.setFont(new Font("Arial", Font.PLAIN, 18));
            JScrollPane scrollPane = new JScrollPane(table);
            // Thêm bảng trực tiếp vào panel thay vì JScrollPane
            scrollPane.setBounds(390,300,600,330);
            this.add(scrollPane);
        }

        public void updateTable() {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Xóa tất cả các hàng hiện tại trong bảng
    
            List<String[]> scoreData = readFileScoreV2("src\\Score.txt");
    
            for (int i = 0; i < scoreData.size(); i++) {
                String[] data = scoreData.get(i);
                model.addRow(new Object[]{i + 1, data[0], data[1]});
            }
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawLogo((Graphics2D) g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == menu.menuButtons[3])
            System.exit(0);
        else if (source == menu.menuButtons[0]) {
            gameEngine.resetGame();
            // gameEngine.setNamePlayer();
            if(gameEngine.getNamePlayer() == null){
                cl.show(this, "Menu");
            }
            else{
                cl.show(this, "GameEngine");
                gameEngine.timer.start();
            }
        }
        else if (source == menu.menuButtons[1])
            cl.show(this, "Instructions");
        else if (source == menu.menuButtons[2]){
            highscores.updateTable();
            cl.show(this, "Highscores");
        }  
        else if (source == instructions.backButton || source == highscores.backButton)
            cl.first(this);
    }
}

class MyFrame extends JFrame
{
    MainPanel mainPanel;

    MyFrame()
    {
        super("PACMAN");
        mainPanel = new MainPanel();

        add(mainPanel);

        initFrame();
    }

    void initFrame()
    {
        setBackground(Color.BLACK);
        setSize(mainPanel.MAX_X, mainPanel.MAX_Y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

}

public class Main {
    public static void main(String[] args) throws Exception {
        new MyFrame();
      
    }
}
