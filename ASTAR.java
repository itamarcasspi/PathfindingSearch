import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;

public class ASTAR {

    private static final String fail = "fail";

    public static String findPath(Node start, char goal,boolean clockwise,boolean timer, boolean open_list,
                                  int finish_x,int finish_y) throws IOException {
        if(start.getTerrain() == goal)
        {
            return Algorithm.path(start);
        }
        long startTime = System.currentTimeMillis();

        PriorityQueue<Node> pqueue = new PriorityQueue<>();
        HashMap<String,Node> open_l = new HashMap<>();
        HashMap<String,Node> closed_l = new HashMap<>();

        pqueue.add(start);
        open_l.put(start.getIndex(),start);

        while(!pqueue.isEmpty())
        {
            if(open_list)
            {
                System.out.println("Open List:(size "+pqueue.size()+")");
            }

            Node curr = pqueue.poll();

            if(curr.getTerrain() == goal)
            {
                String path = Algorithm.path(curr);
                Ex1.output.write(Algorithm.path(curr)+"\n");
                Ex1.output.write("Num: "+ Node.global_index+"\n");
                Ex1.output.write("Cost: "+curr.getCost()+"\n");

                if(timer)
                {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    double elapsedSeconds = (double)elapsedTime / 1000;
                    long miliSecondsDisplay = (elapsedTime) % 60;
                    Ex1.output.write(elapsedSeconds+" seconds"+"\n");
                }
                return path;
            }
            open_l.remove(curr.getIndex());
            closed_l.put(curr.getIndex(),curr);

            Node.Operation[] operations = clockwise ? Node.CLOCKWISE : Node.COUNTER_CLOCKWISE;
            for (int i = 0; i< operations.length; i++) {
                Node.Operation op = operations[i];
                int op_cost = curr.entry_cost(op);
                if (op_cost > 0)
                {
                    Node child = curr.operator(op);
                    if(!open_l.containsKey(child.getIndex()) && !closed_l.containsKey(child.getIndex()))
                    {
                        open_l.put(child.getIndex(),child);
                        pqueue.add(child);
                    }
                    else if(pqueue.contains(child))
                    {
                        Node candidate = open_l.get(child.getIndex());
                        int cand_cost = candidate.getCost() + Algorithm.heuristic(candidate,finish_x,finish_y);
                        int child_cost = child.getCost() + Algorithm.heuristic(child,finish_x,finish_y);
                        if(cand_cost > child_cost )
                        {
                                pqueue.remove(candidate);
                                pqueue.add(child);
                                open_l.put(child.getIndex(), child);

                        }
                    }

                }
            }
        }
        Ex1.output.write("no path\n");
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
}
