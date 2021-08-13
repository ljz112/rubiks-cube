import java.util.*;
public class Corner {
   private int[][] pieces;
   public Corner(int... a){
      int[] c=a;
      pieces=new int[3][3];
      int count=0;
      for (int i=0;i<pieces.length;i++){
         for(int j=0;j<pieces.length;j++){
            pieces[i][j]=c[count];
            count++;
         }
      }
   }
   public int[][] getCorner(){
      return pieces;
   }
}