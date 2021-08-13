import java.util.*;
public class rubikscubetest {
    public static void main (String[] args){
        rubikscube r=new rubikscube(2);
        r.scramble();
        r.solve();
        rubikscube c=new rubikscube(3);
        c.scramble();
        c.solve();
    }
}
