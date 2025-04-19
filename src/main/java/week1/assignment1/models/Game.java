package week1.assignment1.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Game {
    private Long id;
    private String title;
    private String genre;
    private String platform;
}
