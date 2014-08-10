(ns {{name}}.api.user
  (:require [compojure.core :refer [defroutes ANY]]
            [liberator.core :refer [defresource]]
            [io.clojure.liberator-transit]
            [{{name}}.service.security :refer [authenticated? admin? current-user]]))

(defresource user
  [request]
  :allowed-methods [:get :post :put]
  :available-media-types ["application/json"
                          "application/edn"
                          "application/transit+json"
                          "application/transit+msgpack"]
  :authorized? (fn [_] (authenticated? request))
  :allowed? (fn [_]
              (let [method (:request-method request)]
                (if (= :put method)
                  (admin? request)
                  true)))
  :handle-ok (fn [_] (current-user request))
  :post! (fn [_] (println "New user created"))
  :put! (fn [_] (println "Current user updated"))
  :new? #(= :post (get-in % [:request :request-method])))

(defresource user-by-username
  [username request]
  :allowed-methods [:get :put :delete]
  :available-media-types ["application/json"
                          "application/edn"
                          "application/transit+json"
                          "application/transit+msgpack"]
  :authorized? (fn [_] (authenticated? request))
  :allowed? (fn [_] (let [method (:request-method request)]
                      (condp = method
                        :get true
                        :delete (admin? request)
                        :put (or (admin? request)
                                 (current-user username request)))))
  :handle-ok {:user {:username username}}
  :put! (fn [_] (println "User" username "updated"))
  :new? false
  :delete! (fn [_] (println "User" username "deleted")))

(defroutes user-routes
  (ANY "/user" request (user request))
  (ANY "/user/:username" [username :as request] (user-by-username username request)))
