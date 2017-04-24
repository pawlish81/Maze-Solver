package com.pwl.labexcape;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by @Author:piotr.pawliszcze on 13.04.2017.
 */
@Slf4j
@ToString
@Data
@Builder
@AllArgsConstructor
public class Point {

    private int x;

    private int y;

}
