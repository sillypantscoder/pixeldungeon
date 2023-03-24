package com.sillypantscoder.pixeldungeon.items;

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
}
