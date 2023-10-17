(ns invoice-item
  (:require [clojure.test :refer [deftest is testing]]))

(defn- discount-factor [{:keys [discount-rate]
                         :or                {discount-rate 0}}]
  (- 1 (/ discount-rate 100.0)))

(defn subtotal
  [{:keys [precise-quantity precise-price discount-rate]
    :as                item
    :or                {discount-rate 0}}]
  (* precise-quantity precise-price (discount-factor item)))

(deftest test-subtotal-basic
  (is (= (subtotal {:precise-quantity 5 :precise-price 10 :discount-rate 0.1}) 50.0))
  "Verify calc. 5 * 10 = 50")

(deftest test-subtotal-discount
  (is (= (subtotal {:precise-quantity 3 :precise-price 20 :discount-rate 10}) 54.0))
  "Verify with a discount-rate of 10%. (3 * 20) - (10 /100  * (3 * 20)) = 54")

(deftest test-subtotal-zero-quantity
  (is (= (subtotal {:precise-quantity 0 :precise-price 15 :discount-rate 0.2}) 0.0))
  "Verify calc with zero quantity.")

(deftest test-subtotal-negative-price
  (is (= (subtotal {:precise-quantity 4 :precise-price -8 :discount-rate 0}) -32.0))
  "Verify calc with negative value for precise-price. 4 * (-8) = -32")

(deftest test-subtotal-no-discount
  (is (= (subtotal {:precise-quantity 2 :precise-price 12}) 24.0))
  "Verificar un cálculo sin especificar la tasa de descuento. No debe aplicarse descuento.")

(deftest test-subtotal-incorrect-keys
  (is (thrown? Exception (subtotal {:quantity 5 :price 10 :discount 0})))
  "Verify calc with incorrect keys. Should throw an exception")

(deftest test-subtotal-missing-keys
  (is (thrown? Exception (subtotal {:precise-price 10 :discount-rate 0})))
  "Verify calc with missing keys. Should throw an exception")
