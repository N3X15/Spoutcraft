package org.getspout.spout.gui.shortcuts;

import org.getspout.spout.controls.Shortcut;
import org.lwjgl.input.Keyboard;
import org.spoutcraft.spoutcraftapi.gui.Button;
import org.spoutcraft.spoutcraftapi.gui.GenericButton;
import org.spoutcraft.spoutcraftapi.gui.GenericLabel;
import org.spoutcraft.spoutcraftapi.gui.GenericTextField;
import org.spoutcraft.spoutcraftapi.gui.Label;
import org.spoutcraft.spoutcraftapi.gui.TextField;
import org.spoutcraft.spoutcraftapi.gui.WidgetAnchor;

import net.minecraft.src.GuiScreen;

public class GuiEditShortcut extends GuiScreen {
	GuiCommandShortcuts parent;
	Shortcut item;
	Button recordButton, doneButton, addButton, editButton, removeButton;
	Label titleLabel, recordLabel;
	TextField titleText;
	GuiCommandsSlot slot;
	
	boolean recording = false;
	public GuiEditShortcut(GuiCommandShortcuts parent, Shortcut item) {
		this.parent = parent;
		this.item = item;
	}
	
	public void drawScreen(int var1, int var2, float var3) {
		drawDefaultBackground();
		slot.drawScreen(var1, var2, var3);
		super.drawScreen(var1, var2, var3);
	}
	
	protected void keyTyped(char c, int i) {
		if(recording) {
			item.setKey(i);
			recording = false;
			updateRecordButton();
		} else {
			super.keyTyped(c, i);
		}
	}
	
	public void initGui() {
		recordLabel = new GenericLabel("Key:");
		recordLabel.setAlign(WidgetAnchor.TOP_LEFT);
		recordLabel.setWidth(50).setHeight(20);
		recordLabel.setX(10).setY(40);
		getScreen().attachWidget("Spoutcraft", recordLabel);
		
		recordButton = new GenericButton();
		recordButton.setAlign(WidgetAnchor.CENTER_CENTER);
		recordButton.setHeight(20).setWidth(200);
		recordButton.setX(70).setY(40);
		getScreen().attachWidget("Spoutcraft", recordButton);
		updateRecordButton();
		
		titleLabel = new GenericLabel("Name:");
		titleLabel.setAlign(WidgetAnchor.TOP_LEFT);
		titleLabel.setWidth(50).setHeight(20);
		titleLabel.setX(10).setY(10);
		getScreen().attachWidget("Spoutcraft", titleLabel);
		
		titleText = new GenericTextField();
		titleText.setHeight(20).setWidth(200);
		titleText.setX(70).setY(10);
		titleText.setText(item.getTitle());
		titleText.setFocus(true);
		getScreen().attachWidget("Spoutcraft", titleText);
		
		slot = new GuiCommandsSlot(this);
		
		doneButton = new GenericButton("Done");
		doneButton.setHeight(20).setWidth(50);
		doneButton.setX(10).setY(height-30);
		getScreen().attachWidget("Spoutcraft", doneButton);
		
		addButton = new GenericButton("Add Command");
		addButton.setHeight(20).setWidth(100);
		addButton.setX(70).setY(height-30);
		getScreen().attachWidget("Spoutcraft", addButton);
		
		editButton = new GenericButton("Edit Command");
		editButton.setHeight(20).setWidth(100);
		editButton.setX(180).setY(height-30);
		getScreen().attachWidget("Spoutcraft", editButton);
		
		removeButton = new GenericButton("Remove Command");
		removeButton.setHeight(20).setWidth(100);
		removeButton.setX(290).setY(height-30);
		getScreen().attachWidget("Spoutcraft", removeButton);
		
		updateButtons();
	}
	
	private void updateRecordButton() {
		String keyname = recording?"Press a key!":"Click Here!";
		if(item.getKey()>=0 && !recording){
			keyname = Keyboard.getKeyName(item.getKey());
		}
		String name = (recording?"> ":"")+keyname+(recording?" <":"");
		recordButton.setText(name);
	}

	protected void buttonClicked(Button btn) {
		if(btn.equals(recordButton)){
			recording = !recording;
			updateRecordButton();
		}
		if(btn.equals(doneButton)){
			item.setTitle(titleText.getText());
			if(!item.getTitle().equals("") && item.getKey() != -1) {
				parent.getManager().unregisterShortcut(item);
				parent.getManager().registerShortcut(item);
			}
			mc.displayGuiScreen(parent);
		}
		if(btn.equals(addButton)){
			editCommand(-1);
		}
		if(btn.equals(editButton)){
			editCommand(slot.getSelected());
		}
		if(btn.equals(removeButton)){
			item.removeCommand(slot.getSelected());
			slot.updateSelected();
			updateButtons();
		}
	}
	
	public void updateButtons() {
		editButton.setEnabled(slot.getSelected() != -1);
		removeButton.setEnabled(slot.getSelected() != -1);
	}

	public void editCommand(int i) {
		item.setTitle(titleText.getText());
		GuiEditCommand gui = new GuiEditCommand(this, i);
		mc.displayGuiScreen(gui);
	}

	public Shortcut getShortcut() {
		return item;
	}
}
