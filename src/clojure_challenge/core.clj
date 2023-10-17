(ns clojure-challenge.core
  (:require [clojure-challenge.problem-1 :as problem-1])
  (:require [clojure-challenge.invoice-spec]))

(require '[clojure.data.json :as json])

(def invoice (clojure.edn/read-string (slurp "src/invoice.edn")))
(def filtered-items (problem-1/filter-items-by-iva-or-retention invoice))

(defn -main
  [& args]
  (println (clojure-challenge.invoice-spec/check-json "src/invoice.json"))
  )

