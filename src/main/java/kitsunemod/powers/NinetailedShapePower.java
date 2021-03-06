package kitsunemod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import kitsunemod.KitsuneMod;
import kitsunemod.actions.CreateWillOWispAction;

public class NinetailedShapePower extends AbstractShapePower {

    public static int SOULSTEAL_STACKS_PER_WISP = 3;
    public static int WISPS_PER_INCREMENT = 1;


    public static final String POWER_ID = KitsuneMod.makeID("NinetailedShapePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    //public static final String IMG = "alternateVerseResources/images/powers/placeholder_power.png";


    public NinetailedShapePower(final AbstractCreature owner, final AbstractCreature source, int strength, int dexterity) {
        super(owner, source, DESCRIPTIONS, KitsuneMod.KitsuneShapes.NINETAILED,
                strength,
                dexterity);

        name = NAME;
        ID = POWER_ID;

        region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("kitsunemod/images/powers/KitsuneShapePower_84.png"), 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("kitsunemod/images/powers/KitsuneShapePower_32.png"), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public AbstractGameAction getSoulstealActionForAmount(AbstractPlayer player, int amount) {
        return new CreateWillOWispAction(calculateWispsForSoulstealAmount(amount));
    }

    @Override
    public String getSoulstealUIString(int amount) {
        String result = "";
        int wispsForAmount = calculateWispsForSoulstealAmount(amount);
        if (wispsForAmount == 1) {
            result += DESCRIPTIONS[14] + wispsForAmount + DESCRIPTIONS[15];
        } else if (wispsForAmount > 1) {
            result += DESCRIPTIONS[14] + wispsForAmount + DESCRIPTIONS[16];
        } else {
            result += DESCRIPTIONS[17];
        }

        if (WISPS_PER_INCREMENT == 1) {
            return result + DESCRIPTIONS[8] + WISPS_PER_INCREMENT + DESCRIPTIONS[9] + SOULSTEAL_STACKS_PER_WISP + DESCRIPTIONS[11] + DESCRIPTIONS[13];
        } else {
            return result + DESCRIPTIONS[8] + WISPS_PER_INCREMENT + DESCRIPTIONS[10] + SOULSTEAL_STACKS_PER_WISP + DESCRIPTIONS[11] + DESCRIPTIONS[13];
        }
    }

    private int calculateWispsForSoulstealAmount(int amount) {
        //1 + [...] is so that you always get at least 1 wisp
        return (1 + MathUtils.floor((float)amount / (float)SOULSTEAL_STACKS_PER_WISP) * WISPS_PER_INCREMENT);
    }
}
