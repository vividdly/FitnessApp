import javax.swing.*;
import java.awt.*;

public class FitnessTrackerApp extends JFrame {

    private final String username;
    private DashboardPanel dashboard;
    private UserData userData;

    public FitnessTrackerApp(String username) {
        this.username = username != null ? username : "User";
        this.userData = DataManager.loadData();

        setTitle("Fitness Nutrition Dashboard - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 850);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1200, 700));

        dashboard = new DashboardPanel(userData);
        add(dashboard, BorderLayout.CENTER);

        // BOTTOM CONTROL PANEL
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        controls.setBackground(new Color(20, 20, 25));

        JButton updateStepsBtn = new JButton("Update Steps");
        JButton addWaterBtn = new JButton("Add Water");
        JButton resetAllBtn = new JButton("Reset All Data");

        updateStepsBtn.addActionListener(e -> updateSteps());
        addWaterBtn.addActionListener(e -> addWater());
        resetAllBtn.addActionListener(e -> resetAllData());

        controls.add(updateStepsBtn);
        controls.add(addWaterBtn);
        controls.add(resetAllBtn);

        add(controls, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateSteps() {
        String input = JOptionPane.showInputDialog(this,
                "Enter today's steps:", userData.getSteps());

        if (input != null && !input.trim().isEmpty()) {
            try {
                int steps = Integer.parseInt(input.trim());
                userData.setSteps(steps);
                dashboard.updateStats();
                DataManager.saveData(userData);
                JOptionPane.showMessageDialog(this, "Steps updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number!");
            }
        }
    }

    private void addWater() {
        String input = JOptionPane.showInputDialog(this,
                "Enter water intake in liters (e.g. 0.5):", "0.5");

        if (input != null && !input.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(input.trim());
                if (amount > 0) {
                    userData.setWaterLiters(userData.getWaterLiters() + amount);
                    dashboard.updateStats();
                    DataManager.saveData(userData);
                    JOptionPane.showMessageDialog(this, amount + "L water added!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number!");
            }
        }
    }

    private void resetAllData() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Reset ALL data? This cannot be undone.",
                "Warning", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            userData = new UserData();
            DataManager.saveData(userData);
            dashboard = new DashboardPanel(userData);   // Refresh dashboard
            getContentPane().removeAll();
            add(dashboard, BorderLayout.CENTER);
            add(createControlPanel(), BorderLayout.SOUTH); // Re-add controls
            revalidate();
            repaint();
            JOptionPane.showMessageDialog(this, "All data has been reset!");
        }
    }

    private JPanel createControlPanel() {
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        controls.setBackground(new Color(20, 20, 25));

        JButton updateStepsBtn = new JButton("Update Steps");
        JButton addWaterBtn = new JButton("Add Water");
        JButton resetAllBtn = new JButton("Reset All Data");

        updateStepsBtn.addActionListener(e -> updateSteps());
        addWaterBtn.addActionListener(e -> addWater());
        resetAllBtn.addActionListener(e -> resetAllData());

        controls.add(updateStepsBtn);
        controls.add(addWaterBtn);
        controls.add(resetAllBtn);
        return controls;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}