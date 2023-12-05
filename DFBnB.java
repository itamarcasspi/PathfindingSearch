import java.io.IOException;
import java.util.*;

public class DFBnB {
    private static final String fail = "fail";
    private static final int MAX_STEPS = Ex1.board_size*Ex1.board_size;

    private static final int HIGHEST_STEP_COST = 10;

    public static String findPath(Node start, char goal) throws IOException {
        if(start.getTerrain() == goal)
        {
            return Algorithm.path(start);
        }
        long startTime = System.currentTimeMillis();
        String result = "no path";
        int result_cost = 0;
        Stack<Node> list = new Stack<>();
        HashMap<String , Node> H = new HashMap<>();
        list.push(start);
        H.put(start.getIndex(),start);
        int threshhold = MAX_STEPS*HIGHEST_STEP_COST;
        while(!list.isEmpty())
        {
            if(Ex1.print_open)
            {
                System.out.println("List size = "+list.size());
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
                ArrayList<Node> N_LIST = new ArrayList<>();
                Node.Operation[] operations = Ex1.clockwise ? Node.CLOCKWISE : Node.COUNTER_CLOCKWISE;
                for (Node.Operation op : operations) {
                    int op_cost = curr.entry_cost(op);
                    if (op_cost > 0)
                    {
                        Node child = curr.operator(op);
                        N_LIST.add(child);
                    }
                }
                Collections.sort(N_LIST);
                ArrayList<Node> temp_list = new ArrayList<>();
                for (Node n : N_LIST)
                {
                    int f_cost = Algorithm.heuristic(n,Ex1.finish_x,Ex1.finish_y) * n.getCost();
                    if(f_cost >= threshhold)
                    {
                        if(!list.isEmpty()) {


                            Node t = list.pop();
                            while (!list.isEmpty() && t != n)
                            {
                                t = list.pop();
                            }
                        }
                        break;
                    }
                    else if (H.containsKey(n.getIndex()) && H.get(n.getIndex()).getOut())
                    {
                        continue;
                    }
                    else if (H.containsKey(n.getIndex()) && !H.get(n.getIndex()).getOut())
                    {
                        Node _n = H.get(n.getIndex());
                        int heur_n = _n.getCost() + Algorithm.heuristic(_n,Ex1.finish_x,Ex1.finish_y);
                        int heur_actual_n = n.getCost() + Algorithm.heuristic(n,Ex1.finish_x,Ex1.finish_y);

                        if( heur_n <= heur_actual_n)
                        {
                            temp_list.add(n);
                            list.remove(_n);
                            H.remove(n.getIndex());
                        }
                        else
                        {
                            continue;
                        }
                    }
                    else if (n.getTerrain() == goal)
                    {
//                        String path = Algorithm.path(n);
//                        Ex1.output.write(path+"\n");
//                        Ex1.output.write("Num: "+ Node.global_index+"\n");
//                        Ex1.output.write("Cost: "+n.getCost()+"\n");
//
//                        if(Ex1.timer)
//                        {
//                            long elapsedTime = System.currentTimeMillis() - startTime;
//                            double elapsedSeconds = (double)elapsedTime / 1000;
//                            long miliSecondsDisplay = (elapsedTime) % 60;
//                            Ex1.output.write(elapsedSeconds+" seconds"+"\n");
//                        }
//                        return path;
                        threshhold = f_cost;
                        result = Algorithm.path(n);
                        result_cost = n.getCost();
                        Node t = list.pop();
                        while (!list.isEmpty() && t != n)
                        {
                            t = list.pop();
                        }
                    }
                    temp_list.add(n);
                }
                for(int i = temp_list.size()-1; i>=0; i-- )
                {
                    Node temp = temp_list.get(i);
                    list.push(temp);
                    H.put(temp.getIndex(),temp);
                }



            }

        }


        Ex1.output.write(result+"\n");
        Ex1.output.write("Num: "+ Node.global_index+"\n");
        if(result.equals("no path"))
        {
            Ex1.output.write("Cost: "+result_cost+"\n");

        }
        else
        {
            Ex1.output.write("Cost: "+result_cost+"\n");

        }

        if(Ex1.timer)
        {
            long elapsedTime = System.currentTimeMillis() - startTime;
            double elapsedSeconds = (double)elapsedTime / 1000;
            long miliSecondsDisplay = (elapsedTime) % 60;
            Ex1.output.write(elapsedSeconds+" seconds"+"\n");
        }
        return result;

    }

}
