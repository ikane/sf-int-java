package shopping;

public class Store {
    public static void main(String[] args) {
        ClothingPair<Shoe> cps = new ClothingPair<>(
                new Shoe("Brown", 44),
                new Shoe("Brown", 44));

        System.out.println("cps matched? " + cps.isMatched());

        ClothingPair<Shoe> cps2 = new ClothingPair<>(
                new Shoe("Black", 44),
                new Shoe("Brown", 44));

        System.out.println("cps2 matched? " + cps2.isMatched());

        ClothingPair<Shoe> cps3 = new ClothingPair<>(
                new Shoe("Brown", 44),
                new Shoe("Brown", 45));

        System.out.println("cps3 matched? " + cps3.isMatched());

    }
}
