package jp.x0x0b.kadai_ak;

import java.math.BigDecimal;
import java.util.List;
import jp.x0x0b.kadai_ak.model.FoodItem;
import jp.x0x0b.kadai_ak.model.Menu;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

  public static void main(String[] args) {
    log.info("===== 問題1 =====");
    FoodItem rice = new FoodItem("白米", new BigDecimal("2.5"), new BigDecimal("0.3"), new BigDecimal("37.1"));
    log.info("{} - カロリー(プロテイン): {} kcal", rice.getName(), rice.getProteinKiloCalorie());
    log.info("{} - カロリー(脂質): {} kcal", rice.getName(), rice.getFatKiloCalorie());
    log.info("{} - カロリー(炭水化物): {} kcal", rice.getName(), rice.getCarbohydrateKiloCalorie());

    FoodItem natto = new FoodItem("納豆", new BigDecimal("16.5"), new BigDecimal("10.0"), new BigDecimal("12.1"));
    log.info("{} - カロリー(プロテイン): {} kcal", natto.getName(), natto.getProteinKiloCalorie());
    log.info("{} - カロリー(脂質): {} kcal", natto.getName(), natto.getFatKiloCalorie());
    log.info("{} - カロリー(炭水化物): {} kcal", natto.getName(), natto.getCarbohydrateKiloCalorie());


    log.info("===== 問題2 =====");
    Menu menu = Menu.builder()
        .name("納豆ご飯")
        .foodItems(List.of(rice, natto))
        .build();

    log.info("{} - カロリー: {} kcal", menu.getName(), menu.getKiloCalorie());
  }
}
