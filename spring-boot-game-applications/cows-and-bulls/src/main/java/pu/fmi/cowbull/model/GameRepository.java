package pu.fmi.cowbull.model;

public interface GameRepository {
	
	public void addGame(GameInfo gameInfo);
	public GameInfo getGame (String gameId);
}