package shopping;

public class ClothingPair<T extends Colored & Sized> extends Pair<T> {
    public ClothingPair(T left, T right) {
        super(left, right);
    }

    public boolean isMatched() {
        return getLeft().getColor().equals(getRight().getColor())
                && getLeft().getSize() == getRight().getSize();
    }
}
