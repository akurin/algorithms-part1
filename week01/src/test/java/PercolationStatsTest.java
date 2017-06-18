import org.junit.Assert;
import org.junit.Test;

public class PercolationStatsTest {
    private static final double EPSILON = 1e-2;

    @Test
    public void calculates_stats() {
        PercolationStats sut = new PercolationStats(200, 100);
        Assert.assertEquals(0.5929934999999997, sut.mean(), EPSILON);
        Assert.assertEquals(0.00876990421552567, sut.stddev(), EPSILON);
        Assert.assertEquals(0.5912745987737567, sut.confidenceLo(), EPSILON);
        Assert.assertEquals(0.5947124012262428, sut.confidenceHi(), EPSILON);
    }
}
