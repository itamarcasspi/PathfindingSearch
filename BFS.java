import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends Algorithm{

    public static String findPath(Node start_node, char goal_node,boolean clockwise, boolean timer) throws IOException {
        long startTime = System.currentTimeMillis();

        if(start_node.getTerrain() == goal_node)
        {
            return path(start_node);
        }
        //init ds
        Queue<Node> list = new LinkedList<>();
        HashSet<String> open_l = new HashSet<>();
        HashSet<String> closed_l = new HashSet<>();
        //add starting node
        list.add(start_node);
        open_l.add(start_node.getIndex());
        int counter = 1;


        while(!list.isEmpty())
        {
            Node curr = list.remove(); // remove front
            open_l.remove(curr.getIndex());
            closed_l.add(curr.getIndex());

            int operation_index = 0;
            Node.Operation[] operations = clockwise? Node.CLOCKWISE : Node.COUNTER_CLOCKWISE;
            while(curr.hasChild())
            {
                if(operation_index > operations.length-1)
                {
                    break;
                }
                int legal_move = curr.entry_cost(operations[operation_index]);
                if(legal_move>0)
                {
                    Node child = curr.operator(operations[operation_index]);

                    if(!open_l.contains(child.getIndex()) && !closed_l.contains(child.getIndex()))
                    {


                        if(child.getTerrain() == goal_node)
                        {
                            Ex1.output.write(path(child)+"\n");
                            Ex1.output.write("Num: "+ Node.global_index+"\n");
                            Ex1.output.write("Cost: "+child.getCost()+"\n");

                            if(timer)
                            {
                                long elapsedTime = System.currentTimeMillis() - startTime;
                                double elapsedSeconds = (double)elapsedTime / 1000;
                                long miliSecondsDisplay = (elapsedTime) % 60;
                                Ex1.output.write(elapsedSeconds+" seconds"+"\n");
                            }
                            return " ";
                        }
                        open_l.add(child.getIndex());
                        list.add(child);
                    }

                }
                operation_index++;
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
        return "NO PATH";

    }

}

