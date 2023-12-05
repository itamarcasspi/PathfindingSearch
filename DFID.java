import java.io.IOException;
import java.util.HashSet;



public class DFID {

    public static final String cutoff = "cutoff";
    public static final String fail = "fail";

    private static long startTime = System.currentTimeMillis();



    public static String findPath(Node start,char goal,boolean clockwise,boolean timer) throws IOException {
//        if(Ex1.board[start.getY()][start.getX()] == goal)
//        {
////            Ex1.output.write(Algorithm.path(start));
////            Ex1.output.write("Num: "+Node.global_index);
////            Ex1.output.write();
//
//
//            return Algorithm.path(start);
//        }
        long startTime = System.currentTimeMillis();

        for (int limit = 1; limit < Ex1.board_size*Ex1.board_size;limit++)
        {
            System.out.println(limit+"<"+Ex1.board_size*Ex1.board_size);
            HashSet<String> H = new HashSet<>();
            String result = Limited_DFS(start,goal,limit,H,clockwise);
            if(result.equals(fail))
            {
                break;
            } else if (!result.equals(cutoff))
            {

                return result;
            }

        }
        Ex1.output.write("no path");
        Ex1.output.write("Num: "+Node.global_index+"\n");
        Ex1.output.write("Cost: inf\n");
        if(Ex1.timer)
        {
            long elapsedTime = System.currentTimeMillis() - startTime;
            double elapsedSeconds = (double)elapsedTime / 1000;
            long miliSecondsDisplay = (elapsedTime) % 60;
            Ex1.output.write(elapsedSeconds+" seconds"+"\n");
        }
        return fail;
    }
    private static String Limited_DFS(Node node, char goal, int limit, HashSet<String> H,boolean clockwise) throws IOException {
        if(goal == node.getTerrain())
        {
            Ex1.output.write(Algorithm.path(node)+"\n");
            Ex1.output.write("Num: "+Node.global_index+"\n");
            Ex1.output.write("Cost: "+node.getCost()+"\n");
            if(Ex1.timer)
            {
                long elapsedTime = System.currentTimeMillis() - startTime;
                double elapsedSeconds = (double)elapsedTime / 1000;
                long miliSecondsDisplay = (elapsedTime) % 60;
                Ex1.output.write(elapsedSeconds+" seconds"+"\n");
            }
            return " ";
        } else if (limit == 0)
        {
            return cutoff;
        }
        else
        {
            H.add(node.getIndex());
            boolean is_cutoff = false;
            Node.Operation[] operations = clockwise ? Node.CLOCKWISE : Node.COUNTER_CLOCKWISE;
            for (int i = 0; i< operations.length; i++)
            {
                Node.Operation op = operations[i];
                int op_cost = node.entry_cost(op);
                if(op_cost>=0)
                {
                    Node child = node.operator(op);
                    if(!H.contains(child.getIndex()))
                    {
                        String result = Limited_DFS(child,goal,limit-1,H,clockwise);
                        if(result.equals(cutoff))
                        {
                            is_cutoff = true;
                        } else if (!result.equals(fail))
                        {
                            return result;
                        }
                    }
                }
            }
            H.remove(node.getIndex());
            if(is_cutoff)
            {
                return cutoff;
            }
            else
            {
                return fail;
            }
                


        }


    }
}
