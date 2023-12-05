import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.awt.BorderLayout.*;

public class Node implements Comparable<Node>{
    public static int global_index=1;
    private final Node father;
    private int curr_cost;
//    private final int index;
    private final Operation last_op;
    public final int pos_x;
    public final int pos_y;

    private boolean out;

    private int creation_time;


    public static final Operation[] CLOCKWISE = {Operation.EAST, Operation.SOUTHEAST,Operation.SOUTH,
            Operation.SOUTHWEST, Operation.WEST, Operation.NORTHWEST,Operation.NORTH,Operation.NORTHEAST
    };

    public static final Operation[] COUNTER_CLOCKWISE = {Operation.EAST,Operation.NORTHEAST,Operation.NORTH,
    Operation.NORTHWEST,Operation.WEST,Operation.SOUTHWEST,Operation.SOUTH,Operation.SOUTHEAST
    };

    @Override
    public int compareTo(Node o) {
        //manhattan heuristic
//        int this_h_cost = Algorithm.manhattan_cost(this,Ex1.finish_x,Ex1.finish_y);
//        int o_h_cost = Algorithm.manhattan_cost(o,Ex1.finish_x,Ex1.finish_y);
//        return Integer.compare(this_h_cost+this.curr_cost,o_h_cost+o.curr_cost);

//        euclidean heuristic
//        double this_h_cost = Algorithm.euclidean_cost(this,Ex1.finish_x,Ex1.finish_y);
//        double o_h_cost = Algorithm.euclidean_cost(o,Ex1.finish_x,Ex1.finish_y);
//        return Double.compare(this_h_cost+this.curr_cost,o_h_cost+o.curr_cost);

        //diagonal heuristic

        int this_h_cost = Algorithm.diagonal_cost(this,Ex1.finish_x,Ex1.finish_y);
        int o_h_cost = Algorithm.diagonal_cost(o,Ex1.finish_x,Ex1.finish_y);
        int ans = Integer.compare(this_h_cost*this.curr_cost,o_h_cost+o.curr_cost);
        if(ans == 0)
        {
            if(this.creation_time < o.creation_time)
            {
                if(Ex1.new_first)
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                if(!Ex1.new_first)
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }

        }
        return ans;

        //new try
//        double this_h_cost = Algorithm.heuristic(this,Ex1.finish_x,Ex1.finish_y);
//        double o_h_cost = Algorithm.heuristic(o,Ex1.finish_x,Ex1.finish_y);
//        return Double.compare(this_h_cost+this.curr_cost,o_h_cost+o.curr_cost);



        //no heuristic
//        return Integer.compare(this.curr_cost,o.curr_cost);
    }


    enum Operation {
        NORTH,NORTHEAST,EAST,SOUTHEAST,SOUTH,SOUTHWEST,WEST,NORTHWEST,none
    }

    public int getHighestOp()
    {
        int ans = 0;
        for (Operation op:CLOCKWISE)
        {
            int curr_cost = entry_cost(op);
            if(curr_cost > ans)
            {
                ans = curr_cost;
            }
        }
        return ans;
    }

    public int getLowestOp()
    {
        int ans = 10;
        for (Operation op:CLOCKWISE)
        {
            int curr_cost = entry_cost(op);
            if(curr_cost< ans && curr_cost != -1)
            {
                ans = curr_cost;
            }
        }
        return ans;
    }
    public Node(Node _father, int x,int y,Operation _op)
    {
        this.father = _father;
//        global_index++;
        this.pos_x = x;
        this.pos_y = y;
        this.last_op = _op;
        if(this.father != null)
        {
            this.curr_cost = _father.curr_cost + _father.entry_cost(_op);
        }
        else
        {
            this.curr_cost = 0;
        }
        this.out = false;
        this.creation_time = global_index;


    }
    public Node getParent()
    {
        return this.father;
    }
    public int getX(){return this.pos_x;}
    public int getY(){return this.pos_y;}


    public Node getFather()
    {
        return this.father;
    }

    public void print_node()
    {

        System.out.println(this.getTerrain());
        System.out.println(this.getIndex());
        System.out.println("-------");
    }

    public void setOut(boolean isOut)
    {
        this.out = isOut;
    }
    public boolean getOut()
    {
        return this.out;
    }

    public int getCost()
    {
        return this.curr_cost;
    }

    public String getIndex()
    {
        return this.pos_x+","+(this.pos_y);
    }

    public char getTerrain()
    {
        return Ex1.board[this.pos_y][this.pos_x];
    }

    public String opToString()
    {
        switch(this.last_op)
        {
            case NORTH:
                return "U";

            case NORTHEAST:
                return "RU";

            case EAST:
                return "R";

            case SOUTHEAST:
                return "RD";

            case SOUTH:
                return "D";

            case SOUTHWEST:
                return "LD";

            case WEST:
                return "L";

            case NORTHWEST:
                return "LU";

        }
        return "None";
    }

    public Operation getLast_op()
    {
        return last_op;
    }
    public Node operator(Operation _op)
    {
        //update new coordinates
        int new_x = this.pos_x;
        int new_y = this.pos_y;
        switch(_op)
        {
            case NORTH:
                new_y--;
                break;
            case NORTHEAST:
                new_y--;
                new_x++;
                break;
            case EAST:
                new_x++;
                break;
            case SOUTHEAST:
                new_x++;
                new_y++;
                break;
            case SOUTH:
                new_y++;
                break;
            case SOUTHWEST:
                new_y++;
                new_x--;
                break;
            case WEST:
                new_x--;
                break;
            case NORTHWEST:
                new_x--;
                new_y--;
                break;

        }

        Node child = new Node(this, new_x,new_y,_op);
        global_index++;


        return child;
    }

    public boolean hasChild()
    {
        for (Operation curr_op : Operation.values()) {

            if(entry_cost(curr_op)>=0)
            {
//                System.out.println(curr_op);
                return true;
            }
        }
        return false;
    }

    public Queue<Node> allChilds(boolean clockwise)
    {

        Queue<Node> children = new LinkedList<>();
        Operation[] operations = CLOCKWISE;
        int operation_index = 0;
        for (; operation_index<operations.length ;operation_index++)
        {
            int legal_move = this.entry_cost(operations[operation_index]);
            if(legal_move>0)
            {
                Node child = this.operator(operations[operation_index]);
                children.add(child);
            }
        }
        return children;

    }

    public int entry_cost(Operation _op)
    {

        if(_op== Operation.none)
        {
            return -1;
        }
        int x = this.pos_x;
        int y = this.pos_y;

        switch(_op) {
            case NORTH:
                y--;
                break;
            case NORTHEAST:
                y--;
                x++;
                break;
            case EAST:
                x++;

                break;
            case SOUTHEAST:
                x++;

                y++;
                break;
            case SOUTH:
                y++;
                break;
            case SOUTHWEST:
                y++;
                x--;
                break;

            case WEST:
                x--;

                break;
            case NORTHWEST:
                x--;

                y--;
                break;
        }

        if(x<0 || y<0 || x >= Ex1.board_size || y>= Ex1.board_size)
        {
            return -1;
        }

        char terrain = Ex1.board[y][x];
        int cost = -10;
        switch(terrain)
        {
            case 'D':
                cost = 1;
                break;
            case 'R':
                cost =  3;

                break;

            case 'H':
                //if the move is diagonal
                cost = 5;
                if(_op == Operation.NORTHEAST || _op == Operation.SOUTHEAST || _op == Operation.SOUTHWEST ||
                _op == Operation.NORTHWEST)
                {
                    cost =  10;
                }
                break;

            case 'G':
                cost =  5;
                break;

            case 'X':
                cost =  -1;
                break;

            case 'S':
                cost =  0;
                break;
        }



        if(this.father!=null)
        {
            if(reverse_op(this.last_op,_op) || reverse_op(_op,this.last_op))
            {
                return -1;
            }
        }
        return cost;
    }
    public static boolean reverse_op(Operation op_a,Operation op_b)
    {
        if(op_a == Operation.NORTH && op_b == Operation.SOUTH) return true;
        if(op_a == Operation.NORTHEAST && op_b == Operation.SOUTHWEST) return true;
        if(op_a == Operation.EAST && op_b == Operation.WEST) return true;
        if(op_a == Operation.SOUTHEAST && op_b == Operation.NORTHWEST) return true;




        return false;
    }
}


