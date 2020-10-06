/* package codechef; // don't place package name! */

import java.util.*;
import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */

class Board {
    public int[][] arr;
    public int countb, countw;

    Board() {
        arr = new int[8][8];
        countb = 12;
        countw = 12;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = checkers.compliment(i % 2); j < 8; j += 2) { // initialize board
                arr[i][j] = -1;
            }
        }
        for (int i = 7; i >= 5; i--) {
            for (int j = checkers.compliment(i % 2); j < 8; j += 2) {
                arr[i][j] = 1;
            }
        }
    }

    Board(Board b) {
        arr = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                arr[i][j] = b.arr[i][j];
            }
        }

        countb = b.countb;
        countw = b.countw;
    }

}

class Policy {
    int[][] arr;
    double val;

    Policy(int a) {
        arr = new int[8][8];
        if (a == 0) {
            val = Integer.MIN_VALUE;
        } else {
            val = Integer.MAX_VALUE;
        }
    }
}

class checkers {
    static double eval(int[][] arr) {
        double wo = 0, we = 0, bo = 0, be = 0, wke = 0, wko = 0, bke = 0, bko = 0, n = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (arr[i][j] != 0) {
                    n++;
                }
                if (arr[i][j] == 2) {
                    if (i >= 4)
                        wko++;
                    else
                        wke++;

                }
                if (arr[i][j] == -2) {
                    if (i >= 4)
                        bke++;
                    else
                        bko++;

                }
                if (i <= 3) {
                    if (arr[i][j] == 1) {
                        we++;

                    }
                    if (arr[i][j] == -1) {
                        bo++;

                    }
                }
                if (i >= 4) {
                    if (arr[i][j] == 1) {
                        be++;

                    }
                    if (arr[i][j] == -1) {
                        wo++;

                    }
                }
            }
        }
        double ans = (5 * wo + 7 * we + 10 * wko + 15 * wke - (5 * bo + 7 * be + 10 * bko + 15 * bke) )/n ;
        return ans;
    }

    static public int max_depth = 5;

    static boolean killleftup(int i, int j, Board b) {
        if (b.arr[i][j] > 0 && check(i - 2, j - 2) && b.arr[i - 1][j - 1] < 0 && b.arr[i - 2][j - 2] == 0) {
            b.arr[i - 1][j - 1] = 0;
            b.arr[i - 2][j - 2] = b.arr[i][j];
            b.arr[i][j] = 0;
            b.countb = b.countb - 1;
            if (i - 2 == 0)
                b.arr[i - 2][j - 2] = 2;
            // Board b1= new Board(b);
            // Board b2= new Board(b);
            // Board b3= new Board(b);
            // Board b4= new Board(b);
            killleftup(i - 2, j - 2, b);
            killrightup(i - 2, j - 2, b);
            if (b.arr[i - 2][j - 2] == 2) {
                killleftdown(i - 2, j - 2, b);
                killrightdown(i - 2, j - 2, b);
            }

            return true;
        } else if (b.arr[i][j] < 0 && check(i - 2, j - 2) && b.arr[i - 1][j - 1] > 0 && b.arr[i - 2][j - 2] == 0) {
            b.arr[i - 1][j - 1] = 0;
            b.arr[i - 2][j - 2] = b.arr[i][j];
            b.arr[i][j] = 0;
            b.countw = b.countw - 1;

            killleftup(i - 2, j - 2, b);
            killrightup(i - 2, j - 2, b);
            if (b.arr[i - 2][j - 2] == -2) {
                killleftdown(i - 2, j - 2, b);
                killrightdown(i - 2, j - 2, b);
            }
            return true;

        }
        return false;
    }

    static boolean killrightup(int i, int j, Board b) {
        if (b.arr[i][j] > 0 && check(i - 1, j + 1) && check(i - 2, j + 2) && b.arr[i - 1][j + 1] < 0
                && b.arr[i - 2][j + 2] == 0) {
            b.arr[i - 1][j + 1] = 0;
            b.arr[i - 2][j + 2] = b.arr[i][j];
            b.arr[i][j] = 0;
            b.countb--;
            if (i - 2 == 0)
                b.arr[i - 2][j + 2] = 2;

            killleftup(i - 2, j + 2, b);
            killrightup(i - 2, j + 2, b);
            if (b.arr[i - 2][j + 2] == 2) {
                killleftdown(i - 2, j + 2, b);
                killrightdown(i - 2, j + 2, b);
            }
            return true;
        } else if (b.arr[i][j] < 0 && check(i - 1, j + 1) && check(i - 2, j + 2) && b.arr[i - 1][j + 1] > 0
                && b.arr[i - 2][j + 2] == 0) {
            b.arr[i - 1][j + 1] = 0;
            b.arr[i - 2][j + 2] = b.arr[i][j];
            b.arr[i][j] = 0;
            b.countw--;

            killleftup(i - 2, j + 2, b);
            killrightup(i - 2, j + 2, b);
            if (b.arr[i - 2][j + 2] == -2) {
                killleftdown(i - 2, j + 2, b);
                killrightdown(i - 2, j + 2, b);
            }
            return true;
        }
        return false;
    }

    static boolean leftup(int i, int j, Board b) {
        if (check(i - 1, j - 1) && b.arr[i - 1][j - 1] == 0) {

            b.arr[i - 1][j - 1] = b.arr[i][j];
            if (b.arr[i][j] > 0 && i - 1 == 0) {
                b.arr[i - 1][j - 1] = 2;
            }
            b.arr[i][j] = 0;
            return true;

        } else
            return killleftup(i, j, b);

    }

    static boolean rightup(int i, int j, Board b) {
        if (check(i - 1, j + 1) && b.arr[i - 1][j + 1] == 0) {
            b.arr[i - 1][j + 1] = 1;
            if (b.arr[i][j] > 0 && i - 1 == 0) {
                b.arr[i - 1][j + 1] = 2;
            }
            b.arr[i][j] = 0;
            return true;
        } else
            return killrightup(i, j, b);

    }

    static boolean killleftdown(int i, int j, Board b) {
        if (b.arr[i][j] > 0 && check(i + 1, j - 1) && check(i + 2, j - 2) && b.arr[i + 1][j - 1] < 0
                && b.arr[i + 2][j - 2] == 0) {
            b.arr[i + 1][j - 1] = 0;
            b.arr[i + 2][j - 2] = b.arr[i][j];

            b.arr[i][j] = 0;
            b.countb = b.countb - 1;

            killleftdown(i + 2, j - 2, b);
            killrightdown(i + 2, j - 2, b);
            if (b.arr[i + 2][j - 2] == 2) {
                killleftup(i + 2, j - 2, b);
                killrightup(i + 2, j - 2, b);
            }
            return true;
        } else if (b.arr[i][j] < 0 && check(i + 1, j - 1) && check(i + 2, j - 2) && b.arr[i + 1][j - 1] > 0
                && b.arr[i + 2][j - 2] == 0) {
            b.arr[i + 1][j - 1] = 0;
            b.arr[i + 2][j - 2] = b.arr[i][j];
            if (b.arr[i + 2][j - 2] < 0 && i + 2 == 7)
                b.arr[i + 2][j - 2] = -2;
            b.arr[i][j] = 0;
            b.countw = b.countw - 1;

            killleftdown(i + 2, j - 2, b);
            killrightdown(i + 2, j - 2, b);
            if (b.arr[i + 2][j - 2] == -2) {
                killleftup(i + 2, j - 2, b);
                killrightup(i + 2, j - 2, b);
            }
            return true;
        }
        return false;
    }

    static boolean killrightdown(int i, int j, Board b) {
        if (b.arr[i][j] > 0 && check(i + 1, j + 1) && check(i + 2, j + 2) && b.arr[i + 1][j + 1] < 0
                && b.arr[i + 2][j + 2] == 0) {
            b.arr[i + 1][j + 1] = 0;
            b.arr[i + 2][j + 2] = b.arr[i][j];
            b.arr[i][j] = 0;
            b.countb--;

            killleftdown(i + 2, j + 2, b);
            killrightdown(i + 2, j + 2, b);
            if (b.arr[i + 2][j + 2] == 2) {
                killleftup(i + 2, j + 2, b);
                killrightup(i + 2, j + 2, b);
            }
            return true;
        } else if (b.arr[i][j] < 0 && check(i + 1, j + 1) && check(i + 2, j + 2) && b.arr[i + 1][j + 1] > 0
                && b.arr[i + 2][j + 2] == 0) {
            b.arr[i + 1][j + 1] = 0;
            b.arr[i + 2][j + 2] = b.arr[i][j];
            if (b.arr[i + 2][j + 2] < 0 && i + 2 == 7)
                b.arr[i + 2][j + 2] = -2;
            b.arr[i][j] = 0;
            b.countw--;

            killleftdown(i + 2, j + 2, b);
            killrightdown(i + 2, j + 2, b);
            if (b.arr[i + 2][j + 2] == -2) {
                killleftup(i + 2, j + 2, b);
                killrightup(i + 2, j + 2, b);
            }

            return true;
        }
        return false;
    }

    static boolean leftdown(int i, int j, Board b) {
        if (check(i + 1, j - 1) && b.arr[i + 1][j - 1] == 0) {
            b.arr[i + 1][j - 1] = b.arr[i][j];
            if (b.arr[i + 1][j - 1] < 0 && i + 1 == 7)
                b.arr[i + 1][j - 1] = -2;
            b.arr[i][j] = 0;
            return true;
        } else
            return killleftdown(i, j, b);

    }

    static boolean rightdown(int i, int j, Board b) {
        if (check(i + 1, j + 1) && b.arr[i + 1][j + 1] == 0) {
            b.arr[i + 1][j + 1] = b.arr[i][j];
            if (b.arr[i + 1][j + 1] < 0 && i + 1 == 7)
                b.arr[i + 1][j + 1] = -2;
            b.arr[i][j] = 0;
            return true;
        } else
            return killrightdown(i, j, b);

    }

    static boolean check(int i, int j) {
        if (i >= 0 && j >= 0 && i < 8 && j < 8)
            return true;
        return false;
    }

    static Policy maxi(Board b, int depth) {
        Policy p = new Policy(0);

        if (depth > max_depth) {
            p.arr = b.arr;
            p.val = eval(b.arr);
            return p;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b.arr[i][j] == 1) {
                    Board b1 = new Board(b);
                    Board b2 = new Board(b);
                    if (leftup(i, j, b1)) {
                        Board b11 = new Board(b1);
                        Policy p1 = mini(b1, depth + 1);
                        if (p1.val > p.val) {
                            p.arr = b11.arr;
                            p.val = p1.val;
                        }
                    }
                    if (rightup(i, j, b2)) {
                        Board b22 = new Board(b2);
                        Policy p1 = mini(b2, depth + 1);
                        if (p1.val > p.val) {
                            p.arr = b22.arr;
                            p.val = p1.val;
                        }
                    }
                }
                if (b.arr[i][j] == 2) {
                    Board b1 = new Board(b);
                    Board b2 = new Board(b);
                    Board b3 = new Board(b);
                    Board b4 = new Board(b);
                    if (leftup(i, j, b1)) {
                        Board b11 = new Board(b1);
                        Policy p1 = mini(b1, depth + 1);
                        if (p1.val > p.val) {
                            p.arr = b11.arr;
                            p.val = p1.val;
                        }
                    }
                    if (rightup(i, j, b2)) {
                        Board b22 = new Board(b2);
                        Policy p1 = mini(b2, depth + 1);
                        if (p1.val > p.val) {
                            p.arr = b22.arr;
                            p.val = p1.val;
                        }
                    }
                    if (rightdown(i, j, b3)) {
                        Board b33 = new Board(b3);
                        Policy p1 = mini(b3, depth + 1);
                        if (p1.val > p.val) {
                            p.arr = b33.arr;
                            p.val = p1.val;
                        }
                    }
                    if (leftdown(i, j, b4)) {
                        Board b44 = new Board(b4);
                        Policy p1 = mini(b4, depth + 1);
                        if (p1.val > p.val) {
                            p.arr = b44.arr;
                            p.val = p1.val;
                        }
                    }

                }
            }
        }
        if (p.val == Integer.MIN_VALUE) {
            p.arr = b.arr;
            p.val = eval(b.arr);
        }
        return p;
    }

    static Policy mini(Board b, int depth) {
        Policy p = new Policy(1);

        if (depth > max_depth) {
            p.arr = b.arr;
            p.val = eval(b.arr);
            return p;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b.arr[i][j] == -1) {
                    Board b1 = new Board(b);
                    Board b2 = new Board(b);
                    if (leftdown(i, j, b1)) {
                        Board b11 = new Board(b1);
                        Policy p1 = maxi(b1, depth + 1);
                        if (p1.val < p.val) {
                            p.arr = b11.arr;
                            p.val = p1.val;
                        }
                    }
                    if (rightdown(i, j, b2)) {
                        Board b22 = new Board(b2);
                        Policy p1 = maxi(b2, depth + 1);
                        if (p1.val < p.val) {
                            p.arr = b22.arr;
                            p.val = p1.val;
                        }
                    }
                }
                if (b.arr[i][j] == -2) {
                    Board b1 = new Board(b);
                    Board b2 = new Board(b);
                    Board b3 = new Board(b);
                    Board b4 = new Board(b);
                    if (leftup(i, j, b1)) {
                        Board b11 = new Board(b1);
                        Policy p1 = maxi(b1, depth + 1);
                        if (p1.val < p.val) {
                            p.arr = b11.arr;
                            p.val = p1.val;
                        }
                    }
                    if (rightup(i, j, b2)) {
                        Board b22 = new Board(b2);
                        Policy p1 = maxi(b2, depth + 1);
                        if (p1.val < p.val) {
                            p.arr = b22.arr;
                            p.val = p1.val;
                        }
                    }
                    if (rightdown(i, j, b3)) {
                        Board b33 = new Board(b3);
                        Policy p1 = maxi(b3, depth + 1);
                        if (p1.val < p.val) {
                            p.arr = b33.arr;
                            p.val = p1.val;
                        }
                    }
                    if (leftdown(i, j, b4)) {
                        Board b44 = new Board(b4);
                        Policy p1 = maxi(b4, depth + 1);
                        if (p1.val < p.val) {
                            p.arr = b44.arr;
                            p.val = p1.val;
                        }
                    }

                }
            }
        }
        if (p.val == Integer.MAX_VALUE) {
            p.arr = b.arr;
            p.val = eval(b.arr);
        }
        return p;
    }

    static int compliment(int a) {
        if (a == 1)
            return 0;
        return 1;
    }

    public static void main(String[] args) throws java.lang.Exception {
        CheckersGUI frame = new CheckersGUI();

        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Board b = new Board();
        Board temp = new Board();
        boolean white_turn = true;

        while (b.countb != 0 && b.countw != 0) {
            Policy p;
            if (white_turn) {
                p = maxi(temp, 0);
            } else {
                p = mini(temp, 0);
            }
            int countw = 0, countb = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (p.arr[i][j] > 0) {
                        countw++;
                    }
                    if (p.arr[i][j] < 0) {
                        countb++;
                    }
                    b.arr[i][j] = p.arr[i][j];
                    b.countw = countw;
                    b.countb = countb;
                }
            }
            
            frame.setsquare(b.arr);
                
               
            
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(b.arr[i][j]+" ");
                }
                System.out.print("\n");
            }
            System.out.print("\n\n\n black count=" + b.countb + "\n white count= " + b.countw + "\n\n\n");
            
            temp = new Board(b);

            white_turn = !white_turn;

        }
        
    }
}

class CheckersGUI extends JFrame {
    public JPanel board;
    public JButton[][] square;
    
    ImageIcon rscaledIcon,wscaledIcon,i1,i2,rkscaledIcon,wkscaledIcon;
    Image redi,sred,whitei,swhite;

    CheckersGUI() {
       
         i1= new ImageIcon("C://images/red.png");
         redi= i1.getImage();
         sred= redi.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        rscaledIcon= new ImageIcon(sred);

        i1= new ImageIcon("C://images/red-king.png");
         redi= i1.getImage();
         sred= redi.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        rkscaledIcon= new ImageIcon(sred);
        
        i2= new ImageIcon("C://images/white-king.png");
         whitei= i2.getImage();
        swhite= whitei.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        wkscaledIcon= new ImageIcon(swhite);

         i2= new ImageIcon("C://images/white.png");
         whitei= i2.getImage();
        swhite= whitei.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        wscaledIcon= new ImageIcon(swhite);
        
       
        Dimension board_dim = new Dimension(800, 800);
        square = new JButton[8][8];
        board = new JPanel();
        board.setLayout(new GridLayout(8, 8));
        board.setPreferredSize(board_dim);
        board.setBounds(0, 0, board_dim.height, board_dim.width);
        getContentPane().add(board);
        for (int i = 0; i < 64; i++) {
            square[i / 8][i % 8] = new JButton();
            board.add(square[i / 8][i % 8]);
            
            

            int row = (i / 8) % 2;
            if (row == 0)
                square[i / 8][i % 8].setBackground(i % 2 == 1 ? Color.black : Color.white);
            else
                square[i / 8][i % 8].setBackground(i % 2 == 1 ? Color.white : Color.black);
            
        }
        

    }

    void setsquare(int [][] arr ){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                square[i][j].setIcon(null);
                int row = (i) % 2;
                if (row == 0)
                    square[i][j].setBackground(j % 2 == 1 ? Color.black : Color.white);
                if(row==1)
                    square[i][j].setBackground(j % 2 == 1 ? Color.white : Color.black);
                if (arr[i][j] ==1 ) {
                    square[i][j].setIcon(rscaledIcon);
                }
                if (arr[i][j] == -1) {
                    square[i][j].setIcon(wscaledIcon);
                    
                }
                if (arr[i][j] == 2) {
                    square[i][j].setIcon(rkscaledIcon);
                    
                }
                if (arr[i][j] == -2) {
                    square[i][j].setIcon(wkscaledIcon);
                    
                }
                
                
            }
           
        }
    }
}
