import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Window extends JPanel {
    protected final int MAX_X = 1366;
    protected final int MAX_Y = 768;
    protected final int BLOCK_SIZE = 24;
    protected final int N_BLOCKS = 31;
    protected final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
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

    
    public String readFileScore(String filePath) {
        ArrayList<Player> players = new ArrayList<>();
        
        String name;
        int diem;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while (line != null) {
                String[] value = line.split(",");
                name = value[0];
                diem = Integer.parseInt(value[1]); 
                players.add(new Player(name, diem));
                
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // sx diem giam dan
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        StringBuilder content = new StringBuilder();
        for (Player player : players) {
            content.append(player.getNamePlayer()).append(" ").append(player.getScore()).append("\n");
        }
        return content.toString();
    }
}

class MainPanel extends Window implements ActionListener {
    Menu menu;
    Instructions instructions;
    Highscores highscores;
    CardLayout cl;
    Image logo, icon1, icon2;
    Game game;

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
        game = new Game();
    }

    private void addPanels() {
        this.add("Menu", menu);
        this.add("Instructions", instructions);
        this.add("Highscores", highscores);
        this.add("Game", game);
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
                menuButtons[i] = createButton(labels[i], MAX_X / 2 - 200, MAX_Y / 2 - 50 + 100 * i, 400, 50, MainPanel.this);
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
        // JTextArea instructionsArea;
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
            // JScrollPane scrollPane = new JScrollPane(area);
            // add(scrollPane);
            String content = readFile(text);
            area.setText(content);
            // return area;

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

        public Highscores() {
            setLayout(null);
            setBackground(Color.BLACK);
            backButton = createButton("BACK", MAX_X / 2 - 200, MAX_Y / 2 + 250, 400, 50, MainPanel.this);
            String textScore = "C:\\DoAN OOP\\Java-Pacman\\src\\Score.txt";
            scoresArea = createTextArea(textScore, MAX_X / 2 - 300,
                    MAX_Y / 2 - 100);
            this.add(backButton);
            this.add(scoresArea);
        }

        public JTextArea createTextArea(String text, int x, int y) {
            JTextArea area = new JTextArea(text, 10, 50);
            area.setBounds(x, y, 600, 300);
            area.setForeground(Color.RED);
            area.setFont(new Font("Arial", Font.BOLD, 20));
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setEditable(false);
            area.setOpaque(false);
            String content = readFileScore(text);
            area.setText(content);
            // updateScores();
            return area;
        }

        // public String readFileScore(String filePath) {
        //     StringBuilder content =  new StringBuilder();
        //     String name,diem;
        //     try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        //         String line = br.readLine();
        //         while (line != null) {
        //             String[] value = line.split(",");
        //             name = value[0];
        //             diem = value[1];
        //             content.append(name).append(" ").append(diem).append("\n");
                    
        //             line = br.readLine();
        //         }
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        //     return content.toString();
        // }

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
        if (source == menu.menuButtons[0]) {
            game.resetGame();
            
            game.setNamePlayer();
            
            cl.show(this, "Game");
            game.timer.start();
        }
        if (source == menu.menuButtons[1])
            cl.show(this, "Instructions");
        if (source == menu.menuButtons[2])

            cl.show(this, "Highscores");
        if (source == instructions.backButton || source == highscores.backButton)
            cl.first(this);
    }
}

class MyFrame extends JFrame
{
    private enum State {
        MENU,
        GAME;
    }

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
    }

}

public class App {
    public static void main(String[] args) throws Exception {
        new MyFrame();
      
    }
}
