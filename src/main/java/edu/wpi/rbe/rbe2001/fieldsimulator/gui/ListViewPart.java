package edu.wpi.rbe.rbe2001.fieldsimulator.gui;

public class ListViewPart {
    private String Name;
    private long NumAvailable;
    private long Row;
    private long Col;
    private boolean returnRequired;
    public ListViewPart(String name, long numAvailable, long row, long col, boolean returnRequired)
    {
        this.Name = name;
        this.NumAvailable = numAvailable;
        this.Row = row;
        this.Col = col;
        this.returnRequired = returnRequired;
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
    public boolean getreturnRequired(){return returnRequired;}
}
