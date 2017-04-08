import java.util.ArrayList;

/*
Класс логики программы (т.к. Main static)
 */
public class Logic
{

final private int NUMBER_OF_ITEMS = 20; //число объектов
final private int NUMBER_OF_CONTAINERS = 5; //число контейнеров


public void executeLogic()
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
