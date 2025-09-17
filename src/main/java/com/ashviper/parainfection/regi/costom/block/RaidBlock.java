package com.ashviper.parainfection.regi.costom.block;

import com.ashviper.parainfection.entity.ParainfectionEntities;
import com.ashviper.parainfection.entity.goal.BreakRaidBlockGoal;
import com.ashviper.parainfection.regi.ModBlocks;
import com.ashviper.parainfection.regi.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RaidBlock extends Block {
    public static final IntegerProperty PHASE = IntegerProperty.create("phase", 1, 10);
    public static final Map<BlockPos, Set<UUID>> raidMobs = new ConcurrentHashMap<>();
    private static final Map<BlockPos, Long> activeRaids = new ConcurrentHashMap<>();
    private static final Map<BlockPos, Integer> remainingRaidRounds = new ConcurrentHashMap<>();
    private static final long RAID_DURATION = 20L * 60 * 1; // 1分間
    public static final Map<BlockPos, Long> pendingPhaseTransitions = new ConcurrentHashMap<>();

    public RaidBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PHASE, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PHASE);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) return InteractionResult.SUCCESS;
        ServerLevel serverLevel = (ServerLevel) level;

        if (player.isShiftKeyDown()) {
            if (activeRaids.containsKey(pos)) {
                player.sendSystemMessage(Component.literal("襲撃中は回数設定できません"));
                return InteractionResult.SUCCESS;
            }

            int currentRounds = remainingRaidRounds.getOrDefault(pos, 0);
            remainingRaidRounds.put(pos, currentRounds + 1);
            level.setBlock(pos, state.setValue(PHASE, 1), 3);

            player.sendSystemMessage(Component.literal("襲撃回数を " + (currentRounds + 1) + " に設定しました"));
        } else {
            if (activeRaids.containsKey(pos)) {
                int currentPhase = state.getValue(PHASE);
                player.sendSystemMessage(Component.literal("現在のフェーズ: " + currentPhase));
                return InteractionResult.SUCCESS;
            }

            int remaining = remainingRaidRounds.getOrDefault(pos, 0);
            if (remaining <= 0) {
                player.sendSystemMessage(Component.literal("襲撃回数が設定されていません"));
                return InteractionResult.SUCCESS;
            }

            int phase = state.getValue(PHASE);
            startRaid(serverLevel, pos, player, phase);
            activeRaids.put(pos, serverLevel.getGameTime() + RAID_DURATION);
        }

        return InteractionResult.SUCCESS;
    }

    private void startRaid(ServerLevel level, BlockPos center, Player player, int phase) {
        int radius = 150;
        int mobCount = 10 + phase * 10;
        if (mobCount > 90) {
            mobCount = 90;
        }

        RandomSource random = level.random;
        raidMobs.put(center, new HashSet<>());

        // 音を鳴らす
        level.playSound(null, center, SoundEvents.RAID_HORN.get(), net.minecraft.sounds.SoundSource.BLOCKS, 1.5F, 1.0F);

        int successfulSpawns = 0;
        int attempts = 0;

        while (successfulSpawns < mobCount) {
            attempts++;

            // 円形範囲内でランダムな位置を生成
            double angle = random.nextDouble() * 2 * Math.PI;
            double distance = Math.sqrt(random.nextDouble()) * radius; // より均等な分布

            int x = (int) (center.getX() + Math.cos(angle) * distance);
            int z = (int) (center.getZ() + Math.sin(angle) * distance);

            // 安全なスポーン位置を見つける
            BlockPos spawnPos = findSafeSpawnPos(level, new BlockPos(x, center.getY(), z));
            if (spawnPos != null) {
                if (spawnMobForPhase(level, spawnPos, phase, center)) {
                    successfulSpawns++;
                }
            }
        }

        // 周辺のプレイヤーにタイトルを表示
        showTitleToNearbyPlayers(level, center, phase, successfulSpawns);
    }

    /**
     * 安全なスポーン位置を見つける
     */
    private BlockPos findSafeSpawnPos(ServerLevel level, BlockPos basePos) {
        // 地表の高さを取得
        int groundY = level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                basePos.getX(), basePos.getZ());

        BlockPos groundPos = new BlockPos(basePos.getX(), groundY, basePos.getZ());

        // 地面が固体ブロックで、その上の2ブロックが空気かチェック
        if (isValidSpawnLocation(level, groundPos)) {
            return groundPos;
        }

        // 上下に検索して適切な位置を探す
        for (int offset = 1; offset <= 10; offset++) {
            BlockPos upPos = groundPos.above(offset);
            BlockPos downPos = groundPos.below(offset);

            // 上方向をチェック
            if (isValidSpawnLocation(level, upPos)) {
                return upPos;
            }

            // 下方向をチェック（地下に埋まらないよう最小Y制限）
            if (downPos.getY() > level.getMinBuildHeight() + 5 && isValidSpawnLocation(level, downPos)) {
                return downPos;
            }
        }

        return null; // 適切な位置が見つからない
    }

    /**
     * スポーン位置が有効かチェック
     */
    private boolean isValidSpawnLocation(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolidRender(level, pos.below()) &&
                level.getBlockState(pos).isAir() &&
                level.getBlockState(pos.above()).isAir() &&
                pos.getY() > level.getMinBuildHeight() &&
                pos.getY() < level.getMaxBuildHeight() - 1;
    }

    private boolean spawnMobForPhase(ServerLevel level, BlockPos pos, int phase, BlockPos raidBlockPos) {
        EntityType<? extends Monster> type = selectMobTypeForPhase(phase, level.random);
        if (type == null) return false;

        try {
            Mob mob = type.create(level);
            if (mob != null) {
                mob.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                        level.random.nextFloat() * 360F, 0);

                // まずエンティティだけ追加（AIゴールは後付け）
                level.addFreshEntity(mob);

                // ゴール追加は遅延させる
                level.getServer().execute(() -> {
                    Block raidBlock = level.getBlockState(raidBlockPos).getBlock();
                    mob.goalSelector.addGoal(5, new BreakRaidBlockGoal(mob, raidBlockPos, raidBlock));
                });

                raidMobs.computeIfAbsent(raidBlockPos, k -> new HashSet<>())
                        .add(mob.getUUID());

                return true;
            }
        } catch (Exception e) {
            // LOGGER.warn("Failed to spawn mob for raid at {}: {}", pos, e.getMessage());
        }
        return false;
    }


    /**
     * フェーズに応じたモブタイプを選択
     */
    private EntityType<? extends Monster> selectMobTypeForPhase(int phase, RandomSource random) {
        if (phase < 5) {
            int r = random.nextInt(3);
            return switch (r) {
                case 0 -> ParainfectionEntities.CORRODEDCOW.get();
                case 1 -> ParainfectionEntities.CORRODEDZOMBIE.get();
                default -> ParainfectionEntities.GNAWLING.get();
            };
        } else if (phase < 10) {
            int r = random.nextInt(5);
            return switch (r) {
                case 0 -> ParainfectionEntities.CORRODEDCHIKEN.get();
                case 1 -> ParainfectionEntities.CORRODEDZOMBIE.get();
                case 2 -> ParainfectionEntities.CORRODEDCOW.get();
                case 3 -> ParainfectionEntities.CORRODEDSHEEP.get();
                default -> ParainfectionEntities.GNAWLING.get();
            };
        } else {
            int r = random.nextInt(6);
            return switch (r) {
                case 0 -> ParainfectionEntities.CORRODEDCHIKEN.get();
                case 1 -> ParainfectionEntities.CORRODEDZOMBIE.get();
                case 2 -> ParainfectionEntities.CORRODEDCOW.get();
                case 3 -> ParainfectionEntities.CORRODEDSHEEP.get();
                case 4 -> ParainfectionEntities.CORRODEDPIG.get();
                default -> ParainfectionEntities.GNAWLING.get();
            };
        }
    }

    private void dropReward(ServerLevel level, BlockPos pos, int phase) {
        ItemStack reward = switch (phase) {
            case 10 -> new ItemStack(Items.NETHER_STAR);
            case 5 -> new ItemStack(Items.DIAMOND);
            default -> new ItemStack(Items.EMERALD, phase);
        };

        BlockPos dropPos = getSafeDropPosition(level, pos);
        level.playSound(null, dropPos, SoundEvents.ITEM_PICKUP, net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
        Block.popResource(level, dropPos, reward);
    }

    private BlockPos getSafeDropPosition(Level level, BlockPos basePos) {
        BlockPos twoAbove = basePos.above(2);
        if (level.getBlockState(twoAbove).isAir()) return twoAbove;

        BlockPos oneAbove = basePos.above();
        if (level.getBlockState(oneAbove).isAir()) return oneAbove;

        return basePos;
    }

    public static void tick(ServerLevel level) {
        long time = level.getGameTime();
        List<BlockPos> newActiveRaidPositions = new ArrayList<>();

        activeRaids.entrySet().removeIf(entry -> {
            BlockPos pos = entry.getKey();
            long endTime = entry.getValue();

            if (time >= endTime) {
                BlockState state = level.getBlockState(pos);
                if (!(state.getBlock() instanceof RaidBlock raidBlock)) return true;

                int phase = state.getValue(PHASE);
                raidBlock.dropReward(level, pos, phase);

                int remaining = remainingRaidRounds.getOrDefault(pos, 0) - 1;

                if (remaining > 0) {
                    int nextPhase = phase >= 10 ? 1 : phase + 1;
                    level.setBlock(pos, state.setValue(PHASE, nextPhase), 3);
                    raidBlock.startRaid(level, pos, null, nextPhase);
                    newActiveRaidPositions.add(pos);
                    remainingRaidRounds.put(pos, remaining);
                } else {
                    Block.popResource(level, raidBlock.getSafeDropPosition(level, pos), new ItemStack(ModItems.MINI_SUIREN.get()));
                    level.setBlock(pos, state.setValue(PHASE, 1), 3);
                    remainingRaidRounds.remove(pos);
                    cleanupRaidData(pos);
                }

                return true;
            }

            return false;
        });

        // フェーズ遷移を処理
        pendingPhaseTransitions.entrySet().removeIf(entry -> {
            BlockPos pos = entry.getKey();
            long scheduledTime = entry.getValue();

            if (time >= scheduledTime) {
                onRaidPhaseClear(level, pos);
                return true;
            }
            return false;
        });

        for (BlockPos pos : newActiveRaidPositions) {
            activeRaids.put(pos, time + RAID_DURATION);
        }
    }

    /**
     * 周辺のプレイヤーにタイトルを表示
     */
    private void showTitleToNearbyPlayers(ServerLevel level, BlockPos center, int phase, int mobCount) {
        double titleRadius = 200.0; // タイトル表示範囲

        Component titleComponent = Component.literal("フェーズ " + phase + " の襲撃が始まった！");
        Component subtitleComponent = Component.literal("健闘を祈る！ (" + mobCount + "体のモブがスポーンしました)");

        level.players().forEach(player -> {
            if (player.blockPosition().distSqr(center) <= titleRadius * titleRadius) {
                player.connection.send(new ClientboundSetTitleTextPacket(titleComponent));
                player.connection.send(new ClientboundSetSubtitleTextPacket(subtitleComponent));
            }
        });

        // 爆発音を追加
        level.playSound(
                null,                      // 全員に聞こえる
                center,                    // 中心座標
                SoundEvents.RAID_HORN.get(),
                net.minecraft.sounds.SoundSource.BLOCKS,
                1.5F,                      // 音量
                1.0F                       // ピッチ
        );
    }

    /**
     * 襲撃データのクリーンアップ
     */
    private static void cleanupRaidData(BlockPos pos) {
        raidMobs.remove(pos);
        pendingPhaseTransitions.remove(pos);
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(oldState, level, pos, newState, isMoving);

        super.onRemove(oldState, level, pos, newState, isMoving);

        if (!level.isClientSide && oldState.getBlock() != newState.getBlock()) {
            // 通常のクリーンアップ
            activeRaids.remove(pos);
            remainingRaidRounds.remove(pos);
            cleanupRaidData(pos);

            // ---- 追加部分: 破壊通知 ----
            if (level instanceof ServerLevel serverLevel) {
                Component title = Component.literal("襲撃拠点が破壊された！");

                double notifyRadius = 200.0; // 通知を受ける半径
                for (ServerPlayer sp : serverLevel.players()) {
                    if (sp.blockPosition().distSqr(pos) <= notifyRadius * notifyRadius) {
                        sp.connection.send(new ClientboundSetTitleTextPacket(title));
                    }
                }

                // 音を追加しても良い
                serverLevel.playSound(null, pos, SoundEvents.GENERIC_EXPLODE,
                        net.minecraft.sounds.SoundSource.BLOCKS, 1.5F, 1.0F);
            }
        }
    }

    public static void onRaidPhaseClear(ServerLevel level, BlockPos pos) {
        if (!activeRaids.containsKey(pos)) return;

        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof RaidBlock raidBlock)) return;

        int currentPhase = state.getValue(PHASE);
        raidBlock.dropReward(level, pos, currentPhase);

        int remaining = remainingRaidRounds.getOrDefault(pos, 0) - 1;

        if (remaining > 0) {
            int nextPhase = currentPhase >= 10 ? 1 : currentPhase + 1;
            level.setBlock(pos, state.setValue(PHASE, nextPhase), 3);
            raidBlock.startRaid(level, pos, null, nextPhase);
            activeRaids.put(pos, level.getGameTime() + RAID_DURATION);
            remainingRaidRounds.put(pos, remaining);
        } else {
            Block.popResource(level, raidBlock.getSafeDropPosition(level, pos), new ItemStack(ModItems.MINI_SUIREN.get()));
            level.setBlock(pos, state.setValue(PHASE, 1), 3);
            activeRaids.remove(pos);
            remainingRaidRounds.remove(pos);
        }

        raidMobs.remove(pos);
    }
}