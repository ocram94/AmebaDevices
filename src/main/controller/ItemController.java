package main.controller;

import java.util.List;

import main.MainDispatcher;
import main.model.Item;
import main.service.ItemService;

public class ItemController implements Controller {
	ItemService itemService;
	Item item;
	
	public ItemController() {
		itemService= new ItemService();
	}
	
	String mode="";
	
	@Override
	public void doControl(Request request) {
		
		mode= request.get("mode").toString();
		switch(mode) {
		case "InsertForm":
			item=(Item) request.get("item");
			if(item==null) {
				MainDispatcher.getInstance().callView("ItemInsertForm", null);
			}
			else {
				int buildingId = Integer.parseInt(request.get("buildingId").toString());
				if(itemService.insertItem(item, buildingId)) {
					MainDispatcher.getInstance().callView("ItemMenu", null);
				}
				else {
					//TODO
					//request.put("mode", "showerr");
					//MainDispatcher.getInstance().callView("ItemInsertForm", request);
				}
			}
			
			
			break;
		
		
		
			
			
		case "Read":
         	List<Item> items = itemService.getAllItem();
         	request= new Request();
         	request.put("items",items);
         	request.put("mode", "Read");
         	MainDispatcher.getInstance().callView("ItemRead", request);
             break;
			
		case "Return":
		MainDispatcher.getInstance().callView("ItemMenu", null);
		break;
		
		case "UpdateForm":
			item = (Item) request.get("item");
			
			if(item == null) {
				MainDispatcher.getInstance().callView("ItemUpdateForm", null);
			} else {
				int id=Integer.parseInt(item.getId());
				Item foundedItem = itemService.searchItem(id);
				if(foundedItem != null) {
					itemService.updateItem(item);
					MainDispatcher.getInstance().callView("ItemMenu", null);
				} else {
					MainDispatcher.getInstance().callView("ItemNotFound", null);
				}
			}
			
			break;
			
	
		case "DeleteForm":
		
		MainDispatcher.getInstance().callView("DeleteForm", request);
		break;
	
	
		case "DeleteItem":
		int id1=Integer.parseInt(request.get("id").toString());
		itemService.deleteItem(id1);
		
		MainDispatcher.getInstance().callView("ItemMenu", null);
			break;
		}
		
		
		 
	}
}

