package kitsunemod.cards.skills;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kitsunemod.KitsuneMod;
import kitsunemod.actions.ManuallyTriggerSoulstealAction;
import kitsunemod.cards.AbstractKitsuneCard;
import kitsunemod.patches.AbstractCardEnum;
import kitsunemod.powers.InTheShadowsPower;
import kitsunemod.powers.SoulstealPower;

public class VanishIntoShadows extends AbstractKitsuneCard {
    public static final String ID = KitsuneMod.makeID("VanishIntoShadows");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "kitsunemod/images/cards/defend.png";
    private static final int COST = 1;

    private static final int POWER_STACKS = 1;

    private static final int HEAL_AMOUNT = 2;

    public VanishIntoShadows() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.KITSUNE_COLOR,
                CardRarity.RARE, CardTarget.SELF);
        magicNumber = baseMagicNumber = HEAL_AMOUNT;
        secondMagicNumber = baseSecondMagicNumber = POWER_STACKS;
        this.exhaustOnUseOnce = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InTheShadowsPower(p, secondMagicNumber, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new VanishIntoShadows();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            AlwaysRetainField.alwaysRetain.set(this, true);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();

        }
    }
}
