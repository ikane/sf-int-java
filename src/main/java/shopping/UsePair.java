package shopping;

public class UsePair {
    public static void main(String[] args) {
//        This one fails...
//        Pair<String> ps = new Pair<>("Fred", 99);
        Pair<String> ps = new Pair<>("Fred", "Jones");
        String l = ps.getLeft();
        ps.setRight("Smith");
        Pair<Integer> pi = new Pair<>(99, 100);
    }
}
