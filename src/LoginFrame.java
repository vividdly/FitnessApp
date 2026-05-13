import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class LoginFrame extends JFrame {

    private static final Map<String, String> users = new HashMap<>();

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        // EXAMPLE ACCOUNT
        users.put("kryz", "1234");

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

        // HEADER
        JLabel title = new JLabel("Welcome", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(0, 255, 100));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 30, 12, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        center.add(new JLabel("Username") {{ setForeground(Color.WHITE); setFont(new Font("Segoe UI", Font.PLAIN, 14)); }}, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 1;
        center.add(usernameField, gbc);

        gbc.gridy = 2;
        center.add(new JLabel("Password") {{ setForeground(Color.WHITE); setFont(new Font("Segoe UI", Font.PLAIN, 14)); }}, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 3;
        center.add(passwordField, gbc);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setBackground(new Color(0, 255, 100));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFocusPainted(false);
        gbc.gridy = 4;
        center.add(loginBtn, gbc);

        JLabel registerLabel = new JLabel("<html><u>Don't have an account? Create one</u></html>", SwingConstants.CENTER);
        registerLabel.setForeground(new Color(0, 200, 255));
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        center.add(registerLabel, gbc);

        add(center, BorderLayout.CENTER);

        // Action Listeners
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
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText().trim().toLowerCase();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (users.containsKey(username) && users.get(username).equals(password)) {
            dispose();
            new FitnessTrackerApp(username);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // METHOD USE BY REGISTER FRAME
    public static void addUser(String username, String password) {
        if (username != null && password != null) {
            users.put(username.toLowerCase(), password);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}