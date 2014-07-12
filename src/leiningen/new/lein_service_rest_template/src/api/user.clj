(ns {{name}}.api.user
  (:require [compojure.core :refer [defroutes ANY]]
            [liberator.core :refer [defresource]]
            [{{name}}.service.security :refer [authenticated? admin? current-user]]
            [{{name}}.service.json :refer [clj->json]]))

(defresource user
  [request]
  :allowed-methods [:get :post :put]
  :available-media-types ["application/json"]
  :authorized? #(authenticated? (:request %))
  :allowed? (fn [context]
              (let [request (:request context)
                    method (:request-method request)]
                (if (= :put method)
                  (admin? request)
                  true)))
  :handle-ok #(clj->json (current-user (:request %)))
  :post! (fn [_] (println "User created"))
  :put! (fn [_] (println "Current user updated"))
  :new? #(= :post (get-in % [:request :request-method])))

(defresource user-by-username
  :allowed-methods [:get :put :delete]
  :available-media-types ["application/json"]
  :authorized? #(authenticated? (:request %))
  :allowed? (fn [context]
              (let [request (:request context)
                    method (:request-method request)]
                (condp = method
                  :get true
                  :delete (admin? request)
                  :put (or (admin? request)
                            (current-user (get-in context [:request :params :username])
                                          request)))))
  :handle-ok #(clj->json {:user {:username (get-in % [:request :params :username])}})
  :put! (fn [_] (println "User updated"))
  :new? false
  :delete! (fn [_] (println "User deleted")))

(defroutes user-routes
  (ANY "/user" request (user request))
  (ANY "/user/:username" request (user-by-username request)))
