package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString(){
        return switch(this) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };

    }


    public MapDirection next(){
        return values()[(this.ordinal()+1) % 8];
    }

    public MapDirection previous(){
        if(this.ordinal() -1 < 0){
            return values()[7];
        }
        return values()[(this.ordinal()-1)%8];
    }

    public MapDirection changeOrientation(int x){
        return values()[(this.ordinal()+x) % 8];
    }

    public MapDirection opposite() {return values()[(this.ordinal() + 4) % 8];}

    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH -> new Vector2d(0,1);
            case NORTHEAST -> new Vector2d(1,1);
            case EAST -> new Vector2d(1,0);
            case SOUTHEAST -> new Vector2d(1,-1);
            case SOUTH -> new Vector2d(0,-1);
            case SOUTHWEST -> new Vector2d(-1,-1);
            case WEST -> new Vector2d(-1,0);
            case NORTHWEST -> new Vector2d(-1,1);
        };

    }
}
