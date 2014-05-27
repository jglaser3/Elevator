/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Display;

import building.Building;
import java.util.ArrayList;
import person.Person;

/**
 *
 * @author raphaelshejnberg
 */
public class Stats extends Display {
    
    Building building;
    
    ArrayList<Person> people;
    
    String line;
    
    int width = 60;
    public Stats(Building b, ArrayList<Person> people) {
        this.building = b;
        this.people = people;
        buildLine();
    }
    private void buildLine() {
        String out = "";
        for(int i = 0; i < width; i++)
            out += "_";
    }
    private void printLine() {
        System.out.println(this.line);
    }
    
    public void printTimeInElevator() {
        String output;
        int numFloors = building.getNumFloors();
        //prints tops set of lines
        System.out.print("_______");
        for(int i = 1; i < numFloors; i++)
        {
            if(i == (numFloors / 2))
                System.out.print("  To Floor ");
            else 
                System.out.print("___________");
         }
        System.out.print("\n");
    
        //prints top info
        System.out.print("|     |  Floor  ");
        for(int i = 1; i < numFloors; i++)
        {
            if(i<=9)
                System.out.print(String.format("|   %d     ", i));
            else if(i>9)
                System.out.print(String.format("|    %d   ",i));    
        }
        System.out.print("|\n");
    
        //prints top/bottom set of lines
        System.out.print("_______");
        for(int i = 1; i < numFloors; i++)
            System.out.print("___________");
        System.out.print("\n");
        String side= "From Floor";
        
        //prints data
        for(int floor = 1; floor < numFloors; floor++)
        {
            if(floor > side.length() && floor <= 9)
                System.out.print("|     | Floor "+(floor)+" ");
            else if(floor>side.length() && floor>9)
                System.out.print("|     | Floor "+(floor));
            else if(floor<=9)
                System.out.print(String.format("|  %s  | Floor "+floor+ " ", side.charAt(floor-1))); 
            else
                System.out.print(String.format("|  %s  | Floor "+floor, side.charAt(floor-1)));
            
            for(int target = 1; target < building.getNumFloors(); target++)
            {
                long sum=0;
                long count=0;
                long average;
            
                for(Person p: this.people)
                {
                    if(p.getFloor() == floor && p.getDestFloor() == target)
                    {
                        sum += p.getTimeInElevator();
                        count++;
                    }
                }
                if(count != 0)
                    average = (sum / count) / 1000;
                else
                    average = 0;
                if(floor != target)
                    System.out.print(String.format("|    %d    ", average));
                else
                    System.out.print("|   X     ");
            }
        System.out.print("|\n");
    }
        
    //prints bottom set of lines
    System.out.print("_______");
    for(int i = 1; i < numFloors; i++)
        System.out.println("___________");
    
    }
    public void printPeople() {
        printLine();
        System.out.println("| Person | Wait Time | Start Floor | Destination | Ride Time |");
        printLine();
        for(int i = 0; i < this.people.size(); i++)
        {
            Person p = this.people.get(i);
            String output= String.format("|   %d    | %d seconds |     %d      |      %d     | %d seconds |", 
                    i, p.getWaitTime()/1000, p.getFloor(), p.getDestFloor(), p.getTimeInElevator() / 1000);
            System.out.println(output);
        }
        printLine();
    }
    public void printWaitTimes() {
        printLine();
        System.out.println("| Floor | Average Time | Min Wait Time | Max Wait Time |");
        printLine();
        for(int floor = 1; floor < building.getNumFloors(); floor++){
            long min = 0, max = 0, average = 0, sum = 0, count = 0;
            boolean start = false;
            for(int i = 0; i < this.people.size(); i++)
            {
                if(this.people.get(i).getFloor() == floor)
                {
                    long currentWait = this.people.get(i).getWaitTime();
                    if( start == false)
                    {
                        start = true;
                        min = max =currentWait;
                    }
                    if(min > currentWait)
                        min = currentWait;
                    if(max < currentWait)
                        max = currentWait;
                    sum += currentWait;
                    count++;
                }
            }
            if(count != 0)
                average = sum / count;
            
            System.out.printf("|  %d   |   %d seconds  |  %d  seconds   |    %d seconds   |", floor, (average / 1000), (min / 1000), (max / 1000));
        }
        printLine();
    }
  
}
