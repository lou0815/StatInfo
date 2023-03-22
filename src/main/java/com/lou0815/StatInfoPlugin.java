package com.lou0815;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.Skill;

@Slf4j
@PluginDescriptor(
	name = "StatInfo"
)
public class StatInfoPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private StatInfoConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("StatInfoPlugin started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("StatInfoPlugin stopped!");
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		for (Skill s : Skill.values()) {
			if (client.getBoostedSkillLevel(s) != client.getRealSkillLevel(s))
			{
				int diff = client.getBoostedSkillLevel(s)-client.getRealSkillLevel(s);
				client.addChatMessage(ChatMessageType.GAMEMESSAGE,"StatInfo",s.toString() + " " + client.getBoostedSkillLevel(s) + "/" + client.getRealSkillLevel(s) + " " + diff,"");
			}
		}

	}

	@Provides
	StatInfoConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(StatInfoConfig.class);
	}
}
