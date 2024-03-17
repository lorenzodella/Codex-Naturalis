package it.polimi.ingsw.model;

public class DiagonalConfigurationObjectiveCard extends ObjectiveCard implements ConfigurationFinder{
    private Kingdom kingdom;
    private int coveredCorner;

    public DiagonalConfigurationObjectiveCard(String ID) {
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
