package tests;

import blocks.HtmlFactory;
import blocks.elements.Element;

public class CreateHtmlStructureTest {

    public static void test() {
        createSimpleElement();
        createElementWithAttribute();
        createNestedElement();
        createSuperNestedElementStructure();
    }

    private static void createSimpleElement() {

        HtmlFactory htmlFactory = new HtmlFactory();
        Element element = htmlFactory.createNewTextElement("html");
        String htmlContent = element.render();

        // <html></html>

        TestRunner.check(
                htmlContent,
                "<html></html>"
        );
    }

    private static void createElementWithAttribute() {

        HtmlFactory htmlFactory = new HtmlFactory();
        Element element = htmlFactory.createNewTextElement(
                "html",
                htmlFactory.createAttribute("lang", "bg")
        );
        String htmlContent = element.render();

        // <html lang='bg'></html>

        TestRunner.check(
                htmlContent,
                "<html lang='bg'></html>"
        );

        String tildaContent = element.renderAsTSV();

        // html#lang!bg
        TestRunner.check(
                tildaContent,
                "html#lang!bg"
        );
    }

    private static void createNestedElement() {

        HtmlFactory htmlFactory = new HtmlFactory();

        Element element = htmlFactory.createNewTextElement("html");
        Element div = htmlFactory.createNewTextElement("div");
        Element h1 = htmlFactory.createNewTextElement("h1");
        Element h2 = htmlFactory.createNewTextElement("h2", "Second level title");

        div.addElement(h1);
        div.addElement(h2);
        element.addElement(div);

        String htmlContent = element.render();

        // <html>
        //  <div>
        //      <h1></h1>
        //      <h2>Second level title</h2>
        //  </div>
        // </html>

        TestRunner.check(
                htmlContent,
                "<html><div><h1></h1><h2>Second level title</h2></div></html>"
        );

        String tildaContent = element.renderAsTSV();

        // html~div~~h1~~h2

        TestRunner.check(
                tildaContent,
                "html~div~~h1~~h2"
        );
    }

    private static void createSuperNestedElementStructure() {

        HtmlFactory htmlFactory = new HtmlFactory();

        Element div1 = htmlFactory.createNewTextElement("div");
        Element div2 = htmlFactory.createNewTextElement("div", htmlFactory.createAttribute("id", "div2"));
        Element div3 = htmlFactory.createNewTextElement("div", htmlFactory.createAttribute("id", "div3"));
        Element div4 = htmlFactory.createNewTextElement("div", htmlFactory.createAttribute("id", "div4"));
        Element div5 = htmlFactory.createNewTextElement("div", htmlFactory.createAttribute("id", "div5"));

        div1.addElement(div2);
        div1.addElement(div3);
        div4.addElement(div1);
        div5.addElement(div4);

        String htmlContent = div5.render();

        // <div id='div5'>
        //  <div id='div4'>
        //      <div>
        //      <div id='div2'></div>
        //      <div id='div3'></div>
        //      </div>
        //  </div>
        // </div>

        TestRunner.check(
                htmlContent,
                "<div id='div5'><div id='div4'><div><div id='div2'></div><div id='div3'></div></div></div></div>"
        );

        String tildaContent = div5.renderAsTSV();

        // div#id!div5~div#id!div4~~div~~div#id!div2~~div#id!div3

        TestRunner.check(
                tildaContent,
                "div#id!div5~div#id!div4~~div~~div#id!div2~~div#id!div3"
        );
    }
}