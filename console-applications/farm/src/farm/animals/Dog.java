package farm.animals;

public class Dog extends Animal {

    public Dog(int weight, int aggression,
               int speed, int energy) {
        super(weight, aggression, speed, energy);
    }

    public String makeSound() {
        return "WOOF.";
    }
}