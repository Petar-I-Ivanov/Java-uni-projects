package pu.fmi.web.uplow.logic;

import pu.fmi.web.uplow.model.GameInfo;

public interface GameService {

	GameInfo startGame();
	GameInfo getGame(String gameId);
	GameInfo makeTry(String gameId, int guess);
}