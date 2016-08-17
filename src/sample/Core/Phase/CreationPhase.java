package sample.Core.Phase;

import sample.Core.GAction;
import sample.Core.Player;
import sample.Skills.CreateUnitAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreationPhase extends GPhase {
    private List<CreateUnitAction> list;

    @Override
    public void next(GAction a) {
        if (a instanceof CreateUnitAction) {
            CreateUnitAction action = (CreateUnitAction) a;
            if (action.getUnitCount() <= 0) {
                list.remove(action);
            }
        }
        if (list.size() == 0) {
            model.setPhase(new GamePhase());
        } else {
            selectPlayerAndAction();
        }
    }


    @Override
    public void init() {
        list = new ArrayList<>();
        Player weakestPlayer = model.findWeakestPlayer();
        for (Player player : model.getPlayers()) {
            CreateUnitAction createUnitAction = new CreateUnitAction();
            createUnitAction.setUnitNumber(player.equals(weakestPlayer) ? 2 : 1);
            createUnitAction.setPlayer(player);
            list.add(new Random().nextBoolean() ? 0 : list.size(), createUnitAction);
        }
        next(null);
    }

    private void selectPlayerAndAction() {
        CreateUnitAction action = list.get(0);
        model.setPhaseAction(action);
        model.setActivePlayer(action.getPlayer());
        model.setAction(action);
    }
}
