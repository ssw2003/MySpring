using System;
using System.IO;
using System.Net.Sockets;
using System.Threading;

namespace ReversiClient
{
    class RandomGuy
    {
        public TcpClient s;
        StreamReader socketReader;
        StreamWriter socketWriter;

        double t1, t2;
        int me;
        int[][] state = new int[8][]; // state[0][0] is the bottom left corner of the board (on the GUI)
        int turn = -1;
        int round;

        int numValidMoves;


        // main function that (1) establishes a connection with the server, and then plays whenever it is this player's turn
        public RandomGuy(int _me, String host)
        {
            me = _me;
            initClient(host);

            for (int i = 0; i < state.Length; ++i)
            {
                state[i] = new int[8];
                for (int j = 0; j < state[i].Length; ++j)
                {
                    state[i][j] = 0;
                }
            }

            int myMove;

            while (true)
            {
                System.Console.Out.WriteLine("Read");
                readMessage();

                if (turn == me)
                {
                    System.Console.Out.WriteLine("Move");
                    int[] validMoves = getValidMoves(round, state);

                    myMove = move(round, validMoves);

                    String sel = validMoves[myMove] / 8 + "\n" + validMoves[myMove] % 8;

                    System.Console.Out.WriteLine("Selection: " + validMoves[myMove] / 8 + ", " + validMoves[myMove] % 8);

                    socketWriter.WriteLine(sel);
                }
            }
        }

        // You should modify this function
        // validMoves is a list of valid locations that you could place your "stone" on this turn
        // Note that "state" is a global variable 2D list that shows the state of the game
        private int move(int round, int[] validMoves)
        {
            int myMove = 0; //Just does whatever the first valid move is

            return myMove;
        }

        private int[][] copyStateArray(int[][] old)
        {
            int[][] copy = new int[8][];
            for (int i = 0; i < copy.Length; ++i)
            {
                copy[i] = new int[8];
                for (int j = 0; j < copy[i].Length; ++j)
                {
                    copy[i][j] = old[i][j];
                }
            }
            return copy;
        }

        // generates the set of valid moves for the player; returns a list of valid moves (validMoves)
        private int[] getValidMoves(int round, int[][] state)
        {
            int i, j;
            int[] validMoves = new int[64];
            int numValidMoves = 0;
            if (round < 4)
            {
                if (state[3][3] == 0)
                {
                    validMoves[numValidMoves] = 3 * 8 + 3;
                    numValidMoves++;
                }
                if (state[3][4] == 0)
                {
                    validMoves[numValidMoves] = 3 * 8 + 4;
                    numValidMoves++;
                }
                if (state[4][3] == 0)
                {
                    validMoves[numValidMoves] = 4 * 8 + 3;
                    numValidMoves++;
                }
                if (state[4][4] == 0)
                {
                    validMoves[numValidMoves] = 4 * 8 + 4;
                    numValidMoves++;
                }
                System.Console.Out.WriteLine("Valid Moves:");
                for (i = 0; i < numValidMoves; i++)
                {
                    System.Console.Out.WriteLine(validMoves[i] / 8 + ", " + validMoves[i] % 8);
                }
            }
            else
            {
                System.Console.Out.WriteLine("Valid Moves:");
                for (i = 0; i < 8; i++)
                {
                    for (j = 0; j < 8; j++)
                    {
                        if (state[i][j] == 0)
                        {
                            if (couldBe(state, i, j))
                            {
                                validMoves[numValidMoves] = i * 8 + j;
                                numValidMoves++;
                                System.Console.Out.WriteLine(i + ", " + j);
                            }
                        }
                    }
                }
            }
            return validMoves;
        }

        private bool checkDirection(int[][] state, int row, int col, int incx, int incy)
        {
            int[] sequence = new int[7];
            int seqLen;
            int i, r, c;

            seqLen = 0;
            for (i = 1; i < 8; i++)
            {
                r = row + incy * i;
                c = col + incx * i;

                if ((r < 0) || (r > 7) || (c < 0) || (c > 7))
                    break;

                sequence[seqLen] = state[r][c];
                seqLen++;
            }

            int count = 0;
            for (i = 0; i < seqLen; i++)
            {
                if (me == 1)
                {
                    if (sequence[i] == 2)
                        count++;
                    else
                    {
                        if ((sequence[i] == 1) && (count > 0))
                            return true;
                        break;
                    }
                }
                else
                {
                    if (sequence[i] == 1)
                        count++;
                    else
                    {
                        if ((sequence[i] == 2) && (count > 0))
                            return true;
                        break;
                    }
                }
            }

            return false;
        }

        private bool couldBe(int[][] state, int row, int col)
        {
            int incx, incy;

            for (incx = -1; incx < 2; incx++)
            {
                for (incy = -1; incy < 2; incy++)
                {
                    if ((incx == 0) && (incy == 0))
                        continue;

                    if (checkDirection(state, row, col, incx, incy))
                        return true;
                }
            }

            return false;
        }

        public void readMessage()
        {
            try
            {
                byte[] buffer = new byte[1024];

                //System.out.println("Ready to read again");
                turn = Int32.Parse(socketReader.ReadLine());

                if (turn == -999)
                {
                    try
                    {
                        Thread.Sleep(200);
                    }
                    catch (ThreadInterruptedException e)
                    {
                        System.Console.Out.WriteLine(e);
                    }

                    Environment.Exit(1);
                }

                //System.out.println("Turn: " + turn);
                round = Int32.Parse(socketReader.ReadLine());
                t1 = Double.Parse(socketReader.ReadLine());
                System.Console.Out.WriteLine(t1);
                t2 = Double.Parse(socketReader.ReadLine());
                System.Console.Out.WriteLine(t2);
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        state[i][j] = Int32.Parse(socketReader.ReadLine());
                    }
                }
                socketReader.ReadLine();
            }
            catch (IOException e)
            {
                System.Console.Error.WriteLine("Caught IOException: " + e.Message);
            }

            System.Console.Error.WriteLine("Turn: " + turn);
            System.Console.Error.WriteLine("Round: " + round);
            for (int i = 7; i >= 0; i--)
            {
                for (int j = 0; j < 8; j++)
                {
                    System.Console.Error.Write(state[i][j] + " ");
                }
                System.Console.Error.WriteLine();
            }
            System.Console.Error.WriteLine();
        }

        public void initClient(string host)
        {
            int portNumber = 3333 + me;
            try
            {
                s = new TcpClient(AddressFamily.InterNetwork);
                s.Connect(host, portNumber);

                socketReader = new StreamReader(s.GetStream());
                socketWriter = new StreamWriter(s.GetStream());
                socketWriter.AutoFlush = true;
                String info = socketReader.ReadLine();
                System.Console.Out.WriteLine(info);
            }
            catch (System.IO.IOException e)
            {
                System.Console.Error.WriteLine("Caught IOException: " + e.Message);
            }
        }


		//Requires command line args [ipaddress] and [player_number]
        //   ipaddress is the ipaddress on the computer the server was launched on.  Enter "localhost" if it is on the same computer
        //   player_number is 1 (for the black player) and 2 (for the white player)
        public static void Main(String[] args)
        {
            new RandomGuy(Int32.Parse(args[1]), args[0]);
        }

    }
}
