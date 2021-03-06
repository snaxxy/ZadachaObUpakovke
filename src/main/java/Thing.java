import java.util.Random;

/*
Класс элементов
 */
public class Thing
{

private static int IDs = 0; //счетчик идентификаторов элементов
final Random random = new Random();
private int weight;
private int volume;
private int ID; //идентификатор элемента
private int[] criterionSet = new int[5]; //массив критериев


//TODO: magic numbers
public Thing() //конструктор
{
	this.weight = random.nextInt(3) + 1;
	this.volume = random.nextInt(3) + 1;
	this.ID = IDs++;
	for (int i = 0; i < 5; ++i)
	{
		criterionSet[i] = random.nextInt(5) + 1;
	}
}

public int[] getCriterionSet()
{
	return this.criterionSet;
}

public int getWeight()
{
	return this.weight;
}

public int getVolume()
{
	return this.volume;
}

public int getID()
{
	return this.ID;
}

public int criterionSum() //подсчет суммы критериев для элемента
{
	int sum = 0;
	for (int criterion : criterionSet)
	{
		sum += criterion;
	}

	return sum;
}

public void resetIDs()
{
	IDs=0;
}

}


