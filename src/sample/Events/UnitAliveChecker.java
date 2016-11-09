package sample.Events;

import sample.Core.EventChecker;


public class UnitAliveChecker<T extends ShiftEvent> implements EventChecker<T> {
    private static UnitAliveChecker INSTANCE = new UnitAliveChecker();

    private UnitAliveChecker() {
    }

    public static UnitAliveChecker getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean checkEvent(T shiftEvent) {
        return shiftEvent.getObject().isAlive();
    }
}
