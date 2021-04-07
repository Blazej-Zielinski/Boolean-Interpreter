package repo;

/**
 * Representation of a single record in local repository
 */
public class Record implements Comparable<Record> {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;
    private final int h;

    public Record(int[] values) {
        this.a = values[0];
        this.b = values[1];
        this.c = values[2];
        this.d = values[3];
        this.e = values[4];
        this.f = values[5];
        this.g = values[6];
        this.h = values[7];
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

    public int getE() {
        return e;
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int[] getValues() {
        return new int[]{
                getA(),
                getB(),
                getC(),
                getD(),
                getE(),
                getF(),
                getG(),
                getH()
        };
    }

    @Override
    public String toString() {
        return "Record{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                '}';
    }


    @Override
    public int compareTo(Record r) {
        int[] thisValues = getValues();
        int[] rValues = r.getValues();

        if (thisValues[0] > rValues[0]) {
            return 1;
        } else if (thisValues[0] == rValues[0]) {
            return compareTo(thisValues,rValues,1);
        }

        return -1;
    }

    private int compareTo(int[] thisValues, int[] rValues, int index){
        if(index == thisValues.length){
            return 0;
        }

        if (thisValues[index] > rValues[index]) {
            return 1;
        } else if (thisValues[index] == rValues[index]) {
            return compareTo(thisValues,rValues,index + 1);
        }

        return -1;
    }
}
