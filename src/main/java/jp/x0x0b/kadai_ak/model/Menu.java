package jp.x0x0b.kadai_ak.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * メニューを表すクラス
 */
@Getter
@Builder
public class Menu {
  private String name;
  private List<FoodItem> foodItems;

  private long getProteinKiloCalorie() {
    return foodItems.stream()
        .mapToLong(FoodItem::getProteinKiloCalorie)
        .sum();
  }

  private long getFatKiloCalorie() {
    return foodItems.stream()
        .mapToLong(FoodItem::getFatKiloCalorie)
        .sum();
  }

  private long getCarbohydrateKiloCalorie() {
    return foodItems.stream()
        .mapToLong(FoodItem::getCarbohydrateKiloCalorie)
        .sum();
  }

  public long getKiloCalorie() {
    return getProteinKiloCalorie() + getFatKiloCalorie() + getCarbohydrateKiloCalorie();
  }
}
