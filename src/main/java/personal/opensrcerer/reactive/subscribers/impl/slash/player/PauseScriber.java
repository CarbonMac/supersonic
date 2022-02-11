package personal.opensrcerer.reactive.subscribers.impl.slash.player;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import personal.opensrcerer.aspect.annotations.BoundTo;
import personal.opensrcerer.aspect.annotations.Subscriber;
import personal.opensrcerer.aspect.mapping.EventMappingStrategy;
import personal.opensrcerer.config.SlashCommand;
import personal.opensrcerer.reactive.payloads.impl.slash.PauseEvent;
import personal.opensrcerer.reactive.subscribers.abstractions.SlashCommandSuperscriber;
import personal.opensrcerer.services.audio.MusicPlayer;

@Subscriber(typeToHandle = SlashCommandEvent.class,
        strategy = EventMappingStrategy.SLASHCOMMAND_TO_PAUSE)
@BoundTo(SlashCommand.PAUSE)
public class PauseScriber extends SlashCommandSuperscriber<PauseEvent> {
    @Override
    public void onEvent(PauseEvent boxed) {
        SlashCommandEvent event = boxed.raw();
        MusicPlayer.MUSIC_PLAYER.pause(event.getGuild());
        event.reply("Player paused!").queue();
    }
}
