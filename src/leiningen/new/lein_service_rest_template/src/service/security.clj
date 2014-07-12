(ns {{name}}.service.security
  (:require [{{name}}.service.json :refer [clj->json]]))

(defn authenticated?
  "Sample authentication function."
  [request]
  true)

(defn admin?
  "Sample admin authorization function."
  [request]
  true)

(defn current-user
  "Get current user.
   If username is provided, checks if this username refers to the current user."
  ([request]
     {:user {:username "foo"}})
  ([request username]
     true))
