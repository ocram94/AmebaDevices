package com.AmebaDevices.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AmebaDevices.converter.ItemConverter;
import com.AmebaDevices.dto.BuildingDTO;
import com.AmebaDevices.dto.FloorDTO;
import com.AmebaDevices.dto.ItemDTO;
import com.AmebaDevices.dto.ItemTypeDTO;
import com.AmebaDevices.dto.RoomDTO;
import com.AmebaDevices.dto.ThingDTO;
import com.AmebaDevices.model.Building;
import com.AmebaDevices.model.Floor;
import com.AmebaDevices.model.Item;
import com.AmebaDevices.model.ItemType;
import com.AmebaDevices.model.Room;
import com.AmebaDevices.services.ItemService;
import com.AmebaDevices.services.ItemTypeService;
import com.AmebaDevices.services.RoomService;
import com.AmebaDevices.services.ThingService;
import com.AmebaDevices.utils.GestoreEccezioni;

@RestController
@RequestMapping("/Item")
public class ItemController  {
	private ItemService itemService;
	private RoomService roomService;
	private ItemTypeService itemTypeService;
	private ThingService thingService;

	@Autowired
	public ItemController(ItemService itemService, RoomService roomService, ItemTypeService itemTypeService, ThingService thingService) {
		this.itemService =itemService; 
		this.roomService=roomService;
		this.itemTypeService=itemTypeService;
		this.thingService = thingService;
	}
	
	// INSERT
		@RequestMapping(value = "/new", method = RequestMethod.POST)
		public ItemDTO newItem(HttpServletRequest request) {
			ItemDTO idto = new ItemDTO();
			RoomDTO room = roomService.findByPrimaryKey(Long.parseLong(request.getParameter("roomId")));
			ItemTypeDTO itemType = itemTypeService.findByPrimaryKey(Long.parseLong(request.getParameter("itemTypeId")));
			ThingDTO thing = thingService.searchThing(Long.parseLong(request.getParameter("thingId")));
			if (room != null) idto.setRoom(room);
			if (itemType != null ) idto.setItemType(itemType);
			if (thing != null ) idto.setThing(thing);
			String consumo = request.getParameter("consumoEnergetico");
			String seriale = request.getParameter("seriale");
			if (consumo != null) idto.setConsumoEnergetico(consumo);
			if (seriale != null) idto.setSeriale(seriale);
			
			idto = itemService.insertItem(idto);
			return idto;

		}
		
	// READ
		@RequestMapping (value ="", method = RequestMethod.GET)
		public ItemDTO getOne(@RequestParam(value="itemId") long itemId) {
			return itemService.searchItem(itemId);
		}
		
	// UPDATE
		@RequestMapping (value="/edit", method= RequestMethod.POST)
		public ItemDTO updateItem (HttpServletRequest request, @RequestParam(value="itemId") long itemId){
			ItemDTO myItem = itemService.searchItem(itemId);
			String itemTypeId = request.getParameter("itemType");
			String roomId = request.getParameter("room");
			String thingId = request.getParameter("thing");
			String consumoEnergetico = request.getParameter("consumoEnergetico");
			String seriale = request.getParameter("seriale");
			if (itemTypeId != null) {
				ItemTypeDTO itemType = itemTypeService.searchItemType(Long.parseLong(itemTypeId));
				myItem.setItemType(itemType);
			}
			if (roomId != null ) {
				RoomDTO room = roomService.findByPrimaryKey(Long.parseLong(roomId));
				myItem.setRoom(room);
			}
			if (thingId != null ) {
				ThingDTO thing = thingService.searchThing(Long.parseLong(thingId));
				myItem.setThing(thing);
			}
			if (consumoEnergetico != null) {
				myItem.setConsumoEnergetico(consumoEnergetico);
			}
			if (seriale!= null) {
				myItem.setSeriale(seriale);
			}
			
			return itemService.updateItem(myItem);
		}
	

	// DELETE
		@RequestMapping (value = "delete", method = RequestMethod.GET)
		public boolean delete (@RequestParam(value="itemId") long itemId) {
			return itemService.deleteItem(itemId);
		}
	
//		
//		
//	@RequestMapping(value = "insertForm", method = RequestMethod.GET)
//	public String insertForm(HttpServletRequest request) {
//		String id = (String) request.getAttribute("roomId");
//		return "CensimentoItemForm";
//	}
//
//	@RequestMapping(value = "insert", method = RequestMethod.POST)
//	public String insert(HttpServletRequest request) {
//		String consumoEnergetico = request.getParameter("consumoEnergetico");
//		String seriale = request.getParameter("seriale");
//		String id = (String) request.getParameter("roomId");
//		RoomDTO room= roomService.findByPrimaryKey(Long.parseLong(id));
//		ItemDTO item = new ItemDTO();
//		item.setConsumoEnergetico(consumoEnergetico);
//		item.setSeriale(seriale);
//		item.setRoom(room);
//		itemService.insertItem(item);
//		request.setAttribute("roomId", id);
//		List <ItemDTO> listaPerRoom = new ArrayList<>();
//		listaPerRoom = itemService.getAllByRoom(room);
//		request.setAttribute("items", listaPerRoom);
//		return "ItemMenu";
//	}
//
//	@RequestMapping(value = "/menu", method = RequestMethod.GET)
//	public String menu(HttpServletRequest request) {
//		long roomId=Long.parseLong(request.getParameter("roomId"));
//		RoomDTO room= roomService.findByPrimaryKey(roomId);
//		List<ItemTypeDTO> tipologieDisponibili= new ArrayList<>();
//		tipologieDisponibili= itemTypeService.getAllItemType();
//		if (tipologieDisponibili.size() == 0) tipologieDisponibili = new ArrayList<>();
//		System.out.println(tipologieDisponibili.size());
//		request.setAttribute("availableItems", tipologieDisponibili);
//		List <ItemDTO> yourItems = itemService.getAllByRoom(room);
//		if (yourItems.size() == 0) yourItems = new ArrayList<>();
//		System.out.println(yourItems.size());
//		request.setAttribute("yourItems", yourItems);
//		request.setAttribute("roomId", String.valueOf(roomId));
//		return "ItemMenu";
//	}
//
//
//	@RequestMapping(value = "/updateForm", method = RequestMethod.GET)
//	public String updateForm(HttpServletRequest request) {
//		long roomId=Long.parseLong(request.getParameter("roomId"));
//		request.setAttribute("roomId", String.valueOf(roomId));
//		return "ModificaItemForm";
//	}
//
//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	public String update(HttpServletRequest request) {
//		Long id = (long) Integer.parseInt(request.getParameter("id"));
//		String consumoEnergetico = request.getParameter("consumoEnergetico");
//		String seriale = request.getParameter("seriale");
//		String roomdiD= request.getParameter("roomId");
//		RoomDTO room= roomService.findByPrimaryKey(Long.parseLong(roomdiD));
//
//		ItemDTO item = new ItemDTO();
//		item.setId(id);
//		item.setConsumoEnergetico(consumoEnergetico);
//		item.setSeriale(seriale);
//		item.setRoom(room);
//		itemService.updateItem(item);
//		List<ItemDTO> listaPerRoom= new ArrayList<>();
//		listaPerRoom= itemService.getAllByRoom(room); 
//		request.setAttribute("items", listaPerRoom);
//
//		//ItemType updatedItem = itemTypeService.searchItemType(id);
//		//request.setAttribute("item", updatedItem);
//		return "ItemMenu";
//	}
//	
//	@RequestMapping(value = "/addInRoom", method = RequestMethod.GET)
//	public String addNewItem(HttpServletRequest request) {
//		
//		long itemTypeId = Long.parseLong(request.getParameter("itemId"));
//		long roomId=Long.parseLong(request.getParameter("roomId"));
//		String consumoEnergetico = request.getParameter("consumoEnergetico");
//		String seriale = request.getParameter("seriale");
//		RoomDTO room= roomService.findByPrimaryKey(roomId);
//		ItemTypeDTO it = itemTypeService.findByPrimaryKey(itemTypeId);
//		ItemDTO item = new ItemDTO();
//		item.setConsumoEnergetico(consumoEnergetico);
//		item.setSeriale(seriale);
//		item.setRoom(room);
//		item.setItemType(it);
//		itemService.insertItem(item);
//		request.setAttribute("roomId", String.valueOf(roomId));
//		List<ItemTypeDTO> tipologieDisponibili= new ArrayList<>();
//		tipologieDisponibili= itemTypeService.getAllItemType();
//		request.setAttribute("availableItems", tipologieDisponibili);
//		List <ItemDTO> yourItems = itemService.getAllByRoom(room);
//		request.setAttribute("yourItems", yourItems);
//		return "ItemMenu";
//	}
//	
//	@RequestMapping(value = "/removeFromRoom", method = RequestMethod.GET)
//	public String removeFromRoom(HttpServletRequest request) {
//		
//		long itemId = Long.parseLong(request.getParameter("itemId"));
//		long roomId=Long.parseLong(request.getParameter("roomId"));
//		RoomDTO room= roomService.findByPrimaryKey(roomId);
//		itemService.deleteItem(itemId);
//		
//		request.setAttribute("roomId", String.valueOf(roomId));
//		List<ItemTypeDTO> tipologieDisponibili= itemTypeService.getAllItemType();
//		if (tipologieDisponibili.size() == 0) tipologieDisponibili = new ArrayList<>();
//		request.setAttribute("availableItems", tipologieDisponibili);
//		List <ItemDTO> yourItems = itemService.getAllByRoom(room);
//		if (yourItems.size() == 0 ) yourItems = new ArrayList<>();
//		request.setAttribute("yourItems", yourItems);
//		return "ItemMenu";
//	}
//
//	@RequestMapping(value = "/deleteForm", method = RequestMethod.GET)
//	public String deleteForm(HttpServletRequest request) {
//		long roomId=Long.parseLong(request.getParameter("roomId"));
//		request.setAttribute("roomId", String.valueOf(roomId));
//		return "EliminaItemForm";
//	}
//
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	public String delete(HttpServletRequest request) {
//		String deleteId = request.getParameter("id");
//		long roomId = Long.parseLong(request.getParameter("roomId"));
//		RoomDTO room = roomService.findByPrimaryKey(roomId);
//		
//		itemService.deleteItem((long) Integer.parseInt(deleteId));
//		List<ItemDTO> listaPerRoom= new ArrayList<>();
//		listaPerRoom= itemService.getAllByRoom(room); 
//		request.setAttribute("items", listaPerRoom);
//		return "ItemMenu";
//	}
//
//	@RequestMapping(value = "/goBack", method = RequestMethod.GET)
//	public String goBack(HttpServletRequest request) {
//		return "ItemMenu";
//	}
}
