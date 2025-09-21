package com.ashviper.parainfection.regi;

import com.ashviper.parainfection.ParaInfectionMod;
import com.ashviper.parainfection.entity.custom.*;
import com.ashviper.parainfection.entity.custom.Adapted.*;
import com.ashviper.parainfection.entity.custom.Developed.*;
import com.ashviper.parainfection.entity.custom.Purebred.*;
import com.ashviper.parainfection.entity.custom.Corroded.*;
import com.ashviper.parainfection.entity.renderer.Adapted.*;
import com.ashviper.parainfection.entity.renderer.*;
import com.ashviper.parainfection.entity.renderer.Developed.*;
import com.ashviper.parainfection.entity.renderer.Purebred.*;
import com.ashviper.parainfection.entity.renderer.Corroded.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Mob登録を一元管理
 */
@Mod.EventBusSubscriber(modid = ParaInfectionMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParaInfectionMobs {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ParaInfectionMod.MODID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ParaInfectionMod.MODID);

    // 一括管理リスト
    private static final List<MobEntry<?>> ENTRIES = new ArrayList<>();

    /** Mobを一括登録するヘルパー */
    private static <T extends Mob> RegistryObject<EntityType<T>> registerMob(
            String name,
            EntityType.EntityFactory<T> factory,
            MobCategory category,
            float width, float height,
            Supplier<AttributeSupplier.Builder> attributes,
            EntityRendererProviderFactory<T> rendererFactory
    ) {
        RegistryObject<EntityType<T>> type = ENTITY_TYPES.register(name,
                () -> EntityType.Builder.of(factory, category).sized(width, height).build(name));

        // spawn egg
        ITEMS.register(name + "_egg", () -> new ForgeSpawnEggItem(
                type, 0xAABBCC, 0x112233, new Item.Properties().stacksTo(64)));

        // エントリをまとめて保存
        ENTRIES.add(new MobEntry<>(type, attributes, rendererFactory));
        return type;
    }

    private static <T extends Mob> RegistryObject<EntityType<T>> registerMob(
            String name,
            EntityType.EntityFactory<T> factory,
            EntityRendererProviderFactory<T> rendererFactory,
            float width, float height,
            Supplier<AttributeSupplier.Builder> attributes
    ) {
        RegistryObject<EntityType<T>> type = ENTITY_TYPES.register(name,
                () -> EntityType.Builder.of(factory, MobCategory.MONSTER).sized(width, height).build(name));

        // spawn egg を自動生成
        ITEMS.register(name + "_egg", () -> new ForgeSpawnEggItem(
                type,
                0xAABBCC,  // メインカラー
                0x112233,  // スポットカラー
                new Item.Properties().stacksTo(64)
        ));

        ENTRIES.add(new MobEntry<>(type, attributes, rendererFactory));
        return type;
    }

    /** 呼び出し用インターフェース（renderer登録用） */
    @FunctionalInterface
    public interface EntityRendererProviderFactory<T extends Mob> {
        net.minecraft.client.renderer.entity.EntityRendererProvider<T> create();
    }

    /** 管理用データ */
    private record MobEntry<T extends Mob>(
            RegistryObject<EntityType<T>> type,
            Supplier<AttributeSupplier.Builder> attributes,
            EntityRendererProviderFactory<T> renderer
    ) {}

    // ==========================
    // Event Hooks
    // ==========================
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        for (MobEntry<?> e : ENTRIES) {
            event.put(e.type().get(), e.attributes().get().build());
        }
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        for (MobEntry<?> e : ENTRIES) {
            registerRendererUnchecked(event, e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void registerRendererUnchecked(EntityRenderersEvent.RegisterRenderers event, MobEntry<?> e) {
        event.registerEntityRenderer(
                (EntityType) e.type().get(),
                (EntityRendererProvider) e.renderer().create()
        );
    }

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
        ITEMS.register(bus);
    }

    // ==========================
// Mob 登録（ここだけ書けばOK）
// ==========================
    public static final RegistryObject<EntityType<GnawlingEntity>> GNAWLING = registerMob(
            "gnawling",
            GnawlingEntity::new, MobCategory.MONSTER, 1.0f, 0.5f,
            GnawlingEntity::createAttributes,
            () -> GnawlingEntityRenderer::new
    );

    public static final RegistryObject<EntityType<LarvaxEntity>> LARVAX = registerMob(
            "larvax",
            LarvaxEntity::new, MobCategory.MONSTER, 1.0f, 0.5f,
            LarvaxEntity::createAttributes,
            () -> LarvaxEntityRenderer::new
    );

    public static final RegistryObject<EntityType<DevoraEntity>> DEVORA = registerMob(
            "devora",
            DevoraEntity::new, MobCategory.MONSTER, 1.4f, 1.0f,
            DevoraEntity::createAttributes,
            () -> DevoraEntityRenderer::new
    );

    public static final RegistryObject<EntityType<WretchspawnEntity>> WRETCHSPAWN = registerMob(
            "wretchspawn",
            WretchspawnEntity::new, MobCategory.MONSTER, 1.2f, 1.2f,
            WretchspawnEntity::createAttributes,
            () -> WretchspawnRenderer::new
    );

    public static final RegistryObject<EntityType<IncompletefieldEntity>> INCOMPLETEFIELD = registerMob(
            "incompletefield",
            IncompletefieldEntity::new, MobCategory.MONSTER, 0.9f, 1.5f,
            IncompletefieldEntity::createAttributes,
            () -> IncompletefieldRenderer::new
    );

    // Corroded 系
    public static final RegistryObject<EntityType<CorrodedCowEntity>> CORRODEDCOW = registerMob(
            "corroded_cow",
            CorrodedCowEntity::new, MobCategory.MONSTER, 0.9f, 1.4f,
            CorrodedCowEntity::createAttributes,
            () -> CorrodedCowEntityRenderer::new
    );

    public static final RegistryObject<EntityType<CorrodedPigEntity>> CORRODEDPIG = registerMob(
            "corroded_pig",
            CorrodedPigEntity::new, MobCategory.MONSTER, 0.9f, 0.9f,
            CorrodedPigEntity::createAttributes,
            () -> CorrodedPigEntityRenderer::new
    );

    public static final RegistryObject<EntityType<CorrodedChikenEntity>> CORRODEDCHIKEN = registerMob(
            "corroded_chiken",
            CorrodedChikenEntity::new, MobCategory.MONSTER, 0.6f, 0.8f,
            CorrodedChikenEntity::createAttributes,
            () -> CorrodedChikenEntityRenderer::new
    );

    public static final RegistryObject<EntityType<CorrodedSheepEntity>> CORRODEDSHEEP = registerMob(
            "corroded_sheep",
            CorrodedSheepEntity::new, MobCategory.MONSTER, 0.9f, 1.3f,
            CorrodedSheepEntity::createAttributes,
            () -> CorrodedSheepEntityRenderer::new
    );

    public static final RegistryObject<EntityType<CorrodedZombieEntity>> CORRODEDZOMBIE = registerMob(
            "corroded_zombie",
            CorrodedZombieEntity::new, MobCategory.MONSTER, 0.9f, 1.8f,
            CorrodedZombieEntity::createAttributes,
            () -> CorrodedZombieEntityRenderer::new
    );

    public static final RegistryObject<EntityType<CorrodedCreeperEntity>> CORRODEDCREEPER = registerMob(
            "corroded_creeper",
            CorrodedCreeperEntity::new, MobCategory.MONSTER, 0.9f, 1.7f,
            CorrodedCreeperEntity::createAttributes,
            () -> CorrodedCreeperEntityRenderer::new
    );

    // Nesterra 系
    public static final RegistryObject<EntityType<Nesterra1Entity>> NESTERRA1 = registerMob(
            "nesterra_phase1",
            Nesterra1Entity::new, MobCategory.MONSTER, 1.0f, 2.0f,
            Nesterra1Entity::createAttributes,
            () -> Nesterra1EntityRenderer::new
    );

    public static final RegistryObject<EntityType<Nesterra2Entity>> NESTERRA2 = registerMob(
            "nesterra_phase2",
            Nesterra2Entity::new, MobCategory.MONSTER, 1.2f, 2.2f,
            Nesterra2Entity::createAttributes,
            () -> Nesterra2EntityRenderer::new
    );

    public static final RegistryObject<EntityType<Nesterra3Entity>> NESTERRA3 = registerMob(
            "nesterra_phase3",
            Nesterra3Entity::new, MobCategory.MONSTER, 1.4f, 2.4f,
            Nesterra3Entity::createAttributes,
            () -> Nesterra3EntityRenderer::new
    );

    public static final RegistryObject<EntityType<Nesterra4Entity>> NESTERRA4 = registerMob(
            "nesterra_phase4",
            Nesterra4Entity::new, MobCategory.MONSTER, 1.6f, 2.6f,
            Nesterra4Entity::createAttributes,
            () -> Nesterra4EntityRenderer::new
    );

    // developed
    public static final RegistryObject<EntityType<Developed_PlaguelatchEntity>> DEVELOPED_PLAGUELATCH = registerMob(
            "developed_plaguelatch",
            Developed_PlaguelatchEntity::new, MobCategory.MONSTER, 1.5f, 1.8f,
            Developed_PlaguelatchEntity::createAttributes,
            () -> Developed_PlaguelatchRenderer::new
    );

    // Adapted
    public static final RegistryObject<EntityType<Adapted_PlaguelatchEntity>> ADAPTED_PLAGUELATCH = registerMob(
            "adapted_plaguelatch",
            Adapted_PlaguelatchEntity::new, MobCategory.MONSTER, 3.0f, 3.5f,
            Adapted_PlaguelatchEntity::createAttributes,
            () -> Adapted_PlaguelatchRenderer::new
    );

}
