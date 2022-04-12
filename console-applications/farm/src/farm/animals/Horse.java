package farm.animals;

public class Horse extends Animal {

    public Horse(int weight, int aggression,
                 int speed, int energy) {
        super(weight, aggression, speed, energy);
    }

    public String makeSound() {
        return "WHINNY.";
    }
}