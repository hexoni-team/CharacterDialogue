package me.iatog.characterdialogue.loader.dialogue;

import me.iatog.characterdialogue.CharacterDialoguePlugin;
import me.iatog.characterdialogue.api.DialogueImpl;
import me.iatog.characterdialogue.libraries.Cache;
import me.iatog.characterdialogue.libraries.YamlFile;
import me.iatog.characterdialogue.loader.Loader;

public class DialogLoader implements Loader {
	
	private CharacterDialoguePlugin main;
	
	public DialogLoader(CharacterDialoguePlugin main) {
		this.main = main;
	}
	
	@Override
	public void load() {
		YamlFile dialogs = main.getFileFactory().getDialogs();
		Cache cache = main.getCache();
		
		dialogs.getConfigurationSection("dialogue").getKeys(false).forEach(name -> {
			cache.getDialogues().put(name, new DialogueImpl(main, name));
		});
	}

}
