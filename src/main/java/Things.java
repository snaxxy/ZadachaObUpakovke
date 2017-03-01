import java.util.ArrayList;
import java.util.HashMap;

/*
 Класс обертки набора элементов
 */
public class Things {
    private HashMap<Integer, Thing> things = new HashMap<Integer, Thing>();
    private int[][] relationships; //массив отношений
    private ArrayList<ArrayList<Thing>> layers = new ArrayList<ArrayList<Thing>>();
    private ArrayList<ArrayList<Thing>> criterionLayers = new ArrayList<ArrayList<Thing>>();

    public Things(int NUMBER_OF_THINGS) //конструктор
    {
        for (int i = 0; i < NUMBER_OF_THINGS; ++i) {
            Thing thing = new Thing();
            things.put(thing.getID(), thing);
        }
    }


    public void printCriterionTable() //вывод таблицы критериев
    {
        System.out.println("        ТАБЛИЦА КРИТЕРИЕВ");
        System.out.println("---------------------------------");
        System.out.println("     A   B   C   D   E   W   V");

        for (int i = 0; i < 20; ++i) {

            System.out.printf("%2s   ", i + 1);
            for (int j = 0; j < 5; ++j) {
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
        try {
            for (int thing1Key : things.keySet()
                    ) {
                for (int thing2Key : things.keySet()
                        ) {
                    relationships[thing1Key][thing2Key] = compare(things.get(thing1Key), things.get(thing2Key)); //заполнение массива
                }

            }
        } catch (Exception e) {
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

        for (int i = 0; i < 5; ++i) {
            if (thing1.getCriterionSet()[i] < thing2.getCriterionSet()[i]) {
                lesser++;
                allNotLess = false;
                allAreEqual = false;
            }
            if (thing1.getCriterionSet()[i] > thing2.getCriterionSet()[i]) {
                greater++;
                allAreEqual = false;
                existsGreater = true;
            }
        }
        if (((allNotLess) && (existsGreater)) || (greater > lesser)) {
            return 2;
        }

        if ((allAreEqual) || (greater == lesser)) {
            return 1; //выводятся удвоенные результаты чтобы избежать плавающей точки
        }

        return 0;
    }

    public void printRelationships() {
        System.out.println("ТАБЛИЦА ОТНОШЕНИЙ");
        for (int i = 0; i <= things.size(); ++i) {
            System.out.printf("----");
        }
        System.out.println();
        System.out.printf("%4s", "");
        for (int i = 0; i < things.size(); ++i) {
            System.out.printf("%4s", i);
        }
        System.out.println();
        for (int k = 0; k < things.size(); ++k) {
            System.out.printf("%4s", k);
            for (int j = 0; j < things.size(); ++j) {
                System.out.printf("%4s", relationships[k][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void calculateLayers() {
        int maxSum = calculateLayerSum();

        for (int sum = maxSum; sum >= 0; --sum) {
            boolean existsSum = false;
            for (int ID : things.keySet()
                    ) {
                if (things.get(ID).getLayerSum() == sum) {
                    existsSum = true;
                }
            }

            if (existsSum) {
                ArrayList<Thing> layer = new ArrayList<>();
                for (int ID : things.keySet()) {
                    if (things.get(ID).getLayerSum() == sum) {
                        layer.add(things.get(ID));
                    }
                }
                layers.add(layer);
            }
        }
    }

    private int calculateLayerSum() {
        int maxSum = 0;
        for (int i = 0; i < things.size(); ++i) {

            int layerSum = 0;

            for (int j = 0; j < things.size(); ++j) {
                layerSum += relationships[i][j];
            }

            if (layerSum > maxSum) {
                maxSum = layerSum;
            }

            things.get(i).setLayerSum(layerSum);

        }
        return maxSum;
    }

    /*public void calculateLayers()
    {
        int[][] relationships;
        relationships = this.relationships;


        layersLoop:
        while (true)
        {
            int sum = 0;
            int minSum = Integer.MAX_VALUE;
            ArrayList<Thing> layer = new ArrayList<Thing>();
            ArrayList<Integer> layerIndexes = new ArrayList<Integer>();
            for (int i=0; i<things.size(); ++i)
            {
                int max = -1;
                for (int j=0; j<things.size(); ++j)
                {
                    if (relationships[i][j] != -1)
                    {
                        sum += relationships[i][j];
                    }
                    if (relationships[i][j]> max)
                    {
                        max = relationships[i][j];
                    }
                }

                if (max==-1)
                {
                    continue;
                }

                if (sum<minSum)
                {
                    minSum = sum;
                    sum=0;

                    layerIndexes.clear();
                    layerIndexes.add(i);
                }
                else if(sum==minSum)
                {
                    layerIndexes.add(i);
                }
            }
            for (int i=0; i<layerIndexes.size(); ++i)
            {
                layer.add(things.get(layerIndexes.get(i)));

                for (int j=0; j<things.size();++j)
                {
                    relationships[layerIndexes.get(i)][j] = -1;
                    relationships[j][layerIndexes.get(i)] = -1;
                }
            }

            for (int i=0; i<things.size(); ++i)
            {
                for (int j = 0; j < things.size(); ++j)
                {
                    if (relationships[i][j]!=-1)
                    {
                        layers.add(layer);
                        continue layersLoop;
                    }
                }
            }
            layers.add(layer);
            break;
        }
        return;
    }*/

    public void calculateCriterionLayers() {
        int maxSum = 0;

        for (int key : things.keySet()
                ) {
            Thing thing = things.get(key);
            if (thing.criterionSum() > maxSum) {
                maxSum = thing.criterionSum();
            }
        }

        for (int i = maxSum; i >= 0; --i) {
            boolean exists = false;
            for (int key : things.keySet()
                    ) {
                Thing thing = things.get(key);
                if (thing.criterionSum() == i) {
                    exists = true;
                }
            }
            if (exists) {
                ArrayList<Thing> layer = new ArrayList<Thing>();
                for (int key : things.keySet()) {
                    Thing thing = things.get(key);
                    if (thing.criterionSum() == i) {
                        layer.add(thing);
                    }
                }
                criterionLayers.add(layer);
            }
        }
    }

    public void printLayers() {
        System.out.println("СЛОИ");
        int i = 0;
        for (ArrayList<Thing> layer : layers
                ) {
            System.out.printf("Слой " + i++ + ": ");
            for (Thing thing : layer
                    ) {
                System.out.printf(thing.getID() + " (" + thing.getLayerSum() + ") ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public ArrayList<ArrayList<Thing>> getLayers() {
        return this.layers;
    }

    public ArrayList<ArrayList<Thing>> getCriterionLayers()
    {
        return this.criterionLayers;
    }
}
