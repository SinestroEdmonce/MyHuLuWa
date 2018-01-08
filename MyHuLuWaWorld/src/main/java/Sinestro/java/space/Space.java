package space;
import creature.*;

public class Space {
    private int spaceLength;
    private int spaceWidth;
    private PositionInfo positionInfo[][];

    public Space(int length,int width){
        this.spaceWidth=width;
        this.spaceLength=length;
        this.positionInfo=new PositionInfo[this.spaceLength][this.spaceWidth];

        for (int i=0;i<this.spaceLength;i++){
            for (int j=0;j<this.spaceWidth;j++){
                positionInfo[i][j]=new PositionInfo(new Coordinate(i,j));
            }
        }
    }

    public PositionInfo[] getHuLuWaPositionInfo(){
        PositionInfo[] huLuWaPositionInfo=new PositionInfo[10];
        //HuLuWa:Total number<=7
        int count=0;

        for(int i=0;i<this.spaceLength;i++){
            for(int j=0;j<this.spaceWidth;j++){
                if(this.positionInfo[i][j].isHolderEmpty()==false){
                    huLuWaPositionInfo[count]=positionInfo[i][j];
                    count++;
                }
            }
        }
        return huLuWaPositionInfo;
    }

    public PositionInfo getPositionInfo(int ptx,int pty){
        return this.positionInfo[ptx][pty];
    }

    public PositionInfo getPositionInfo(Coordinate tmp){
        return this.positionInfo[tmp.getPtX()][tmp.getPtY()];
    }

    public int getSpaceLength(){
        return this.spaceLength;
    }

    public int getSpaceWidth(){
        return this.spaceWidth;
    }

    public void clearSpace(){
        for(int i=0;i<this.spaceLength;i++){
            for (int j=0;j<this.spaceWidth;j++){
                this.positionInfo[i][j].setHolder(null);
                this.positionInfo[i][j].setCreatureCrdn(new Coordinate(i,j));
            }
        }
    }

    public void displaySpace(){
        for (int i=0;i<this.spaceLength;i++){
            for (int j=0;j<this.spaceWidth;j++){
                if (this.positionInfo[i][j].isHolderEmpty()==true){
                    System.out.print(" ");
                }
                else {
                    System.out.print(this.positionInfo[i][j].getHolder().getCreatureName());
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
