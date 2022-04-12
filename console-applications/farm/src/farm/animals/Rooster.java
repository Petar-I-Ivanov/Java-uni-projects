package farm.animals;

public class Rooster extends Animal {

    public Rooster(int weight, int aggression,
                   int speed, int energy) {
        super(weight, aggression, speed, energy);
    }

    public String makeSound() {
        return "COCK-A-DOODLE-DOO.";
    }
}