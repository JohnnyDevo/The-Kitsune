package kitsunemod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import kitsunemod.KitsuneMod;

public abstract class AbstractKitsunePower extends TwoAmountPower implements GatheringPower, WispAffectingPower {

    public void onShapeChange(KitsuneMod.KitsuneShapes shape, AbstractShapePower power) {}

    public void onEnergyChanged(int e) {}

    public int onCalculateMaxWisps(int amount) {
        return amount;
    }

    public boolean shouldHoldFire() {
        return false;
    }

    @Override
    public void onApplyLightOrDark(boolean isLight) {
    }

    @Override
    public int onChannelWisp(int amount) {
        return amount;
    }

}
