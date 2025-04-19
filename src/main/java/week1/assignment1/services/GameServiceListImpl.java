package week1.assignment1.services;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import week1.assignment1.models.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Primary
public class GameServiceListImpl implements GameService {
    private List<Game> games = new ArrayList<Game>();
    private long autoIncrementId = 1L;

    public GameServiceListImpl() {
        addGame(new Game(null, "Wargame: European Escalation", "Real-Time Tactics", "PC"));
        addGame(new Game(null, "Wargame: AirLand Battle", "Real-Time Tactics", "PC"));
        addGame(new Game(null, "Wargame: Red Dragon", "Real-Time Tactics", "PC"));
        addGame(new Game(null, "Steel Division: Normandy 44", "Real-Time Tactics", "PC"));
        addGame(new Game(null, "Steel Division 2", "Real-Time Tactics", "PC"));
        addGame(new Game(null, "WARNO", "Real-Time Tactics", "PC"));
        addGame(new Game(null, "R.U.S.E.", "Real-Time Strategy", "PC/PlayStation 3/Xbox 360"));
        addGame(new Game(null, "Nekopara Vol. 0", "Visual Novel", "PC/PlayStation 4/Nintendo Switch"));
        addGame(new Game(null, "Nekopara Vol. 1", "Visual Novel", "PC/PlayStation 4/Nintendo Switch"));
        addGame(new Game(null, "Nekopara Vol. 2", "Visual Novel", "PC/PlayStation 4/Nintendo Switch"));
        addGame(new Game(null, "Nekopara Vol. 3", "Visual Novel", "PC/PlayStation 4/Nintendo Switch"));
        addGame(new Game(null, "Nekopara Vol. 4", "Visual Novel", "PC/PlayStation 4/Nintendo Switch"));
        addGame(new Game(null, "Nekopara Extra", "Visual Novel", "PC/PlayStation 4/Nintendo Switch"));
    }

    @Override
    public List<Game> getAllGames() {
        return games;
    }

    @Override
    public Game getGameById(long id) {
        for (Game game : games) {
            if (game.getId() == id) {
                return game;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found with ID: " + id);
    }

    @Override
    public Game addGame(Game newGame) {
        if (isDuplicate(newGame))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicates are not allowed.");
        }
        if (newGame.getId() == null) {
            newGame.setId(this.autoIncrementId++);
        } else {
            // If ID was provided manually, ensure we don't duplicate
            this.autoIncrementId = Math.max(this.autoIncrementId, newGame.getId() + 1);
        }
        games.add(newGame);
        return newGame;
    }

    @Override
    public Game updateGame(long id, Game gameUpdate) {
        if (isDuplicate(gameUpdate))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicates are not allowed.");
        }
        for (int i = 0; i < games.size(); i++) {
            if (Objects.equals(games.get(i).getId(), id)) {
                gameUpdate.setId(id);
                games.set(i, gameUpdate);
                return gameUpdate;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found with ID: " + id);
    }

    @Override
    public void deleteGame(long id) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid game ID");
        }
        for (int i = 0; i < games.size(); i++) {
            if (Objects.equals(games.get(i).getId(), id)) {
                games.remove(i);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found with ID: " + id);
    }

    private boolean isDuplicate(Game newGame)
    {
        return games.stream().anyMatch(existing -> existing.getTitle().equalsIgnoreCase(
                newGame.getTitle()) &&
                existing.getPlatform().equalsIgnoreCase(newGame.getPlatform()));
        //&& existing.getGenre().equalsIgnoreCase(newGame.getGenre()));
    }
}
