
/*
Класс логики программы (т.к. Main static)
 */
public class Logic {

    final private int NUMBER_OF_ITEMS=20; //число объектов
    final private int NUMBER_OF_CONTAINERS=5; //число контейнеров



    public void executeLogic()
    {
        Things things = new Things(NUMBER_OF_ITEMS); //создание набора элементов
        things.printCriterionTable(); //вывод таблицы критериев, веса и объема элементов

        if(!things.calculateRelationships())//расчет таблицы отношений
        {
            System.err.println("CALCULATION ERROR");
        };
        things.printRelationships(); //вывод таблицы отношений

        things.calculateLayers();
        //things.printLayers();
        Containers containers1 = new Containers(NUMBER_OF_CONTAINERS); //создание набора контейнеров
        containers1.packContainer(things);
        containers1.printContainers(); //вывод содержимого контейнеров

        things.calculateCriterionLayers();
        Containers containers2 = new Containers(NUMBER_OF_CONTAINERS); //создание набора контейнеров
        containers2.packContainerCriterion(things);
        containers2.printContainers(); //вывод содержимого контейнеров

        return;
    }

}
