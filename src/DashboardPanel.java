import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final UserData userData;

    private JLabel stepsLabel, waterLabel, caloriesLabel, proteinLabel;
    private JLabel stepsUnitLabel, waterUnitLabel, caloriesUnitLabel, proteinUnitLabel;
    private JProgressBar stepsPB, waterPB, caloriesPB, proteinPB;
    private DefaultListModel<String> foodListModel;

    public DashboardPanel(UserData data) {
        this.userData = data;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(20, 20, 25));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createHeader(), BorderLayout.NORTH);
        add(createStatsRow(), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
        center.setOpaque(false);
        center.add(createFoodLogPanel());
        center.add(createCalculatorsPanel());
        add(center, BorderLayout.CENTER);

        updateStats();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Good Morning ✨");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);
        return header;
    }

    private JPanel createStatsRow() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 12, 0));
        panel.setOpaque(false);

        panel.add(createStatCard("Steps", "👟", stepsLabel = new JLabel("0"),
                stepsUnitLabel = new JLabel("/10000"), stepsPB = createProgressBar(10000),
                this::editStepsGoal, this::resetSteps));

        panel.add(createStatCard("Water", "💧", waterLabel = new JLabel("0.0"),
                waterUnitLabel = new JLabel("L / 3.0"), waterPB = createProgressBar(300),
                this::editWaterGoal, this::resetWater));

        panel.add(createStatCard("Calories", "🔥", caloriesLabel = new JLabel("0"),
                caloriesUnitLabel = new JLabel("/2500"), caloriesPB = createProgressBar(2500),
                this::editCalorieGoal, this::resetCalories));

        panel.add(createStatCard("Protein", "🥩", proteinLabel = new JLabel("0"),
                proteinUnitLabel = new JLabel("g / 150g"), proteinPB = createProgressBar(150),
                this::editProteinGoal, this::resetProtein));

        return panel;
    }

    private JProgressBar createProgressBar(int max) {
        JProgressBar pb = new JProgressBar(0, max);
        pb.setForeground(new Color(0, 255, 100));
        pb.setBackground(new Color(40, 40, 50));
        pb.setBorderPainted(false);
        return pb;
    }

    private JPanel createStatCard(String title, String emoji, JLabel valueLabel, JLabel unitLabel,
                                  JProgressBar pb, Runnable editAction, Runnable resetAction) {

        JPanel card = new JPanel(new BorderLayout(10, 8));
        card.setBackground(new Color(30, 255, 100, 30));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 255, 100, 80), 2),
                new EmptyBorder(15, 15, 15, 15)));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(new JLabel(emoji) {{ setFont(new Font("Segoe UI", Font.PLAIN, 34)); }}, BorderLayout.WEST);
        top.add(new JLabel(title) {{
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }}, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(5, 0));
        center.setOpaque(false);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(Color.WHITE);
        center.add(valueLabel, BorderLayout.WEST);

        if (unitLabel != null) {
            unitLabel.setForeground(new Color(180, 255, 200));
            center.add(unitLabel, BorderLayout.CENTER);
        }

        card.add(top, BorderLayout.NORTH);
        card.add(center, BorderLayout.CENTER);

        if (pb != null) {
            card.add(pb, BorderLayout.SOUTH);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setOpaque(false);

        if (editAction != null) {
            JButton editBtn = new JButton("Set Goal");
            editBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            editBtn.addActionListener(e -> editAction.run());
            buttonPanel.add(editBtn);
        }

        if (resetAction != null) {
            JButton resetBtn = new JButton("Reset");
            resetBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            resetBtn.addActionListener(e -> resetAction.run());
            buttonPanel.add(resetBtn);
        }

        if (buttonPanel.getComponentCount() > 0) {
            card.add(buttonPanel, BorderLayout.SOUTH);
        }

        return card;
    }

    // GOAL EDITOR
    private void editStepsGoal() {
        String input = JOptionPane.showInputDialog(this, "New Daily Steps Goal:", userData.getStepsGoal());
        if (input != null && !input.trim().isEmpty()) {
            try {
                int goal = Integer.parseInt(input.trim());
                userData.setStepsGoal(goal);
                stepsPB.setMaximum(goal);
                updateStats();
                DataManager.saveData(userData);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid number!");
            }
        }
    }

    private void editWaterGoal() {
        String input = JOptionPane.showInputDialog(this, "New Daily Water Goal (Liters):", userData.getWaterGoal());
        if (input != null && !input.trim().isEmpty()) {
            try {
                double goal = Double.parseDouble(input.trim());
                userData.setWaterGoal(goal);
                waterPB.setMaximum((int)(goal * 100));
                updateStats();
                DataManager.saveData(userData);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid number!");
            }
        }
    }

    private void editCalorieGoal() {
        String input = JOptionPane.showInputDialog(this, "New Daily Calorie Goal:", userData.getCalorieGoal());
        if (input != null && !input.trim().isEmpty()) {
            try {
                int goal = Integer.parseInt(input.trim());
                userData.setCalorieGoal(goal);
                caloriesPB.setMaximum(goal);
                updateStats();
                DataManager.saveData(userData);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid number!");
            }
        }
    }

    private void editProteinGoal() {
        String input = JOptionPane.showInputDialog(this, "New Daily Protein Goal (g):", userData.getProteinGoal());
        if (input != null && !input.trim().isEmpty()) {
            try {
                int goal = Integer.parseInt(input.trim());
                userData.setProteinGoal(goal);
                proteinPB.setMaximum(goal);
                updateStats();
                DataManager.saveData(userData);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid number!");
            }
        }
    }

    // Reset methods
    private void resetSteps() {
        if (JOptionPane.showConfirmDialog(this, "Reset Steps to 0?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            userData.setSteps(0);
            updateStats();
            DataManager.saveData(userData);
        }
    }

    private void resetWater() {
        if (JOptionPane.showConfirmDialog(this, "Reset Water to 0?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            userData.setWaterLiters(0);
            updateStats();
            DataManager.saveData(userData);
        }
    }

    private void resetCalories() {
        if (JOptionPane.showConfirmDialog(this, "Reset Calories intake to 0?\n(Food log and Protein will remain)",
                "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            userData.resetCaloriesOnly();
            updateStats();
            DataManager.saveData(userData);
        }
    }

    private void resetProtein() {
        if (JOptionPane.showConfirmDialog(this, "Reset Protein intake to 0?\n(Food log and Calories will remain)",
                "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            userData.resetProteinOnly();
            refreshFoodLog();
            updateStats();
            DataManager.saveData(userData);
        }
    }



    // FOOD LOG
    private JPanel createFoodLogPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(30, 255, 100, 30));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Today's Food Log");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        foodListModel = new DefaultListModel<>();
        JList<String> foodList = new JList<>(foodListModel);
        panel.add(new JScrollPane(foodList), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(2, 3, 8, 8));
        formPanel.setOpaque(false);

        JTextField nameField = new JTextField("Chicken Breast");
        JTextField calField = new JTextField("250");
        JTextField protField = new JTextField("30");

        JButton addBtn = new JButton("Add Food");
        JButton clearBtn = new JButton("Reset Day");

        addBtn.addActionListener(e -> addFoodEntry(nameField, calField, protField));
        clearBtn.addActionListener(e -> resetDailyIntake());

        JLabel foodLbl = new JLabel("Food"); foodLbl.setForeground(Color.WHITE);
        JLabel calLbl = new JLabel("Cal"); calLbl.setForeground(Color.WHITE);
        JLabel protLbl = new JLabel("Protein(g)"); protLbl.setForeground(Color.WHITE);

        formPanel.add(foodLbl);
        formPanel.add(calLbl);
        formPanel.add(protLbl);
        formPanel.add(nameField);
        formPanel.add(calField);
        formPanel.add(protField);

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.add(addBtn);
        buttons.add(clearBtn);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttons, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        refreshFoodLog();
        return panel;
    }

    private void addFoodEntry(JTextField nameField, JTextField calField, JTextField protField) {
        try {
            String name = nameField.getText().trim();
            int cal = Integer.parseInt(calField.getText().trim());
            int protein = Integer.parseInt(protField.getText().trim());

            if (name.isEmpty()) throw new Exception();

            userData.addFood(name, cal, protein);
            refreshFoodLog();
            updateStats();
            DataManager.saveData(userData);

            nameField.setText("");
            calField.setText("");
            protField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid values!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetDailyIntake() {
        if (JOptionPane.showConfirmDialog(this, "Reset today's food intake?", "Confirm", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            userData.clearDailyIntake();
            refreshFoodLog();
            updateStats();
            DataManager.saveData(userData);
        }
    }

    // CALCULATOR
    private JPanel createCalculatorsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 15));
        panel.setOpaque(false);
        panel.add(createProteinCalculatorPanel());
        panel.add(createCalorieCalculatorPanel());
        return panel;
    }

    // PROTEIN AND CALORIE CALCULATOR
    private JPanel createProteinCalculatorPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBackground(new Color(30, 255, 100, 30));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Protein Calculator");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setOpaque(false);
        JTextField weightField = new JTextField("70");
        String[] goals = {"Maintain", "Build Muscle", "Lose Weight"};
        JComboBox<String> goalCombo = new JComboBox<>(goals);
        JButton calcBtn = new JButton("Calculate & Apply");

        calcBtn.addActionListener(e -> applyProteinRecommendation(weightField, goalCombo));

        JLabel weightLbl = new JLabel("Weight (kg):"); weightLbl.setForeground(Color.WHITE);
        JLabel goalLbl = new JLabel("Goal:"); goalLbl.setForeground(Color.WHITE);

        form.add(weightLbl); form.add(weightField);
        form.add(goalLbl); form.add(goalCombo);
        form.add(new JLabel("")); form.add(calcBtn);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private void applyProteinRecommendation(JTextField weightField, JComboBox<String> goalCombo) {
        try {
            double weight = Double.parseDouble(weightField.getText().trim());
            String goal = (String) goalCombo.getSelectedItem();
            int recommended = switch (goal != null ? goal : "Maintain") {
                case "Build Muscle" -> (int) (weight * 2.0);
                case "Lose Weight" -> (int) (weight * 1.6);
                default -> (int) (weight * 1.8);
            };

            userData.setProteinGoal(recommended);
            proteinPB.setMaximum(recommended);
            updateStats();
            DataManager.saveData(userData);
            JOptionPane.showMessageDialog(this, "Protein goal updated to " + recommended + "g");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid weight!");
        }
    }

    private JPanel createCalorieCalculatorPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBackground(new Color(30, 255, 100, 30));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Calorie Calculator");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 8));
        form.setOpaque(false);

        JTextField ageField = new JTextField("25");
        JTextField weightField = new JTextField("70");
        JTextField heightField = new JTextField("175");
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female"});
        JComboBox<String> activityBox = new JComboBox<>(new String[]{"Sedentary", "Light", "Moderate", "Active", "Very Active"});

        JButton calcBtn = new JButton("Calculate & Apply Goal");
        calcBtn.addActionListener(e -> calculateAndApplyCalories(ageField, weightField, heightField, genderBox, activityBox));

        JLabel ageLbl = new JLabel("Age:"); ageLbl.setForeground(Color.WHITE);
        JLabel weightLbl = new JLabel("Weight (kg):"); weightLbl.setForeground(Color.WHITE);
        JLabel heightLbl = new JLabel("Height (cm):"); heightLbl.setForeground(Color.WHITE);
        JLabel genderLbl = new JLabel("Gender:"); genderLbl.setForeground(Color.WHITE);
        JLabel activityLbl = new JLabel("Activity:"); activityLbl.setForeground(Color.WHITE);

        form.add(ageLbl); form.add(ageField);
        form.add(weightLbl); form.add(weightField);
        form.add(heightLbl); form.add(heightField);
        form.add(genderLbl); form.add(genderBox);
        form.add(activityLbl); form.add(activityBox);
        form.add(new JLabel("")); form.add(calcBtn);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private void calculateAndApplyCalories(JTextField ageF, JTextField weightF, JTextField heightF,
                                           JComboBox<String> genderBox, JComboBox<String> activityBox) {
        try {
            int age = Integer.parseInt(ageF.getText().trim());
            double weight = Double.parseDouble(weightF.getText().trim());
            double height = Double.parseDouble(heightF.getText().trim());
            boolean isMale = "Male".equals(genderBox.getSelectedItem());

            double bmr = isMale ?
                    (10 * weight) + (6.25 * height) - (5 * age) + 5 :
                    (10 * weight) + (6.25 * height) - (5 * age) - 161;

            double multiplier = switch (activityBox.getSelectedIndex()) {
                case 0 -> 1.2; case 1 -> 1.375; case 2 -> 1.55;
                case 3 -> 1.725; default -> 1.9;
            };

            int recommended = (int) (bmr * multiplier);
            userData.setCalorieGoal(recommended);
            caloriesPB.setMaximum(recommended);
            updateStats();
            DataManager.saveData(userData);

            JOptionPane.showMessageDialog(this, "Recommended Calories: " + recommended + "\nApplied!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please fill all fields correctly!");
        }
    }

    public void updateStats() {
        stepsLabel.setText(String.valueOf(userData.getSteps()));
        waterLabel.setText(String.format("%.2f", userData.getWaterLiters()));

        caloriesLabel.setText(String.valueOf(userData.getCaloriesConsumed()));
        caloriesUnitLabel.setText(" / " + userData.getCalorieGoal());

        proteinLabel.setText(String.valueOf(userData.getProteinConsumed()));
        proteinUnitLabel.setText("g / " + userData.getProteinGoal());

        stepsUnitLabel.setText(" / " + userData.getStepsGoal());
        waterUnitLabel.setText("L / " + userData.getWaterGoal());

        stepsPB.setMaximum(userData.getStepsGoal());
        stepsPB.setValue(userData.getSteps());
        waterPB.setMaximum((int)(userData.getWaterGoal() * 100));
        waterPB.setValue((int) (userData.getWaterLiters() * 100));
        caloriesPB.setMaximum(userData.getCalorieGoal());
        caloriesPB.setValue(userData.getCaloriesConsumed());
        proteinPB.setMaximum(userData.getProteinGoal());
        proteinPB.setValue(userData.getProteinConsumed());

        revalidate();
        repaint();
    }

    private void refreshFoodLog() {
        foodListModel.clear();
        for (UserData.FoodEntry f : userData.getFoodLog()) {
            foodListModel.addElement(f.name + " — " + f.calories + " cal — " + f.protein + "g");
        }
    }
}