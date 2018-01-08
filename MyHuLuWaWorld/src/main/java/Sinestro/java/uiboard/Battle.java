package uiboard;

import creature.*;
import io.IOFile;
import queue.*;
import space.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.*;
import java.util.Iterator;
import java.util.Vector;

import formation.*;

public class Battle extends JPanel {
    private int battleWidth;
    private int battleLength;
    private FormationID formationID;
    private Timer battleTimer;
    private ActionListener battleAction;

    private RunningMode runningMode;

    // 各色角色
    private HuLuWa[] huLuWa;
    private XiaoLouLuo[] xiaoLouLuo;
    private int xiaoLouLuoNum;
    private LaoYeYe yeYe;
    private XieZiJing xieZiJing;
    private SheJing sheJing;

    //Formation Construct Variable
    private Space space;
    QueueArray justiceQueue;

    // 存放死去的角色
    private Vector<Creature> death;

    // 若she和scorp分别被收服，则不再放入death容器中
    private int sheDieFlag=0;
    private int xieZiDieFlag=0;

    // flushGround是大背景，对应background, badEndingGround, happyEndingGround三种情况
    private Thing2D flushGround;
    private Thing2D background;
    private Thing2D badEndingGround;
    private Thing2D happyEndingGround;

    //若蛇精和蝎子精都被收服，则小喽啰不再复活

    public boolean mainEvilDeadFlag;

    //roles存放当前活着的角色
    public Vector<Creature> roles;

    protected boolean startFlag;

    //结局：endingType值为1则为badEnding，值为2则为happyEnding
    protected int endFlag;

    public Battle(FormationID formationIDTMP) {
        this.formationID=formationIDTMP;
        addKeyListener(new TAdapter());
        setFocusable(true);
        init();
        setRestartParameter();
    }

    /**
     * 重载paint函数，调用了painting函数
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        painting(g);
    }

    private void painting(Graphics g){
        if(runningMode==RunningMode.BEGIN){
            g.drawImage(flushGround.getImage(),0,0,this);
            drawRoles(g);
        } else if(runningMode==RunningMode.PLAYING) {
            freshEndingType();
            //setAttackImage();
            freshRole();
            drawRoles(g);
        } else if(runningMode==RunningMode.PLAYBACK) {
            g.drawImage(flushGround.getImage(),0,0,this);
            drawRoles(g);
        } else if(runningMode==RunningMode.OVER){
            g.drawImage(flushGround.getImage(),0,0,this);
        }
        repaint();
    }

    private void drawRoles(Graphics g) {
        // Draw Background
        g.drawImage(background.getImage(),0,0,this);

        // Draw Creatures
        for (Creature role : roles) {
            if (role.isAlive()) {
                g.drawImage(role.getImage(), role.getX(), role.getY(), role.getCreatureWidth(), role.getCreatureLength(), this);
            }
        }
    }

    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE && runningMode==RunningMode.BEGIN){
                runningMode = RunningMode.PLAYING;
                startFlag=true;
                startThread();
                initTimer(20);
            }
            else if (key == KeyEvent.VK_L && (runningMode==RunningMode.BEGIN || runningMode==RunningMode.OVER)) {
                runningMode = RunningMode.PLAYBACK;
                int flag = 1;
                JFileChooser jFileChooser = null;
                jFileChooser = new JFileChooser(new File("document"));
                jFileChooser.setDialogTitle("Choose Files");
                flag = jFileChooser.showDialog(null, null);
                IOFile.setReadFile(jFileChooser.getSelectedFile());
                initTimer(10);
            }
            else if (key == KeyEvent.VK_R && (runningMode==RunningMode.PLAYING||runningMode==RunningMode.OVER)) {
                for(Creature c: roles){
                    c.runningFlag = false;
                }
                setRestartParameter();
                roles.clear();
                setCreature();
                freshRole();
            }
            else if(key == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }
    }

    private void initTimer(int time){
        battleAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //setAttackImage();
                freshEndingType();
                LoadOrSave();
                repaint();
            }
        };
        battleTimer = new Timer(time,battleAction);
        battleTimer.start();
    }

    private synchronized void LoadOrSave(){
        if(runningMode==RunningMode.BEGIN){

        } else if(runningMode==RunningMode.PLAYING) {
            if(!sheJing.isAlive()){
                sheDieFlag=1;
            }
            if(!xieZiJing.isAlive()){
                xieZiDieFlag=1;
            }
            try {
                IOFile.writeFile(roles);
                IOFile.writeFile(death);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(runningMode==RunningMode.PLAYBACK) {
            String str = null;
            if (IOFile.getReadFile() == null) {
                return;
            }
            str = IOFile.getNextString();
            if (str != null) {
                playback(str);
            } else {
                runningMode = RunningMode.OVER;
            }

        } else if(runningMode==RunningMode.OVER){

        }
    }

    private void playback(String str){
        String name = null;
        int id=0;
        int getX = -1, getY = -1;
        boolean isAlive = false;
        boolean attack = false;

        String[] readFileLine = str.split(" ");
        if(readFileLine.length != 6)
            return;
        name = readFileLine[0];
        id = Integer.parseInt(readFileLine[1]);
        getX = Integer.parseInt(readFileLine[2]);
        getY = Integer.parseInt(readFileLine[3]);
        isAlive = (readFileLine[4].equals("true")) ? true:false;
        attack = (readFileLine[5].equals("true")) ? true:false;

        if (name.equals("YEYE") ) {
            yeYe.setX(getX);
            yeYe.setY(getY);
            yeYe.update();
        } else if (name.equals( "XIEZI")) {
            xieZiJing.setX(getX);
            xieZiJing.setY(getY);
            xieZiJing.update();
            xieZiJing.setAttack(attack);
            if(!isAlive)
                xieZiJing.setDie();
        } else if (name.equals("SHE")) {
            sheJing.setX(getX);
            sheJing.setY(getY);
            sheJing.update();
            if(!isAlive)
                sheJing.setDie();
        } else if(name.equals("HuLuWa")) {
            huLuWa[id-1].setX(getX);
            huLuWa[id-1].setY(getY);
            huLuWa[id-1].update();
            huLuWa[id-1].setAttack(attack);
            if(!isAlive){
                huLuWa[id-1].setDie();
            } else {
                huLuWa[id-1].setLive();
            }
        } else if(name.equals("LOULUO")){
            xiaoLouLuo[id-1].setX(getX);
            xiaoLouLuo[id-1].setY(getY);
            xiaoLouLuo[id-1].update();
        }
        repaint();
    }

    public int getBoardWidth() {
        return this.battleWidth;
    }

    public int getBoardHeight() {
        return this.battleLength;
    }

    private void init(){
        setCreature();
        initBackground();
        initRole();
    }

    private void setRestartParameter(){
        this.endFlag=0;
        runningMode=RunningMode.BEGIN;
        mainEvilDeadFlag = false;
        startFlag=true;
        flushGround.setImage(background.getImage());
    }

    private void initBackground() {
        flushGround = new Thing2D("HuLuWa-BG1.png",0,0,1600,900);
        background = new Thing2D("HuLuWa-BG1.png",0,0,1600,900);
        badEndingGround = new Thing2D("HuLuWa-BG1(lose).png",0,0,1600,900);
        happyEndingGround = new Thing2D("HuLuWa-BG1(win).png",0,0,1600,900);
        battleWidth = background.getImage().getWidth(this);
        battleLength = background.getImage().getHeight(this);
    }

    private void initRole(){
        // 将各色角色加入roles容器中
        roles = new Vector<Creature>();
        // death初始不为空
        death = new Vector<Creature>();
        for(HuLuWa huluwa: huLuWa) {
            roles.add(huluwa);
            death.add(huluwa);
        }
        for(XiaoLouLuo louLuo: xiaoLouLuo){
            roles.add(louLuo);
            death.add(louLuo);
        }
        roles.add(yeYe);

        roles.add(xieZiJing);
        roles.add(sheJing);

        death.add(yeYe);

        death.add(xieZiJing);
        death.add(sheJing);
    }

    private void setJusticeQueue(HuLuWa[] huLuWaTMP){
        for (int i=0;i<7;i++)
            this.justiceQueue.creatureEnqueue(huLuWaTMP[i]);
    }

    private void setCreature(){
        // 葫芦娃阵型
        this.space=new Space(160,90);
        this.justiceQueue=new QueueArray(7);
        HuLuWa[] huLuWaTMP=new HuLuWa[7];
        huLuWaTMP[0] = new HuLuWa("HuLuWa1.png",10, 10, Role.YI,120, 240,this,1);
        huLuWaTMP[1] = new HuLuWa("HuLuWa2.png",10, 10, Role.ER,120,340,this,2);
        huLuWaTMP[2] = new HuLuWa("HuLuWa3.png",10, 10, Role.SAN,120,440,this,3);
        huLuWaTMP[3] = new HuLuWa("HuLuWa4.png", 10, 10, Role.SI,120,540,this,4);
        huLuWaTMP[4] = new HuLuWa("HuLuWa5.png", 10, 10, Role.WU,120,640,this,5);
        huLuWaTMP[5] = new HuLuWa("HuLuWa6.png", 10, 10, Role.LIU,120,740,this,6);
        huLuWaTMP[6] = new HuLuWa("HuLuWa7.png",10, 10, Role.QI,120,840,this,7);

        this.setJusticeQueue(huLuWaTMP);

        switch(this.formationID){
            case FENGSHI:{
                new FengShi().formation(this.justiceQueue,new Coordinate(60, 30),this.space);
                break;
            }
            case HEYI:{
                new HeYi().formation(this.justiceQueue,new Coordinate(60, 30),this.space);
                break;
            }
            case CHONGE:{
                new ChongE().formation(this.justiceQueue,new Coordinate(60, 30),this.space);
                break;
            }
            case YANXING:{
                new YanXing().formation(this.justiceQueue,new Coordinate(70, 10),this.space);
                break;
            }
            case CHANGSHE:{
                new ChangShe().formation(this.justiceQueue,new Coordinate(30, 10),this.space);
                break;
            }
            case FANGZHEN:{
                new FangZhen().formation(this.justiceQueue,new Coordinate(50, 25),this.space);
                break;
            }
            default: break;
        }

        PositionInfo[] huLuWaPositionInfos=this.space.getHuLuWaPositionInfo();

        huLuWa = new HuLuWa[7];
        huLuWa[0] = new HuLuWa("HuLuWa1.png", huLuWaPositionInfos[0].getCreatureCrdn().getPtX()*10, huLuWaPositionInfos[0].getCreatureCrdn().getPtY()*10, Role.YI,120, 240,this,1);
        huLuWa[1] = new HuLuWa("HuLuWa2.png", huLuWaPositionInfos[1].getCreatureCrdn().getPtX()*10, huLuWaPositionInfos[1].getCreatureCrdn().getPtY()*10, Role.ER,120,340,this,2);
        huLuWa[2] = new HuLuWa("HuLuWa3.png", huLuWaPositionInfos[2].getCreatureCrdn().getPtX()*10, huLuWaPositionInfos[2].getCreatureCrdn().getPtY()*10, Role.SAN,120,440,this,3);
        huLuWa[3] = new HuLuWa("HuLuWa4.png", huLuWaPositionInfos[3].getCreatureCrdn().getPtX()*10, huLuWaPositionInfos[3].getCreatureCrdn().getPtY()*10, Role.SI,120,540,this,4);
        huLuWa[4] = new HuLuWa("HuLuWa5.png", huLuWaPositionInfos[4].getCreatureCrdn().getPtX()*10, huLuWaPositionInfos[4].getCreatureCrdn().getPtY()*10, Role.WU,120,640,this,5);
        huLuWa[5] = new HuLuWa("HuLuWa6.png", huLuWaPositionInfos[5].getCreatureCrdn().getPtX()*10, huLuWaPositionInfos[5].getCreatureCrdn().getPtY()*10, Role.LIU,120,740,this,6);
        huLuWa[6] = new HuLuWa("HuLuWa7.png", huLuWaPositionInfos[6].getCreatureCrdn().getPtX()*10, huLuWaPositionInfos[6].getCreatureCrdn().getPtY()*10, Role.QI,120,840,this,7);

        // 喽啰方阵
        xiaoLouLuoNum = 16;
        xiaoLouLuo = new XiaoLouLuo[xiaoLouLuoNum];
        xiaoLouLuo[0] = new XiaoLouLuo("XiaoLouLuo.png",1000,220,this,1);
        xiaoLouLuo[1] = new XiaoLouLuo("XiaoLouLuo.png",1000,220+150,this,2);
        xiaoLouLuo[2] = new XiaoLouLuo("XiaoLouLuo.png",1000,220+300,this,3);
        xiaoLouLuo[3] = new XiaoLouLuo("XiaoLouLuo.png",1000,220+450,this,4);
        xiaoLouLuo[4] = new XiaoLouLuo("XiaoLouLuo.png",1150,220,this,5);
        xiaoLouLuo[5] = new XiaoLouLuo("XiaoLouLuo.png",1150,220+150,this,6);
        xiaoLouLuo[6] = new XiaoLouLuo("XiaoLouLuo.png",1150,220+300,this,7);
        xiaoLouLuo[7] = new XiaoLouLuo("XiaoLouLuo.png",1150,220+450,this,8);
        xiaoLouLuo[8] = new XiaoLouLuo("XiaoLouLuo.png",1300,220,this,9);
        xiaoLouLuo[9] = new XiaoLouLuo("XiaoLouLuo.png",1300,220+150,this,10);
        xiaoLouLuo[10] = new XiaoLouLuo("XiaoLouLuo.png",1300,220+300,this,11);
        xiaoLouLuo[11] = new XiaoLouLuo("XiaoLouLuo.png",1300,220+450,this,12);
        xiaoLouLuo[12] = new XiaoLouLuo("XiaoLouLuo.png",1450,220,this,13);
        xiaoLouLuo[13] = new XiaoLouLuo("XiaoLouLuo.png",1450,220+150,this,14);
        xiaoLouLuo[14] = new XiaoLouLuo("XiaoLouLuo.png",1450,220+300,this,15);
        xiaoLouLuo[15] = new XiaoLouLuo("XiaoLouLuo.png",1450,220+450,this,16);

        // 爷爷、蛇精、蝎子精分别只有一个，其参数内设即可
        xieZiJing = new XieZiJing(this);
        sheJing = new SheJing(this);
        yeYe = new LaoYeYe(this);
    }

    private void setAttackImage(){
        /*if(huLuWa[0].isAttacking()){
            huLuWa[0].setImageByUrl("HuLuWa1-a.png");
        } else {
            huLuWa[0].setImageByUrl("HuLuWa1.png");
        }
        if(huLuWa[1].isAttacking()){
            huLuWa[1].setImageByUrl("HuLuWa2-a.png");
        } else {
            huLuWa[1].setImageByUrl("HuLuWa2.png");
        }
        if(huLuWa[2].isAttacking()){
            huLuWa[2].setImageByUrl("HuLuWa3-a.png");
        } else {
            huLuWa[2].setImageByUrl("HuLuWa3.png");
        }
        if(huLuWa[3].isAttacking()){
            huLuWa[3].setImageByUrl("HuLuWa4-a.png");
        } else {
            huLuWa[3].setImageByUrl("HuLuWa4.png");
        }
        if(huLuWa[4].isAttacking()){
            huLuWa[4].setImageByUrl("HuLuWa5-a.png");
        } else {
            huLuWa[4].setImageByUrl("HuLuWa5.png");
        }
        if(huLuWa[5].isAttacking()){
            huLuWa[5].setImageByUrl("HuLuWa6-a.png");
        } else {
            huLuWa[5].setImageByUrl("HuLuWa6.png");
        }
        if(huLuWa[6].isAttacking()){
            huLuWa[6].setImageByUrl("HuLuWa7-a.png");
        } else {
            huLuWa[6].setImageByUrl("HuLuWa7.png");
        }*/
    }

    private void startThread(){
        new Thread(yeYe).start();
        new Thread(sheJing).start();
        new Thread(xieZiJing).start();

        this.xiaoLouLuoNum=16;
        for(int i=0;i<xiaoLouLuoNum;i++) {
            new Thread(xiaoLouLuo[i]).start();
        }
        for(int i=0;i<7;i++){
            new Thread(huLuWa[i]).start();
        }
    }

    private void freshEndingType(){
        if (!yeYe.isAlive()) {
            for (Creature c : roles) {
                c.runningFlag = false;
            }
            this.endFlag = 1;
        }
        if (!xieZiJing.isAlive() && !sheJing.isAlive()) {
            mainEvilDeadFlag = true;
        }

        int flag = 0;
        for (Creature role : roles) {
            if (!role.isRightRole()) {
                flag = 1;
            }

        }
        if (flag == 0) {
            for (Creature c : roles) {
                c.runningFlag = false;
            }
            this.endFlag = 2;
        }

        if(this.endFlag==1 && (runningMode==RunningMode.PLAYING || runningMode==RunningMode.PLAYBACK)){
            flushGround.setImage(badEndingGround.getImage());
            runningMode=RunningMode.OVER;
        } else if(this.endFlag==2 && (runningMode==RunningMode.PLAYING || runningMode==RunningMode.PLAYBACK)){
            flushGround.setImage(happyEndingGround.getImage());
            runningMode=RunningMode.OVER;
        }
    }

    private void freshRole(){
        Iterator<Creature> iterator = roles.iterator();
        while(iterator.hasNext()){
            Creature c = iterator.next();
            if (!c.isAlive()) {
                iterator.remove();
            }
        }

        Iterator<Creature> iteratorDeath = death.iterator();
        while(iteratorDeath.hasNext()){
            Creature c = iteratorDeath.next();
            if (c.isAlive()) {
                iteratorDeath.remove();
            }
        }

        for(HuLuWa huluwa: huLuWa) {
            if(huluwa.isAlive() && !roles.contains(huluwa)) {
                roles.add(huluwa);
            } else if(!huluwa.isAlive() && !death.contains(huluwa)){
                death.add(huluwa);
            }
        }
        if(yeYe.isAlive() && !roles.contains(yeYe)) {
            roles.add(yeYe);
        } else {
            death.add(yeYe);
        }
        if(sheJing.isAlive() && !roles.contains(sheJing)) {
            roles.add(sheJing);
        } else if(!sheJing.isAlive() && !death.contains(sheJing) && sheDieFlag==0){
            death.add(sheJing);
        }
        if(xieZiJing.isAlive() && !roles.contains(xieZiJing)) {
            roles.add(xieZiJing);
        } else if(!xieZiJing.isAlive() && !death.contains(xieZiJing) && xieZiDieFlag==0){
            death.add(xieZiJing);
        }
        for(XiaoLouLuo louluo: xiaoLouLuo){
            if(louluo.isAlive() && !roles.contains(louluo)) {
                roles.add(louluo);
            } else if(!louluo.isAlive() && !death.contains(louluo)){
                death.add(louluo);
            }
        }
    }
    
}
