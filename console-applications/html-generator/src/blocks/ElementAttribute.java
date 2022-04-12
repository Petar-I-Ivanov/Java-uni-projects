package blocks;

public class ElementAttribute {

    private String name;
    private String value;

    public ElementAttribute(String name, String value) {

        this.name = name;
        this.value = value;
    }

    public String getAttributeHTML() {
        return " " + name + "='" + value + "'";
    }

    public String getAttributeTSV() {
        return "#" + name + "!" + value;
    }
}