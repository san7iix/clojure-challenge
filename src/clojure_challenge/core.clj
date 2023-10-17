(ns clojure-challenge.core)

(def invoice (clojure.edn/read-string (slurp "src/invoice.edn")))

(defn filter-items-by-iva-or-retention
"This function receives an invoice and return the items what satisfaces some of the two conditions"
  [invoice]
  (->> invoice
       :invoice/items
       (filter (fn [item]
                 (let [taxes (-> item :taxable/taxes) retentions (-> item :retentionable/retentions)]
                   (if (and (some #(= 19 (:tax/rate %)) taxes)
                            (some #(= 1 (:retention/rate %)) retentions)
                            )
                     false
                     true
                     )
                   )
                 ))))





(def filtered-items (filter-items-by-iva-or-retention invoice))

(defn -main
  [& args]
  (println filtered-items)
  )

