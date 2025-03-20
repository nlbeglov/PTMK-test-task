package org.example;

import static org.example.models.CreateTable.createTableSQL;
import static org.example.models.DataGenerator.generateData;
import static org.example.models.FilterSimple.startSimpleFilter;
import static org.example.models.InsertRow.insertRowSQL;
import static org.example.models.SelectAll.selectAge;


public class App 
{
    public static void main( String[] args )
    {
        int param = Integer.parseInt(args[0]);
        switch (param) {
            case 1: {
                createTableSQL();
                break;
            }
            case 2: {
                insertRowSQL(args);
                break;
            }
            case 3: {
                selectAge();
                break;
            }
            case 4: {
                generateData();
                break;
            }
            case 5: {
                startSimpleFilter();
                break;
            }
        }
    }
}
