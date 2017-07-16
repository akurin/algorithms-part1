import edu.princeton.cs.algs4.Point2D;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PointSETTest {
    @Test
    public void returns_nearest() {
        PointSET pointSET = new PointSET();
        pointSET.insert(new Point2D(1, 1));
        pointSET.insert(new Point2D(0, .02));
        pointSET.insert(new Point2D(.5, .5));


        assertThat(pointSET.nearest(new Point2D(.1, .2)), is(new Point2D(0, .02)));
    }
}
