package com.pwl.labexcape;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by @Author:piotr.pawliszcze on 14.04.2017.
 */
public class PointTest {

    @Test
    public void testPointObject() throws Exception {
        Point point = new Point(2,3);

        assertThat(point).as("test X in point object").extracting(Point::getX).containsExactly(2);
        assertThat(point).as("test Y in point object").extracting(Point::getY).containsExactly(3);
        assertThat(point).as("test toStringMethod").extracting(Point::toString).containsExactly("Point(x=2, y=3)");

    }

}