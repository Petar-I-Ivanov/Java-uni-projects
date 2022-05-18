package pu.fmi.cowbull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import pu.fmi.cowbull.logic.GameServiceImpl;
import pu.fmi.cowbull.model.GameRepository;
import pu.fmi.cowbull.model.GameRepositoryImpl;

class GameServiceImplTest {

	@Test
	void test() {
		GameRepository gameRepository = new GameRepositoryImpl();
		var gameService = new GameServiceImpl(gameRepository);
		var gameInfo = gameService.startGame();
		assertNotNull(gameInfo);
		assertNotNull(gameInfo.getGameId());
		assertNotNull(gameInfo.getNumber());
		assertEquals(4, gameInfo.getNumber().length());
		assertEquals(0, gameInfo.getHistory().size());
	}
}