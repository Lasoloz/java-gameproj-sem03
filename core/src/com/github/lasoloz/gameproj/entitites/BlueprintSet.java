package com.github.lasoloz.gameproj.entitites;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.lasoloz.gameproj.graphics.AnimationWrapper;
import com.github.lasoloz.gameproj.graphics.Drawable;
import com.github.lasoloz.gameproj.graphics.SpriteWrapper;
import com.github.lasoloz.gameproj.util.ResourceLoader;

import java.util.ArrayList;

public class BlueprintSet {
    private ArrayList<Blueprint> blueprints;
    private TextureAtlas pack;

    public BlueprintSet(String blueprintSetPath) throws BlueprintException {
        FileHandle blueprintSetFile =
                ResourceLoader.loadInternalOrLocalResource(blueprintSetPath);

        if (blueprintSetFile == null) {
            throw new BlueprintException(
                    "BlueprintSet",
                    "Blueprint set does not exist!"
            );
        }

        JsonValue root = new JsonReader().parse(blueprintSetFile);

        blueprints = new ArrayList<Blueprint>();
        readRoot(root);
    }

    public Blueprint getBlueprint(
            int blueprintIndex
    ) throws IndexOutOfBoundsException {
        return blueprints.get(blueprintIndex);
    }



    private void readRoot(JsonValue root) throws BlueprintException {
        try {
            String packName = root.getString("pack");
            pack = new TextureAtlas(
                    ResourceLoader.loadInternalOrLocalResource(packName)
            );

            readBlueprints(root.get("blueprints"), pack);
        } catch (NullPointerException ex) {
            throw new BlueprintException(
                    "BlueprintSet",
                    "BlueprintSet JSON file has invalid field(s)!"
            );
        }
    }

    private void readBlueprints(JsonValue blueprints, TextureAtlas pack) {
        JsonValue bpIter = blueprints.child;

        while (bpIter != null) {
            String type = bpIter.getString("type");
            if (type.equals("misc")) {
                createMisc(bpIter, pack);
            } else if (type.equals("loot")) {
                createLoot(bpIter);
            } else {
                createUnit(bpIter);
            }

            bpIter = bpIter.next;
        }
    }

    private void createMisc(JsonValue blueprint, TextureAtlas pack) {
        String name = blueprint.getString("name");
        String indexRegion = blueprint.getString("index");
        SpriteWrapper index = new SpriteWrapper(pack.findRegion(indexRegion));
        Blueprint misc = new Blueprint(name, index);

        // Get idle information:
        Drawable idleAnimation = extractIdle(blueprint, pack);

        misc.addDrawableProperty(Property.PR_IDLE, idleAnimation);

        blueprints.add(misc);
    }

    private void createLoot(JsonValue blueprint) {
    }

    private void createUnit(JsonValue blueprint) {
    }



    // Action extractor functions:
    private Drawable extractIdle(JsonValue blueprint, TextureAtlas pack) {
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
}
