package kitsunemod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import kitsunemod.KitsuneMod;

public class ShadePower extends AbstractKitsunePower {

    public static final String POWER_ID = KitsuneMod.makeID("ShadePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int DEFAULT_HEAL_AMOUNT = 2;
    //public static final String IMG = "alternateVerseResources/images/powers/placeholder_power.png";

    public ShadePower(
            final AbstractCreature owner,
            final int amount) {

        this.owner = owner;
        isTurnBased = false;
        name = NAME;
        ID = POWER_ID;
        type = PowerType.BUFF;
        this.amount = amount;

        region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("kitsunemod/images/powers/ShadePower_84.png"), 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("kitsunemod/images/powers/ShadePower_32.png"), 0, 0, 32, 32);

        updateDescription();

    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && !info.owner.isPlayer) {
            flash();
            int tmp = DEFAULT_HEAL_AMOUNT;
            if (owner.hasPower(ShadeExtraHealPower.POWER_ID)) {
                AbstractPower p = owner.getPower(ShadeExtraHealPower.POWER_ID);
                tmp = p.amount;
                p.flash();
            }
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            AbstractDungeon.actionManager.addToTop(new HealAction(owner, owner, tmp));
            return 0;
        }
        return damageAmount;
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }


    @Override
    public void updateDescription() {
        int tmp = DEFAULT_HEAL_AMOUNT;
        if (owner.hasPower(ShadeExtraHealPower.POWER_ID)) {
            tmp = owner.getPower(ShadeExtraHealPower.POWER_ID).amount;
        }
        if (amount == 1) {
            description = DESCRIPTIONS[0] + tmp + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];

        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + tmp + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3];
        }
    }
}
