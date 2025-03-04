package jp.x0x0b.kadai_ak.model


import spock.lang.Specification

class MenuSpec extends Specification {

    // 正常系テスト
    def "メニューの総カロリーを計算する"() {
        given:
        def iName = "納豆ご飯"
        def foodItems = []
        foodItems.add(new FoodItem("白米", new BigDecimal("2.5"), new BigDecimal("0.3"), new BigDecimal("37.1")))
        foodItems.add(new FoodItem("納豆", new BigDecimal("16.5"), new BigDecimal("10.0"), new BigDecimal("12.1")))

        when:
        def menu = Menu.builder().name(iName).foodItems(foodItems).build()

        then:
        menu.getName() == iName
        // 2.5 * 4.0 + 0.3 * 9.0 + 37.1 * 4.0 = 161.1, 四捨五入後 161
        // 16.5 * 4.0 + 10.0 * 9.0 + 12.1 * 4.0 = 148.4, 四捨五入後 148
        // 合計365Kcal
        menu.getKiloCalorie() == 365L
    }

    def "空メニュー"() {
        given:
        def iName = "空メニュー"
        def foodItems = []

        when:
        def menu = Menu.builder().name(iName).foodItems(foodItems).build()

        then:
        menu.getName() == iName
        menu.getKiloCalorie() == 0L
    }
}
