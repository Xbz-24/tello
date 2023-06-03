package org.com.model;

import org.com.enums.PieceColor;

public class Player {
    private String name;
    private PieceColor color;
    public Player(String name, PieceColor color)
    {
        if(name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if(color == null)
        {
            throw new IllegalArgumentException("Player color cannot be null");
        }
        this.name = name.trim();
        this.color = color;
    }

    public String getName()
    {
        return name;
    }

    public PieceColor getColor()
    {
        return color;
    }
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof Player))
            return false;
        Player other = (Player)obj;
        return name.equals(other.name) && color == other.color;
    }
    @Override
    public int hashCode(){
        return 31 * name.hashCode() + color.hashCode();
    }
}
