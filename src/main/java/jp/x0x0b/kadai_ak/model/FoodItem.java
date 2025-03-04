package jp.x0x0b.kadai_ak.model;

import com.google.common.annotations.VisibleForTesting;
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
  private final long proteinKiloCalorie;
  private final long fatKiloCalorie;
  private final long carbohydrateKiloCalorie;

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
    this.proteinGramIntake = roundGramIntake(proteinGramIntake);
    this.fatGramIntake = roundGramIntake(fatGramIntake);
    this.carbohydrateGramIntake = roundGramIntake(carbohydrateGramIntake);
    // 1gあたりのカロリーと摂取量からカロリーを計算する（丸めたあとのPFC摂取量を使用する）
    this.proteinKiloCalorie = calculateKiloCalorie(this.proteinGramIntake, PROTEIN_KILO_CALORIE_PER_GRAM);
    this.fatKiloCalorie = calculateKiloCalorie(this.fatGramIntake, FAT_KILO_CALORIE_PER_GRAM);
    this.carbohydrateKiloCalorie = calculateKiloCalorie(this.carbohydrateGramIntake, CARBOHYDRATE_KILO_CALORIE_PER_GRAM);
    log.debug("MenuItem created: {}", this);
  }

  /**
   * たんぱく質、脂質、炭水化物の摂取量を丸める
   * @param value 摂取量
   * @return 丸めた摂取量
   */
  @VisibleForTesting
  BigDecimal roundGramIntake(BigDecimal value) {
    return value.setScale(INTAKE_GRAM_DECIMAL_PLACE, RoundingMode.HALF_UP);
  }

  /**
   * 摂取量と1gあたりのカロリーからカロリーを計算する
   * @param gramIntake 摂取量
   * @param kiloCaloriePerGram 1gあたりのカロリー
   * @return カロリー
   */
  @VisibleForTesting
  long calculateKiloCalorie(BigDecimal gramIntake, BigDecimal kiloCaloriePerGram) {
    return gramIntake.multiply(kiloCaloriePerGram).setScale(KILO_CALORIE_DECIMAL_PLACE, RoundingMode.HALF_UP).longValue();
  }
}
