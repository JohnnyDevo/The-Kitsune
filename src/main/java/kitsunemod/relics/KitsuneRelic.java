package kitsunemod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import kitsunemod.KitsuneMod;
import kitsunemod.powers.AbstractShapePower;

public abstract class KitsuneRelic extends CustomRelic {

    public KitsuneRelic(String ID, Texture IMG, Texture OUTLINE, RelicTier TIER, LandingSound SOUND) {
        super(ID, IMG, OUTLINE, TIER, SOUND);
    }

    public void onChangeShape(KitsuneMod.KitsuneShapes shape, AbstractShapePower shapePower) {}
    public boolean shouldAutoChangeShape() {return true;}
    public int onCalculateWispDamage(int amount) {return amount;}
    public int onCalculateLightTriggerThreshold(int amount) {return amount;}
    public int onCalculateDarkTriggerThreshold(int amount) {return amount;}
    public void onTriggeredDark() {}
    public void onTriggeredLight() {}
    public int onCalculateMaxWisps(int amount) {return amount;}
    public boolean shouldTriggerSoulsteal() {return true;}
}
