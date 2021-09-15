package org.example.web.dto;

public class BookParameterToFilter {
    private int parameter;
    private String value;

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BookParameterToFilter{" +
                "parameter=" + parameter +
                ", value='" + value + '\'' +
                '}';
    }
}
