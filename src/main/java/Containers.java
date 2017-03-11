import java.util.ArrayList;
import java.util.List;

/*
Класс обертки набора контейнеров
 */
public class Containers
{

private List<Container> containers = new ArrayList<>(); //список контейнеров

public Containers(int NUMBER_OF_CONTAINERS)
{
	for (int i = 0; i < NUMBER_OF_CONTAINERS; ++i)
	{
		containers.add(new Container(5, 7));
	}
}

public void packContainer(Things things) //упаковка контейнеров согласно таблице отношений
{
	for (int i = things.getLayers().size()-1; i >= 0; --i)
	{
		ArrayList<Thing> layer = things.getLayers().get(i);
		for (Thing thing : layer)
		{

			tryPacking(thing);

		}
}
}



public void packContainerCriterion(Things things) //упаковка контейнеров согласно сумме критериев
{
	for (ArrayList<Thing> layer : things.getCriterionLayers())
	{

		for (Thing thing : layer)
		{

			tryPacking(thing);

		}

	}
}

public void tryPacking(Thing thing) //поиск контейнера для упаковки элемента
{
	for (Container container : containers)
	{

		if ((thing.getWeight() <= container.freeWeight()) && (thing.getVolume() <= container.freeVolume()))
		{
			container.putThing(thing);
			return;
		}

	}
}


public void printContainers() //вывод содержимого
{
	System.out.println("КОНТЕЙНЕРЫ");
	int i = 1;
	for (Container container : containers)
	{
		System.out.printf("Container " + i++ + " [V(" + container.getTotalVolume() + "/" + container.getVolumeCapacity() + ") W(" + container.getTotalWeight() + "/" + container.getWeightCapacity() + ")] : ");
		container.printContainer();
		System.out.println();
	}
	System.out.println();
}
}
