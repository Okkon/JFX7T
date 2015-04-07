package sample;

/**
 * Created by kondrashov on 20.03.2015.
 */
public interface GMod {
    void onHit(Hit hit);

    void onTakeHit(Hit hit);

    boolean isInvisible();

    boolean blocksTower();

    void onTakeShot(Shell shell);
}
