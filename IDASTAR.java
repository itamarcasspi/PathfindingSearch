import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class IDASTAR {

    public static String findPath(Node start, char goal,boolean open_list) throws IOException {
        if(start.getTerrain() == goal)
        {
            return Algorithm.path(start);
        }
        Stack<Node> list = new Stack<>();
        HashMap<String,Node> H = new HashMap<>();
        long startTime = System.currentTimeMillis();
        //calculate current threshold from start pos
        int threshold = Algorithm.heuristic(start,Ex1.finish_x,Ex1.finish_y);
        while(threshold < Integer.MAX_VALUE)
        {
            int minF = Integer.MAX_VALUE;
            list.push(start);
            H.put(start.getIndex(),start);
            while(!list.isEmpty())
            {
                if(open_list)
                {
                    System.out.println("Open list: "+"Size:"+list.size()+list);
                }

                Node curr = list.pop();
                if(curr.getOut())
                {
                    H.remove(curr.getIndex());
                }
                else
                {
                    curr.setOut(true);
                    list.push(curr);

                    Node.Operation[] operations = Ex1.clockwise ? Node.CLOCKWISE : Node.COUNTER_CLOCKWISE;
                    for (Node.Operation op : operations)
                    {
                        int op_cost = curr.entry_cost(op);
                        if (op_cost > 0)
                        {
                            Node child = curr.operator(op);
                            if(child.getCost() > threshold)
                            {
                                minF = Math.min(minF,child.getCost());
                                continue;
                            }
                            else if(H.containsKey(child.getIndex()) && H.get(child.getIndex()).getOut())
                            {
                                continue;
                            }
                            else if (H.containsKey(child.getIndex()) &&!H.get(child.getIndex()).getOut())
                            {
                                Node _child = H.get(child.getIndex());
                                if(_child.getCost()>child.getCost())
                                {
                                    list.remove(_child);
                                    H.remove(child.getIndex());
                                }
                                else
                                {
                                    continue;
                                }
                            }
                            else if (child.getTerrain() == goal)
                            {
                                String path = Algorithm.path(child);
                                Ex1.output.write(path+"\n");
                                Ex1.output.write("Num: "+ Node.global_index+"\n");
                                Ex1.output.write("Cost: "+child.getCost()+"\n");

                                if(Ex1.timer)
                                {
                                    long elapsedTime = System.currentTimeMillis() - startTime;
                                    double elapsedSeconds = (double)elapsedTime / 1000;
                                    long miliSecondsDisplay = (elapsedTime) % 60;
                                    Ex1.output.write(elapsedSeconds+" seconds"+"\n");
                                }
                                return path;
                            }
                            list.push(child);
                            H.put(child.getIndex(),child);
                        }
                    }
                }
            }
            threshold = minF;
            start = new Node(null,Ex1.start_x,Ex1.start_y,null);

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
        return "fail";
    }
}
