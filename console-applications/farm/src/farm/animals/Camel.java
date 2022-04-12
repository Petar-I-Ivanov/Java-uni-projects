package farm.animals;

public class Camel extends Animal {

    public Camel(int weight, int aggression,
                 int speed, int energy) {
        super(weight, aggression, speed, energy);
    }

    public String makeSound() {
        return "GRUNT.";
    }
}