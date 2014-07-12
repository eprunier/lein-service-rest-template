(ns {{name}}.service.json
  (:require [cheshire.core :refer [generate-string]]))

(defn clj->json
  "Converts Clojure data to JSON."
  [data]
  (generate-string data))
