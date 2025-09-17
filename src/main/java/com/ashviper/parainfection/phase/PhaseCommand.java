package com.ashviper.parainfection.phase;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class PhaseCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("phase")
                .requires(source -> source.hasPermission(2)) // OP権限レベル2以上
                .then(Commands.literal("get")
                        .executes(context -> {
                            ServerLevel world = context.getSource().getLevel();
                            PhasePointData data = get(world);

                            float points = data.getPoints();
                            int phase = data.getPhase();

                            context.getSource().sendSuccess(() ->
                                    Component.literal("Points: " + points + ", Phase: " + phase), false);
                            return 1;
                        }))
                .then(Commands.literal("add")
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes(context -> {
                                    ServerLevel world = context.getSource().getLevel();
                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                    PhasePointData data = get(world);
                                    data.addPoints(amount);

                                    context.getSource().sendSuccess(() ->
                                            Component.literal("Added " + amount + " points. Now: " + data.getPoints()), false);
                                    return 1;
                                })))
                .then(Commands.literal("set")
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes(context -> {
                                    ServerLevel world = context.getSource().getLevel();
                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                    PhasePointData data = get(world);
                                    data.resetPoints();
                                    data.addPoints(amount);

                                    context.getSource().sendSuccess(() ->
                                            Component.literal("Points set to " + amount), false);
                                    return 1;
                                })))
                .then(Commands.literal("reset")
                        .executes(context -> {
                            ServerLevel world = context.getSource().getLevel();
                            PhasePointData data = get(world);
                            data.resetPoints();

                            context.getSource().sendSuccess(() ->
                                    Component.literal("Points reset to 0"), false);
                            return 1;
                        }))
                .then(Commands.literal("resetPhase")
                        .executes(context -> {
                            ServerLevel world = context.getSource().getLevel();
                            PhasePointData data = get(world);
                            data.resetPoints();

                            // 状態の初期化
                            com.ashviper.parainfection.phase.PhaseTickHandler.resetPhaseState(world);

                            context.getSource().sendSuccess(() ->
                                    Component.literal("フェーズ進行をリセットしました。"), false);
                            return 1;
                        }))
        );
    }

    private static PhasePointData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(
                PhasePointData::load, PhasePointData::new, "parainfection_phase");
    }
}