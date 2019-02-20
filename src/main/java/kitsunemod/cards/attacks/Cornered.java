package kitsunemod.cards.attacks;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kitsunemod.KitsuneMod;
import kitsunemod.cards.AbstractElderCard;
import kitsunemod.cards.AbstractKitsuneCard;
import kitsunemod.patches.AbstractCardEnum;

public class Cornered extends AbstractElderCard {
    public static final String ID = KitsuneMod.makeID("Cornered");
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "kitsunemod/images/cards/default_attack.png";


    private static final int COST = 1;

    private static final int BASE_DAMAGE = 8;
    private static final int BASE_MISSING_HP_MULT = 10;
    private static final int ELDER_TIER_MISSING_HP_MULT = 10;
    private static final int ELDER_TIER_UNBLOCKED_DAMAGE_REQUIREMENT = 15;
    private static final int SWITCH_EFFECT_DAMAGE_THRESHHOLD = 25;

    public Cornered() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.KITSUNE_COLOR,
                CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = BASE_DAMAGE;
        magicNumber = baseMagicNumber = BASE_MISSING_HP_MULT;
        secondMagicNumber = baseSecondMagicNumber = BASE_DAMAGE;
    }

    public Cornered(int timesUpgraded) {
        this();
        initializeWithUpgrades(timesUpgraded);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
        if (damage > SWITCH_EFFECT_DAMAGE_THRESHHOLD) {
            effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), effect));
        rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = this.baseDamage + MathUtils.floor((float)(AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth) * ((float)magicNumber/ 100));
        super.applyPowers();
        rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
        baseDamage = secondMagicNumber;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.baseDamage = this.baseDamage + MathUtils.floor((float)(AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth) * ((float)magicNumber/ 100));
        super.applyPowers();
        rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
        baseDamage = secondMagicNumber;
    }

    @Override
    public void onMoveToDiscard() {
        rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public int onLoseHp(DamageInfo info, int finalAmount) {
        misc += finalAmount;
        int tempTimesUpgraded = this.timesUpgraded;
        upgrade();
        if (timesUpgraded != tempTimesUpgraded) {
            playUpgradeVfx();
        }
        return finalAmount;
    }

    @Override
    protected boolean allCondition() {
        return misc >= (timesUpgraded + 1) * ELDER_TIER_UNBLOCKED_DAMAGE_REQUIREMENT;
    }

    @Override
    public void upgradeAll() {
        if (timesUpgraded < 9) {
            upgradeName();
            upgradeMagicNumber(ELDER_TIER_MISSING_HP_MULT);
        }
    }

    @Override
    public void upgradeName() {
        upgraded = true;
        name = NAME + "+" + timesUpgraded;
        initializeTitle();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Cornered(timesUpgraded);
    }
}
