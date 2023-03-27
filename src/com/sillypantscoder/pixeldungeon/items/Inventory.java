package com.sillypantscoder.pixeldungeon.items;

import java.util.ArrayList;

public class Inventory {
	public InventorySlot[] slots;
	public Inventory() {
		slots = new InventorySlot[10];
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new InventorySlot();
		}
	}
	public void add(Item item) {
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].item == null) {
				slots[i].item = item;
				return;
			}
		}
	}
	public void update() {
		ArrayList<Item> items = new ArrayList<Item>();
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].item != null) {
				items.add(slots[i].item);
				slots[i].item = null;
			}
		}
		for (int i = 0; i < items.size(); i++) {
			slots[i].item = items.get(i);
		}
	}
	public void remove(Item item) {
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].item == item) {
				slots[i].item = null;
				update();
			}
		}
	}
}
