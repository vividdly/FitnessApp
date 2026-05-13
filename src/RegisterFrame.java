import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame(LoginFrame loginFrame) {
        setTitle("Create Account");
        setSize(420, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(20, 20, 25));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create New Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 255, 100));
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(title, gbc);

        JTextField userField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        JPasswordField confirmField = new JPasswordField(20);

        gbc.gridwidth = 1;
        gbc.gridy = 1; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; panel.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1; panel.add(confirmField, gbc);

        JButton registerBtn = new JButton("CREATE ACCOUNT");
        registerBtn.setBackground(new Color(0, 255, 100));
        registerBtn.setForeground(Color.BLACK);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(registerBtn, gbc);

        registerBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String pass = new String(passField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (username.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }
            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
                return;
            }

            LoginFrame.addUser(username, pass);
            JOptionPane.showMessageDialog(this, "Account created successfully!\nYou can now login.");
            dispose();
        });

        add(panel);
        setVisible(true);
    }
}