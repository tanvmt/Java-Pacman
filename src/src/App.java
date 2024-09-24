import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Window extends JPanel {
    final int MAX_X = 1366;
    final int MAX_Y = 768;
    final int BLOCK_SIZE = 24;


    public int getMAX_X() {
        return MAX_X;
    }

    public int getMAX_Y() {
        return MAX_Y;
    }

    public int getBlockSize(){
        return BLOCK_SIZE;
    }
}

class MainPanel extends Window implements ActionListener {
    Menu menu;
    Play play;
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
        play = new Play();
        instructions = new Instructions();
        highscores = new Highscores();
        game = new Game();
    }

    private void addPanels() {
        this.add("Menu", menu);
        this.add("Play", play);
        this.add("Instructions", instructions);
        this.add("Highscores", highscores);
        this.add("Game", game);
    }

    

    private void loadImages() {
        logo = new ImageIcon("src/logo.png").getImage();
        icon1 = new ImageIcon("src/menu icon.png").getImage();
        icon2 = new ImageIcon("src/menu icon 2.png").getImage();
    }

    private void drawLogo(Graphics2D g2D) {
        g2D.drawImage(logo, 251, 70, this);
        g2D.drawImage(icon1, 0, 0, this);
        g2D.drawImage(icon1, 0, 676, this);
        g2D.drawImage(icon2, 1296, 0, this);
        g2D.drawImage(icon2, 1296, 676, this);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 400, 50);
        button.setFont(new Font("Arial", Font.BOLD, 40));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.RED);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(MainPanel.this);
        return button;
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
                menuButtons[i] = createButton(labels[i], getMAX_X() / 2 - 200, getMAX_Y() / 2 - 50 + 100 * i);
                this.add(menuButtons[i]);
            }
        }

        
        

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawLogo((Graphics2D) g);
        }
    }

    class Play extends Window {
        JButton[] modeButtons = new JButton[3];

        Play() {
            initButtons();
            setLayout(null);
            setBackground(Color.BLACK);
        }

        private void initButtons() {
            String[] modes = { "EASY", "HARD", "BACK" };
            for (int i = 0; i < 3; i++) {
                modeButtons[i] = createButton(modes[i], getMAX_X() / 2 - 200, getMAX_Y() / 2 - 50 + 100 * i);
                this.add(modeButtons[i]);
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
            backButton = createButton("BACK", getMAX_X() / 2 - 200, getMAX_Y() / 2 + 250);

            String text = "src/Instructions.txt";
            instructionsArea = createTextArea(text, getMAX_X() / 2 - 300, getMAX_Y() / 2 - 100);

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

        Highscores() {
            setLayout(null);
            setBackground(Color.BLACK);
            backButton = createButton("BACK", getMAX_X() / 2 - 200, getMAX_Y() / 2 + 250);
            scoresArea = createTextArea("1. Player A: 1000\n2. Player B: 900\n3. Player C: 800", getMAX_X() / 2 - 300,
                    getMAX_Y() / 2 - 100);
            this.add(backButton);
            this.add(scoresArea);
        }

        private JTextArea createTextArea(String text, int x, int y) {
            JTextArea area = new JTextArea(text, 10, 50);
            area.setBounds(x, y, 600, 300);
            area.setForeground(Color.RED);
            area.setFont(new Font("Arial", Font.BOLD, 20));
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setEditable(false);
            area.setOpaque(false);
            return area;
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
        if (source == menu.menuButtons[0])
            cl.show(this, "Play");
        if (source == menu.menuButtons[1])
            cl.show(this, "Instructions");
        if (source == menu.menuButtons[2])
            cl.show(this, "Highscores");
        if (source == play.modeButtons[2] || source == instructions.backButton || source == highscores.backButton)
            cl.first(this);
        if (source == play.modeButtons[0]) {
            cl.show(this, "Game");
        }
    }
}

// class Game extends Window {
//     Button b;

//     Game() {
//         setBackground(Color.BLACK);
//         b = new Button("SDJALKDKSAJDLK");
//         setLayout(new BorderLayout());
//         add(b);
//     }
// }

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
        setSize(mainPanel.getMAX_X(), mainPanel.getMAX_Y());
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
