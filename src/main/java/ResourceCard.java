package org.example.src.main.java;

public class ResourceCard extends PlayableCard implements PointsProvider{
    private Kingdom kingdom;
    private int point;

    @Override
    public int computePoints(PlayerTable table) {
        return this.point;
    }
}
