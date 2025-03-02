package jp.x0x0b.kadai_ak.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * 食品を表すクラス
 */
@Log4j2
@Getter
@ToString
public class FoodItem {

  private final String name;
  private final BigDecimal proteinGramIntake; // たんぱく質(g)
  private final BigDecimal fatGramIntake; // 脂質(g)
  private final BigDecimal carbohydrateGramIntake; // 炭水化物(g)

  // 1gあたりのカロリー
  private static final BigDecimal PROTEIN_KILO_CALORIE_PER_GRAM = new BigDecimal("4.0");
  private static final BigDecimal FAT_KILO_CALORIE_PER_GRAM = new BigDecimal("9.0");
  private static final BigDecimal CARBOHYDRATE_KILO_CALORIE_PER_GRAM = new BigDecimal("4.0");

  // 小数第x位まで丸める（= 小数第x+1位で四捨五入）
  private static final int INTAKE_GRAM_DECIMAL_PLACE = 1;
  private static final int KILO_CALORIE_DECIMAL_PLACE = 0;

  public FoodItem(String name, BigDecimal proteinGramIntake, BigDecimal fatGramIntake, BigDecimal carbohydrateGramIntake) {
    this.name = name;
    // 今回は摂取量を丸めた値を保持する
    // 要件によってはそのまま保持して、get時に丸めることも考えられる
    this.proteinGramIntake = roundingGramIntake(proteinGramIntake);
    this.fatGramIntake = roundingGramIntake(fatGramIntake);
    this.carbohydrateGramIntake = roundingGramIntake(carbohydrateGramIntake);
    log.debug("MenuItem created: {}", this);
  }

  /**
   * たんぱく質、脂質、炭水化物の摂取量を丸める
   * @param value 摂取量
   * @return 丸めた摂取量
   */
  private BigDecimal roundingGramIntake(BigDecimal value) {
    return value.setScale(INTAKE_GRAM_DECIMAL_PLACE, RoundingMode.HALF_UP);
  }

  /**
   * 摂取量と1gあたりのカロリーからカロリーを計算する
   * @param gramIntake 摂取量
   * @param kiloCaloriePerGram 1gあたりのカロリー
   * @return カロリー
   */
  private long getKiloCalorie(BigDecimal gramIntake, BigDecimal kiloCaloriePerGram) {
    return gramIntake.multiply(kiloCaloriePerGram).setScale(KILO_CALORIE_DECIMAL_PLACE, RoundingMode.HALF_UP).longValue();
  }

  public long getProteinKiloCalorie() {
    return getKiloCalorie(proteinGramIntake, PROTEIN_KILO_CALORIE_PER_GRAM);
  }

  public long getFatKiloCalorie() {
    return getKiloCalorie(fatGramIntake, FAT_KILO_CALORIE_PER_GRAM);
  }

  public long getCarbohydrateKiloCalorie() {
    return getKiloCalorie(carbohydrateGramIntake, CARBOHYDRATE_KILO_CALORIE_PER_GRAM);
  }
}
