import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	//-1 - несравнимо
	//0 - меньше
	//1 - эквивалентно
	//2 - больше
	relationships = new int[things.size()][things.size()];
	try
	{
		for (int i : things.keySet())
		{
			for (int j : things.keySet())
			{
				int comparison = compare(things.get(i), things.get(j)); //заполнение массива
				if ((((R[i][j] == 0) || (R[i][j] == 2))

						&&

						(comparison == 2))

						||

						((R[i][j] == 1) || (R[j][i] == 2) && (comparison == 0)))
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
			char out = 'E';
			switch (relationships[k][j])
			{
				case -1:
					out = '#';
					break;
				case 0:
					out = '<';
					break;
				case 1:
					out = '=';
					break;
				case 2:
					out = '>';
					break;
			}
			System.out.printf("%4s", out);
		}
		System.out.println();
	}
	System.out.println();
}

public void printR()
{
	System.out.println("ТАБЛИЦА СРАВНЕНИЙ (R)");
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
			char out = 'E';
			switch (R[k][j])
			{
				case 0:
					out = '>';
					break;
				case 1:
					out = '<';
					break;
				case 2:
					out = '=';
					break;
				case 3:
					out = '#';
					break;
			}

			System.out.printf("%4s", out);
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

private boolean isExcluded(ArrayList<Thing> layer, Thing thing)
{
	for (Thing exclude : layer)
	{
		if (exclude.equals(thing))
		{
			return true;
		}
	}
	for (ArrayList<Thing> layer2 : layers)
	{
		for (Thing thing1 : layer2)
		{
			if (thing1.equals(thing))
				return true;
		}
	}
	return false;
}

public void calculateLayers()
{
	while (true)
	{
		ArrayList<Thing> layer = new ArrayList<>();
		for (int i = 0; i < relationships.length; ++i)
		{
			boolean existsWorse = false;
			boolean existsBetter = false;


			for (int j = 0; j < relationships.length; ++j)
			{
				if (i == j)
				{
					continue;
				}
				if (isExcluded(layer, things.get(i)))
				{
					continue;
				}
				if ((relationships[i][j] == 0) && (!isExcluded(layer, things.get(j))))
				{
					existsWorse = true;
				}
				if (((relationships[i][j] == 1) || (relationships[i][j] == 2)) && (!isExcluded(layer, things.get(j))))
				{
					existsBetter = true;
				}


			}


			if (existsWorse && !existsBetter)
			{
				layer.add(things.get(i));
			}
		}
		if (layer.size() == 0)
		{
			for (int i = 0; i < things.size(); ++i)
			{
				if (!isExcluded(layer, things.get(i)))
				{
					layer.add(things.get(i));
				}
			}
			layers.add(layer);
			break;
		}
		layers.add(layer);
	}
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
			System.out.printf(thing.getID() + " ");
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
