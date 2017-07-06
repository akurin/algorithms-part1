import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;

public class BruteCollinearPointsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void rejects_null_point() {
        thrown.expect(IllegalArgumentException.class);

        new BruteCollinearPoints(new Point[]{
                null,
                new Point(1, 1)
        });
    }

    @Test
    public void rejects_repeated_point() {
        thrown.expect(IllegalArgumentException.class);

        new BruteCollinearPoints(new Point[]{
                new Point(1, 1),
                new Point(1, 1)
        });
    }

    @Test
    public void caluclates_sements() {
        BruteCollinearPoints sut = new BruteCollinearPoints(new Point[]{
                new Point(1, 1),
                new Point(10, 10),
                new Point(100, 100),
                new Point(15, 15),
                new Point(13, 1),
                new Point(1, 17),
        });

        Assert.assertThat(sut.numberOfSegments(), is(1));
    }
}
