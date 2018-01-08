package uiboard;

import javax.swing.*;

public final class Ground extends JFrame {
    private FormationID formationID;

    public Ground(FormationID formationIDTMP){
        this.formationID=formationIDTMP;
        Battle battle = new Battle(formationID);
        add(battle);
        setSize(battle.getBoardWidth(), battle.getBoardHeight());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
    }
}
