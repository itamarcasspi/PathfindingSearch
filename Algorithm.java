public class Algorithm {
    //returns the path of nodes from arg node to start node.
    //D is used in the Heuristic function. set to be equal to the cheapest cost of operation.
    static private final int D = 1;
    static private final int D2 = 1;
    public static String path(Node node)
    {
        String path = path_rec(node,"");
        if(path.length()==0)
        {
            return "empty path";
        }
        return path.substring(0,path.length()-1);
    }

    private static String path_rec(Node curr_node ,String ans)
    {
        if(curr_node.getFather() == null)
        {
            return ans;
        }
        return path_rec(curr_node.getFather(),curr_node.opToString()+"-"+ans);
    }
    //This is a classical manhattan function, calculate sum of distance between node to finish, along x + y axis

    public static int manhattan_cost(Node node,int finish_x,int finish_y)
    {
        //decrement 1 for array indexing
//        finish_y -= 1;
//        finish_y -= 1;
        int distance_x = Math.abs(node.getX() - finish_x);
        int distance_y = Math.abs(node.getY() - finish_y);
        return D*(distance_x + distance_y);
    }

    public static double euclidean_cost(Node node, int finish_x, int finish_y)
    {
//        finish_y -= 1;
//        finish_x -= 1;
        int x_diff = node.getX() - finish_x;
        int y_diff = node.getY() - finish_y;
        int distance_x = Math.abs(x_diff);
        int distance_y = Math.abs(y_diff);
//
//        double cost_to_enter = node.getFather().entry_cost(node.getLast_op());

        return Math.sqrt(distance_x*distance_x+distance_y*distance_y);

//        return 0;
//        int distance_x = Math.abs(node.getX() - node.getFather().getX());
//        int distance_y = Math.abs(node.getY() - node.getFather().getY());
//        return Math.sqrt(distance_x*distance_x+distance_y*distance_y);
    }

    public static int diagonal_cost(Node node, int finish_x, int finish_y)
    {
//        finish_y -= 1;
//        finish_x -= 1;
        int x_diff = node.getX() - finish_x;
        int y_diff = node.getY() - finish_y;
        int dx = Math.abs(x_diff);
        int dy = Math.abs(y_diff);
        int diag = Math.min(dx, dy);
        int straight = dx + dy;

        return  diag + (straight - 2 * diag);
    }
    //diagonal distance calculaation using chebyshev
    public static int heuristic(Node current, int finish_x,int finish_y) {
        int x_diff = current.getX() - finish_x;
        int y_diff = current.getY() - finish_y;
        int dx = Math.abs(x_diff);
        int dy = Math.abs(y_diff);
        int diag_distance = Math.max(dx,dy);
        return diag_distance;
    }




}


//
//        if(x_diff>0)
//        {
//            if (y_diff>0)
//            {
//                op = Node.Operation.SOUTHEAST;
//            }
//            else if(y_diff<0)
//            {
//                op = Node.Operation.NORTHEAST;
//            }
//            else
//            {
//                op = Node.Operation.EAST;
//            }
//        }
//        else if(x_diff<0)
//        {
//            if (y_diff>0)
//            {
//                op = Node.Operation.SOUTHWEST;
//            }
//            else if(y_diff<0)
//            {
//                op = Node.Operation.NORTHWEST;
//            }
//            else
//            {
//                op = Node.Operation.WEST;
//            }
//        }
//        else
//        {
//            if (y_diff>0)
//            {
//                op = Node.Operation.SOUTH;
//            }
//            else
//            {
//                op = Node.Operation.NORTH;
//            }
//        }