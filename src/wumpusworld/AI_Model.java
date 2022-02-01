package wumpusworld;
import java.util.*;

public class AI_Model {
    public static int Agent_move;
    public static int X_Finding = 1;
    public static int Y_Finding = 1;
    public static double pit_falling_from_breeze;
    public static double no_of_breezes_in_the_map;
    public static double wumpus_falling_from_stench;
    public static double no_of_stench_in_the_map;
    public static double no_of_wumpus_in_the_map;
    public static double no_of_pits_in_the_map;
    public static List < List < Integer >> nodes_which_are_adjecent = new ArrayList < > ();
    
    public void X_Y_Resetting() {
        X_Finding = 1;
        Y_Finding = 1;
    }
    /**
     * The following function is used to get the adjacent X coordinates in the map
     **/
    public int getting_the_adjecent_X() {
        return X_Finding;
    }
    /**
     * The following function is used to get the adjacent Y coordinates in the map
     **/
    public int getting_the_adjecent_Y() {
        return Y_Finding;
    }
    /**
     * The following function is used to delete the adjacent list values
     **/
    public void adjecent_list_deletion() {
        nodes_which_are_adjecent.clear();
    }
    /**
     * The following function is used to Increment the stench
     **/
    public void stench_Incrementation() {
        no_of_stench_in_the_map++;
    }
    /**
     * The following function is used to Increment the breeze
     **/
    public void breeze_Incrementation() {
        no_of_breezes_in_the_map++;
    }
    /**
     * The following function is used to Increment the pit
     **/
    public void pit_Incrementation() {
        pit_falling_from_breeze++;
        no_of_pits_in_the_map++;
    }
    /**
     * The following function is used to Increment the wumpus
     **/
    public void wumpus_Incrementation() {
        wumpus_falling_from_stench++;
        no_of_wumpus_in_the_map++;
    }
    /**
    * The following function is used to shoot the arrow when the agent find the wumpus
    **/
    public void Shoot_the_arrow(int moving_direction, World w) {
        System.out.println(moving_direction);
        switch (moving_direction) {
            case 0:
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_SHOOT);
                break;
            case 1:
                w.doAction(World.A_SHOOT);
                break;
            case 2:
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_SHOOT);
                break;
            case 3:
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_SHOOT);
                break;
        }
    }
    /**
    * The following function is used to getting the into Wumpus probability
    **/
    public double getting_into_Wumpus_probability() {
        double score_of_entering_the_dangerzone = 0;
        int get = (Agent_move == 0 || no_of_stench_in_the_map == 0) ? 1 : 0;
        switch (get) {
            case 1:
                return 0;
            default:
                int get1 = (wumpus_falling_from_stench == 0) ? 1 : 0;
                switch (get1) {
                    case 1:
                        double a = ((wumpus_falling_from_stench + 1) / no_of_stench_in_the_map);
                        double b = ((no_of_wumpus_in_the_map + 1) / Agent_move);
                        double d = (no_of_stench_in_the_map / Agent_move);
                        score_of_entering_the_dangerzone = (a * b) / (d + 2);
                        break;
                    default:
                        double g = (wumpus_falling_from_stench / no_of_stench_in_the_map);
                        double h = (no_of_wumpus_in_the_map / Agent_move);
                        double f = (no_of_stench_in_the_map / Agent_move);
                        score_of_entering_the_dangerzone = (g * h) / f;
                        break;
                }
        }
        return score_of_entering_the_dangerzone;
    }
    /**
     * The following function is used to getting the into pit probability
     **/
    public double getting_into_Pit_probability() {
        double score_of_entering_the_dangerzone = 0;
        int get = (Agent_move == 0 || no_of_breezes_in_the_map == 0) ? 1 : 0;
        switch (get) {
            case 1:
                return 0;
            default:
                int get1 = (pit_falling_from_breeze == 0) ? 1 : 0;
                switch (get1) {
                    case 1:
                        double a = ((pit_falling_from_breeze + 1) / no_of_breezes_in_the_map);
                        double b = ((no_of_pits_in_the_map + 1) / Agent_move);
                        double d = (no_of_breezes_in_the_map / Agent_move);
                        score_of_entering_the_dangerzone = (a * b) / (d + 2);
                        break;
                    default:
                        double g = (pit_falling_from_breeze / no_of_breezes_in_the_map);
                        double h = (no_of_pits_in_the_map / Agent_move);
                        double f = (no_of_breezes_in_the_map / Agent_move);
                        score_of_entering_the_dangerzone = (g * h) / f;
                        break;
                }
        }
        return score_of_entering_the_dangerzone;
    }
    /**
     * The following function is used to remove the adjacent list 
     **/
    public void Adj_list_removing(int fetchingxcord, int fetchingycord) {
        int deletingxcord = 0;
        int deletingycord = 0;
        List < List < Integer >> cloningdata = new ArrayList < > (nodes_which_are_adjecent);
        int Adj_node_lenght = nodes_which_are_adjecent.size();
        int start = 0;
        while (start < Adj_node_lenght) {
            deletingxcord = nodes_which_are_adjecent.get(start).get(0);
            deletingycord = nodes_which_are_adjecent.get(start).get(1);
            int get = (deletingxcord == fetchingxcord && deletingycord == fetchingycord) ? 1 : 0;
            switch (get) {
                case 1:
                    cloningdata.remove(nodes_which_are_adjecent.get(start));
            }
            start++;
        }
        nodes_which_are_adjecent = new ArrayList < > (cloningdata);
    }
    /**
     * The following function is used to select the node which the agent to move next
     **/
    public ArrayList < Integer > select_node_of_next(int present_x_value, int present_y_value, World cosmos) {

        int currenting_node_to_X = 0;
        int currenting_node_to_Y = 0;
        double score_of_entering_the_dangerzone = 1000;
        double temporary_score = 0;
        ArrayList < Integer > node_of_next = new ArrayList < Integer > ();
        int Adj_node_lenght = nodes_which_are_adjecent.size();
        int start = 0;
        while (start < Adj_node_lenght) {
            int recordBreeze, xCord, yCord, random, recordStench;
            recordBreeze = xCord = yCord = random = recordStench = 0;
            double dgs = 0;
            RiskCalculate rC = new RiskCalculate(recordBreeze, xCord, yCord, random, recordStench, dgs);
            currenting_node_to_X = nodes_which_are_adjecent.get(start).get(0);
            currenting_node_to_Y = nodes_which_are_adjecent.get(start).get(1);
            temporary_score = rC.riskAnaylsis(present_x_value, present_y_value, currenting_node_to_X, currenting_node_to_Y, cosmos);
            int get1 = (temporary_score < score_of_entering_the_dangerzone) ? 1 : 0;
            switch (get1) {
                case 1:
                    score_of_entering_the_dangerzone = temporary_score;
                    int get = (!node_of_next.isEmpty()) ? 1 : 0;
                    switch (get) {
                        case 1:
                            node_of_next.clear();;
                    }
                    node_of_next.add(currenting_node_to_X);
                    node_of_next.add(currenting_node_to_Y);
            }
            start++;
        }
        return node_of_next;
    }
    /**
     * The following function is used to add the adjacent move
     **/
    public void add_adjecent(int currenting_node_to_X, int currenting_node_to_Y, World cosmos) {
        List < Integer > temporary_score1 = new ArrayList < Integer > ();
        List < Integer > temporary_score2 = new ArrayList < Integer > ();
        List < Integer > temporary_score3 = new ArrayList < Integer > ();
        List < Integer > temporary_score4 = new ArrayList < Integer > ();
        int validmove_up = (cosmos.isValidPosition(currenting_node_to_X, currenting_node_to_Y + 1) && cosmos.isUnknown(currenting_node_to_X, currenting_node_to_Y + 1)) ? 1 : 0;
        int validmove_dwn = (cosmos.isValidPosition(currenting_node_to_X, currenting_node_to_Y - 1) && cosmos.isUnknown(currenting_node_to_X, currenting_node_to_Y - 1)) ? 2 : 0;
        int validmove_left = (cosmos.isValidPosition(currenting_node_to_X - 1, currenting_node_to_Y) && cosmos.isUnknown(currenting_node_to_X - 1, currenting_node_to_Y)) ? 3 : 0;
        int validmove_rgt = (cosmos.isValidPosition(currenting_node_to_X + 1, currenting_node_to_Y) && cosmos.isUnknown(currenting_node_to_X + 1, currenting_node_to_Y)) ? 4 : 0;
        List < Integer > checking = new ArrayList < Integer > (Arrays.asList(validmove_up, validmove_dwn, validmove_left, validmove_rgt));
        for (int i: checking) {
            switch (i) {
                case 1:
                    temporary_score1.add(currenting_node_to_X);
                    temporary_score1.add(currenting_node_to_Y + 1);
                    nodes_which_are_adjecent.add(temporary_score1);
                    break;
                case 2:
                    temporary_score2.add(currenting_node_to_X);
                    temporary_score2.add(currenting_node_to_Y - 1);
                    nodes_which_are_adjecent.add(temporary_score2);
                    break;
                case 3:
                    temporary_score3.add(currenting_node_to_X - 1);
                    temporary_score3.add(currenting_node_to_Y);
                    nodes_which_are_adjecent.add(temporary_score3);
                    break;
                case 4:
                    temporary_score4.add(currenting_node_to_X + 1);
                    temporary_score4.add(currenting_node_to_Y);
                    nodes_which_are_adjecent.add(temporary_score4);
                    break;

            }
        }
        delete_all_clones();
    }
    /**
     * The following function is used to select the direction which the agent to move
     **/
    public int select_dir(int present_x_value, int present_y_value, int X_travel, int Y_travel, World cosmos) {
        boolean move_rgt = false;
        boolean move_left = false;
        boolean move_up = false;
        boolean move_dwn = false;
        int previous_dir = 0;
        int original_dir = -1;
        int moving_direction = 0;
        int goingmove_left = (X_travel < 0 && Y_travel == 0) ? 1 : 0;
        int goingmove_rgt = (X_travel > 0 && Y_travel == 0) ? 2 : 0;
        int goingmove_dwn = (X_travel == 0 && Y_travel < 0) ? 3 : 0;
        int goingmove_up = (X_travel == 0 && Y_travel > 0) ? 4 : 0;
        int goingmove_rgtmove_up = (X_travel > 0 && Y_travel > 0) ? 5 : 0;
        int goingmove_rgtmove_dwn = (X_travel > 0 && Y_travel < 0) ? 6 : 0;
        int goingmove_leftmove_dwn = (X_travel < 0 && Y_travel < 0) ? 7 : 0;
        int goingmove_leftmove_up = (X_travel < 0 && Y_travel > 0) ? 8 : 0;
        int validating_in_all_directions = (goingmove_left > 0) ? goingmove_left : (goingmove_rgt > 0) ? goingmove_rgt : (goingmove_dwn > 0) ? goingmove_dwn : (goingmove_up > 0) ? goingmove_up : (goingmove_rgtmove_up > 0) ? goingmove_rgtmove_up : (goingmove_rgtmove_dwn > 0) ? goingmove_rgtmove_dwn : (goingmove_leftmove_dwn > 0) ? goingmove_leftmove_dwn : (goingmove_leftmove_up > 0) ? goingmove_leftmove_up : 0;
        switch (validating_in_all_directions) 
        {

            case 1:

                boolean this_direction = (cosmos.isVisited(present_x_value - 1, present_y_value) || (present_x_value - 1 == X_Finding && present_y_value == Y_Finding));
                original_dir = (!cosmos.hasPit(present_x_value - 1, present_y_value) && this_direction) ? 3 : original_dir;
                move_left = (!cosmos.hasPit(present_x_value - 1, present_y_value) && this_direction) ? true : move_left;

                break;
            case 2:

                boolean this_direction1 = (cosmos.isVisited(present_x_value + 1, present_y_value) || (present_x_value + 1 == X_Finding && present_y_value == Y_Finding));
                original_dir = (!cosmos.hasPit(present_x_value + 1, present_y_value) && this_direction1) ? 1 : original_dir;
                move_rgt = (!cosmos.hasPit(present_x_value + 1, present_y_value) && this_direction1) ? true : move_rgt;

                break;
            case 3:

                boolean this_direction2 = (cosmos.isVisited(present_x_value, present_y_value - 1) || (present_x_value == X_Finding && present_y_value - 1 == Y_Finding));
                original_dir = (!cosmos.hasPit(present_x_value, present_y_value - 1) && this_direction2) ? 2 : original_dir;
                move_dwn = (!cosmos.hasPit(present_x_value, present_y_value - 1) && this_direction2) ? true : move_dwn;

                break;
            case 4:

                boolean this_direction3 = (cosmos.isVisited(present_x_value, present_y_value + 1) || (present_x_value == X_Finding && present_y_value + 1 == Y_Finding));
                original_dir = (!cosmos.hasPit(present_x_value, present_y_value + 1) && this_direction3) ? 0 : original_dir;
                move_up = (!cosmos.hasPit(present_x_value, present_y_value - 1) && this_direction3) ? true : move_up;

                break;
            case 5:
                int move_rgtcheck = (!cosmos.hasPit(present_x_value + 1, present_y_value) && cosmos.isVisited(present_x_value + 1, present_y_value)) ? 1 : 0;
                int move_upcheck = (!cosmos.hasPit(present_x_value, present_y_value + 1) && cosmos.isVisited(present_x_value, present_y_value + 1)) ? 2 : 0;
                int valmove_upmove_rgt = (move_rgtcheck > 0) ? move_rgtcheck : (move_upcheck > 0) ? move_upcheck : 0;
                switch (valmove_upmove_rgt) {
                    case 1:
                        original_dir = 1;
                        move_rgt = true;
                        break;
                    case 2:
                        original_dir = 0;
                        move_up = true;
                        break;
                }

                break;
            case 6:
                int move_rgtcheck1 = (!cosmos.hasPit(present_x_value + 1, present_y_value) && cosmos.isVisited(present_x_value + 1, present_y_value)) ? 1 : 0;
                int move_dwncheck = (!cosmos.hasPit(present_x_value, present_y_value - 1) && cosmos.isVisited(present_x_value, present_y_value - 1)) ? 2 : 0;
                int valmove_upmove_rgt1 = (move_rgtcheck1 > 0) ? move_rgtcheck1 : (move_dwncheck > 0) ? move_dwncheck : 0;
                switch (valmove_upmove_rgt1) {
                    case 1:
                        original_dir = 1;
                        move_rgt = true;
                        break;
                    case 2:
                        original_dir = 2;
                        move_dwn = true;
                        break;
                }

                break;
            case 7:
                int move_leftcheck = (!cosmos.hasPit(present_x_value - 1, present_y_value) && cosmos.isVisited(present_x_value - 1, present_y_value)) ? 1 : 0;
                int move_dwncheck1 = (!cosmos.hasPit(present_x_value, present_y_value - 1) && cosmos.isVisited(present_x_value, present_y_value - 1)) ? 2 : 0;
                int valmove_upmove_rgt2 = (move_leftcheck > 0) ? move_leftcheck : (move_dwncheck1 > 0) ? move_dwncheck1 : 0;
                switch (valmove_upmove_rgt2) {
                    case 1:
                        original_dir = 3;
                        move_left = true;
                        break;
                    case 2:
                        original_dir = 2;
                        move_dwn = true;
                        break;
                }

                break;
            case 8:
                int move_leftcheck1 = (!cosmos.hasPit(present_x_value - 1, present_y_value) && cosmos.isVisited(present_x_value - 1, present_y_value)) ? 1 : 0;
                int move_upcheck1 = (!cosmos.hasPit(present_x_value, present_y_value + 1) && cosmos.isVisited(present_x_value, present_y_value + 1)) ? 2 : 0;
                int valmove_upmove_rgt3 = (move_leftcheck1 > 0) ? move_leftcheck1 : (move_upcheck1 > 0) ? move_upcheck1 : 0;
                switch (valmove_upmove_rgt3) {
                    case 1:
                        original_dir = 3;
                        move_left = true;
                        break;
                    case 2:
                        original_dir = 2;
                        move_up = true;
                        break;
                }

                break;
        }
        int move_updating_latest_direction = (original_dir == -1) ? 1 : 0;
        switch (move_updating_latest_direction) {
            case 1:
                this.Adj_list_removing(X_Finding, Y_Finding);
                moving_direction = this.Move_calculating_for_the_next(cosmos);
                return moving_direction;
        }
        previous_dir = cosmos.getDirection();
        switch (previous_dir) {
            case 0:
                moving_direction = (move_rgt == true) ? 3 : (move_left == true) ? 0 : (move_up == true) ? 1 : (move_dwn == true) ? 2 : moving_direction;
                break;
            case 1:
                moving_direction = (move_rgt == true) ? 1 : (move_left == true) ? 2 : (move_up == true) ? 0 : (move_dwn == true) ? 3 : moving_direction;
                break;
            case 2:
                moving_direction = (move_rgt == true) ? 0 : (move_left == true) ? 3 : (move_up == true) ? 2 : (move_dwn == true) ? 1 : moving_direction;
                break;
            case 3:
                moving_direction = (move_rgt == true) ? 2 : (move_left == true) ? 1 : (move_up == true) ? 3 : (move_dwn == true) ? 0 : moving_direction;
                break;
        }

        return moving_direction;
    }
    public void delete_all_clones() {
        List < List < Integer >> temporary_scorelist = new ArrayList < > (new HashSet < > (nodes_which_are_adjecent));
        nodes_which_are_adjecent = new ArrayList < > (temporary_scorelist);
    }
    /**
     * The following function is used to move the agent
     **/
    public int get_path(World cosmos) {
        int path = 0;
        int currenting_node_to_X = cosmos.getPlayerX();
        int currenting_node_to_Y = cosmos.getPlayerY();
        int calculating_move = (currenting_node_to_X == X_Finding && currenting_node_to_Y == Y_Finding && !nodes_which_are_adjecent.isEmpty()) ? 1 : 0;
        switch (calculating_move) {
            case 1:
                Adj_list_removing(currenting_node_to_X, currenting_node_to_Y);
                path = Move_calculating_for_the_next(cosmos);
                break;
            case 0:
                path = select_dir(currenting_node_to_X, currenting_node_to_Y, X_Finding - currenting_node_to_X, Y_Finding - currenting_node_to_Y, cosmos);
                Agent_move++;
                break;
        }
        return path;
    }
    /**
     * The following function is used to calculating for the next move
     **/
    public int Move_calculating_for_the_next(World current_env) {
        int xCords = current_env.getPlayerX();
        int yCords = current_env.getPlayerY();
        int towardsx = 0;
        int towardsy = 0;
        int xpath = 0;
        int ypath = 0;
        int path = 0;
        add_adjecent(xCords, yCords, current_env);
        List < Integer > towardsblock = new ArrayList < Integer > (select_node_of_next(xCords, yCords, current_env));
        towardsx = towardsblock.get(0);
        towardsy = towardsblock.get(1);
        X_Finding = towardsx;
        Y_Finding = towardsy;
        xpath = towardsx - xCords;
        ypath = towardsy - yCords;
        path = select_dir(xCords, yCords, xpath, ypath, current_env);
        Agent_move++;
        return path;
    }

}