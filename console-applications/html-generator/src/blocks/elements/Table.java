package blocks.elements;

import blocks.HtmlFactory;

public class Table implements Element {

    private HtmlFactory htmlFactory = new HtmlFactory();
    private Element table;

    private Element[] rows;
    private Element[][] cols;

    public Table (String tableName, int row, int col) {

        table = (tableName.equals(""))
                ? htmlFactory.createNewTextElement("table")
                : htmlFactory.createNewTextElement("table", htmlFactory.createAttribute("name", tableName));

        rows = new Element[row];
        cols = new Element[row][col];
        createTable();
    }

    public void addElement(Element element, int row, int col) {

        if (row > rows.length || col > cols[0].length) {
            return;
        }

        cols[row - 1][col - 1].addElement(element);
    }

    @Override
    public void addElement(Element element) {
        addElement(element, 1, 1);
    }

    @Override
    public String render() {
       return table.render();
    }

    @Override
    public String renderAsTSV() {
        return table.renderAsTSV();
    }

    private void createTable() {

        for (int i = 0; i < rows.length; i++) {

            rows[i] = htmlFactory.createNewTextElement("tr");

            for (int j = 0; j < cols[0].length; j++) {

                cols[i][j] = htmlFactory.createNewTextElement("td");
                rows[i].addElement(cols[i][j]);
            }

            table.addElement(rows[i]);
        }
    }
}