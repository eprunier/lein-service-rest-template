(ns {{name}}.app
  (:require [compojure.core :refer [defroutes routes]]
            [compojure.handler :as handler]
            [compojure.route :as route]))

;;; Load public routes
(require '[{{name}}.api.user :refer [user-routes]])

;; Ring handler definition
(defroutes api-handler
  (-> (routes user-routes
              (route/not-found "Resource not found"))
      (handler/api)))
