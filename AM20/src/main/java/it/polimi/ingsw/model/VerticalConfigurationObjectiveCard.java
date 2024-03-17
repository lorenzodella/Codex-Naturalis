package it.polimi.ingsw.model;

public class VerticalConfigurationObjectiveCard extends ObjectiveCard implements ConfigurationFinder{
    private Kingdom kingdom1;
    private Kingdom kingdom2;
    private int coveredCorner;

    public VerticalConfigurationObjectiveCard(String ID) {
        super(ID);
    }

    @Override
    public int findConfiguration(DynamicMatrix<PlayableCard> mat) {
        return 0;
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}