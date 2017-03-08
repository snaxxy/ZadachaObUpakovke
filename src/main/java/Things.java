import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
 Класс обертки набора элементов
 */
public class Things
{
private HashMap<Integer, Thing> things = new HashMap<>(); //карта элементов
private int[][] relationships; //массив отношений
private ArrayList<ArrayList<Thing>> layers = new ArrayList<>(); //список слоев элементов согласно таблице отношений
private ArrayList<ArrayList<Thing>> criterionLayers = new ArrayList<>(); //список слоев элементов согласно сумме критериев
private ArrayList<Thing>[][] xSquared;
private int[][] R;

public Things(int NUMBER_OF_THINGS) //конструктор
{
	for (int i = 0; i < NUMBER_OF_THINGS; ++i)
	{
		Thing thing = new Thing();
		things.put(thing.getID(), thing);
	}
}


public void printCriterionTable() //вывод таблицы критериев
{
	System.out.println("        ТАБЛИЦА КРИТЕРИЕВ");
	System.out.println("---------------------------------");
	System.out.println("     A   B   C   D   E   W   V");

	for (int i = 0; i < 20; ++i)
	{

		System.out.printf("%2s   ", i);
		for (int j = 0; j < 5; ++j)
		{
			System.out.printf(this.things.get(i).getCriterionSet()[j] + "   ");
		}
		System.out.printf(this.things.get(i).getWeight() + "   " + this.things.get(i).getVolume() + "\n");
	}


	System.out.println("-------------------------------");
	System.out.println();
}

public boolean calculateRelationships() //расчет отношений
{
	relationships = new int[things.size()][things.size()];
	try
	{
		for (int i : things.keySet())
		{
			for (int j : things.keySet())
			{
				int comparison = compare(things.get(i), things.get(j)); //заполнение массива
				if
						(
							(
								(
								(R[i][j] == 0)
								||
								(R[i][j] == 2)
								)

								&&

								(
								comparison==2
								)
							)

							||

							(
								(
									R[i][j] == 1
									)
									||
									(
									R[j][i] == 2
								)
								&&
								(
								comparison==0
								)
							)
						)
					relationships[i][j] = comparison;

				else if (R[i][j] == 0)
					relationships[i][j] = 2;
				else if (R[i][j] == 1)
					relationships[i][j] = 0;
				else if (R[i][j] == 3)
					relationships[i][j] = -1;
				else
					relationships[i][j] = 1;
			}

		}
	} catch (Exception e)
	{
		return false;
	}
	return true;
}

private int compare(Thing thing1, Thing thing2) //сравнение двух элементов
{
	int lesser = 0; //счетчик результатов "меньше"
	int greater = 0; //счетчик результатов "больше"
	boolean allNotLess = true; //флаг "нет результатов "меньше""
	boolean existsGreater = false; //флаг "есть результат "больше""
	boolean allAreEqual = true; //флаг "все результаты "равно""

	for (int i = 0; i < 5; ++i)
	{
		if (thing1.getCriterionSet()[i] < thing2.getCriterionSet()[i])
		{
			lesser++;
			allNotLess = false;
			allAreEqual = false;
		}
		if (thing1.getCriterionSet()[i] > thing2.getCriterionSet()[i])
		{
			greater++;
			allAreEqual = false;
			existsGreater = true;
		}
	}
	if (((allNotLess) && (existsGreater)) || (greater > lesser))
	{
		return 2;
	}

	if ((allAreEqual) || (greater == lesser))
	{
		return 1; //выводятся удвоенные результаты чтобы избежать плавающей точки
	}

	return 0;
}

public void printRelationships()
{
	System.out.println("ТАБЛИЦА ОТНОШЕНИЙ");
	for (int i = 0; i <= things.size(); ++i)
	{
		System.out.printf("----");
	}
	System.out.println();
	System.out.printf("%4s", "");
	for (int i = 0; i < things.size(); ++i)
	{
		System.out.printf("%4s", i);
	}
	System.out.println();
	for (int k = 0; k < things.size(); ++k)
	{
		System.out.printf("%4s", k);
		for (int j = 0; j < things.size(); ++j)
		{
			System.out.printf("%4s", relationships[k][j]);
		}
		System.out.println();
	}
	System.out.println();
}

/*public void calculateLayers() //подсчет слоев согласно таблице отношений
{
	int maxSum = calculateLayerSum(); //поиск максимальной суммы строки в таблице

	for (int sum = maxSum; sum >= 0; --sum) //проход по убыванию значений суммы с максимального
	{
		boolean existsSum = false; //флаг существования как минимум одного элемента с текущей суммой
		for (int ID : things.keySet())
		{
			if (things.get(ID).getLayerSum() == sum)
			{
				existsSum = true;
			}
		}

		if (existsSum) //в случае существования проходим карту еще раз, занося соответствующие значения в новый слой
		{
			ArrayList<Thing> layer = new ArrayList<>(); //новый слой
			for (int ID : things.keySet())
			{
				if (things.get(ID).getLayerSum() == sum)
				{
					layer.add(things.get(ID));
				}
			}
			layers.add(layer); //вносим новый слой в список слоев
		}
	}
}*/

public void calculateLayers()
{
//TODO
}

public void calculateR()
{
	R = new int[things.size()][things.size()];
	//0 - '>'
	//1 - '<'
	//2 - '='
	//3 - 'несравнимо'

	LPR lpr = new LPR();
	R = lpr.decide(things.size());
}


private int calculateLayerSum() //подсчет суммы слоя для таблицы отношений
{
	int maxSum = 0;
	for (int i = 0; i < things.size(); ++i)
	{

		int layerSum = 0;

		for (int j = 0; j < things.size(); ++j)
		{
			layerSum += relationships[i][j];
		}

		if (layerSum > maxSum)
		{
			maxSum = layerSum;
		}

		things.get(i).setLayerSum(layerSum);

	}
	return maxSum;
}

public void calculateCriterionLayers() //подсчет слоев для суммы критериев
{
	int maxSum = 0;

	for (int key : things.keySet()) //поиск максимальной суммы критериев
	{
		Thing thing = things.get(key);
		if (thing.criterionSum() > maxSum)
		{
			maxSum = thing.criterionSum();
		}
	}

	for (int i = maxSum; i >= 0; --i) //проход в убывающем порядке начиная с максимальной суммы
	{
		boolean exists = false; //флаг существования как минимум одного элемента с текущей суммой
		for (int key : things.keySet())
		{
			Thing thing = things.get(key);
			if (thing.criterionSum() == i)
			{
				exists = true;
			}
		}
		if (exists) //если есть элемент
		{
			ArrayList<Thing> layer = new ArrayList<>(); //создаем новый слой
			for (int key : things.keySet()) //добавляем элементы в слой
			{
				Thing thing = things.get(key);
				if (thing.criterionSum() == i)
				{
					layer.add(thing);
				}
			}
			criterionLayers.add(layer); //добавляем слой в список слоев
		}
	}
}

public void printLayers()
{
	System.out.println("СЛОИ");
	int i = 0;
	for (ArrayList<Thing> layer : layers)
	{
		System.out.printf("Слой " + i++ + ": ");
		for (Thing thing : layer)
		{
			System.out.printf(thing.getID() + " (" + thing.getLayerSum() + ") ");
		}
		System.out.println();
	}
	System.out.println();
}

public ArrayList<ArrayList<Thing>> getLayers()
{
	return this.layers;
}

public ArrayList<ArrayList<Thing>> getCriterionLayers()
{
	return this.criterionLayers;
}
}
