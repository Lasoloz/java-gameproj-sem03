package com.github.lasoloz.gameproj.blueprints;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.lasoloz.gameproj.blueprints.*;
import com.github.lasoloz.gameproj.graphics.AnimationWrapper;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;
import com.github.lasoloz.gameproj.util.ResourceLoader;

import java.util.ArrayList;

/**
 * Class used for storing unit blueprints for a map. It supports loading them
 * from `json` files.
 * private members: blueprints - list of blueprints stored.
 * pack - Texture atlas used for the specific blueprint set
 */
public class BlueprintSet {
    private ArrayList<Blueprint> blueprints;
    private TextureAtlas pack;

    /**
     * Constructor for blueprint set
     * @param blueprintSetPath Path towards blueprint definition (`.bpset.json`)
     * @throws BlueprintException Exception thrown in case of failure
     */
    public BlueprintSet(String blueprintSetPath) throws BlueprintException {
        FileHandle blueprintSetFile =
                ResourceLoader.loadInternalOrLocalResource(blueprintSetPath);

        if (blueprintSetFile == null) {
            throw new BlueprintException(
                    "BlueprintSet",
                    "Blueprint set does not exist!"
            );
        }

        // Get root from json
        JsonValue root = new JsonReader().parse(blueprintSetFile);

        // Create blueprint list
        blueprints = new ArrayList<Blueprint>();
        // Read information from root
        readRoot(root);
    }

    /**
     * Get a blueprint from the list (used in maps)
     * @param blueprintIndex Index of requested blueprint
     * @return Selected blueprint from the array list
     * @throws IndexOutOfBoundsException Exception thrown in case of invalid
     * blueprint selection
     */
    public Blueprint getBlueprint(
            int blueprintIndex
    ) throws IndexOutOfBoundsException {
        return blueprints.get(blueprintIndex);
    }


    /**
     * Read information starting from root.
     * @param root Root `json` object
     * @throws BlueprintException Exception thrown in case of failure
     */
    private void readRoot(JsonValue root) throws BlueprintException {
        try {
            String packName = root.getString("pack");
            pack = new TextureAtlas(
                    ResourceLoader.loadInternalOrLocalResource(packName)
            );

            readBlueprints(root.get("blueprints"));
        } catch (NullPointerException ex) {
            // Catch `NullPointerException`s and pack them in
            // `BlueprintException`s
            throw new BlueprintException(
                    "BlueprintSet",
                    "BlueprintSet JSON file has invalid field(s)!"
            );
        }
    }

    /**
     * Read blueprints from blueprint list of `root`
     * @param blueprints List of blueprints inside `root`
     */
    private void readBlueprints(JsonValue blueprints) {
        JsonValue bpIter = blueprints.child;

        while (bpIter != null) {
            String type = bpIter.getString("type");
            Blueprint result;
            if (type.equals("misc")) {
                result = createMisc(bpIter);
            } else if (type.equals("loot")) {
                result = createLoot(bpIter);
            } else {
                result = createUnit(bpIter);
            }
            // Set name:
            String name = bpIter.getString("name");
            result.setName(name);
            this.blueprints.add(result);

            bpIter = bpIter.next;
        }
    }

    /**
     * Create miscellaneous units' definitions (e.g. torch)
     * @param blueprint Blueprint item in blueprint list of `root`
     * @return New blueprint defining a miscellaneous unit
     */
    private Blueprint createMisc(JsonValue blueprint) {
        // Get idle information:
        SpriteWrapper indexSprite = extractIndex(blueprint);
        Drawable idleAnimation = extractIdle(blueprint);

        // Create blueprint for instance class:
        MiscBlueprint misc = new MiscBlueprint(indexSprite, idleAnimation);

        // Return blueprint:
        return misc;
    }

    /**
     * Create loot definition
     * @param blueprint Blueprint item in blueprint list of `root`
     * @return New blueprint defining a loot unit
     */
    private Blueprint createLoot(JsonValue blueprint) {
        // Get index and idle information:
        SpriteWrapper indexSprite = extractIndex(blueprint);
        Drawable idleAnimation = extractIdle(blueprint);

        // Create loot blueprint:
        LootBlueprint loot = new LootBlueprint(indexSprite, idleAnimation);

        // Get loot value:
        int value = blueprint.getInt("value");
        loot.setValue(value);

        // Return blueprint:
        return loot;
    }

    /**
     * Create a new unit (player or enemy)
     * @param blueprint Blueprint item in blueprint list of `root`
     * @return New blueprint defining a player or enemy
     */
    private Blueprint createUnit(JsonValue blueprint) {
        // Get index and idle information:
        SpriteWrapper indexSprite = extractIndex(blueprint);
        Drawable idleAnimation = extractIdle(blueprint);

        // Create blueprint for player class:
        UnitBlueprint unit = createUnitBlueprint(
                blueprint,
                indexSprite,
                idleAnimation
        );

        // Additional setup:
        addUnitInformation(unit, blueprint);

        // Return blueprint:
        return unit;
    }


    /**
     * Create a player or unit base blueprint before parsing additional info
     * @param blueprint Blueprint item in blueprint list of `root`
     * @param indexSprite Index sprite
     * @param idleAnimation Idle image/animation of unit
     * @return New UnitBlueprint without any data regarding unit specific info
     * (e.g. max health points)
     */
    private UnitBlueprint createUnitBlueprint(
            JsonValue blueprint,
            SpriteWrapper indexSprite,
            Drawable idleAnimation
    ) {
        // Check, if there is player or enemy blueprint:
        if (blueprint.getString("type").equals("player")) {
            return new PlayerBlueprint(indexSprite, idleAnimation);
        } else {
            return new EnemyBlueprint(indexSprite, idleAnimation);
        }
    }


    /**
     * Extract index image from a blueprint definition
     * @param blueprint Blueprint item in blueprint list of `root`
     * @return Index image SpriteWrapper
     * @see SpriteWrapper
     */
    private SpriteWrapper extractIndex(JsonValue blueprint) {
        String indexRegion = blueprint.getString("index");
        return new SpriteWrapper(pack.findRegion(indexRegion));
    }


    /**
     * Extract idle animation or image
     * @param blueprint Blueprint item in blueprint list of `root`
     * @return Drawable object defining a SpriteWrapper or AnimationWrapper
     * @see AnimationWrapper
     * @see SpriteWrapper
     * @see Drawable
     */
    private Drawable extractIdle(JsonValue blueprint) {
        JsonValue idleData = blueprint.get("idle");

        String type = idleData.getString("type");
        String region = idleData.getString("region");
        int originX = idleData.getInt("origin_x");
        int originY = idleData.getInt("origin_y");
        if (type.equals("animation")) {
            float animSpeed = idleData.getFloat("anim_speed");
            return new AnimationWrapper(
                    pack,
                    region,
                    animSpeed,
                    originX,
                    originY
            );
        } else {
            return new SpriteWrapper(pack.findRegion(region), originX, originY);
        }
    }


    /**
     * Add unit specific information to the blueprint
     * @param unitBlueprint Base unit blueprint before adding unit information
     * @param blueprint Blueprint item in blueprint list of `root` json object
     */
    private void addUnitInformation(
            UnitBlueprint unitBlueprint, JsonValue blueprint
    ) {
        // Get health:
        unitBlueprint.setMaxHealth(blueprint.getInt("max_health"));

        // Get range:
        unitBlueprint.setRange(blueprint.getInt("range"));

        // Get attack and protection stats:
        JsonValue stats = blueprint.get("stats");
        unitBlueprint.setMeleeAttackMean(stats.getInt("melee_attack_mean"));
        unitBlueprint.setMeleeAttackVar(stats.getInt("melee_attack_var"));
        unitBlueprint.setRangedAttackMean(stats.getInt("ranged_attack_mean"));
        unitBlueprint.setRangedAttackVar(stats.getInt("ranged_attack_var"));
        unitBlueprint.setProtectionMean(stats.getInt("protection_mean"));
        unitBlueprint.setProtectionVar(stats.getInt("protection_var"));
    }
}
