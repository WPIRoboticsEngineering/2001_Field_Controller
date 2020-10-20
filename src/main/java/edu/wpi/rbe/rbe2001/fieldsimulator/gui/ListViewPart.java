package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

public class ListViewPart {
    private String Name;
    private long NumAvailable;
    private long Row;
    private long Col;
    public ListViewPart(String name, long numAvailable, long row, long col)
    {
        this.Name = name;
        this.NumAvailable = numAvailable;
        this.Row = row;
        this.Col = col;
    }
    public String getName(){
        return Name;
    }
    public long getNumAvailable(){
        return NumAvailable;
    }
    public long getRow(){
        return Row;
    }
    public long getCol(){
        return Col;
    }
}
