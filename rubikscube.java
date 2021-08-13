import java.util.*;
public class rubikscube {
    //instance cube, length, sides
    public char[][][] cube;
    public int len;
    public static final int SIDES=6;                    
    private ArrayList<String> scramble;
    private ArrayList<Character> strat;
    private Corner[] corners; 
    private Edge[] edges; 
    public int count=0;
    private char[][][] refCube;
    public String[] LOCATEC={"U","U","U","U","L","F","F","F","F","Up","R","R","R","R","Up","B","B","B","B","Up","L","L","L","L","Up","L","D","D","D","D","L","L"};
    public String[] COORDSC;
    public String[] LOCATEE={"U","Up","Up","Up","L","Fp","Fp","Fp","Up","Rp","Rp","Rp","Rp","Up","Bp","Bp","Bp","Bp","Up","Lp","Lp","Lp","Lp","Up","Fp","L","Dp","Dp","Dp","Dp","L","L","U","U"};
    public String[] COORDSE={"12","21","12","01","10","10","21","12","01","01","10","21","12","01","01","10","21","12","01","01","10","21","12","01","01","10","10","21","12","01","10","10","10","01","12"};
    
    //constructor. set solved cube. white, red, blue, orange, green, yellow. make the reference cube
    public rubikscube(int n){
        cube=new char[n][n][SIDES];
        len=n;
        scramble=new ArrayList<String>();
        strat=new ArrayList<Character>();
        for (int i=0;i<SIDES;i++){
            for (int j=0;j<n;j++){
                for (int k=0;k<n;k++){
                    if (i==0){
                        cube[j][k][i]='w';
                    } else if (i==1){
                        cube[j][k][i]='r';
                    } else if (i==2){ 
                        cube[j][k][i]='b';
                    } else if (i==3){
                        cube[j][k][i]='o';
                    } else if (i==4){
                        cube[j][k][i]='g';
                    } else {
                        cube[j][k][i]='y';
                    }
                }
            }
        }
        printCube(cube,len);
        corners=getCorners();
        edges=getEdges();
        refCube=new char[3][3][SIDES];
        makeRef();
        COORDSC=getCOORDSC();
    }
    public void makeRef(){
      String a="abcdefghijklmnopqrstwxyz";
      String b="ABCDEFGHIJKLMNOPQRSTWXYZ";
      char[] cornerNames=a.toCharArray();
      char[] edgeNames=b.toCharArray();
      int countCorner=0;
      int countEdge=0;
      for (int i=0;i<SIDES;i++){
         for (int j=0;j<3;j++){
            for (int k=0;k<3;k++){
               if (isCornerRef(j,k)){
                  refCube[j][k][i]=cornerNames[countCorner];
                  countCorner++;
               } else if (isEdgeRef(j,k)){
                  refCube[j][k][i]=edgeNames[countEdge];
                  countEdge++;
               } else {
                  refCube[j][k][i]=cube[0][0][i];
               }
            }
          }
       }
    }
    public Corner[] getCorners() {
      int a=len-1;
      Corner c1=new Corner(0,0,0,0,a,3,0,0,4);//aka the buffer
      Corner c2=new Corner(0,a,0,0,a,2,0,0,3);
      Corner c3=new Corner(a,0,0,0,0,1,0,a,4);
      Corner c4=new Corner(a,a,0,0,a,1,0,0,2);
      Corner c5=new Corner(0,0,5,a,0,1,a,a,4);
      Corner c6=new Corner(0,a,5,a,a,1,a,0,2);
      Corner c7=new Corner(a,0,5,a,a,3,a,0,4);
      Corner c8=new Corner(a,a,5,a,a,2,a,0,3);
      Corner[] c=new Corner[8];
      c[0]=c1;
      c[1]=c2;
      c[2]=c3;
      c[3]=c4;
      c[4]=c5;
      c[5]=c6;
      c[6]=c7;
      c[7]=c8;
      return c;
    }
    public Edge[] getEdges(){
      Edge e1=new Edge(0,1,0,0,1,3);
      Edge e2=new Edge(1,0,0,0,1,4);
      Edge e3=new Edge(1,2,0,0,1,2);
      Edge e4=new Edge(2,1,0,0,1,1);
      Edge e5=new Edge(1,0,2,1,2,1);
      Edge e6=new Edge(1,0,3,1,2,2);
      Edge e7=new Edge(1,0,4,1,2,3);
      Edge e8=new Edge(1,0,1,1,2,4);
      Edge e9=new Edge(0,1,5,2,1,1);
      Edge e10=new Edge(1,0,5,2,1,4);
      Edge e11=new Edge(1,2,5,2,1,2);
      Edge e12=new Edge(2,1,5,2,1,3);
      Edge[] edge=new Edge[12];
      edge[0]=e1;
      edge[1]=e2;
      edge[2]=e3;
      edge[3]=e4;
      edge[4]=e5;
      edge[5]=e6;
      edge[6]=e7;
      edge[7]=e8;
      edge[8]=e9;
      edge[9]=e10;
      edge[10]=e11;
      edge[11]=e12;
      return edge;
    }  
    
    //print cube
    public void printCube(char[][][] cube, int l){
       //print white
       for (int i=0;i<l;i++){
           for (int j=0;j<l;j++){
              System.out.print(cube[i][j][0]+" ");
           }
           System.out.println();
        }
        System.out.println();
        //print r,b,o,g
        for (int i=0;i<l;i++){
           for (int j=1;j<SIDES-1;j++){
              for (int k=0;k<l;k++){
                 System.out.print(cube[i][k][j]+" ");
              }
              System.out.print("  ");
           }
           System.out.println();
        }
        //print yellow
        System.out.println();
        for (int i=0;i<l;i++){
           for (int j=0;j<l;j++){
              System.out.print(cube[i][j][5]+" ");
           }
           System.out.println();
        }
    }
    
    //scramble cube
    public void scramble(){
      System.out.println();
      int rand1=(int)(Math.random()*100)+25;
      int rand2=0;
      for (int i=0;i<rand1;i++){
         rand2=(int)(Math.random()*12);
         if (rand2==0){
            U();
            scramble.add("U");
         } else if (rand2==1){
            Up();
            scramble.add("Up");
         } else if (rand2==2){
            D();
            scramble.add("D");
         } else if (rand2==3){
            Dp();
            scramble.add("Dp");
         } else if (rand2==4){
            R();
            scramble.add("R");
         } else if (rand2==5){
            Rp();
            scramble.add("Rp");
         } else if (rand2==6){
            L();
            scramble.add("L");
         } else if (rand2==7){
            Lp();
            scramble.add("Lp");
         } else if (rand2==8){
            F();
            scramble.add("F");
         } else if (rand2==9){
            Fp();
            scramble.add("Fp");
         } else if (rand2==10){
            B();
            scramble.add("B");
         } else {
            Bp();
            scramble.add("Bp");
         }
      }
      printCube(cube,len);
      System.out.println();
      System.out.println(getScramble());
      scramble.clear();
    }
    
    //solving the cube. Using the blindfolded solve method.
    public void solve(){
      if (len>1){
         corners();
         if(len<3){
            System.out.println();
            printStrat();
            System.out.println();
         }
         System.out.println();
         printCube(cube,len);
      }
      if(len>2){
         edges();
         System.out.println();
         printStrat();
         System.out.println();
         System.out.println();
         printCube(cube,len);
      }  
      System.out.println();
      strat.clear();
      count=0;
    }
    public void corners(){
      while(!(cornersInPosition())){
         excecuteCorner(getNextMoveCorner());
      }
    }
    public void edges(){
      while(!(edgesInPosition())){
         excecuteEdge(getNextMoveEdge());
      }
    }
    //returns the reference character for where top thingy needs to go
    public char findDestination(int r, int c, int side){
      if(len==2&&r==len-1){
         r=len;
      }
      if (len==2&&c==len-1){
         c=len;
      }
      return refCube[r][c][side];
    }
    public char getNextMoveCorner(){
    //get an array list of moves, probably convert into actual moves so you don't have to do them over and over
    //after each move, check if the cube is in position. If it is, use findDestination to find the char value
    //complete the array list so that cube is back in its original position
    //return the char value at the end
      char a=' ';
      int side=0;
      int r=0;
      int c=0;
      int times=0;
      for (int i=0;i<LOCATEC.length;i++){
         if (i<5){
            side=0;
         } else if (i<10||i==25||i==31){
            side=1;
         } else if (i<15){
            side=2;
         } else if (i<20){
            side=3;
         } else if (i<25){
            side=4;
         } else if (i<31){
            side=5;
         } else {
            side=0;
         }
         r=Integer.parseInt(COORDSC[i].substring(0,1));
         c=Integer.parseInt(COORDSC[i].substring(1));
         if (isInPosition(r,c,side)&&times==0){ //fix isInPosition
            a=findDestination(r,c,side); //fix refCube
            times++;
         }
         convert(LOCATEC[i]);
      }
      strat.add(a);
      return a;
    }
    public char getNextMoveEdge(){
      char a=' ';
      int side=0;
      int r=0;
      int c=0;
      int times=0;
      for (int i=0;i<LOCATEE.length;i++){
         if (i<5||(i<35&&i>31)){  
            side=0;
         } else if (i<9||i==24||i==25||i==31){
            side=1;
         } else if (i<14){
            side=2;
         } else if (i<19){
            side=3;
         } else if (i<24){
            side=4;
         } else if (i<31){
            side=5;
         } else {
            side=0;
         }
         r=Integer.parseInt(COORDSE[i].substring(0,1));
         c=Integer.parseInt(COORDSE[i].substring(1));
         if (isInPosition(r,c,side)&&times==0){//try to find the coordinates
            times++;
            a=findDestination(r,c,side);   
         }
         convert(LOCATEE[i]);
      }
      strat.add(a);
      return a;
    }
    
    //algorithms for solving
    //swaps two corner pieces
    public void excecuteCorner(char c){
      if (c=='a'||c=='n'||c=='q'){
         c=findNewCorner();
         strat.set(strat.size()-1, c);
      }
      if (c=='x'||c=='y'||c=='w'||c=='z'){
         if(c=='x'){
            Dp();
         }
         if (c=='y'){
            D();
         } 
         if (c=='z'){
            D();
            D();
         }
         Fp();
      } else if (c=='i'||c=='j'||c=='l'||c=='p'){
         if (c=='i'){
            Rp();
         }
         if (c=='j'){
            R();
            R();
         }
         if (c=='l'){
            R();
         }
         if (c=='p'){
            Dp();
            R();
         }
      } else if (c=='e'||c=='f'||c=='g'||c=='h'||c=='s'){
         if(c=='e'){
            Fp();
         }
         if (c=='f'){
            F();
            F();
         } 
         if (c=='h'){
            F();
         }
         if (c=='s'){
            D();
         }
         D();
      } else if (c=='b'||c=='o'){
         if(c=='b'){
            R();
         }
         Dp();
      } else if (c=='d'||c=='r'||c=='m'){
         if(c=='r'){
            F();
         }
         if (c=='m'){
            Rp();
         }
         F();
      } else if (c=='c'||c=='t'){
         if(c=='t'){
            F();
         }
         F();
         Rp();
      }
      //do y perm
      yPerm();
      //get it back to normal
      if (c=='x'||c=='y'||c=='w'||c=='z'){
         F();
         if(c=='x'){
            D();
         }
         if (c=='y'){
            Dp();
         } 
         if (c=='z'){
            D();
            D();
         }
      } else if (c=='i'||c=='j'||c=='l'||c=='p'){
         if (c=='i'){
            R();
         }
         if (c=='j'){
            R();
            R();
         }
         if (c=='l'){
            Rp();
         }
         if (c=='p'){
            Rp();
            D();
         }
      } else if (c=='e'||c=='f'||c=='g'||c=='h'||c=='s'){
         Dp();         
         if(c=='e'){
            F();
         }
         if (c=='f'){
            F();
            F();
         } 
         if (c=='h'){
            Fp();
         }
         if (c=='s'){
            Dp();
         }
      } else if (c=='b'||c=='o'){
         D();
         if(c=='b'){
            Rp();
         }
      } else if (c=='d'||c=='r'||c=='m'){
         Fp();
         if(c=='r'){
            Fp();
         }
         if (c=='m'){
            R();
         }
      } else if (c=='c'||c=='t'){
         R();
         Fp();
         if(c=='t'){
            Fp();
         }
      }
    }
    public void excecuteEdge(char c){
      if (c=='C'||c=='I'){
         c=findNewEdge();
         strat.set(strat.size()-1, c);
      }
      if (c=='W'||c=='X'||c=='Y'||c=='Z'){
         if(c=='W'){
            Dp();
         }
         if (c=='Y'){
            D();
            D();
         } 
         if (c=='Z'){
            D();
         }
         L();
         L();
      } else if (c=='F'||c=='J'||c=='N'||c=='R'){
         if(c=='J'){
            M();
         }
         if (c=='N'){
            M();
            M();
         } 
         if (c=='R'){
            Mp();
         }
         Lp();
      } else if (c=='G'||c=='K'||c=='O'||c=='S'){
         if(c=='K'){
            Mp();
         }
         if (c=='G'){
            M();
            M();
         } 
         if (c=='S'){
            M();
         }
         L();
      } else if (c=='E'||c=='H'||c=='M'||c=='P'||c=='L'){
         if(c=='L'){
            Dp();
         }
         if(c=='E'||c=='H'||c=='L'){
            MRp();
         } else {
            MR();
         }
         if (c=='E'||c=='P'){
            Dp();
         } else {
            D();
         }
         L();
         L();
      } else if (c=='Q'||c=='T'){
         L();
         if(c=='Q'){
            M();
            L();
         } else {
            Mp();
            Lp();
         }
      }
      //t perm/ja/jb perm  
      if (c=='A'){
         jaPerm();
      } else if (c=='D'){
         jbPerm();
      } else {  
         tPerm();
      }
      //cases for putting it back
      if (c=='W'||c=='X'||c=='Y'||c=='Z'){
         L();
         L();
         if(c=='W'){
            D();
         }
         if (c=='Y'){
            D();
            D();
         } 
         if (c=='Z'){
            Dp();
         }
      } else if (c=='F'||c=='J'||c=='N'||c=='R'){
         L();
         if(c=='J'){
            Mp();
         }
         if (c=='N'){
            M();
            M();
         } 
         if (c=='R'){
            M();
         }
      } else if (c=='G'||c=='K'||c=='O'||c=='S'){
         Lp();
         if(c=='K'){
            M();
         }
         if (c=='G'){
            M();
            M();
         } 
         if (c=='S'){
            Mp();
         }
      } else if (c=='E'||c=='H'||c=='M'||c=='P'||c=='L'){
         L();
         L();
         if (c=='E'||c=='P'){
            D();
         } else {
            Dp();
         }
         if(c=='E'||c=='H'||c=='L'){
            MR();
         } else {
            MRp();
         }
         if(c=='L'){
            D();
         }
      } else if (c=='Q'||c=='T'){
         if(c=='Q'){
            Lp();
            Mp();
         } else {
            L();
            M();
         }
         Lp();
      }
      
    }
    public void yPerm(){
      R();
      Up();
      Rp();
      Up();
      R();
      U();
      Rp();
      Fp();
      R();
      U();
      Rp();
      Up();
      Rp();
      F();
      R();
    }
    //swaps two edge pieces
    public void tPerm(){
      R();
      U();
      Rp();
      Up();
      Rp();
      F();
      R();
      R();
      Up();
      Rp();
      Up();
      R();
      U();
      Rp();
      Fp();
    }
    //special cases in solving edges, for conveniency's sake
    public void jaPerm(){
      Fp();
      U();
      Bp();
      U();
      U();
      F();
      Up();
      Fp();
      U();
      U();
      B();
      F();
      Up();
    }
    public void jbPerm(){
      R();
      U();
      Rp();
      Fp();
      R();
      U();
      Rp();
      Up();
      Rp();
      F();
      R();
      R();
      Up();
      Rp();
      Up();
    }
    
    //moving basics
    //rotates a face in the counterclockwise direction
    public void rotateFace(int side){
       char[][] c=new char[len][len];
       //loop through rows on cube, cols on c
       for (int j=0;j<len;j++){
         //loop through rows
          for (int i=0;i<len;i++){
            c[i][j]=cube[j][len-1-i][side];
          }
       }
       //set c to face in cube
       for (int i=0;i<len;i++){
         for (int j=0;j<len;j++){
            cube[i][j][side]=c[i][j];
         }
      }
    }
    //rotates the pieces just below the face for u and d moves
    
    public void switchFacesUorD(char d){    
      int a=0;
      if (d=='u'){
         a=0;//coordinate 
      } 
      if (d=='d'){
         a=len-1;
      }
      if (d=='m'){
         a=1;//might have to change this later
      }
      char temp;
      for (int i=0;i<len;i++){//loop for amount of edges 
         for (int j=0;j<3;j++){  //loop for amount of switches to make
            if (j==0){
               temp=cube[a][i][2];
               cube[a][i][2]=cube[a][i][1];
               cube[a][i][1]=temp;
            } else if (j==1){
               temp=cube[a][i][4];
               cube[a][i][4]=cube[a][i][1];
               cube[a][i][1]=temp;
            } else if (j==2){
               temp=cube[a][i][4];
               cube[a][i][4]=cube[a][i][3];
               cube[a][i][3]=temp;
            }
         }
      }
    }
    //switches faces for L and R moves
    public void switchFacesRorL(char d){
      int a=0;
      int b=0;
      if (d=='l'){
         a=0;//column number
         b=len-1;
      } 
      if (d=='r'){
         a=len-1;
         b=0;
      }
      if (d=='m'){
         a=1;
         b=1;
      }
      char temp;
      for (int i=0;i<len;i++){
         for (int j=0;j<3;j++){  
            if (j==0){
               temp=cube[i][a][1];
               cube[i][a][1]=cube[i][a][0];
               cube[i][a][0]=temp;
            } else if (j==1){
               temp=cube[len-1-i][b][3];
               cube[len-1-i][b][3]=cube[i][a][1];
               cube[i][a][1]=temp;
            } else if (j==2){
               temp=cube[i][a][5];
               cube[i][a][5]=cube[i][a][1];
               cube[i][a][1]=temp;
            }
         }
      }
    }
    public void switchFacesForB(char d){
      int a=0;
      int b=0;
      if (d=='f'){
         a=0;//row number
         b=len-1;
      } 
      if (d=='b'){
         a=len-1;
         b=0;
      }
      char temp;
      for (int i=0;i<len;i++){
         for (int j=0;j<3;j++){  
            if (j==0){
               temp=cube[b][i][0];
               cube[b][i][0]=cube[i][a][2];
               cube[i][a][2]=temp;
            } else if (j==1){
               temp=cube[len-i-1][b][4];
               cube[len-i-1][b][4]=cube[i][a][2];
               cube[i][a][2]=temp;
            } else if (j==2){
               temp=cube[i][a][2];
               cube[i][a][2]=cube[a][len-i-1][5];
               cube[a][len-i-1][5]=temp;
            }
         }
      }

    }
    
    //moves. 12 of them
    public void U(){
      for (int i=0;i<3;i++){
         rotateFace(0);
         switchFacesUorD('u');
      }
      count++;
    }
    public void Up(){
      rotateFace(0);
      switchFacesUorD('u');
      count++;
    }
    public void Mp(){//goes in the direction of Up or D.
      switchFacesUorD('m');
      count++;
    }
    public void M(){//goes in the direction of Up or D.
      for (int i=0;i<3;i++){
         switchFacesUorD('m');
      }
      count++;
    }
    public void MR(){//goes in the direction of R or Lp.
      switchFacesRorL('m');
      count++;
    }
    public void MRp(){//goes in the direction of L or Rp.
      for (int i=0;i<3;i++){
         switchFacesRorL('m');
      }
      count++;
    }
    public void D(){
      for (int i=0;i<3;i++){
         rotateFace(5);
      }
      switchFacesUorD('d');  
      count++;
    }
    public void Dp(){
      rotateFace(5);
      for (int i=0;i<3;i++){
         switchFacesUorD('d');
      }
      count++;
    }
    public void R(){
      for (int i=0;i<3;i++){
         rotateFace(2);
      }
      switchFacesRorL('r');
      count++;
    }
    public void Rp(){
      rotateFace(2);
      for (int i=0;i<3;i++){
         switchFacesRorL('r');
      }
      count++;
    }
    public void L(){
      for (int i=0;i<3;i++){
         rotateFace(4);
         switchFacesRorL('l');
      }
      count++;
    }
    public void Lp(){
      rotateFace(4);
      switchFacesRorL('l');
      count++;
    }
    public void F(){
      for (int i=0;i<3;i++){
         rotateFace(1);
         switchFacesForB('f');
      }
      count++;
    }
    public void Fp(){      
      rotateFace(1);
      switchFacesForB('f');
      count++;
    }
    public void B(){
      for (int i=0;i<3;i++){
         rotateFace(3);
      }
      switchFacesForB('b');
      count++;
    }
    public void Bp(){
      rotateFace(3);
      for (int i=0;i<3;i++){
         switchFacesForB('b');
      }
      count++;
    }
    
    //testing methods
    //set face 0 to a's, b's, and c's so that you could see the rotateFace method
    public void changeUpSide(){
      for(int i=0;i<len;i++){
         for (int j=0;j<len;j++){
            if (i==0){
               cube[i][j][0]='a';
            }else if (i==1){
               cube[i][j][0]='b';
            } else {
               cube[i][j][0]='c';
            }
         }
      }
   }
   //returns a string of the scramble done, check if scramble on cube matches scramble on computer
    public String getScramble(){
      String moves="Scramble: ";
      for(String s:scramble){
         moves+=s+" ";
      }
      moves+="Amount of moves: "+scramble.size();
      return moves;
    }
    //just to distinguish pieces from one another.
    public boolean isCenter(int r, int c){
      if (r!=0&&r!=len-1&&c!=0&&c!=len-1){
         return true;
      }
      return false;
    }
    public boolean isEdge(int r, int c){
      return !(isCenter(r,c)||isCorner(r,c));
    }
    public boolean isCorner(int r, int c){
      return ((r==0&&c==len-1)||(r==0&&c==0)||(r==len-1&&c==len-1)||(r==len-1&&c==0));
    }
    //method to check if a piece is in the correct position
    public boolean isInPosition(int r, int c, int side){
      if (isCenter(r,c)){
         return true;
      } else if (isCorner(r,c)){
         return isInPositionCorner(r,c,side);
      } else {
         return isInPositionEdge(r,c,side);
      }
    }
    public boolean isInPositionEdge(int r, int c, int side){
      int e=0;
      for (int i=0;i<edges.length;i++){
         int[][] a=edges[i].getEdge();
         for (int j=0;j<a.length;j++){
            if (r==a[j][0]&&c==a[j][1]&&side==a[j][2]){
               e=i;
            }
         }
      }
      Edge edg=edges[e];
      int[][] b=edg.getEdge();
      for (int i=0;i<b.length;i++){
          if(!(cube[b[i][0]][b[i][1]][b[i][2]]==cube[1][1][b[i][2]])){
            return false;
         }
      }  
      return true;
    }
    public boolean isInPositionCorner(int r, int c, int side){
      //find the corner which this piece belongs to
      //loops through all the corners
      int corner=0;
      for (int i=0;i<corners.length;i++){
         //loop through the possible three parts of the cube
         int[][] a=corners[i].getCorner();
         for (int j=0;j<a.length;j++){
            if (r==a[j][0]&&c==a[j][1]&&side==a[j][2]){
               corner=i;
            }
         }
      } 
      Corner corn=corners[corner];
      int[][] b=corn.getCorner();
      //see if the side matches the center piece
      for (int i=0;i<b.length;i++){
         if(cube[b[i][0]][b[i][1]][b[i][2]]!=refCube[1][1][b[i][2]]){
            return false;
         }
      }  
      return true;
    }
    public void convert(String s){
      if (s.equals("U")){
            U();
         } else if (s.equals("Up")){
            Up();
         } else if (s.equals("D")){
            D();
         } else if (s.equals("Dp")){
            Dp();
         } else if (s.equals("R")){
            R();
         } else if (s.equals("Rp")){
            Rp();
         } else if (s.equals("L")){
            L();
         } else if (s.equals("Lp")){
            Lp();
         } else if (s.equals("F")){
            F();
         } else if (s.equals("Fp")){
            Fp();
         } else if (s.equals("B")){
            B();
         } else {
            Bp();
         }

    }
    public boolean cornersInPosition(){
      for (int i=0;i<corners.length;i++){
         int[][] pos=corners[i].getCorner();
         if(!(isInPosition(pos[0][0],pos[0][1], pos[0][2]))){
            return false;
         }
      }
      return true;
    }
    public boolean edgesInPosition(){
      for (int i=0;i<edges.length;i++){
         int[][] pos=edges[i].getEdge();
         if(!(isInPosition(pos[0][0],pos[0][1], pos[0][2]))){
            return false;
         }
      }
      return true;
    }
    public boolean isSolved(){
      return cornersInPosition()||edgesInPosition();
    }
    public char findNewCorner(){
      for (int i=corners.length-1;i>=0;i--){
         int[][] pos=corners[i].getCorner();
         if (!(isInPosition(pos[0][0], pos[0][1], pos[0][2]))&&i!=0){
            return findDestination(pos[0][0], pos[0][1], pos[0][2]);
         }
      }
      return ' ';
    }
    public char findNewEdge(){
      for (int i=edges.length-1;i>=0;i--){
         int[][] pos=edges[i].getEdge();
         if (!(isInPosition(pos[0][0], pos[0][1], pos[0][2]))&&i!=2){
            return findDestination(pos[0][0], pos[0][1], pos[0][2]);
         }
      }
      return ' ';
    }
    public void printRect(int[][] a){
      for(int i=0;i<a.length;i++){
         for(int j=0;j<a[i].length;j++){
            System.out.print(a[i][j]+" ");
         }
         System.out.println();
      }
    }
    public void printStrat(){
      System.out.print("Strategy: ");
      for(int i=0;i<strat.size();i++){
         System.out.print(strat.get(i)+" ");
      }
    } 
    public String[] getCOORDSC(){
      int a=len-1;
      String[] b={"00","0"+a,a+""+a,a+"0","00","00","0"+a,a+""+a,a+"0","00","00","0"+a,a+""+a,a+"0","00","00","0"+a,a+""+a,a+"0","00","00","0"+a,a+""+a,a+"0","00","00","00","0"+a,a+""+a,a+"0","00","00"};
      return b;
    }
    public boolean isCornerRef(int r, int c){
      int a=refCube.length;
      return ((r==0&&c==a-1)||(r==0&&c==0)||(r==a-1&&c==a-1)||(r==a-1&&c==0));
    }
    public boolean isCenterRef(int r, int c){
      int a=refCube.length;
      if (r!=0&&r!=a-1&&c!=0&&c!=a-1){
         return true;
      }
      return false;
    }
    public boolean isEdgeRef(int r, int c){
      return !(isCenterRef(r,c)||isCornerRef(r,c));
    }
}