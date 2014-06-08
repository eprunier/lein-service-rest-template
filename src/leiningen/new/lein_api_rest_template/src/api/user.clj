(ns {{name}}.api.user
  (:require [compojure.core :refer [defroutes GET]]
            [{{name}}.service.security :refer [restricted authenticated?]]
            [{{name}}.service.json :refer [clj->json]]))

(defn- handle-user 
  [request]
  (clj->json {:user {:username (get-in request [:params :username])
                     :role "user"}}))

(defroutes user-routes
  (GET "/user/:username" request (restricted authenticated? handle-user request)))
