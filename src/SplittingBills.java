import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SplittingBills {

    static Scanner sc = new Scanner(System.in);
    static int total_num_persons;

    public static void main(String args[]){

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        ArrayList<Integer> row;
        System.out.println("Enter total no. of persons involved in sharing amount");
        total_num_persons = sc.nextInt();
        for(int i=0; i<total_num_persons; i++){
            row = new ArrayList<>();
            for(int j=0; j<total_num_persons; j++){
                row.add(0);
            }
            graph.add(row);
        }

        String transactions_flag;

        System.out.println("Do you want to add a transaction: enter (y/n)");
        transactions_flag = sc.next();

        while(transactions_flag.equals("y")){
            System.out.println("Add transaction person number between (0 - "+(total_num_persons-1)+")");
            int p_no = get_personNumber(sc.nextInt());
            System.out.println("Add transaction amount");
            int amt = get_Amount(sc.nextInt());
            System.out.println("enter total number of persons this amount to be splitted to");
            int t_num_split = sc.nextInt();
            if(t_num_split > total_num_persons){
                System.out.println("person number exceeded limit");
                System.exit(0);
            }
            System.out.println("Enter the split person numbers in a single line with space");
            int[] p_nums = new int[t_num_split];
            for(int index=0; index<t_num_split; index++){
                p_nums[index] = get_personNumber(sc.nextInt());
            }

            // constructing a matrix with given amount inputs.
            // Each row number represents a person and to which persons that row number person has to pay amount.

            int init_split_amt = amt/t_num_split;
            for(int ar_idx=0; ar_idx < p_nums.length; ar_idx++){
                if(p_nums[ar_idx] != p_no){
                    int col_num = p_nums[ar_idx];
                    int current_amt = graph.get(col_num).get(p_no);
                    graph.get(col_num).set(p_no, current_amt + init_split_amt);
                }
            }
            System.out.println("Do you want to add another transaction: enter (y/n)");
            transactions_flag = sc.next();
        }

        if(!graph.isEmpty()){
            doSplit(graph, total_num_persons);
        }

    }

    public static int get_Amount(int i) {
        if(i<0){
            System.out.println("amount cannot be negative");
            System.exit(0);
        }
        return i;
    }

    public static int get_personNumber(int i) {
        if(i >= total_num_persons){
            System.out.println("person number exceeded limit. it should be in range of 0-"+(total_num_persons-1)+")");
            System.exit(0);
        }
        return i;
    }

    private static void doSplit(ArrayList<ArrayList<Integer>> graph, int total_num_persons) {

        /*Hashmap key represents the person number and value represents the
        amount that should be paid/received*/

        HashMap<Integer, Integer> NetAmnt_positive = new HashMap<>();
        HashMap<Integer, Integer> NetAmnt_negative = new HashMap<>();

        /* (NetAmnt_positive)Net amount positive indicates amount to be received and
         (NetAmnt_negative)Net amount negative indicates amount to be paid*/

        for(int i=0; i<total_num_persons; i++){
            int row_sum = 0, col_sum = 0;
            for(int j=0; j<total_num_persons; j++){
                row_sum = row_sum + graph.get(i).get(j);
            }
            for(int j=0; j<total_num_persons; j++){
                col_sum = col_sum + graph.get(j).get(i);
            }
            if(col_sum - row_sum > 0){
                NetAmnt_positive.put(i, col_sum - row_sum);
            }
            else if(col_sum - row_sum < 0){
                NetAmnt_negative.put(i, col_sum - row_sum);
            }
        }


        while (!NetAmnt_positive.isEmpty() && !NetAmnt_negative.isEmpty()){

            Map.Entry<Integer, Integer> max = null;     // holds the max value present in NetAmnt_positive map
            Map.Entry<Integer, Integer> min = null;     // holds the min value present in NetAmnt_negative map

            for (Map.Entry<Integer, Integer> entry : NetAmnt_positive.entrySet())
            {
                if (max == null || entry.getValue().compareTo(max.getValue()) > 0)
                {
                    max = entry;
                }
            }
            for (Map.Entry<Integer, Integer> entry : NetAmnt_negative.entrySet())
            {
                if(min == null || entry.getValue().compareTo(min.getValue()) < 0)
                {
                    min = entry;
                }
            }

            // calculating the difference amount of max and min value and updating the Net amount map values.

            int difference_amount = max.getValue() + min.getValue();
            if(difference_amount > 0){

                System.out.println(min.getKey()+" owes "+max.getKey()+" : "+Math.abs(min.getValue()));

                NetAmnt_negative.remove(min.getKey());
                NetAmnt_positive.put(max.getKey(), difference_amount);

            }else if(difference_amount < 0){

                System.out.println(min.getKey()+" owes "+max.getKey()+" : "+max.getValue());

                NetAmnt_positive.remove(max.getKey());
                NetAmnt_negative.put(min.getKey(), difference_amount);

            }else{

                System.out.println(min.getKey()+" owes "+max.getKey()+" : "+max.getValue());

                NetAmnt_positive.remove(max.getKey());
                NetAmnt_negative.remove(min.getKey());

            }
        }

    }

}
