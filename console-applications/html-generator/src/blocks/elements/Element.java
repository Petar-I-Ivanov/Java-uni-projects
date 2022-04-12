package blocks.elements;

public interface Element {

    void addElement(Element element);
    String render();
    String renderAsTSV();
}