package homeostaticseasons.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;

import homeostaticseasons.HomeostaticSeasons;
import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;
import homeostaticseasons.api.SeasonChangeMethod;
import homeostaticseasons.config.ConfigHandler;
import homeostaticseasons.platform.Services;

public class SeasonCommand {

    private static final SuggestionProvider<CommandSourceStack> SUGGEST_SEASONS = (ctx, builder) -> {
        for (Season season : Season.values()) {
            builder.suggest(season.name());
        }

        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("season")
            .then(Commands.literal("set")
                .requires(cs -> isConfigured() && cs.hasPermission(2))
                .then(Commands.argument("season", StringArgumentType.string())
                    .suggests(SUGGEST_SEASONS)
                    .executes(
                        ctx -> setSeasonTime(
                            ctx.getSource(),
                            Season.valueOf(StringArgumentType.getString(ctx, "season"))
                        )
                    )
                )
            )
            .then(Commands.literal("skip")
                .requires(cs -> isConfigured() && cs.hasPermission(2))
                .then(Commands.argument("season", StringArgumentType.string())
                    .suggests(SUGGEST_SEASONS)
                    .executes(
                        ctx -> skipToSeason(
                            ctx.getSource(),
                            Season.valueOf(StringArgumentType.getString(ctx, "season"))
                        )
                    )
                )
            )
            .then(Commands.literal("query")
                .requires(SeasonCommand::isValidDimension)
                .executes(ctx -> {
                    ServerLevel level = ctx.getSource().getLevel();
                    Season currentSeason = HomeostaticSeasonsAPI.getCurrentSeason(level);
                    long ticksUntilNextSeason = HomeostaticSeasonsAPI.getTimeUntilNextSeason(level);

                    ctx.getSource().sendSuccess(() -> Component.translatable(
                        HomeostaticSeasons.MODID + ".command.query_season",
                        Component.translatable(
                            currentSeason.getTranslationKey()
                        )
                    ), false);

                    if (ConfigHandler.Common.seasonChangeMethod() != SeasonChangeMethod.FIXED) {
                        ctx.getSource().sendSuccess(() -> Component.translatable(
                            HomeostaticSeasons.MODID + ".command.query_next_season",
                            String.format("%.1f", (double) ticksUntilNextSeason / 24000D),
                            Long.toString(ticksUntilNextSeason),
                            Component.translatable((HomeostaticSeasonsAPI.getNextSeason(level, currentSeason)).getTranslationKey())
                        ), false);
                    }

                    return currentSeason.ordinal();
                })
            )
        );
    }

    private static int setSeasonTime(CommandSourceStack source, Season season) {
        for (ServerLevel serverlevel : source.getServer().getAllLevels()) {
            ServerLevelData levelData = Services.PLATFORM.getServerLevelData(serverlevel);
            long time = HomeostaticSeasonsAPI.getSeasonTime(serverlevel, season);

            if (time != -1L) {
                levelData.setGameTime(time);
                source.sendSuccess(() -> Component.translatable("commands.time.set", time), true);
            }
        }

        return (int)(source.getLevel().getDayTime() % 24000L);
    }

    private static int skipToSeason(CommandSourceStack source, Season season) {
        long currentTime = source.getLevel().getGameTime();

        for (ServerLevel serverlevel : source.getServer().getAllLevels()) {
            ServerLevelData levelData = Services.PLATFORM.getServerLevelData(serverlevel);
            long timeUntilSeason = HomeostaticSeasonsAPI.getTimeUntilSeason(serverlevel, season);

            if (timeUntilSeason != -1L) {
                long newTime = currentTime + timeUntilSeason;
                levelData.setGameTime(newTime);
                source.sendSuccess(() -> Component.translatable("commands.time.set", newTime), true);
            }
        }

        return (int)(source.getLevel().getDayTime() % 24000L);
    }

    private static boolean isConfigured() {
        return ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.CONFIGURED;
    }

    private static boolean isValidDimension(CommandSourceStack source) {
        return ConfigHandler.Common.isValidDimension(source.getLevel().dimension());
    }

}
