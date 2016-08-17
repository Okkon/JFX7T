package sample.Core;

public interface UnitSelector {
    GUnit getSelectedUnit();

    void close();

    void setUnitCounter(int unitCounter);
}
