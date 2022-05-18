package pu.fmi.cowbull.logic;

import pu.fmi.cowbull.model.GameInfo;

public interface GameService {
	
	GameInfo startGame();
	GameInfo getGame(String gameId);
	GameInfo makeTry(String gameId, String guess);
}