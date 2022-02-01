package wumpusworld;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class RiskCalculate {
    public int recordBreeze, xCord, yCord, random, recordStench;
    public double riskassumed;
    RiskCalculate(int bRec, int x, int y, int rand, int st, double caution) {
        recordBreeze = bRec;
        xCord = x;
        yCord = y;
        random = rand;
        recordStench = st;
        riskassumed = caution;
    }
    // Used for calculating the risk involved in further blocks if present block has both Breeze and Stench.
    public double breezeStenchActivated(World current_env, int posX, int posY, int xLoc, int yLoc, AI_Model aIAlgo) {
        int stechgreater = (recordStench > recordBreeze) ? 1 : 0;
        int breezegreater = (recordBreeze > recordStench) ? 2 : 0;
        int stench2 = (recordStench >= 2) ? 1 : 0;
        int findinggreatersense = (stechgreater > 0) ? stechgreater : (breezegreater > 0) ? breezegreater : 0;
        switch (findinggreatersense) {
            case 1:
                recordStench++;
                riskassumed = aIAlgo.getting_into_Wumpus_probability() * recordStench;
                riskassumed += 0.00001;
                break;
            case 2:
                recordBreeze++;
                riskassumed = aIAlgo.getting_into_Pit_probability() * recordBreeze;
                break;
            default:
                recordStench++;
                recordBreeze++;
                riskassumed += aIAlgo.getting_into_Wumpus_probability();
                riskassumed += aIAlgo.getting_into_Pit_probability();
                riskassumed += 0.00001;
                break;
        }

        switch (stench2) {
            case 1:
                xCord = posX - current_env.getPlayerX();
                yCord = posY - current_env.getPlayerY();
                int storedxCord = aIAlgo.X_Finding;
                int storedyCord = aIAlgo.Y_Finding;
                aIAlgo.X_Finding = posX;
                aIAlgo.Y_Finding = posY;
                random = aIAlgo.select_dir(current_env.getPlayerX(), current_env.getPlayerY(), xCord, yCord, current_env);
                aIAlgo.Shoot_the_arrow(random, current_env);
                aIAlgo.X_Finding = storedxCord;
                aIAlgo.Y_Finding = storedyCord;
                return this.riskAnaylsis(xLoc, yLoc, posX, posY, current_env);
        }
        return 0;
    }
    // Used for calculating the risk involved in further blocks if present block has a Breeze.
    public int breezeActivated(AI_Model aIAlgo) {
        int prevStren = (recordStench > recordBreeze) ? 1 : 0;
        switch (prevStren) {
            case 1:
                return 0;
        }
        recordBreeze++;
        riskassumed = (recordStench >= 1 && recordBreeze >= 1) ? aIAlgo.getting_into_Pit_probability() * recordBreeze : riskassumed + aIAlgo.getting_into_Pit_probability();
        return 0;
    }
    // Used for calculating the risk in further blocks if present block has a Stench.
    public int brzFound(AI_Model aIAlgo) {
        int brezfound = (recordBreeze > recordStench) ? 1 : 0;
        switch (brezfound) {
            case 1:
                return 0;
        }
        recordStench++;
        riskassumed = (recordStench >= 1 && recordBreeze >= 1) ? (aIAlgo.getting_into_Wumpus_probability() * recordStench) : (riskassumed + aIAlgo.getting_into_Wumpus_probability());
        int stenchmore = (recordStench >= 2) ? 1 : 0;
        return stenchmore;
    }

    // Used for calculation of the risk involved in the provided map of Wumpus game.
    public double riskAnaylsis(int xLoc, int yLoc, int posX, int posY, World current_env) {
        AI_Model aIAlgo = new AI_Model();
        // Validating in all Directions
        boolean visit = (current_env.isVisited(posX, posY + 1));
        boolean validDown = current_env.isVisited(posX, posY - 1);
        boolean validRig = (current_env.isVisited(posX + 1, posY));
        boolean validLeft = (current_env.isVisited(posX - 1, posY));
        // Up Move
        int vUBS = (visit && (current_env.hasBreeze(posX, posY + 1) && current_env.hasStench(posX, posY + 1))) ? 1 : 0;
        int vUBreeze = (visit && (current_env.hasBreeze(posX, posY + 1))) ? 2 : 0;
        int vUStench = (visit && (current_env.hasStench(posX, posY + 1))) ? 3 : 0;
        int vUp = (visit) ? 4 : 0;
        int vUMove = (vUBS > 0) ? vUBS : (vUBreeze > 0) ? vUBreeze : (vUStench > 0) ? vUStench : (vUp > 0) ? vUp : 0;
        // Down Move
        int vDBS = (validDown && (current_env.hasBreeze(posX, posY - 1) && current_env.hasStench(posX, posY - 1))) ? 1 : 0;
        int vDBreeze = (validDown && (current_env.hasBreeze(posX, posY - 1))) ? 2 : 0;
        int vDStench = (validDown && (current_env.hasStench(posX, posY - 1))) ? 3 : 0;
        int vDown = (validDown) ? 4 : 0;
        int vDMove = (vDBS > 0) ? vDBS : (vDBreeze > 0) ? vDBreeze : (vDStench > 0) ? vDStench : (vDown > 0) ? vDown : 0;
        // Right Move
        int vRBS = (validRig && (current_env.hasBreeze(posX + 1, posY) && current_env.hasStench(posX + 1, posY))) ? 1 : 0;
        int vRBreeze = (validRig && (current_env.hasBreeze(posX + 1, posY))) ? 2 : 0;
        int vRStench = (validRig && (current_env.hasStench(posX + 1, posY))) ? 3 : 0;
        int vRight = (validRig) ? 4 : 0;
        int vRMove = (vRBS > 0) ? vRBS : (vRBreeze > 0) ? vRBreeze : (vRStench > 0) ? vRStench : (vRight > 0) ? vRight : 0;
        // Left Move
        int vLBS = (validLeft && (current_env.hasBreeze(posX - 1, posY) && current_env.hasStench(posX - 1, posY))) ? 1 : 0;
        int vLBreeze = (validLeft && (current_env.hasBreeze(posX - 1, posY))) ? 2 : 0;
        int vLStench = (validLeft && (current_env.hasStench(posX - 1, posY))) ? 3 : 0;
        int vLeft = (validLeft) ? 4 : 0;
        int vLMove = (vLBS > 0) ? vLBS : (vLBreeze > 0) ? vLBreeze : (vLStench > 0) ? vLStench : (vLeft > 0) ? vLeft : 0;
        // Iterating over all the Moves
        List < Integer > validatings = new ArrayList < Integer > (Arrays.asList(vUMove, vDMove, vRMove, vLMove));
        for (int i: validatings) {
            switch (i) {
                case 1:
                    breezeStenchActivated(current_env, posX, posY, xLoc, yLoc, aIAlgo);
                    break;
                case 2:
                    breezeActivated(aIAlgo);
                    break;
                case 3:
                    int stenchMore = brzFound(aIAlgo);
                    switch (stenchMore) {
                        case 1:
                            xCord = posX - current_env.getPlayerX();
                            yCord = posY - current_env.getPlayerY();
                            int storedxCord = aIAlgo.X_Finding;
                            int storedyCord = aIAlgo.Y_Finding;
                            aIAlgo.X_Finding = posX;
                            aIAlgo.Y_Finding = posY;
                            random = aIAlgo.select_dir(current_env.getPlayerX(), current_env.getPlayerY(), xCord, yCord, current_env);
                            aIAlgo.Shoot_the_arrow(random, current_env);
                            aIAlgo.X_Finding = storedxCord;
                            aIAlgo.Y_Finding = storedyCord;
                            return this.riskAnaylsis(xLoc, yLoc, posX, posY, current_env);
                    }
                    riskassumed += 0.00001;
                    break;
                case 4:
                    return 0;
                default:
                    break;
            }
        }
        return riskassumed;
    }
}