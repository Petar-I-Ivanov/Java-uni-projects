package pu.fmi.cowbull.logic;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import pu.fmi.cowbull.model.GameInfo;
import pu.fmi.cowbull.model.GameRepository;
import pu.fmi.cowbull.model.TryInfo;

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
		String number = Integer.toString(random.nextInt(9000) + 1000);
		
		gameInfo.setNumber(number);
		gameRepository.addGame(gameInfo);
		
		return gameInfo;
	}

	@Override
	public GameInfo getGame(String gameId) {
		return gameRepository.getGame(gameId);
	}

	@Override
	public GameInfo makeTry(String gameId, String guess) {
		
		GameInfo gameInfo = gameRepository.getGame(gameId);
		
		TryInfo tryInfo = new TryInfo();
		tryInfo.setGuessString(guess);
		
		int[] cowsAndBulls = countCowsAndBulls(guess, gameInfo.getNumber());
		
		tryInfo.setCows(cowsAndBulls[0]);
		tryInfo.setBulls(cowsAndBulls[1]);
		
		gameInfo.getHistory().add(tryInfo);
		return gameInfo;
	}
	
	private static int[] countCowsAndBulls(String guess, String number) {
		
		int[] cowsAndBulls = {0, 0};
		
		for (int i = 0; i < guess.length(); i++) {
			for (int j = 0; j < number.length(); j++) {
				
				if (guess.charAt(i) == number.charAt(j) && i != j) {
					cowsAndBulls[0]++;
				}
				
				if (guess.charAt(i) == number.charAt(j) && i == j) {
					cowsAndBulls[1]++;
				}
			}
		}
		
		System.out.println("Number:" + number + ", guess:" + guess + ", cows:" + cowsAndBulls[0] + ", bulls:" + cowsAndBulls[1]);
		
		return cowsAndBulls;
	}
	
}