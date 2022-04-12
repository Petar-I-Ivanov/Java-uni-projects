package farm.animals;

public class Sheep extends Animal {

    public Sheep(int weight, int aggression,
                 int speed, int energy) {
        super(weight, aggression, speed, energy);
    }

    public String makeSound() {
        return "BAA.";
    }
}