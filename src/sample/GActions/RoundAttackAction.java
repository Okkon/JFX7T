package sample.GActions;

public class RoundAttackAction extends SwingAttackAction {
    private static final RoundAttackAction INSTANCE = new RoundAttackAction();
    private RoundAttackAction() {
        power = 7;
    }
    public static RoundAttackAction getInstance() {
        return INSTANCE;
    }
}
