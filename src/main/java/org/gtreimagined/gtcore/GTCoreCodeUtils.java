package org.gtreimagined.gtcore;

import net.minecraft.core.Direction;

public class GTCoreCodeUtils {
    /** Array with length 17 and some overflow containing the Coordinates of Pixels from 0/16 to 16/16 */
    public static final float[] PX_P = {
            0.0000F, 0.0625F, 0.1250F, 0.1875F
            , 0.2500F, 0.3125F, 0.3750F, 0.4375F
            , 0.5000F, 0.5625F, 0.6250F, 0.6875F
            , 0.7500F, 0.8125F, 0.8750F, 0.9375F
            , 1.0000F, 1.0625F, 1.1250F, 1.1875F
            , 1.2500F, 1.3125F, 1.3750F, 1.4375F
            , 1.5000F, 1.5625F, 1.6250F, 1.6875F
            , 1.7500F, 1.8125F, 1.8750F, 1.9375F
            , 2.0000F};

    /** Array with length 17 and some overflow containing the Coordinates of Pixels from 16/16 to 0/16 */
    public static final float[] PX_N = {
            1.0000F, 0.9375F, 0.8750F, 0.8125F
            , 0.7500F, 0.6875F, 0.6250F, 0.5625F
            , 0.5000F, 0.4375F, 0.3750F, 0.3125F
            , 0.2500F, 0.1875F, 0.1250F, 0.0625F
            , 0.0000F,-0.0625F,-0.1250F,-0.1875F
            ,-0.2500F,-0.3125F,-0.3750F,-0.4375F
            ,-0.5000F,-0.5625F,-0.6250F,-0.6875F
            ,-0.7500F,-0.8125F,-0.8750F,-0.9375F
            ,-1.0000F};

    public static double[] getFacingCoordsClicked(Direction aSide, double aHitX, double aHitY, double aHitZ) {
        return switch (aSide) {
            case DOWN -> new double[]{Math.min(0.99, Math.max(0, aHitX)), Math.min(0.99, Math.max(0, 1 - aHitZ))};
            case UP -> new double[]{Math.min(0.99, Math.max(0, aHitX)), Math.min(0.99, Math.max(0, aHitZ))};
            case NORTH -> new double[]{Math.min(0.99, Math.max(0, 1 - aHitX)), Math.min(0.99, Math.max(0, 1 - aHitY))};
            case SOUTH -> new double[]{Math.min(0.99, Math.max(0, aHitX)), Math.min(0.99, Math.max(0, 1 - aHitY))};
            case WEST -> new double[]{Math.min(0.99, Math.max(0, aHitZ)), Math.min(0.99, Math.max(0, 1 - aHitY))};
            case EAST -> new double[]{Math.min(0.99, Math.max(0, 1 - aHitZ)), Math.min(0.99, Math.max(0, 1 - aHitY))};
        };
    }

    public static long bind_(long aMin, long aMax, long aBoundValue) {
        return Math.max(aMin, Math.min(aMax, aBoundValue));
    }
}
