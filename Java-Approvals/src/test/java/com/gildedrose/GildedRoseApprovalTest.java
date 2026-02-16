package com.gildedrose;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@UseReporter(DiffReporter.class)
public class GildedRoseApprovalTest {

    @Test
    public void foo() {

        Item[] items = new Item[]{new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        Approvals.verifyAll("Items", items);
    }

    @Test
    public void updateQualityOneDay() {

        CombinationApprovals.verifyAllCombinations(this::doUpdateQuality,
            new String[]{"foo"},
            new Integer[]{0},
            new Integer[]{0},
            new Options().withScrubber(String::trim));
    }

    public String doUpdateQuality(String foo, int sellIn, int quality) {
        Item[] items = new Item[]{new Item(foo, sellIn, quality)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app.items[0].toString();
    }

    @Test
    public void thirtyDays() {

        ByteArrayOutputStream fakeoutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeoutput));
        System.setIn(new ByteArrayInputStream("a\n".getBytes()));

        Program.main();
        String output = fakeoutput.toString();

        Approvals.verify(output);
    }
}
