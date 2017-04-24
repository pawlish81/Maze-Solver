package com.pwl.labexcape;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.*;

/**
 * Created by @Author:piotr.pawliszcze on 14.04.2017.
 */
public class NoEscapeExceptionTest {

    @Test
    public void testException() {
        assertThatThrownBy(() -> { throw new NoEscapeException("No Escpae!"); }).isInstanceOf(Exception.class)
                .hasMessageContaining("No Escpae!");
    }


}