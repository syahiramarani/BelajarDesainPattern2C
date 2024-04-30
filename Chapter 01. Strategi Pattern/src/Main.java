public class Main {
    public static void main(String[] args) {
        System.out.println("-----------Mallard-------------");
        MallardDuck mallard = new MallardDuck();
        mallard.quack();
        mallard.swim();
        mallard.display();
        mallard.fly();

        System.out.println("-----------RedHead-------------");
        MallardDuck redhead = new MallardDuck();
        redhead.quack();
        redhead.swim();
        redhead.display();
        redhead.fly();

        System.out.println("-----------Rubber-------------");
        RubberDuck rubber = new RubberDuck();
        rubber.quack();
        rubber.swim();
        rubber.display();
        rubber.fly();

        System.out.println("-----------Wooden-------------");
        WoodenDuck wood = new WoodenDuck();
        wood.quack();
        wood.swim();
        wood.display();
        wood.fly();
    }
}
