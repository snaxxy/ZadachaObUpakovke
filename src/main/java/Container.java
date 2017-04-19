import java.util.ArrayList;

/*
 Класс контейнеров
 */
public class Container
{

private int weightCapacity; //предел веса
private int volumeCapacity; //предел объема
private ArrayList<Thing> things = new ArrayList<>(); //список элементов в контейнере


public Container(int weightCapacity, int volumeCapacity)
{
	this.weightCapacity = weightCapacity;
	this.volumeCapacity = volumeCapacity;
}

public void putThing(Thing thing)
{
	things.add(thing);
} //упаковка элемента в контейнер

public int freeVolume()
{
	return volumeCapacity - getTotalVolume();
} //доступный объем

public int freeWeight()
{
	return weightCapacity - getTotalWeight();
} //доступный вес

public int getWeightCapacity()
{
	return weightCapacity;
}

public int getVolumeCapacity()
{
	return volumeCapacity;
}

public int getTotalWeight() //занятый вес
{
	int totalWeight = 0;
	for (Thing thing : things)
	{
		totalWeight += thing.getWeight();
	}
	return totalWeight;
}

public int getTotalVolume() //занятый объем
{
	int totalVolume = 0;
	for (Thing thing : things)
	{
		totalVolume += thing.getVolume();
	}
	return totalVolume;
}

public void printContainer() //вывод содержимого
{

	for (Thing thing : things)
	{
		System.out.printf("%2s ", thing.getID());
	}
}

}
