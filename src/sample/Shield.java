package sample;

public class Shield extends AbstractGMod {
    public Shield(int value) {
        this.value = value;
    }

    @Override
    public void onTakeShot(Shell shell) {
        final int reduceDamage = shell.reduceDamage(value, DamageType.PHYSICAL);
        if (reduceDamage > 0) {
            GameModel.MODEL.log(String.format("Shield activated! %s damage absorbed!", reduceDamage));
        }
    }
}
