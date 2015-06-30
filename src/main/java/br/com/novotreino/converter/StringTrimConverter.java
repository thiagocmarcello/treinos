package br.com.novotreino.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = String.class)
public class StringTrimConverter implements Serializable, Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent cmp,
			String value) {
		if (value != null && cmp instanceof HtmlInputText) {
			return value.trim();
		}
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent cmp,
			Object value) {
		if (value != null) {
			return value.toString();
		}
		return null;
	}
}