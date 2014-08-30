package com.uscauv.vision;

import org.junit.Test;
import org.opencv.core.Point;

import static org.junit.Assert.assertEquals;

/**
 * Created by vmagro on 8/30/14.
 */
public class VisionUtilTest {

    @Test
    public void testDistance() throws Exception {
        assertEquals(0, VisionUtil.distance(new Point(0, 0), new Point(0, 0)), 0);
        assertEquals(1, VisionUtil.distance(new Point(1, 0), new Point(0, 0)), 0);
        assertEquals(1, VisionUtil.distance(new Point(0, 0), new Point(0, 1)), 0);
        assertEquals(Math.sqrt(2), VisionUtil.distance(new Point(0, 0), new Point(1, 1)), 0);
    }
}
