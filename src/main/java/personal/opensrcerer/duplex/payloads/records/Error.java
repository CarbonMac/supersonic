package personal.opensrcerer.duplex.payloads.records;

import net.dv8tion.jda.api.entities.MessageEmbed;

public record Error(MessageEmbed embed, Throwable throwable) {}
