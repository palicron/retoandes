package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaItems {

	
	@JsonProperty(value="items")
	private List<Items> items;
	
	public ListaItems(@JsonProperty(value="items")List<Items> items)
	{
		this.items = items;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}
}
