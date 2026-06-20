import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.math.*;
import java.text.*;

class RandomGuy {

    public Socket s;
	public BufferedReader sin;
	public PrintWriter sout;
    Random generator = new Random();

    double t1, t2;
    int me;
    int boardState;
    int state[][] = new int[8][8]; // state[0][0] is the bottom left corner of the board (on the GUI)
    int turn = -1;
    int round;
    
    int validMoves[] = new int[64];
    int numValidMoves;
    
    
    // main function that (1) establishes a connection with the server, and then plays whenever it is this player's turn
    public RandomGuy(int _me, String host) {
        me = _me;
        initClient(host);

        int myMove;
        
        while (true) {
            System.out.println("Read");
            readMessage();
            
            if (turn == me) {
                System.out.println("Move");
                getValidMoves(round, state);
                
                myMove = move();
                //myMove = generator.nextInt(numValidMoves);        // select a move randomly
                
                String sel = validMoves[myMove] / 8 + "\n" + validMoves[myMove] % 8;
                
                System.out.println("Selection: " + validMoves[myMove] / 8 + ", " + validMoves[myMove] % 8);
                
                sout.println(sel);
            }
        }
        //while (turn == me) {
        //    System.out.println("My turn");
            
            //readMessage();
        //}
    }
    
    // You should modify this function
    // validMoves is a list of valid locations that you could place your "stone" on this turn
    // Note that "state" is a global variable 2D list that shows the state of the game
    private int move() {
        // just move randomly for now
        int myMove = generator.nextInt(numValidMoves);
//[I'M STARTING HERE FIRST TIME]
if (numValidMoves > 0 && round >= 4) {
int i = numValidMoves;
int j = 64;
int myState[][] = new int[8][8];
int thisThis[] = new int[64];
while (j > 0) {
j--;
myState[j % 8][j / 8] = state[j % 8][j / 8];
}
int alpha = -1000000;
int beta = 1000000;
while (i > 0) {
i--;
j = validMoves[i];
int thisState0[][] = new int[8][8];
int thisState1[][] = new int[8][8];
int thisState2[][] = new int[8][8];
int thisState3[][] = new int[8][8];
int thisState4[][] = new int[8][8];
int u = 64;
while (u > 0) {
u--;
thisState0[u % 8][u / 8] = myState[u % 8][u / 8];
}
thisState0[j / 8][j % 8] = me;
if (getValue(thisState0) > alpha) { alpha = getValue(thisState0); }
if (alpha > beta) {
thisThis[i] = getValue(thisState0);
continue;
}
int u0 = 64;
int thatThing0 = 1000000;
while (u0 > 0) {
 u0--;
 if (myCouldBe(thisState0, u0 / 8, u0 % 8, 3 - me) == false) { continue; }
 thisState0[u0 / 8][u0 % 8] = 3 - me;
 int u1 = 64;
 int thatThing1 = -1000000;
 while (u1 > 0) {
  u1--;
  if (true) { int k = 64; while (k > 0) { k--; thisState1[k / 8][k % 8] = thisState0[k / 8][k % 8]; } }
  if (myCouldBe(thisState1, u1 / 8, u1 % 8, me) == false) { continue; }
  thisState1[u1 / 8][u1 % 8] = me;
  int u2 = 64;
  int thatThing2 = 1000000;
  while (u2 > 0) {
   u2--;
   if (true) { int k = 64; while (k > 0) { k--; thisState2[k / 8][k % 8] = thisState1[k / 8][k % 8]; } }
   if (myCouldBe(thisState2, u2 / 8, u2 % 8, 3 - me) == false) { continue; }
   thisState2[u2 / 8][u2 % 8] = 3 - me;
   int u3 = 64;
   int thatThing3 = -1000000;
   while (u3 > 0) {
    u3--;
    if (true) { int k = 64; while (k > 0) { k--; thisState3[k / 8][k % 8] = thisState2[k / 8][k % 8]; } }
    if (myCouldBe(thisState3, u3 / 8, u3 % 8, me) == false) { continue; }
    thisState3[u3 / 8][u3 % 8] = me;
    int u4 = 64;
    int thatThing4 = 1000000;
    while (u4 > 0) {
     u4--;
     if (true) { int k = 64; while (k > 0) { k--; thisState4[k / 8][k % 8] = thisState3[k / 8][k % 8]; } }
     if (myCouldBe(thisState4, u4 / 8, u4 % 8, 3 - me) == false) { continue; }
     thisState4[u4 / 8][u4 % 8] = 3 - me;
     beta = getMin(beta, getValue(thisState4));
     thatThing4 = getMin(thatThing4, getValue(thisState4));
     if (alpha > beta) { break; }
    }
    alpha = getMax(thatThing3, alpha);
    thatThing3 = getMax(thatThing3, thatThing4);
    thisState3[u3 / 8][u3 % 8] = 0;
    if (alpha > beta) { break; }
   }
   beta = getMax(thatThing2, beta);
   thatThing2 = getMin(thatThing2, thatThing3);
   thisState2[u2 / 8][u2 % 8] = 0;
   if (alpha > beta) { break; }
  }
  alpha = getMax(thatThing1, alpha);
  thatThing1 = getMax(thatThing1, thatThing2);
  thisState1[u1 / 8][u1 % 8] = 0;
  if (alpha > beta) { break; }
 }
 beta = getMax(thatThing0, beta);
 thatThing0 = getMin(thatThing0, thatThing1);
 thisState0[u0 / 8][u0 % 8] = 0;
 if (alpha > beta) { break; }
}
thisThis[i] = thatThing0;
}
i = numValidMoves;
int sg = 0;
while (i > 0) {
i--; if (thisThis[i] > sg) { sg = thisThis[i]; myMove = i; }
}
}
//[I'M ENDING HERE FIRST TIME]
        
        return myMove;
    }
//[I'M STARTING HERE SECOND TIME]
private int getMin(int a, int b) {
if (a > b) { return b; } return a;
}
private int getMax(int a, int b) {
if (a > b) { return a; } return b;
}
private int getValue(int thisState[][]) {
int corner_thing = 0;
int sdp = 0;
int mx_0 = 1;
int mx_1 = 0;
int my_0 = 0;
int my_1 = 1;
int b_0 = 0;
int b_1 = 0;
int s_state[][] = new int[8][8];
int t_state[][] = new int[8][8];
if (sdp == 1) { int bt = mx_1; mx_1 = mx_0; mx_0 = bt; bt = my_1; my_1 = my_0; my_0 = bt; }
while (corner_thing > 0) {
corner_thing--;
int bt = mx_0;
mx_0 = mx_1;
mx_1 = -1 * bt;
bt = my_0;
my_0 = my_1;
my_1 = -1 * bt;
} int i = 0;
int j = 0;
while (i < 8) { j = 0; while (j < 8) { s_state[i][j] = thisState[i][j]; t_state[i][j] = thisState[i][j];
if (s_state[i][j] == me) {
t_state[i][j] = 1;
}
if (s_state[i][j] == 3 - me) {
t_state[i][j] = -1;
}
j++; }
i++;
} if (mx_0 + my_0 == -1) { b_0 = 7; }
if (mx_1 + my_1 == -1) { b_1 = 7; }
int x = 0;
int y = 0;
int z = 0;
while (x < 8) {
x++;
y = 0;
while (y < 8) { y++;
z = 0;
int k = 0;
i = 0;
while (i < x) { j = 0;
while (j < y) { k = 0;
while (j + k < 8 && i - k >= 0) {
if (s_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] != s_state[b_0][b_1]) { z = 1; }
k++; }
j++; }
i++;
}
if (z == 0) {
i = 0;
while (i < x) { j = 0;
while (j < y) {
k = 0;
while (j + k < 8 && i - k >= 0) {
t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] = t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] * 3;
k++;
}
j++; }
i++; }
}
//
}
}
corner_thing = 0;
sdp = 1;
int mx_0 = 1;
int mx_1 = 0;
int my_0 = 0;
int my_1 = 1;
int b_0 = 0;
int b_1 = 0;
int s_state[][] = new int[8][8];
int t_state[][] = new int[8][8];
if (sdp == 1) { int bt = mx_1; mx_1 = mx_0; mx_0 = bt; bt = my_1; my_1 = my_0; my_0 = bt; }
while (corner_thing > 0) {
corner_thing--;
int bt = mx_0;
mx_0 = mx_1;
mx_1 = -1 * bt;
bt = my_0;
my_0 = my_1;
my_1 = -1 * bt;
} int i = 0;
int j = 0;
while (i < 8) { j = 0; while (j < 8) { s_state[i][j] = thisState[i][j]; t_state[i][j] = thisState[i][j];
if (s_state[i][j] == me) {
t_state[i][j] = 1;
}
if (s_state[i][j] == 3 - me) {
t_state[i][j] = -1;
}
j++; }
i++;
} if (mx_0 + my_0 == -1) { b_0 = 7; }
if (mx_1 + my_1 == -1) { b_1 = 7; }
int x = 0;
int y = 0;
int z = 0;
while (x < 8) {
x++;
y = 0;
while (y < 8) { y++;
z = 0;
int k = 0;
i = 0;
while (i < x) { j = 0;
while (j < y) { k = 0;
while (j + k < 8 && i - k >= 0) {
if (s_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] != s_state[b_0][b_1]) { z = 1; }
k++; }
j++; }
i++;
}
if (z == 0) {
i = 0;
while (i < x) { j = 0;
while (j < y) {
k = 0;
while (j + k < 8 && i - k >= 0) {
t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] = t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] * 3;
k++;
}
j++; }
i++; }
}
//
}
}
corner_thing = 1;
sdp = 0;
int mx_0 = 1;
int mx_1 = 0;
int my_0 = 0;
int my_1 = 1;
int b_0 = 0;
int b_1 = 0;
int s_state[][] = new int[8][8];
int t_state[][] = new int[8][8];
if (sdp == 1) { int bt = mx_1; mx_1 = mx_0; mx_0 = bt; bt = my_1; my_1 = my_0; my_0 = bt; }
while (corner_thing > 0) {
corner_thing--;
int bt = mx_0;
mx_0 = mx_1;
mx_1 = -1 * bt;
bt = my_0;
my_0 = my_1;
my_1 = -1 * bt;
} int i = 0;
int j = 0;
while (i < 8) { j = 0; while (j < 8) { s_state[i][j] = thisState[i][j]; t_state[i][j] = thisState[i][j];
if (s_state[i][j] == me) {
t_state[i][j] = 1;
}
if (s_state[i][j] == 3 - me) {
t_state[i][j] = -1;
}
j++; }
i++;
} if (mx_0 + my_0 == -1) { b_0 = 7; }
if (mx_1 + my_1 == -1) { b_1 = 7; }
int x = 0;
int y = 0;
int z = 0;
while (x < 8) {
x++;
y = 0;
while (y < 8) { y++;
z = 0;
int k = 0;
i = 0;
while (i < x) { j = 0;
while (j < y) { k = 0;
while (j + k < 8 && i - k >= 0) {
if (s_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] != s_state[b_0][b_1]) { z = 1; }
k++; }
j++; }
i++;
}
if (z == 0) {
i = 0;
while (i < x) { j = 0;
while (j < y) {
k = 0;
while (j + k < 8 && i - k >= 0) {
t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] = t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] * 3;
k++;
}
j++; }
i++; }
}
//
}
}
corner_thing = 1;
sdp = 1;
int mx_0 = 1;
int mx_1 = 0;
int my_0 = 0;
int my_1 = 1;
int b_0 = 0;
int b_1 = 0;
int s_state[][] = new int[8][8];
int t_state[][] = new int[8][8];
if (sdp == 1) { int bt = mx_1; mx_1 = mx_0; mx_0 = bt; bt = my_1; my_1 = my_0; my_0 = bt; }
while (corner_thing > 0) {
corner_thing--;
int bt = mx_0;
mx_0 = mx_1;
mx_1 = -1 * bt;
bt = my_0;
my_0 = my_1;
my_1 = -1 * bt;
} int i = 0;
int j = 0;
while (i < 8) { j = 0; while (j < 8) { s_state[i][j] = thisState[i][j]; t_state[i][j] = thisState[i][j];
if (s_state[i][j] == me) {
t_state[i][j] = 1;
}
if (s_state[i][j] == 3 - me) {
t_state[i][j] = -1;
}
j++; }
i++;
} if (mx_0 + my_0 == -1) { b_0 = 7; }
if (mx_1 + my_1 == -1) { b_1 = 7; }
int x = 0;
int y = 0;
int z = 0;
while (x < 8) {
x++;
y = 0;
while (y < 8) { y++;
z = 0;
int k = 0;
i = 0;
while (i < x) { j = 0;
while (j < y) { k = 0;
while (j + k < 8 && i - k >= 0) {
if (s_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] != s_state[b_0][b_1]) { z = 1; }
k++; }
j++; }
i++;
}
if (z == 0) {
i = 0;
while (i < x) { j = 0;
while (j < y) {
k = 0;
while (j + k < 8 && i - k >= 0) {
t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] = t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] * 3;
k++;
}
j++; }
i++; }
}
//
}
}
corner_thing = 2;
sdp = 0;
int mx_0 = 1;
int mx_1 = 0;
int my_0 = 0;
int my_1 = 1;
int b_0 = 0;
int b_1 = 0;
int s_state[][] = new int[8][8];
int t_state[][] = new int[8][8];
if (sdp == 1) { int bt = mx_1; mx_1 = mx_0; mx_0 = bt; bt = my_1; my_1 = my_0; my_0 = bt; }
while (corner_thing > 0) {
corner_thing--;
int bt = mx_0;
mx_0 = mx_1;
mx_1 = -1 * bt;
bt = my_0;
my_0 = my_1;
my_1 = -1 * bt;
} int i = 0;
int j = 0;
while (i < 8) { j = 0; while (j < 8) { s_state[i][j] = thisState[i][j]; t_state[i][j] = thisState[i][j];
if (s_state[i][j] == me) {
t_state[i][j] = 1;
}
if (s_state[i][j] == 3 - me) {
t_state[i][j] = -1;
}
j++; }
i++;
} if (mx_0 + my_0 == -1) { b_0 = 7; }
if (mx_1 + my_1 == -1) { b_1 = 7; }
int x = 0;
int y = 0;
int z = 0;
while (x < 8) {
x++;
y = 0;
while (y < 8) { y++;
z = 0;
int k = 0;
i = 0;
while (i < x) { j = 0;
while (j < y) { k = 0;
while (j + k < 8 && i - k >= 0) {
if (s_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] != s_state[b_0][b_1]) { z = 1; }
k++; }
j++; }
i++;
}
if (z == 0) {
i = 0;
while (i < x) { j = 0;
while (j < y) {
k = 0;
while (j + k < 8 && i - k >= 0) {
t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] = t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] * 3;
k++;
}
j++; }
i++; }
}
//
}
}
corner_thing = 2;
sdp = 1;
int mx_0 = 1;
int mx_1 = 0;
int my_0 = 0;
int my_1 = 1;
int b_0 = 0;
int b_1 = 0;
int s_state[][] = new int[8][8];
int t_state[][] = new int[8][8];
if (sdp == 1) { int bt = mx_1; mx_1 = mx_0; mx_0 = bt; bt = my_1; my_1 = my_0; my_0 = bt; }
while (corner_thing > 0) {
corner_thing--;
int bt = mx_0;
mx_0 = mx_1;
mx_1 = -1 * bt;
bt = my_0;
my_0 = my_1;
my_1 = -1 * bt;
} int i = 0;
int j = 0;
while (i < 8) { j = 0; while (j < 8) { s_state[i][j] = thisState[i][j]; t_state[i][j] = thisState[i][j];
if (s_state[i][j] == me) {
t_state[i][j] = 1;
}
if (s_state[i][j] == 3 - me) {
t_state[i][j] = -1;
}
j++; }
i++;
} if (mx_0 + my_0 == -1) { b_0 = 7; }
if (mx_1 + my_1 == -1) { b_1 = 7; }
int x = 0;
int y = 0;
int z = 0;
while (x < 8) {
x++;
y = 0;
while (y < 8) { y++;
z = 0;
int k = 0;
i = 0;
while (i < x) { j = 0;
while (j < y) { k = 0;
while (j + k < 8 && i - k >= 0) {
if (s_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] != s_state[b_0][b_1]) { z = 1; }
k++; }
j++; }
i++;
}
if (z == 0) {
i = 0;
while (i < x) { j = 0;
while (j < y) {
k = 0;
while (j + k < 8 && i - k >= 0) {
t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] = t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] * 3;
k++;
}
j++; }
i++; }
}
//
}
}
corner_thing = 3;
sdp = 0;
int mx_0 = 1;
int mx_1 = 0;
int my_0 = 0;
int my_1 = 1;
int b_0 = 0;
int b_1 = 0;
int s_state[][] = new int[8][8];
int t_state[][] = new int[8][8];
if (sdp == 1) { int bt = mx_1; mx_1 = mx_0; mx_0 = bt; bt = my_1; my_1 = my_0; my_0 = bt; }
while (corner_thing > 0) {
corner_thing--;
int bt = mx_0;
mx_0 = mx_1;
mx_1 = -1 * bt;
bt = my_0;
my_0 = my_1;
my_1 = -1 * bt;
} int i = 0;
int j = 0;
while (i < 8) { j = 0; while (j < 8) { s_state[i][j] = thisState[i][j]; t_state[i][j] = thisState[i][j];
if (s_state[i][j] == me) {
t_state[i][j] = 1;
}
if (s_state[i][j] == 3 - me) {
t_state[i][j] = -1;
}
j++; }
i++;
} if (mx_0 + my_0 == -1) { b_0 = 7; }
if (mx_1 + my_1 == -1) { b_1 = 7; }
int x = 0;
int y = 0;
int z = 0;
while (x < 8) {
x++;
y = 0;
while (y < 8) { y++;
z = 0;
int k = 0;
i = 0;
while (i < x) { j = 0;
while (j < y) { k = 0;
while (j + k < 8 && i - k >= 0) {
if (s_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] != s_state[b_0][b_1]) { z = 1; }
k++; }
j++; }
i++;
}
if (z == 0) {
i = 0;
while (i < x) { j = 0;
while (j < y) {
k = 0;
while (j + k < 8 && i - k >= 0) {
t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] = t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] * 3;
k++;
}
j++; }
i++; }
}
//
}
}
corner_thing = 3;
sdp = 1;
int mx_0 = 1;
int mx_1 = 0;
int my_0 = 0;
int my_1 = 1;
int b_0 = 0;
int b_1 = 0;
int s_state[][] = new int[8][8];
int t_state[][] = new int[8][8];
if (sdp == 1) { int bt = mx_1; mx_1 = mx_0; mx_0 = bt; bt = my_1; my_1 = my_0; my_0 = bt; }
while (corner_thing > 0) {
corner_thing--;
int bt = mx_0;
mx_0 = mx_1;
mx_1 = -1 * bt;
bt = my_0;
my_0 = my_1;
my_1 = -1 * bt;
} int i = 0;
int j = 0;
while (i < 8) { j = 0; while (j < 8) { s_state[i][j] = thisState[i][j]; t_state[i][j] = thisState[i][j];
if (s_state[i][j] == me) {
t_state[i][j] = 1;
}
if (s_state[i][j] == 3 - me) {
t_state[i][j] = -1;
}
j++; }
i++;
} if (mx_0 + my_0 == -1) { b_0 = 7; }
if (mx_1 + my_1 == -1) { b_1 = 7; }
int x = 0;
int y = 0;
int z = 0;
while (x < 8) {
x++;
y = 0;
while (y < 8) { y++;
z = 0;
int k = 0;
i = 0;
while (i < x) { j = 0;
while (j < y) { k = 0;
while (j + k < 8 && i - k >= 0) {
if (s_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] != s_state[b_0][b_1]) { z = 1; }
k++; }
j++; }
i++;
}
if (z == 0) {
i = 0;
while (i < x) { j = 0;
while (j < y) {
k = 0;
while (j + k < 8 && i - k >= 0) {
t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] = t_state[mx_0 * (j + k) + my_0 * (i - k) + b_0][mx_1 * (j + k) + my_1 * (i - k) + b_1] * 3;
k++;
}
j++; }
i++; }
}
//
}
}
int gi = 64;
int gj = 0;
while (gi > 0) {
gi--;
gj = gj + t_state[gi % 8][gi / 8]; }
return gj;
}
    private boolean myCheckDirection(int thatState[][], int row, int col, int incx, int incy, int mePerson) {
        int sequence[] = new int[7];
        int seqLen;
        int i, r, c;
        
        seqLen = 0;
        for (i = 1; i < 8; i++) {
            r = row+incy*i;
            c = col+incx*i;
        
            if ((r < 0) || (r > 7) || (c < 0) || (c > 7))
                break;
        
            sequence[seqLen] = thatState[r][c];
            seqLen++;
        }
        
        int count = 0;
        for (i = 0; i < seqLen; i++) {
            if (mePerson == 1) {
                if (sequence[i] == 2)
                    count ++;
                else {
                    if ((sequence[i] == 1) && (count > 0))
                        return true;
                    break;
                }
            }
            else {
                if (sequence[i] == 1)
                    count ++;
                else {
                    if ((sequence[i] == 2) && (count > 0))
                        return true;
                    break;
                }
            }
        }
        
        return false;
    }
    
    private boolean myCouldBe(int thatState[][], int row, int col, int mePerson) {
        int incx, incy;
        
        for (incx = -1; incx < 2; incx++) {
            for (incy = -1; incy < 2; incy++) {
                if ((incx == 0) && (incy == 0))
                    continue;
            
                if (myCheckDirection(thatState, row, col, incx, incy, mePerson))
                    return true;
            }
        }
        
        return false;
    }
//[I'M ENDING HERE SECOND TIME]
    
    // generates the set of valid moves for the player; returns a list of valid moves (validMoves)
    private void getValidMoves(int round, int state[][]) {
        int i, j;
        
        numValidMoves = 0;
        if (round < 4) {
            if (state[3][3] == 0) {
                validMoves[numValidMoves] = 3*8 + 3;
                numValidMoves ++;
            }
            if (state[3][4] == 0) {
                validMoves[numValidMoves] = 3*8 + 4;
                numValidMoves ++;
            }
            if (state[4][3] == 0) {
                validMoves[numValidMoves] = 4*8 + 3;
                numValidMoves ++;
            }
            if (state[4][4] == 0) {
                validMoves[numValidMoves] = 4*8 + 4;
                numValidMoves ++;
            }
            System.out.println("Valid Moves:");
            for (i = 0; i < numValidMoves; i++) {
                System.out.println(validMoves[i] / 8 + ", " + validMoves[i] % 8);
            }
        }
        else {
            System.out.println("Valid Moves:");
            for (i = 0; i < 8; i++) {
                for (j = 0; j < 8; j++) {
                    if (state[i][j] == 0) {
                        if (couldBe(state, i, j)) {
                            validMoves[numValidMoves] = i*8 + j;
                            numValidMoves ++;
                            System.out.println(i + ", " + j);
                        }
                    }
                }
            }
        }
        
        
        //if (round > 3) {
        //    System.out.println("checking out");
        //    System.exit(1);
        //}
    }
    
    private boolean checkDirection(int state[][], int row, int col, int incx, int incy) {
        int sequence[] = new int[7];
        int seqLen;
        int i, r, c;
        
        seqLen = 0;
        for (i = 1; i < 8; i++) {
            r = row+incy*i;
            c = col+incx*i;
        
            if ((r < 0) || (r > 7) || (c < 0) || (c > 7))
                break;
        
            sequence[seqLen] = state[r][c];
            seqLen++;
        }
        
        int count = 0;
        for (i = 0; i < seqLen; i++) {
            if (me == 1) {
                if (sequence[i] == 2)
                    count ++;
                else {
                    if ((sequence[i] == 1) && (count > 0))
                        return true;
                    break;
                }
            }
            else {
                if (sequence[i] == 1)
                    count ++;
                else {
                    if ((sequence[i] == 2) && (count > 0))
                        return true;
                    break;
                }
            }
        }
        
        return false;
    }
    
    private boolean couldBe(int state[][], int row, int col) {
        int incx, incy;
        
        for (incx = -1; incx < 2; incx++) {
            for (incy = -1; incy < 2; incy++) {
                if ((incx == 0) && (incy == 0))
                    continue;
            
                if (checkDirection(state, row, col, incx, incy))
                    return true;
            }
        }
        
        return false;
    }
    
    public void readMessage() {
        int i, j;
        String status;
        try {
            //System.out.println("Ready to read again");
            turn = Integer.parseInt(sin.readLine());
            
            if (turn == -999) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                
                System.exit(1);
            }
            
            //System.out.println("Turn: " + turn);
            round = Integer.parseInt(sin.readLine());
            t1 = Double.parseDouble(sin.readLine());
            System.out.println(t1);
            t2 = Double.parseDouble(sin.readLine());
            System.out.println(t2);
            for (i = 0; i < 8; i++) {
                for (j = 0; j < 8; j++) {
                    state[i][j] = Integer.parseInt(sin.readLine());
                }
            }
            sin.readLine();
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        
        System.out.println("Turn: " + turn);
        System.out.println("Round: " + round);
        for (i = 7; i >= 0; i--) {
            for (j = 0; j < 8; j++) {
                System.out.print(state[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void initClient(String host) {
        int portNumber = 3333+me;
        
        try {
			s = new Socket(host, portNumber);
            sout = new PrintWriter(s.getOutputStream(), true);
			sin = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            String info = sin.readLine();
            System.out.println(info);
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

    
    // compile on your machine: javac *.java
    // call: java RandomGuy [ipaddress] [player_number]
    //   ipaddress is the ipaddress on the computer the server was launched on.  Enter "localhost" if it is on the same computer
    //   player_number is 1 (for the black player) and 2 (for the white player)
    public static void main(String args[]) {
        new RandomGuy(Integer.parseInt(args[1]), args[0]);
    }
    
}
