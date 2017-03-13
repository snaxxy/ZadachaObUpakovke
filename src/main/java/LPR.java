import java.util.Random;
import java.util.Scanner;

/**
 * Класс лица, принимающего решения
 */
public class LPR
{
//0 - '>'
//1 - '<'
//2 - '='
//3 - 'несравнимо'
public LPR()
{

}

public int[][] decide(int size)
{
	int[][] R = new int[size][size];

	for (int i = 0; i < size; ++i)
	{
		for (int j = i; j < size; ++j)
		{
			if (i == j)
			{
				R[i][j] = 2;
				continue;
			}

			Random random = new Random();

			int randomResult = random.nextInt(30);

			if (randomResult == 0)
			{
				R[i][j] = 0;
				R[j][i] = 1;
				continue;
			}

			if (randomResult == 1)
			{
				R[i][j] = 1;
				R[j][i] = 0;
				continue;
			}

			if (randomResult <= 4)
			{
				R[i][j] = 2;
				R[j][i] = 2;
				continue;
			}

			{
				R[i][j] = 3;
				R[j][i] = 3;
			}

		}
	}
	return R;
}
}
