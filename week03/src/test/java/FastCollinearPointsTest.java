import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;

public class FastCollinearPointsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void rejects_null_point() {
        thrown.expect(IllegalArgumentException.class);

        new FastCollinearPoints(new Point[]{
                null,
                new Point(1, 1)
        });
    }

    @Test
    public void rejects_repeated_point() {
        thrown.expect(IllegalArgumentException.class);

        new FastCollinearPoints(new Point[]{
                new Point(1, 1),
                new Point(1, 1)
        });
    }

    @Test
    public void caluclates_segments_case_0() {
        FastCollinearPoints sut = new FastCollinearPoints(new Point[]{
                new Point(0, 0),
                new Point(3, 3),
                new Point(2, 2),
                new Point(5, 5),
                new Point(1, 100),
                new Point(4, 4),
        });

        Assert.assertThat(sut.numberOfSegments(), is(1));
        Assert.assertThat(sut.segments()[0].toString(), is("(0, 0) -> (5, 5)"));
    }

    @Test
    public void caluclates_segments_case_1() {
        FastCollinearPoints sut = new FastCollinearPoints(new Point[]{
                new Point(7453, 14118),
                new Point(2682, 14118),
                new Point(7821, 14118),
                new Point(5067, 14118),
                new Point(9972, 4652),
                new Point(16307, 4652),
                new Point(5766, 4652),
                new Point(4750, 4652)
        });

        Assert.assertThat(sut.numberOfSegments(), is(2));
        Assert.assertThat(sut.segments()[0].toString(), is("(2682, 14118) -> (7821, 14118)"));
        Assert.assertThat(sut.segments()[1].toString(), is("(4750, 4652) -> (16307, 4652)"));
    }
}
