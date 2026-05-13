import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable {

    // Daily Intake
    private int steps = 2500;
    private double waterLiters = 1.25;
    private int caloriesConsumed = 0;
    private int proteinConsumed = 0;
    private List<FoodEntry> foodLog = new ArrayList<>();

    // CUSTOM GOALS
    private int stepsGoal = 10000;
    private double waterGoal = 3.0;
    private int calorieGoal = 2500;
    private int proteinGoal = 150;

    public static class FoodEntry implements Serializable {
        String name;
        int calories;
        int protein;

        public FoodEntry(String name, int calories, int protein) {
            this.name = name != null ? name : "Unknown";
            this.calories = Math.max(0, calories);
            this.protein = Math.max(0, protein);
        }
    }

    // GETTERS AND SETTERS SECTION

    public int getSteps() { return steps; }
    public void setSteps(int steps) { this.steps = Math.max(0, steps); }

    public double getWaterLiters() { return waterLiters; }
    public void setWaterLiters(double liters) {
        this.waterLiters = Math.max(0, Math.min(10.0, liters));
    }

    public int getCaloriesConsumed() { return caloriesConsumed; }
    public int getProteinConsumed() { return proteinConsumed; }

    // GOALS
    public int getStepsGoal() { return stepsGoal; }
    public void setStepsGoal(int goal) { this.stepsGoal = Math.max(1000, goal); }

    public double getWaterGoal() { return waterGoal; }
    public void setWaterGoal(double goal) { this.waterGoal = Math.max(0.5, goal); }

    public int getCalorieGoal() { return calorieGoal; }
    public void setCalorieGoal(int goal) { this.calorieGoal = Math.max(500, goal); }

    public int getProteinGoal() { return proteinGoal; }
    public void setProteinGoal(int goal) { this.proteinGoal = Math.max(30, goal); }

    public void addFood(String name, int cal, int protein) {
        foodLog.add(new FoodEntry(name, cal, protein));
        this.caloriesConsumed += cal;
        this.proteinConsumed += protein;
    }

    public List<FoodEntry> getFoodLog() { return foodLog; }

    public void clearDailyIntake() {
        foodLog.clear();
        caloriesConsumed = 0;
        proteinConsumed = 0;
    }
    public void resetCaloriesOnly() {
        caloriesConsumed = 0;
        // Keep protein and food log
    }

    public void resetProteinOnly() {
        proteinConsumed = 0;
        // Keep calories and food log
    }
}