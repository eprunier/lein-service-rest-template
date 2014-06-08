(ns {{name}}.service.security
  (:require [{{name}}.service.json :refer [clj->json]]))

(defmacro restricted
  "Macro for restricted part of the API. 
   Takes a predicate function and the handler to execute if predicate is true."
  [predicate-fn handler request & args]
  `(let [{:keys [~'authorized ~'status ~'message]} (~predicate-fn ~request)] 
     (if ~'authorized
       (~handler ~request ~@args)
       {:status ~'status
        :headers {}
        :body (clj->json {:error ~'message})})))

(defn authenticated?
  "Sample authentication function. Test if current user is not null."
  [request]
  (if true
    {:authorized true}
    {:authorized false
     :status 401
     :message "Authentication required"}))

(defn admin?
  "Sample authorization function. Test if current user it admin."
  [request]
  (let [authorized false]
    (if authorized
      {:authorized true}
      {:authorized false
       :status 403
       :message "Admin authorization required"})))
