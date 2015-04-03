package br.com.novotreino.converter;

import java.lang.reflect.Method;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class BaseConverter<T> implements Converter {

    private static final String REGEX = "SELECIONE|SELECIONE\\.\\.\\.|Selecione\\.\\.\\.|empty|\\-{2}\\s*\\w+\\s*\\-{2}";
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value != null && !value.isEmpty() && !value.matches(REGEX)
                && !value.equals("0")) {
            return (T) component.getAttributes().get(value);
        }
        return null;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (value != null) {
            T entity = (T) value;
            if (entity.getClass().isInstance(value)) {
                Integer id = getId(value);
                if (id != null) {
                    component.getAttributes().put(id.toString(), entity);
                    return id.toString();
                }
            }
        }
        return "";
    }

    private int getId(Object o) {
        Class<?> clazz = o.getClass();
        Object valor = null;
        for (Method m : clazz.getMethods()) {
            try {
                if (isGetId(m)) {
                    valor = m.invoke(o);
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (valor != null) {
            return (Integer) valor;
        } else {
            return 0;
        }
    }

    private static boolean isGetId(Method m) {
        return m.getName().startsWith("getId")
                && m.getReturnType() != void.class
                && m.getParameterTypes().length == 0;
    }
}