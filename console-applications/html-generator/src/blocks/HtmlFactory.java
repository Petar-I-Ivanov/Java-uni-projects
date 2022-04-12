package blocks;

import blocks.elements.Element;
import blocks.elements.ElementList;
import blocks.elements.ElementSimple;
import blocks.elements.Table;

import java.util.ArrayList;

public class HtmlFactory {

    public ElementSimple createNewTextElement(String name) {
        return new ElementSimple(name, "", null);
    }

    public ElementSimple createNewTextElement(String name, String content) {
        return new ElementSimple(name, content, null);
    }

    public ElementSimple createNewTextElement(String name, ElementAttribute attribute) {
        return new ElementSimple(name, "", attribute);
    }

    public ElementSimple createTextElement(String content) {
        return new ElementSimple("", content, null);
    }

    public ElementList createList() {
        return new ElementList(ElementListEnum.Unordered, null);
    }

    public ElementList createList(ElementListEnum listType) {
        return new ElementList(listType, null);
    }

    public ElementList createList(ElementListEnum listType, ArrayList<Element> collection) {
        return new ElementList(listType, collection);
    }

    public Table createTable(String tableName, int row, int col) {
        return new Table(tableName, row, col);
    }

    public Table createTable(int row, int col) {
        return new Table("", row, col);
    }

    public ElementAttribute createAttribute(String name, String value) {
        return new ElementAttribute(name, value);
    }
}