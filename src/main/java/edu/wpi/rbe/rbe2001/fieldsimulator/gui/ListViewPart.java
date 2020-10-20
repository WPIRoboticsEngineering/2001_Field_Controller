package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

public class ListViewPart {
    private String Name;
    private int NumAvailable;
    private int Row;
    private int Col;
    public ListViewPart(String name, int numAvailable, int row, int col)
    {
        this.Name = name;
        this.NumAvailable = numAvailable;
        this.Row = row;
        this.Col = col;
    }
    public String getName(){
        return Name;
    }
    public int getNumAvailable(){
        return NumAvailable;
    }
    public int getRow(){
        return Row;
    }
    public int getCol(){
        return Col;
    }
}
