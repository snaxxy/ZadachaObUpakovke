import java.util.ArrayList;
import java.util.List;

/*
Класс логики программы (т.к. Main static)
 */
public class Logic
{

final private int NUMBER_OF_ITEMS = 20; //число объектов
final private int NUMBER_OF_CONTAINERS = 5; //число контейнеров


public void executeLogic()
{


	while (true)
	{

		Things things = new Things(NUMBER_OF_ITEMS); //создание набора элементов
		//things.calculateR();
		things.calculateRelationships();
		things.calculateLayers();
		Containers containers1 = new Containers(NUMBER_OF_CONTAINERS); //создание набора контейнеров
		containers1.packContainer(things);
		things.calculateCriterionLayers();
		Containers containers2 = new Containers(NUMBER_OF_CONTAINERS); //создание набора контейнеров
		containers2.packContainerCriterion(things);

		boolean containerBetter = (containers1.getCriterionSum()>containers2.getCriterionSum());

		boolean smallLayers=true;
		int layersCount=0;
		boolean emptyLayer=false;

		for (ArrayList<Thing> layer: things.getLayers()
			 )
		{
if (layer.size()>6)
	smallLayers=false;
layersCount++;
if (layer.size()==0)
{
	emptyLayer=true;
}
		}

		if (containerBetter&&things.foundBigger&&smallLayers&&layersCount>4&&!emptyLayer)
		{
			things.printCriterionTable(); //вывод таблицы критериев, веса и объема элементов
			//things.printComparisons();
			//things.printR();
			things.printPairs();
			things.printRelationships();
			things.printLayers();
			containers1.printContainers(); //вывод содержимого контейнеров
			System.out.println(containers1.getCriterionSum());
			containers2.printContainers(); //вывод содержимого контейнеров
			System.out.println(containers2.getCriterionSum());
			break;
		}
		else
		{
			new Thing().resetIDs();
		}

	}
}

public static String convert(long milliseconds)
{
	long seconds = milliseconds / 1000;
	long s = seconds % 60;
	long m = (seconds / 60) % 60;
	long h = (seconds / (60 * 60)) % 24;
	return String.format("%d:%02d:%02d", h, m, s);
}

}
