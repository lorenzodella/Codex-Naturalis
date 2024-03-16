package it.polimi.ingsw;

public class VerticalConfigurationObjectiveCard extends ObjectiveCard implements ConfigurationFinder{
    private Kingdom kingdom1;
    private Kingdom kingdom2;
    private int coveredCorner;

    @Override
    public int findConfiguration(DynamicMatrix<PlayableCard> mat) {
        return 0;
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}