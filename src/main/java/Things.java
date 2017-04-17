
import java.util.ArrayList;
import java.util.HashMap;

/*
 Класс обертки набора элементов
 */
public class Things
{
public HashMap<Integer, Thing> getThings()
{
	return things;
}

private HashMap<Integer, Thing> things = new HashMap<>(); //карта элементов
private int[][] relationships; //массив отношений
private ArrayList<ArrayList<Thing>> layers = new ArrayList<>(); //список слоев элементов согласно таблице отношений
private ArrayList<ArrayList<Thing>> criterionLayers = new ArrayList<>(); //список слоев элементов согласно сумме критериев
public boolean foundBigger=false;
private ArrayList<Thing> bestLayer = new ArrayList<>();

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

public void calculateRelationships() //расчет отношений
{
	//-1 - несравнимо
	//0 - меньше
	//1 - эквивалентно
	//2 - больше

	relationships = new int[things.size()][things.size()];
	for (int i : things.keySet())
		{
			for (int j : things.keySet())
			{
				relationships[i][j] = compare(things.get(i), things.get(j)); //заполнение массива
			}

		}
}

private int compare(Thing thing1, Thing thing2) //сравнение двух элементов
{
	boolean existsLess = false; //флаг "есть результат "меньше""
	boolean existsGreater = false; //флаг "есть результат "больше""
	boolean allAreEqual = true; //флаг "все результаты "равно""

	for (int i = 0; i < 5; ++i)
	{
		if (thing1.getCriterionSet()[i] < thing2.getCriterionSet()[i])
		{
			allAreEqual = false;
			existsLess = true;
		}
		if (thing1.getCriterionSet()[i] > thing2.getCriterionSet()[i])
		{
			allAreEqual = false;
			existsGreater = true;
		}
	}


	if (allAreEqual)
	{
		return 1;
	}

	if (existsGreater&&!existsLess)
	{
		return 2;
	}
	if (existsLess&&!existsGreater)
	{
		return 0;
	}

	return -1;
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

public void printPairs()
{
	StringBuilder output = new StringBuilder();
	output.append("R = {");
	for (int k = 0; k < things.size(); ++k)
	{
		for (int j = 0; j < things.size(); ++j)
		{
			if ((relationships[k][j]==0)||(relationships[k][j]==2))
				output.append("(x" + k + ", x" + j + "), ");
		}
		output.append("\n");
	}
	output.deleteCharAt(output.length()-1);
	output.deleteCharAt(output.length()-2);
	output.append("}\n");
	System.out.printf(output.toString());
}

private boolean isExcluded(Thing thing)
{

	for (ArrayList<Thing> layer2 : layers)
	{
		for (Thing thing1 : layer2)
		{
			if (thing1.equals(thing))
			{
				return true;
			}
		}
	}
	return false;
}

public void calculateLayers()
{
int divider=0; //разделитель между лучшими и худшими слоями
	int bigLayers=0;
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
				if (isExcluded(things.get(i)))
				{
					continue;
				}
				if ((relationships[i][j] == 0) && (!isExcluded(things.get(j))))
				{
					existsWorse = true;
				}
				if (((relationships[i][j] == 1) || (relationships[i][j] == 2)) && (!isExcluded(things.get(j))))
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

			for (int i = 0; i < relationships.length; ++i)
			{
				boolean existsNotBetter = false;

				for (int j = 0; j < relationships.length; ++j)
				{
					if (i == j)
					{
						continue;
					}
					if (isExcluded(things.get(i)))
					{
						continue;
					}
					if (relationships[i][j] != 2)
					{
						existsNotBetter = true;
					}

				}

				if ((!existsNotBetter) && (!isExcluded(things.get(i))))
				{
					layer.add(things.get(i));
				}

			}

			if (layer.size() == 0)
			{
				for (int i = 0; i < things.size(); ++i)
				{
					if (!isExcluded(things.get(i)))
					{
						layer.add(things.get(i));
					}
				}
				layers.add(divider, layer);
				break;
			}
			else
			{
				layers.add(layers.size()-bigLayers++, layer);
				foundBigger=true;
			}
		}
		else
		{
			layers.add(divider++, layer);
		}
	}
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
public void setLayers(ArrayList<ArrayList<Thing>> layers)
{
	this.layers=layers;
}



public ArrayList<ArrayList<Thing>> getCriterionLayers()
{
	return this.criterionLayers;
}
}
