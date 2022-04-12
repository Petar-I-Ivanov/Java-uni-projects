package blocks.elements;

import blocks.ElementAttribute;

import java.util.ArrayList;

public class ElementSimple implements Element {

    private String name;
    private String textContent;

    private ElementAttribute attribute;
    private ArrayList<Element> innerElements = new ArrayList<>();

    public ElementSimple(String name, String textContent, ElementAttribute attribute) {

        this.name = name;
        this.textContent = textContent;
        this.attribute = attribute;
    }

    public void addElement(Element element) {
        innerElements.add(element);
    }

    public String render() {

        if (name.equals("")) { return textContent; }

        for (Element e : innerElements) {
            textContent += e.render();
        }

        return "<" + name + ((attribute != null) ? attribute.getAttributeHTML() : "") + ">"
                + textContent + "</" + name + ">";
    }

    public String renderAsTSV() {

        StringBuilder tsvContent = new StringBuilder(name + ((attribute != null) ? attribute.getAttributeTSV() : ""));

        for (Element e : innerElements) {
            tsvContent.append("~").append(e.renderAsTSV());
        }

        return tsvContent.toString();
    }
}