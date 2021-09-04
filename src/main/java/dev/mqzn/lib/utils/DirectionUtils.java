package dev.mqzn.lib.utils;

import org.bukkit.block.BlockFace;

public final class DirectionUtils {


    private DirectionUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot access DirectionUtils class, it's a utility class !");
    }

    public static float directionToYaw(final BlockFace direction) {
        if (direction == null) {
            return 0.0f;
        }
        switch (direction) {
            case SOUTH_SOUTH_WEST: {
                return 22.5f;
            }
            case SOUTH_WEST: {
                return 45.0f;
            }
            case WEST_SOUTH_WEST: {
                return 67.5f;
            }
            case WEST: {
                return 90.0f;
            }
            case WEST_NORTH_WEST: {
                return 112.5f;
            }
            case NORTH_WEST: {
                return 135.0f;
            }
            case NORTH_NORTH_WEST: {
                return 157.5f;
            }
            case NORTH: {
                return 180.0f;
            }
            case NORTH_NORTH_EAST: {
                return -157.5f;
            }
            case NORTH_EAST: {
                return -135.0f;
            }
            case EAST_NORTH_EAST: {
                return -112.5f;
            }
            case EAST: {
                return -90.0f;
            }
            case EAST_SOUTH_EAST: {
                return -67.5f;
            }
            case SOUTH_EAST: {
                return -45.0f;
            }
            case SOUTH_SOUTH_EAST: {
                return -22.5f;
            }
            default: {
                return 0.0f;
            }
        }
    }

    public static BlockFace yawToDirection(float yaw) {

        // removing unusual bounds !
        while (yaw > 180.0f) {
            yaw -= 360.0f;
        }


        while (yaw <= -180.0f) {
            yaw += 360.0f;
        }


        if (yaw < -168.75) {
            return BlockFace.NORTH;
        }
        if (yaw < -146.25) {
            return BlockFace.NORTH_NORTH_EAST;
        }
        if (yaw < -123.75) {
            return BlockFace.NORTH_EAST;
        }
        if (yaw < -101.25) {
            return BlockFace.EAST_NORTH_EAST;
        }
        if (yaw < -78.75) {
            return BlockFace.EAST;
        }
        if (yaw < -56.25) {
            return BlockFace.EAST_SOUTH_EAST;
        }
        if (yaw < -33.75) {
            return BlockFace.SOUTH_EAST;
        }
        if (yaw < -11.25) {
            return BlockFace.SOUTH_SOUTH_EAST;
        }
        if (yaw < 11.25) {
            return BlockFace.SOUTH;
        }
        if (yaw < 33.75) {
            return BlockFace.SOUTH_SOUTH_WEST;
        }
        if (yaw < 56.25) {
            return BlockFace.SOUTH_WEST;
        }
        if (yaw < 78.75) {
            return BlockFace.WEST_SOUTH_WEST;
        }
        if (yaw < 101.25) {
            return BlockFace.WEST;
        }
        if (yaw < 123.75) {
            return BlockFace.WEST_NORTH_WEST;
        }
        if (yaw < 146.25) {
            return BlockFace.NORTH_WEST;
        }
        if (yaw < 168.75) {
            return BlockFace.NORTH_NORTH_WEST;
        }
        return BlockFace.NORTH;
    }


}

