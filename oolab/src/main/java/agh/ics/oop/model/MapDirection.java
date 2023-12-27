package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public String toString(MapDirection input) {
        switch (input) {
            case NORTH -> {return "Północ";}
            case SOUTH -> {return "Południe";}
            case EAST -> {return "Wschód";}
            case WEST -> {return "Zachód";}
            default -> {return "";}
        }
    }

    public static MapDirection next(MapDirection input) {
        return MapDirection.values()[((input.ordinal() + 1) % 4)];
    }

    public static MapDirection previous(MapDirection input) {
        return MapDirection.values()[((input.ordinal() + 3) % 4)];
    }

    public static Vector2d unitVector(MapDirection input) {
        int a = 0;
        int b = 1;
        for (int i = 0; i < input.ordinal(); i++) {
            int temp = a;
            a = b;
            b = -temp;
        }
        return new Vector2d(a,b);
    }



}