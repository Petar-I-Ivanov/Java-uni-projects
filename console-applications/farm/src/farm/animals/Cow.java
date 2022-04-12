package farm.animals;

public class Cow extends Animal {

    public Cow(int weight, int aggression,
               int speed, int energy) {
        super(weight, aggression, speed, energy);
    }

    public String makeSound() {
        return "MOO.";
    }
}