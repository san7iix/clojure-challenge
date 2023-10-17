(ns clojure-challenge.problem-1)


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
