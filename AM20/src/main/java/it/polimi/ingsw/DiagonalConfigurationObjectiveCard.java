package it.polimi.ingsw;

public class DiagonalConfigurationObjectiveCard extends ObjectiveCard implements ConfigurationFinder{
    private Kingdom kingdom;
    private char direction;

    @Override
    public int findConfiguration(DynamicMatrix<PlayableCard> mat) {
        return 0;
    }

    @Override
    public int computePoints(PlayerTable table) {
        return 0;
    }
}
