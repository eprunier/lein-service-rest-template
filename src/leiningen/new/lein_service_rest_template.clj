(ns leiningen.new.lein-service-rest-template
  (:refer-clojure :exclude [read])
  (:import [java.io ByteArrayOutputStream])
  (:use [clojure.java.io :as io]
        [leiningen.new.templates :only [renderer sanitize year ->files]]))

(def ^{:private true :const true} template-name "lein-service-rest-template")

(def render-text (renderer template-name))

(defn resource-input
  "Get resource input stream. Usefull for binary resources like images."
  [resource-path]
  (-> (str "leiningen/new/" (sanitize template-name) "/" resource-path)
      io/resource
      io/input-stream))

(defn render 
  "Render the content of a resource"
  ([resource-path]
     (resource-input resource-path))
  ([resource-path data]
     (render-text resource-path data)))

(defn lein-service-rest-template
  "Create a new REST API project based on Liberator"
  [name]
  (let [data {:name name
              :sanitized (sanitize name)
              :year (year)}]
    (println "Generating the project" (str name "..."))
    (->files data
             [".gitignore" (render ".gitignore" data)]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["dev/user.clj" (render "dev/user.clj" data)]
             ["src/{{sanitized}}/config.clj" (render "src/config.clj" data)]
             ["src/{{sanitized}}/server.clj" (render "src/server.clj" data)]
             ["src/{{sanitized}}/app.clj" (render "src/app.clj" data)]
             ["src/{{sanitized}}/service/db.clj" (render "src/service/db.clj" data)]
             ["src/{{sanitized}}/service/security.clj" (render "src/service/security.clj" data)]
             ["src/{{sanitized}}/service/json.clj" (render "src/service/json.clj" data)]
             ["src/{{sanitized}}/api/user.clj" (render "src/api/user.clj" data)]
             )
    (println "Project" name "successfully generated")))
