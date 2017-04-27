package com.pwl.labexcape;

import com.google.common.collect.Lists;
import com.google.common.collect.Ranges;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Please implement your solution here
 */
@Slf4j
public class LabEscape {

    private static final char FREE = 32;
    private static final char PATH = 8226;


    private static Point exitPoint;

    /**
     * @param labyrinth A labyrinth drawn on a matrix of characters. It's at least 4x4, can be a rectangle or a square.
     *                  Walkable areas are represented with a space character, walls are represented with a big O character.
     *                  The escape point is always on the border (see README)
     * @param startX    Starting row number for the escape. 0 based.
     * @param startY    Starting column number for the escape. 0 based.
     * @return A char matrix with the same labyrinth and a path drawn from the starting point to the escape
     * @throws NoEscapeException when no path exists to the outside, from the selected starting point
     */
    public static char[][] drawPathForEscape(char[][] labyrinth, int startX, int startY) throws NoEscapeException {
        if (labyrinth == null) {
            throw new NoEscapeException("Labyrinth is null");
        }


        int y = labyrinth.length;
        int x = Arrays.stream(labyrinth).findFirst().get().length;




        displayLabyrinth(labyrinth);

        validateMinimumSizeOfLabyrinth(y, x);
        validateStartPoint(startX, startY, y, x);
        validateSizeofLabyrinth(labyrinth, x);
        validateCorrectCharactersAsLabyrinthStructure(labyrinth);

        exitPoint = getExitCoordinatesMap(labyrinth);
        log.info("Labyrinth size : {}x{} , exit point ({},{})", labyrinth[0].length,labyrinth.length,exitPoint.getX(),exitPoint.getY());
        validateStartPointsNotExit(startX, startY, exitPoint);

        findPath(labyrinth, startX, startY);
        log.info("Solved");
        displayLabyrinth(labyrinth);

        return labyrinth;
    }


    /**
     * Set path as to exit
     *
     * @param labyrinth - labyrinth
     * @param startX    - start point x  {@code integer}
     * @param startY    - start point x  {@code integer}
     * @return labyrinth with path
     */
    private static boolean findPath(char[][] labyrinth, int startX, int startY) {

        if (startY == exitPoint.getY() && startX == exitPoint.getX()) {
            labyrinth[startY][startX] = PATH;

            char[] copiedChar = labyrinth[startY];
            labyrinth[startY] = new char[labyrinth[startY].length + 1];
            System.arraycopy(copiedChar, 0, labyrinth[startY], 0, copiedChar.length);
            labyrinth[startY][startX + 1] = '*';
            return true;
        }

        if (labyrinth[startY][startX] == FREE) {
            labyrinth[startY][startX] = PATH;

            int dx = -1;
            int dy = 0;
            if (findPath(labyrinth, startX + dx, startY + dy)) {
                return true;
            }

            dx = 1;
            dy = 0;
            if (findPath(labyrinth, startX + dx, startY + dy)) {
                return true;
            }

            dx = 0;
            dy = -1;
            if (findPath(labyrinth, startX + dx, startY + dy)) {
                return true;
            }

            dx = 0;
            dy = 1;
            if (findPath(labyrinth, startX + dx, startY + dy)) {
                return true;
            }
            labyrinth[startY][startX] = FREE;
        }

        return false;
    }


    /**
     * Check if start point is not in exit
     *
     * @param startX    - start point x  {@code integer}
     * @param startY    - start point x  {@code integer}
     * @param exitPoint - exit coordinates {@link Point}
     * @throws NoEscapeException - when start point equal exit point.
     */
    private static void validateStartPointsNotExit(int startX, int startY, Point exitPoint) throws NoEscapeException {
        if (startX == exitPoint.getX() && startY == exitPoint.getY()) {
            throw new NoEscapeException("Start point equal exit point (%s,%s)", exitPoint.getX(), exitPoint.getY());
        }
    }


    /**
     * Check if labyrinth has exit
     *
     * @param labyrinth - labyrinth
     * @return - exit coordinates as int[] List
     * @throws NoEscapeException -
     */
    private static Point getExitCoordinatesMap(char[][] labyrinth) throws NoEscapeException {
        List<Point> exitPointList = Lists.newArrayList();
        IntStream.range(0, labyrinth.length).forEach(labY -> {
            if (labY == 0 || labY == labyrinth.length - 1) {
                String wallString = String.valueOf(labyrinth[labY]).substring(1, labyrinth[labY].length);

                if (wallString.contains(String.valueOf(FREE))) {
                    Point point = Point.builder().x(String.valueOf(labyrinth[labY]).indexOf(FREE)).y(labY).build();
                    exitPointList.add(point);
                }

                if (wallString.contains(String.valueOf(FREE))) {
                    exitPointList.add(Point.builder().x(String.valueOf(labyrinth[labY]).indexOf(FREE)).y(labY).build());
                }
            } else {
                if (labyrinth[labY][0] == FREE) {
                    exitPointList.add(Point.builder().x(0).y(labY).build());
                }
                if (labyrinth[labY][labyrinth[labY].length - 1] == FREE) {
                    exitPointList.add(Point.builder().x(labyrinth[labY].length - 1).y(labY).build());
                }
            }

        });
        if (exitPointList.isEmpty()) {
            throw new NoEscapeException("No exit in labyrinth");
        }

        if (exitPointList.size() > 1) {
            throw new NoEscapeException("Labyrinth has more then one exit");
        }
        return exitPointList.stream().findFirst().get();
    }

    /**
     * Check structure of labyrinth from characters ' ', 'O', '.'
     *
     * @param labyrinth - labyrinth
     * @throws NoEscapeException {@link NoEscapeException} when labyrinth contain bad character
     */
    private static void validateCorrectCharactersAsLabyrinthStructure(char[][] labyrinth) throws NoEscapeException {
        Optional<char[]> incorrectRow = Stream.of(labyrinth).filter(c -> !String.valueOf(c).matches("[O\\s]*")).findAny();
        if (incorrectRow.isPresent()) {
            throw new NoEscapeException("Labyrinth contains incorrect sign in row : '" + new String(incorrectRow.get()) + "'");
        }
    }

    /**
     * Display labyrinth
     *
     * @param labyrinth - labyrinth
     */
    private static void displayLabyrinth(char[][] labyrinth) {
        IntStream.range(0, labyrinth.length).forEach(labX -> {
            StringBuilder rowBuilder = new StringBuilder();
            IntStream.range(0, labyrinth[labX].length).forEach(labY -> rowBuilder.append(labyrinth[labX][labY]).append(""));
            log.info("      " + rowBuilder.toString());
        });
    }

    /**
     * Check if labyrinth has all sides equal
     *
     * @param labyrinth - labyrinth
     * @param x         - first row length
     * @throws NoEscapeException {@link NoEscapeException} when labyrinth is smaller
     */
    private static void validateSizeofLabyrinth(char[][] labyrinth, int x) throws NoEscapeException {
        //validate if all rows has same size
        Optional<char[]> any = Arrays.stream(labyrinth).filter((char[] c) -> c.length != x).findAny();
        if (any.isPresent()) {
            throw new NoEscapeException("One of the rows '%s' length is different", new String(any.get()));
        }
    }

    /**
     * Check if start point contain in labyrinth
     *
     * @param startX - start point x  {@code integer}
     * @param startY - start point x  {@code integer}
     * @param y      - labyrinth column length {@code integer}
     * @param x      - labyrinth column length {@code integer}
     * @throws NoEscapeException {@link NoEscapeException} when start point is out size labyrinth
     */
    private static void validateStartPoint(int startX, int startY, int y, int x) throws NoEscapeException {
        if (!Ranges.closed(0, x).contains(startX) || !Ranges.closed(0, y).contains(startY)) {
            throw new NoEscapeException("Start point outside labyrinth, Labyrinth size = %s x %s", x, y);
        }
    }

    /**
     * Check if size of labyrinth is bigger or smaller than 4x4
     *
     * @param y - labyrinth column length {@code integer}
     * @param x - labyrinth column length {@code integer}
     * @throws NoEscapeException - throw if size of labyrinth is bigger or equal than 4x4
     */
    private static void validateMinimumSizeOfLabyrinth(int y, int x) throws NoEscapeException {
        //validate minimum size
        int minimumSize = 4;
        if (minimumSize > x || minimumSize > y) {
            throw new NoEscapeException("Labyrinth is smaller then minimum size 4x4");
        }
    }

}
