package tests;

import blocks.HtmlFactory;
import blocks.elements.Element;
import blocks.elements.Table;

public class CreateNewTableTest {

    public static void test() {
        testCreateEmptyTable();
        testCreateEmptyTableMultipleRows();
        testCreateTableWithSingleElement();
        testCreateTableWithSingleElementOnSpecificRow();
        testCreateTableWithSingleElementOutsideTheTable();
    }

    private static void testCreateEmptyTable() {

        HtmlFactory htmlFactory = new HtmlFactory();
        Table e = htmlFactory.createTable("test", 1, 1);
        String htmlContent = e.render();

        // <table name='test'>
        //  <tr>
        //      <td></td>
        //  </tr>
        // </table>

        TestRunner.check(
                htmlContent,
                "<table name='test'><tr><td></td></tr></table>"
        );
    }

    private static void testCreateEmptyTableMultipleRows() {

        HtmlFactory htmlFactory = new HtmlFactory();
        Table e = htmlFactory.createTable(3, 1);
        String htmlContent = e.render();

        // <table>
        //  <tr>
        //      <td></td>
        //  </tr>
        //  <tr>
        //      <td></td>
        //  </tr>
        //  <tr>
        //      <td></td>
        //  </tr>
        //</table>

        TestRunner.check(
                htmlContent,
                "<table><tr><td></td></tr><tr><td></td></tr><tr><td></td></tr></table>"
        );
    }

    private static void testCreateTableWithSingleElement() {

        HtmlFactory htmlFactory = new HtmlFactory();

        Table e = htmlFactory.createTable(1, 1);
        Element h1  = htmlFactory.createNewTextElement("h1", "New message");
        e.addElement(h1);

        String htmlContent = e.render();

        // <table>
        //  <tr>
        //      <td><h1>New message</h1></td>
        //  </tr>
        // </table>

        TestRunner.check(
                htmlContent,
                "<table><tr><td><h1>New message</h1></td></tr></table>"
        );
    }

    private static void testCreateTableWithSingleElementOnSpecificRow() {

        HtmlFactory htmlFactory = new HtmlFactory();

        Table e = htmlFactory.createTable(2, 2);
        Element h1  = htmlFactory.createNewTextElement("h1", "New message");
        e.addElement(h1, 2,2);

        String htmlContent = e.render();

        // <table>
        //  <tr>
        //      <td></td>
        //      <td></td>
        //  </tr>
        //  <tr>
        //      <td></td>
        //      <td><h1>New message</h1></td>
        //  </tr>
        // </table>

        TestRunner.check(
                htmlContent,
                "<table><tr><td></td><td></td></tr><tr><td></td><td><h1>New message</h1></td></tr></table>"
        );
    }

    private static void testCreateTableWithSingleElementOutsideTheTable() {

        HtmlFactory htmlFactory = new HtmlFactory();

        Table e     = htmlFactory.createTable(2, 2);
        Element h1  = htmlFactory.createNewTextElement("h1", "New message");
        e.addElement(h1, 3,3);

        String htmlContent = e.render();

        // <table>
        //  <tr>
        //      <td></td>
        //      <td></td>
        //  </tr>
        //  <tr>
        //      <td></td>
        //      <td></td>
        //  </tr>
        // </table>

        TestRunner.check(
                htmlContent,
                "<table><tr><td></td><td></td></tr><tr><td></td><td></td></tr></table>"
        );
    }
}