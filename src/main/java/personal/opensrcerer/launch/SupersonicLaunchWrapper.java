package personal.opensrcerer.launch;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import personal.opensrcerer.reactive.emitters.Emitter;
import personal.opensrcerer.reactive.emitters.slash.player.*;
import personal.opensrcerer.reactive.emitters.system.ReadyEmitter;
import personal.opensrcerer.reactive.emitters.slash.misc.MumEmitter;

import javax.security.auth.login.LoginException;
import java.util.stream.Collectors;

public abstract class SupersonicLaunchWrapper {
    public static void run() throws LoginException {
        SupersonicConstants.ENVIRONMENT_VARIABLES = System.getenv();

        SupersonicConstants.JDA = JDABuilder.create(
                SupersonicConstants.getVariable("DISCORD_TOKEN"),
                SupersonicConstants.cacheFlags
                        .stream()
                        .map(CacheFlag::getRequiredIntent)
                        .collect(Collectors.toSet()))
                .disableCache(SupersonicConstants.disabledCacheFlags)
                .enableCache(SupersonicConstants.cacheFlags)
                .setMemberCachePolicy(MemberCachePolicy.VOICE)
                .setEnableShutdownHook(true)
                .setActivity(
                        Activity.of(
                                SupersonicConstants.activity.getLeft(),
                                SupersonicConstants.activity.getRight()
                        )
                ).build();

        Emitter.addListeners(
                /* SYSTEM */
                new ReadyEmitter(),

                /* MISC */
                new MumEmitter(),

                /* PLAYER */
                new JoinVoiceEmitter(),
                new LeaveVoiceEmitter(),
                new PauseEmitter(),
                new UnpauseEmitter(),
                new PlayEmitter(),
                new SearchEmitter(),
                new SkipEmitter()
        );
    }
}
