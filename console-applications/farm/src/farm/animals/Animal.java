package farm.animals;

import farm.enums.*;

public abstract class Animal {

    protected int weight;
    protected int aggression;
    protected int speed;
    protected int energy;

    protected boolean hasEvolved;
    protected AnimalTypeEnum type;

    protected int foodGivenCounter = 0;
    protected int abilityUsedCounter = 0;

    protected Animal (int weight, int aggression,
                   int speed, int energy) {

        this.weight     = weight;
        this.aggression = aggression;
        this.speed      = speed;
        this.energy     = energy;
    }

    public void printCharacteristics() {
        System.out.println( "W:"  + this.weight +
                            " A:" + this.aggression +
                            " S:" + this.speed +
                            " E:" + this.energy
        );
    }

    public void eating(FoodTypeEnum typeOfFood) {

        if (this.isEatingNotPossible()) {
            return;
        }

        this.type = switch (typeOfFood) {
            case FOOD      -> AnimalTypeEnum.FOOD;
            case TRANSPORT -> AnimalTypeEnum.TRANSPORT;
            case FIGHTING  -> AnimalTypeEnum.FIGHTING;
        };

        this.hasEvolved = true;
        System.out.println("The animal evolved.");
    }

    public void specialAbility() {

        if (this.isAbilityNotAvailable()) {
            return;
        }

        switch (this.type) {
            case FOOD      -> this.speed += this.speed / 2;
            case TRANSPORT -> this.weight += this.weight * 0.1;
            case FIGHTING  -> this.aggression++;
        }

        System.out.println("The animal used special ability.");
    }

    protected abstract String makeSound();

    private boolean isSleeping() {
        return (this.weight == 0 || this.aggression == 0
                || this.speed == 0 || this.energy == 0);
    }

    private boolean isEatingNotPossible() {

        if (this.isSleeping() && this.foodGivenCounter++ < 2) {

            System.out.println("The animal refuses to eat.");
            return true;
        }

        if (this.type == null && this.hasEvolved) {

            System.out.println("The animal won't evolve anymore.");
            return true;
        }

        if (this.type != null) {

            this.type = null;
            System.out.println("The animal is normal again.");
            return true;
        }

        return false;
    }

    private boolean isAbilityNotAvailable() {

        if (this.type == null) {

            System.out.println(this.makeSound());
            return true;
        }

        if (this.abilityUsedCounter++ == 3) {

            this.weight = 0;
            this.aggression = 0;
            this.speed = 0;
            this.energy = 0;
            this.type = null;

            System.out.println("The animal used ability more than 3 times.");
            return true;
        }

        return false;
    }
}