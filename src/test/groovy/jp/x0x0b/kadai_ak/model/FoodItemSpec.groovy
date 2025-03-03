package jp.x0x0b.kadai_ak.model


import spock.lang.Specification

class FoodItemSpec extends Specification {

    // 正常系テスト
    def "想定入力 - #testName"() {
        when:
        def foodItem = new FoodItem(iName, new BigDecimal(iProtain), new BigDecimal(iFat), new BigDecimal(iCarbohydrate))

        then:
        foodItem.getName() == iName
        foodItem.getProteinGramIntake() == new BigDecimal(eProtain)
        foodItem.getFatGramIntake() == new BigDecimal(eFat)
        foodItem.getCarbohydrateGramIntake() == new BigDecimal(eCarbohydrate)

        where:
        testName             | iName  | iProtain  | iFat   | iCarbohydrate || eProtain | eFat   | eCarbohydrate
        "基本1"              | "白米" | "2.5"     | "0.3"  | "37.1"        || "2.5"    | "0.3"  | "37.1"
        "基本2"              | "納豆" | "16.5"    | "10.0" | "12.1"        || "16.5"   | "10.0" | "12.1"
        "四捨五入(切り上げ)" | "item" | "160.551" | "9.88" | "9.99"        || "160.6"  | "9.9"  | "10.0"
        "四捨五入(切り捨て)" | "item" | "4.500"   | "0.02" | "2.1499"      || "4.5"    | "0.0"  | "2.1"
    }

    // BigDecimalのコンストラクタに渡す文字列が数値以外の場合、NumberFormatExceptionが発生することを確認
    def "異常入力 - #testName"() {
        when:
        new FoodItem(iName, new BigDecimal(iProtain), new BigDecimal(iFat), new BigDecimal(iCarbohydrate))

        then:
        thrown(eException)

        where:
        testName     | iName  | iProtain  | iFat  | iCarbohydrate || eException
        "空白入力"   | "item" | ""        | "0.3" | "37.1"        || NumberFormatException
        "文字列入力" | "item" | "invalid" | "0.3" | "37.1"        || NumberFormatException
    }

    // カロリー計算のテスト
    def "カロリー計算 - #testName"() {
        when:
        def foodItem = new FoodItem(iName, new BigDecimal(iProtain), new BigDecimal(iFat), new BigDecimal(iCarbohydrate))

        then:
        foodItem.getProteinKiloCalorie() == eProteinKiloCalorie
        foodItem.getFatKiloCalorie() == eFatKiloCalorie
        foodItem.getCarbohydrateKiloCalorie() == eCarbohydrateKiloCalorie

        where:
        testName                             | iName  | iProtain | iFat   | iCarbohydrate || eProteinKiloCalorie | eFatKiloCalorie | eCarbohydrateKiloCalorie
        // Protain: 2.5 * 4.0 = 10.0, Fat: 0.3 * 9.0 = 2.7, Carbohydrate: 37.1 * 4.0 = 148.4
        "基本1"                              | "白米" | "2.5"    | "0.3"  | "37.1"        || 10                  | 3               | 148
        // Protain: 16.5 * 4.0 = 66.0, Fat: 10.0 * 9.0 = 90.0, Carbohydrate: 12.1 * 4.0 = 48.4
        "基本2"                              | "納豆" | "16.5"   | "10.0" | "12.1"        || 66                  | 90              | 48
        // 摂取量が四捨五入されない場合 Protain: 3.63 * 4.0 = 14.52, Fat: 9.62 * 9.0 = 86.58, Carbohydrate: 3.63 * 4.0 = 14.52
        // 計算される摂取量　　　　　　 Protain: 3.6 * 4.0 = 14.4, Fat: 9.6 * 9.0 = 86.4, Carbohydrate: 3.6 * 4.0 = 14.4
        // 四捨五入された摂取量で計算され、計算されたカロリーも四捨五入される
        "摂取量が四捨五入後の値で計算される" | "item" | "3.63"   | "9.62" | "3.63"        || 14                  | 86              | 14
    }
}
