package blocks.elements;

import blocks.ElementListEnum;
import blocks.HtmlFactory;

import java.util.ArrayList;

public class ElementList implements Element  {

    private HtmlFactory htmlFactory = new HtmlFactory();
    private String name;

    private Element list;
    private Element itemList;

    public ElementList (ElementListEnum listType, ArrayList<Element> collection) {

        name = (listType == ElementListEnum.Unordered) ? "ul" : "ol";
        list = htmlFactory.createNewTextElement(name);

        if (collection != null) {

            for (Element element : collection) {

                itemList = htmlFactory.createNewTextElement("li");
                itemList.addElement(element);
                list.addElement(itemList);
            }
        }
    }

    @Override
    public void addElement(Element element) {

        itemList = htmlFactory.createNewTextElement("li");
        itemList.addElement(element);
        list.addElement(itemList);
    }

    @Override
    public String render() {
        return list.render();
    }

    @Override
    public String renderAsTSV() {
        return list.renderAsTSV();
    }
}