package me.iatog.conditionaldialog.listeners;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.iatog.conditionaldialog.ConditionalDialogPlugin;
import me.iatog.conditionaldialog.dialogs.DialogMethod;
import me.iatog.conditionaldialog.enums.ClickType;
import me.iatog.conditionaldialog.interfaces.FileFactory;
import me.iatog.conditionaldialog.libraries.YamlFile;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class NPCInteractEvent implements Listener {
	
	private ConditionalDialogPlugin main;
	
	public NPCInteractEvent(ConditionalDialogPlugin main) {
		this.main = main;
	}
	
	@EventHandler
	public void onNPCRightClick(NPCRightClickEvent event) {
		runEvent(event);
	}
	
	@EventHandler
	public void onNPCLeftClick(NPCLeftClickEvent event) {
		runEvent(event);
	}
	
	private void runEvent(NPCClickEvent event) {
		Player player = event.getClicker();
		NPC npc = event.getNPC();
		int id = npc.getId();
		FileFactory files = main.getFileFactory();
		YamlFile dialogsFile = files.getDialogs();
		if(!dialogsFile.contains("dialogs.npcs."+id)) {
			return;
		}
		ClickType clickType = ClickType.valueOf(dialogsFile.getString("dialogs.npcs."+id+".click"));
		if((event instanceof NPCRightClickEvent && clickType != ClickType.RIGHT) || (event instanceof NPCLeftClickEvent && clickType != ClickType.LEFT)) {
			return;
		}
		List<String> dialogs = dialogsFile.getStringList("dialogs.npcs."+id+".dialog");
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Runnable task = () -> {
			for(int i=0;i<dialogs.size();i++) {
				String dialog = dialogs.get(i);
				if(!dialog.contains(":")) {
					continue;
				}
				String[] splitted = dialog.split(":");
				String methodName = splitted[0].toUpperCase().trim();
				String arg = dialog.substring(methodName.length() + 1).trim();
				if(!main.getCache().getMethods().containsKey(methodName)) {
					main.getLogger().warning("The method \"" + methodName + "\" doesn't exist");
					return;
				}
				DialogMethod method = main.getCache().getMethods().get(methodName);
				method.cast(player, arg);
			}
		};
		
		executor.submit(task);
		executor.shutdown();
	}
	
}
