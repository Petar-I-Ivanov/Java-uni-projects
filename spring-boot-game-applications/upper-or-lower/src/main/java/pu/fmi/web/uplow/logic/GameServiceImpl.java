package pu.fmi.web.uplow.logic;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import pu.fmi.web.uplow.model.GameRepository;
import pu.fmi.web.uplow.model.TryInfo;
import pu.fmi.web.uplow.model.GameInfo;

@Service("gameService")
public class GameServiceImpl implements GameService {
	
	private GameRepository gameRepository;
	
	public GameServiceImpl(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	@Override
	public GameInfo startGame() {
		
		GameInfo gameInfo = new GameInfo();
		gameInfo.setGameId(UUID.randomUUID().toString());

		Random random = new Random();
		gameInfo.setNumber(random.nextInt(100) + 1);
		gameRepository.addGame(gameInfo);
		
		return gameInfo;
	}

	@Override
	public GameInfo getGame(String gameId) {
		return gameRepository.getGame(gameId);
	}

	@Override
	public GameInfo makeTry(String gameId, int guess) {
		
		GameInfo gameInfo = gameRepository.getGame(gameId);
		
		TryInfo tryInfo = new TryInfo();
		tryInfo.setGuess(guess);
		tryInfo.setMessage(setMessage(gameInfo.getNumber(), guess));
		
		gameInfo.getHistory().add(tryInfo);
		return gameInfo;
	}

	private String setMessage(int number, int guess) {
		
		if (number < guess) {
			return "Go lower.";
		}
		
		if (number > guess) {
			return "Go higher.";
		}
		
		return "";
	}
}
