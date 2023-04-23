package com.example.shift;

public class Filter {

    boolean fastFood;
    boolean cafe;
    boolean warehouse;
    boolean transportation;
    double distance;
    public Filter() {
        this.fastFood = true;
        this.cafe = true;
        this.warehouse = true;
        this.transportation = true;
        this.distance = 10;
    }

    public boolean isIn(int type) {
        switch (type) {
            case(1) : {
                return this.fastFood;
            }
            case(2) : {
                return this.cafe;
            }
            case(3) : {
                return this.warehouse;
            }
            case(4) : {
                return this.transportation;
            }
        }
        return false;
    }

    public double getDistance() {
        return this.distance;
    }

    public boolean getFastFood() {
        return this.fastFood;
    }

    public boolean getCafe() {
        return this.cafe;
    }
    public boolean getWarehouse() {
        return this.warehouse;
    }
    public boolean getTransportation() {
        return this.transportation;
    }
}
