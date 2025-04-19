package week1.assignment1.services;

import week1.assignment1.models.Game;

import java.util.List;

public interface GameService {
    List<Game> getAllGames();
    Game getGameById(long id);
    Game addGame(Game game);
    Game updateGame(long id, Game game);
    void deleteGame(long id);
}
