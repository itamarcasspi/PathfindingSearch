

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class Ex1 {
    public static char[][] board;
    public static int board_size;
    public static boolean clockwise; // if true node creation order is clockwise.
    public static boolean new_first;//true for newfirst order, in ASTAR and DFBNB
    public static boolean timer; // if true show timestamp in output file.
    public static boolean print_open;

    public static int start_x;
    public static int start_y;
    public static int finish_x;
    public static int finish_y;
    public static final char goal_mark = 'G';

    public static FileWriter output;




    public static void parse_coordinates(String[] str)
    {
        int i = 0;
        for (int j = 0; j<4; j++)
        {
            for (;i<str.length;i++)
            {
                if(str[i].length()!=0) break;
            }
            switch (j)
            {
                //-1 covnertion for array indexing
                case 0:
                    start_y = Integer.parseInt(str[i++])-1;
                    break;
                case 1:
                    start_x = Integer.parseInt(str[i++])-1;
                    break;
                case 2:
                    finish_y = Integer.parseInt(str[i++])-1;
                    break;
                case 3:
                    finish_x = Integer.parseInt(str[i++])-1;
                    break;
            }
        }
    }


    public static void main(String[] args) throws IOException {
        //load the input file
        String filename = "input.txt";
        File file_read = new File(filename);
        Scanner file_scanner = new Scanner(file_read);

        //Parse the program's instruction
        String algo_name = file_scanner.nextLine();

        String clock_and_order_line = file_scanner.nextLine();
        String[] clock_and_order = clock_and_order_line.split(" ");


        clockwise = clock_and_order[0].equals("clockwise");
        new_first = false;

        if(clock_and_order.length > 1)
        {

            new_first = clock_and_order[1].equals("new-order");
        }

        String show_time = file_scanner.nextLine();
        timer = show_time.equals(("with time"));

        String open_or_not = file_scanner.nextLine();
        print_open = open_or_not.equals("with open");

        board_size = Integer.parseInt(file_scanner.nextLine());
        board = new char[board_size][board_size];

        String coordinates = file_scanner.nextLine();
        String[] split = coordinates.split("[^0-9]");
        parse_coordinates(split);

        //Parse the board's values
        for (int i = 0; i < board_size; i++)
        {
            //parse first char array
            String curr_line = file_scanner.nextLine();
            for (int j = 0; j < board_size; j++)
            {
                board[i][j] = curr_line.charAt(j);
            }

        }
        int[] st_fn_coord = new int[4];
        int j = 0;
        for (int i = 0; i< split.length && j<4;i++)
        {
            if(split[i].length()>0)
            {
                st_fn_coord[j++] = Integer.valueOf(split[i])-1;
//                System.out.println(st_fn_coord[j]);
            }

        }
        Node start = new Node(null,start_x,start_y, Node.Operation.none);
        output = new FileWriter("output.txt");

        switch (algo_name)
        {
            case "BFS":
                BFS.findPath(start,goal_mark,clockwise,timer);
//                ASTAR.findPath(start,goal_mark,clockwise,timer,print_open,finish_x,finish_y);
//                DFID.findPath(start,goal_mark,clockwise,timer);
//                IDASTAR.findPath(start,goal_mark,print_open);
//                DFBnB.findPath(start,goal_mark);

                break;
            case "DFID":
                DFID.findPath(start,goal_mark,clockwise,timer);
                break;
            case "A*":
                ASTAR.findPath(start,goal_mark,clockwise,timer,print_open,finish_x,finish_y);
                break;
            case "IDA*":
                IDASTAR.findPath(start,goal_mark,print_open);
                break;
            case "DFBnB":
                DFBnB.findPath(start,goal_mark);
                break;





        }

        output.close();
    }
}