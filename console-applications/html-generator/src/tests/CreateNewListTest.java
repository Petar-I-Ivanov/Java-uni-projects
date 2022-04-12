package tests;

import blocks.HtmlFactory;
import blocks.elements.Element;
import blocks.ElementListEnum;

import java.util.ArrayList;

public class CreateNewListTest {

    public static void test() {
        createNewEmptyList();
        createNewEmptyUnorderedList();
        createNewUnorderedListWithElements();
        createNewOrderedListWithElements();
    }

    private static void createNewEmptyList() {

        HtmlFactory htmlFactory = new HtmlFactory();
        Element element = htmlFactory.createList();
        String htmlContent = element.render();

        // <ul></ul>

        TestRunner.check(
                htmlContent,
                "<ul></ul>"
        );
    }

    private static void createNewEmptyUnorderedList() {

        HtmlFactory htmlFactory = new HtmlFactory();
        Element element = htmlFactory.createList(ElementListEnum.Ordered);
        String htmlContent = element.render();

        // <ol></ol>

        TestRunner.check(
                htmlContent,
                "<ol></ol>"
        );
    }

    private static void createNewUnorderedListWithElements() {

        HtmlFactory htmlFactory = new HtmlFactory();

        Element element = htmlFactory.createList(ElementListEnum.Unordered);
        element.addElement(htmlFactory.createNewTextElement("div", "Element 1"));
        element.addElement(htmlFactory.createTextElement("Simple Text Element"));

        String htmlContent = element.render();

        // <ul>
        //  <li> <div>Element 1</div> </li>
        //  <li>Simple Text Element</li>
        // </ul>

        TestRunner.check(
                htmlContent,
                "<ul><li><div>Element 1</div></li><li>Simple Text Element</li></ul>"
        );
    }

    private static void createNewOrderedListWithElements() {

        HtmlFactory htmlFactory = new HtmlFactory();

        ArrayList<Element> collection = new ArrayList<>();
        collection.add(htmlFactory.createNewTextElement("div", "Element 1"));
        collection.add(htmlFactory.createTextElement("Simple Text Element"));
        collection.add(htmlFactory.createNewTextElement(
                "h1",
                htmlFactory.createAttribute("class", "test"))
        );
        Element element = htmlFactory.createList(ElementListEnum.Ordered, collection);

        String htmlContent = element.render();

        // <ol>
        //  <li> <div>Element 1</div> </li>
        //  <li>Simple Text Element</li>
        //  <li> <h1 class='test'></h1> </li>
        // </ol>

        TestRunner.check(
                htmlContent,
                "<ol><li><div>Element 1</div></li><li>Simple Text Element</li><li><h1 class='test'></h1></li></ol>"
        );
    }
}