import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginFrame extends JFrame {

    private static final String USERS_FILE = "users.ser";
    private static Map<String, String> users = new HashMap<>();

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        loadUsers(); // Load saved users when app starts

        // Default accounts (only if no users exist)
        if (users.isEmpty()) {
            users.put("kryz", "1234");
            users.put("admin", "admin");
        }

        setTitle("Login - Fitness Nutrition Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 520);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(20, 20, 25));

        initUI();
        setVisible(true);
    }

    private void initUI() {
        setLayout(new BorderLayout(20, 20));

        JLabel title = new JLabel("Welcome Back", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(0, 255, 100));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 30, 12, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        center.add(new JLabel("Username") {{ setForeground(Color.WHITE); }}, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 1;
        center.add(usernameField, gbc);

        gbc.gridy = 2;
        center.add(new JLabel("Password") {{ setForeground(Color.WHITE); }}, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 3;
        center.add(passwordField, gbc);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setBackground(new Color(0, 255, 100));
        loginBtn.setForeground(Color.BLACK);
        gbc.gridy = 4;
        center.add(loginBtn, gbc);

        JLabel registerLabel = new JLabel("<html><u>Don't have an account? Create one</u></html>", SwingConstants.CENTER);
        registerLabel.setForeground(new Color(0, 200, 255));
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        center.add(registerLabel, gbc);

        add(center, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> handleLogin());
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterFrame(LoginFrame.this);
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText().trim().toLowerCase();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!");
            return;
        }

        if (users.containsKey(username) && users.get(username).equals(password)) {
            dispose();
            new FitnessTrackerApp(username);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void addUser(String username, String password) {
        if (username != null && password != null) {
            users.put(username.toLowerCase().trim(), password);
            saveUsers();
        }
    }

    // SAVES AND LOAD USERS
    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Failed to save users: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (Map<String, String>) ois.readObject();
        } catch (Exception e) {
            users = new HashMap<>();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}