package uiboard;

import java.awt.*;
import java.awt.event.*;

public class FormChooser {
    private Frame f;
    private Button but1;
    private Button but2;
    private Button but3;
    private Button but4;
    private Button but5;
    private Button but6;
    private FormationID formationID=FormationID.NULL;

    public FormChooser(){
        init();
    }

    public void init()
    {
        f = new Frame("FormationChooser: Please Choose Formation within 5 sec!");

        f.setBounds(600,400,700,100);
        f.setLayout(new FlowLayout());

        but1 = new Button("Heyi");
        but2 = new Button("Changshe");
        but3 = new Button("Yanxing");
        but4 = new Button("Chonge");
        but5 = new Button("Fangxing");
        but6 = new Button("Fengshi");

        f.add(but1);
        f.add(but2);
        f.add(but3);
        f.add(but4);
        f.add(but5);
        f.add(but6);

        myEvent();

        f.setVisible(true);

    }

    private void myEvent()
    {
        f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        but1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)//鼠标点击事件
            {
                if(e.getClickCount() == 1)
                    formationID=FormationID.HEYI;
                deleteFrame();
            }
        });

        but2.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)//鼠标点击事件
            {
                if(e.getClickCount() == 1)
                    formationID=FormationID.CHANGSHE;
                deleteFrame();
            }
        });

        but3.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)//鼠标点击事件
            {
                if(e.getClickCount() == 1)
                    formationID=FormationID.YANXING;
                deleteFrame();
            }
        });

        but4.addMouseListener(new MouseAdapter()
        {

            public void mouseClicked(MouseEvent e)//鼠标点击事件
            {
                if(e.getClickCount() == 1)
                    formationID=FormationID.CHONGE;
                deleteFrame();
            }
        });

        but5.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)//鼠标点击事件
            {
                if(e.getClickCount() == 1)
                    formationID=FormationID.FANGZHEN;
                deleteFrame();
            }

        });

        but6.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)//鼠标点击事件
            {
                if(e.getClickCount() == 1)
                    formationID=FormationID.FENGSHI;
                deleteFrame();
            }
        });

    }

    public FormationID getFormationID(){
        return this.formationID;
    }

    public void deleteFrame(){
        this.f.setVisible(false);
    }

    public boolean isVisible(){
        return this.f.isVisible();
    }
}

