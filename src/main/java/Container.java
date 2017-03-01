import java.util.ArrayList;

/*
 Класс контейнеров
 */
public class Container {

    private int weightCapacity;
    private int volumeCapacity;
    private ArrayList<Thing> things = new ArrayList<Thing>();


    public Container(int weightCapacity, int volumeCapacity)
    {
        this.weightCapacity=weightCapacity;
        this.volumeCapacity=volumeCapacity;
    }

    public void putThing(Thing thing)
    {
        things.add(thing);
    }

    public int freeVolume()
    {

        return volumeCapacity-getTotalVolume();
    }

    public int freeWeight()
    {
        return weightCapacity-getTotalWeight();
    }

    public int getWeightCapacity()
    {
        return weightCapacity;
    }

    public int getVolumeCapacity()
    {
        return volumeCapacity;
    }

    public int getTotalWeight()
    {
        int totalWeight=0;
        for (Thing thing: things
                ) {
            totalWeight+=thing.getWeight();
        }
        return totalWeight;
    }

    public int getTotalVolume()
    {
        int totalVolume=0;
        for (Thing thing: things
                ) {
            totalVolume+=thing.getVolume();
        }
        return totalVolume;
    }

    public void printContainer()
    {

        for (Thing thing: things
             ) {
            System.out.printf("%2s ", thing.getID());
        }
    }

}
