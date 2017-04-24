package com.pwl.labexcape;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by pwl on 2017-04-11.
 */
public class LabEscapeTest {

    char[][] solvedLabyrinth = {
            ("OOOOOOOOOO").toCharArray(),
            ("O    O   O").toCharArray(),
            ("O OO O O O").toCharArray(),
            ("O  O O O O").toCharArray(),
            ("O OO   O  ").toCharArray(),
            ("O OOOOOOOO").toCharArray(),
            ("O OOOOOOOO").toCharArray(),
            ("O OOOOOOOO").toCharArray()
    };


    /**
     * Test
     */
    @Test
    public void drawPathForEscapeFromNullLabyrinth() {
        Assertions.assertThatThrownBy(() -> LabEscape.drawPathForEscape(null, 0, 4))
                .isInstanceOf(NoEscapeException.class)
                .hasMessage("Labyrinth is null");
    }

    @Test
    public void drawPathForEscapeFromSmallLabyrinth() {

        char[][] threeRowLabyrinth = {
                ("OOOOOOOOOO").toCharArray(),
                ("O    O   O").toCharArray(),
                ("O OO O O O").toCharArray()
        };

        char[][] threeColLabyrinth = {
                ("OOO").toCharArray(),
                ("O O").toCharArray(),
                ("O  ").toCharArray(),
                ("OOO").toCharArray(),
                ("OOO").toCharArray(),
        };


        Assertions.assertThatThrownBy(() -> LabEscape.drawPathForEscape(threeColLabyrinth, 0, 4))
                .isInstanceOf(NoEscapeException.class)
                .hasMessage("Labyrinth is smaller then minimum size 4x4");


        Assertions.assertThatThrownBy(() -> LabEscape.drawPathForEscape(threeRowLabyrinth, 0, 4))
                .isInstanceOf(NoEscapeException.class)
                .hasMessage("Labyrinth is smaller then minimum size 4x4");
    }

    @Test
    public void drawPathForEscapeFromIncorrectRowSize() {
        char[][] solvedLabyrinth = {
                ("OOOOOOOOOO").toCharArray(),
                ("O    O   O").toCharArray(),
                ("O OO O O ").toCharArray(), //incorrect line 9 chars
                ("O  O O O O").toCharArray(),
                ("O OO OOOO ").toCharArray(),
                ("O OOOOOOOO").toCharArray(),
                ("O OOOOOOOO").toCharArray(),
                ("O OOOOOOOO").toCharArray()
        };

        Assertions.assertThatThrownBy(() -> LabEscape.drawPathForEscape(solvedLabyrinth, 1, 4))
                .hasMessage("One of the rows 'O OO O O ' length is different");

    }


    @Test
    public void drawPathForEscapeFromIncorrectStartPoint() {
        Assertions.assertThatThrownBy(() -> LabEscape.drawPathForEscape(solvedLabyrinth, 13, 2))
                .isInstanceOf(NoEscapeException.class)
                .hasMessage("Start point outside labyrinth, Labyrinth size = 10 x 8");

    }

    @Test
    public void drawPathForEscapeWithLabyrinthWithIncorrectSigns() throws NoEscapeException {
        char[][] incorectLabyrinth = {
                ("OOOOOOOOOO").toCharArray(),
                ("O    O   O").toCharArray(),
                ("O OO O O O").toCharArray(),
                ("O  O O O O").toCharArray(),
                ("O OO   O  ").toCharArray(),
                ("O OOOOOOOO").toCharArray(),
                ("O OOOXOOOO").toCharArray(), //incorect sign 'X'
                ("O OOOOOOOO").toCharArray()
        };

        Assertions.assertThatThrownBy(() -> LabEscape.drawPathForEscape(incorectLabyrinth, 3, 2))
                .isInstanceOf(NoEscapeException.class)
                .hasMessage("Labyrinth contains incorrect sign in row : 'O OOOXOOOO'");

    }


    @Test
    public void drawPathForEscapeWithLabyrinthWithNoExit() throws NoEscapeException {
        char[][] incorectLabyrinth = {
                ("OOOOOOOOOO").toCharArray(),
                ("O    O   O").toCharArray(),
                ("O    O O O").toCharArray(),
                ("O  O O O O").toCharArray(),
                ("O OO   O O").toCharArray(),
                ("OOOOOOOOOO").toCharArray(),
                ("OOOOOOOOOO").toCharArray(),
                ("OOOOOOOOOO").toCharArray()
        };
        Assertions.assertThatThrownBy(() -> LabEscape.drawPathForEscape(incorectLabyrinth, 3, 2))
                .isInstanceOf(NoEscapeException.class)
                .hasMessage("No exit in labyrinth");

    }


    @Test
    public void drawPathForEscapeWithLabyrinthWithManyNoExits() throws NoEscapeException {
        char[][] incorectLabyrinth = {
                ("OO OOOOOOO").toCharArray(),
                ("O    O   O").toCharArray(),
                ("O    O O O").toCharArray(),
                ("O  O O O O").toCharArray(),
                ("O OO   O O").toCharArray(),
                ("OO      OO").toCharArray(),
                ("OOO OOOOOO").toCharArray(),
                ("OOO OOOOOO").toCharArray()
        };
        Assertions.assertThatThrownBy(() -> LabEscape.drawPathForEscape(incorectLabyrinth, 3, 2))
                .isInstanceOf(NoEscapeException.class)
                .hasMessage("Labyrinth has more then one exit");

    }

    @Test
    public void drawPathForEscapeWithLabyrinthStartPointOnExitPoint() throws NoEscapeException {
        char[][] incorectLabyrinth = {
                ("OOOOOOOOOOO").toCharArray(),
                ("O     O   O").toCharArray(),
                ("O  OO O O O").toCharArray(),
                ("O   O O O O").toCharArray(),
                ("O  OO   O O").toCharArray(),
                ("O O      OO").toCharArray(),
                ("          O").toCharArray(),
                ("OOOOOOOOOOO").toCharArray()
        };
        Assertions.assertThatThrownBy(() -> LabEscape.drawPathForEscape(incorectLabyrinth, 0, 6))
                .isInstanceOf(NoEscapeException.class)
                .hasMessage("Start point equal exit point (0,6)");

    }


    @Test
    public void drawPathForEscape() throws NoEscapeException {
        char[][] solvedLabyrinth = {
                ("OOOOOOOOOO").toCharArray(),
                ("O    O   O").toCharArray(),
                ("O OO O O O").toCharArray(),
                ("O    O O O").toCharArray(),
                ("O OO   O  ").toCharArray(),
                ("O OOOOOOOO").toCharArray(),
                ("O        O").toCharArray(),
                ("OOOOOOOOOO").toCharArray()
        };


        LabEscape.drawPathForEscape(solvedLabyrinth, 5, 6);
    }

    @Test
    public void drawPathForEscapeLab2() throws NoEscapeException {

        char[][] labyrinth = {
                ("OOOOOOOOOO").toCharArray(),
                ("O    O   O").toCharArray(),
                ("O OO O O O").toCharArray(),
                ("O  O O O O").toCharArray(),
                ("O OO   O  ").toCharArray(),
                ("OOOOO OOOO").toCharArray(),
                ("O        O").toCharArray(),
                ("OOOOOOOOOO").toCharArray()
        };


        char[][] expectedLabyrinth = {
                ("OOOOOOOOOO").toCharArray(),
                ("O    O•••O").toCharArray(),
                ("O OO O•O•O").toCharArray(),
                ("O  O O•O•O").toCharArray(),
                ("O OO ••O••*").toCharArray(),
                ("OOOOO•OOOO").toCharArray(),
                ("O    •   O").toCharArray(),
                ("OOOOOOOOOO").toCharArray()
        };


        char[][] solvedLabyrinth = LabEscape.drawPathForEscape(labyrinth, 5, 6);
        assertThat(labyrinth).containsExactly(expectedLabyrinth);
    }

    @Test
    public void drawPathForEscapeLab3() throws NoEscapeException {
        char[][] labyrinth = {
                ("OOOOOOOOOO").toCharArray(),
                ("O    O   O").toCharArray(),
                ("O OO O O O").toCharArray(),
                ("O OO O O O").toCharArray(),
                ("O OO   O  ").toCharArray(),
                ("O OOOOOOOO").toCharArray(),
                ("O        O").toCharArray(),
                ("OOOOOOOOOO").toCharArray()
        };

        char[][] expectedLabyrinth = {
                ("OOOOOOOOOO").toCharArray(),
                ("O••••O•••O").toCharArray(),
                ("O•OO•O•O•O").toCharArray(),
                ("O•OO•O•O•O").toCharArray(),
                ("O•OO•••O••*").toCharArray(),
                ("O•OOOOOOOO").toCharArray(),
                ("O•••••   O").toCharArray(),
                ("OOOOOOOOOO").toCharArray()
        };

        char[][] solvedLabyrinth = LabEscape.drawPathForEscape(labyrinth, 5, 6);
        assertThat(solvedLabyrinth).containsExactly(expectedLabyrinth);

    }

}