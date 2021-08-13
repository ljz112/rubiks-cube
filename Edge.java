import java.util.*;
public class Edge {
   private int[][] pieces;
   public Edge (int... a){
      int[] e=a;
      pieces=new int[2][3];
      int count=0;
      for(int i=0;i<pieces.length;i++){
         for(int j=0;j<pieces[0].length;j++){
            pieces[i][j]=e[count];
            count++;
         }
      }
   }
   public int[][] getEdge(){
      return pieces;
   }
}