(ns coxswain-mayhem.routes
  (:require [compojure.api.sweet :as sweet]
            [compojure.api.core :as api]
            [compojure.route :as route]
            [ring.util.http-response :as http]))

(defn build-session-route [path method description body-params resp-schema handler]
  (api/context
    path []
    :tags ["game"]
    (sweet/resource
      (assoc
        {:description description}
        method
        {:summary    ""
         :parameters {:path-params {:session-id s/Str}
                      :body-params body-params}
         :responses  {200 {:schema resp-schema}}
         :handler    handler}))))

(defn ok-or-bad-request [resp]
  (if resp
    (http/ok resp)
    (http/bad-request)))

(defn build-app []
  (-> {:swagger
       {:ui   "/swagger/ui"
        :spec "/swagger.json"
        :data {:info {:title       "Coxswain Mayhem"
                      :description "JRPG-style virtual tabletop for ttrpgs."}
               :tags [{:name "game" :description "game api"}]}}}
      (sweet/api
        (api/context
          "" []
          :tags [""]
          ())
        (api/context
          "" []
          :tags [""]
          ()))
      (sweet/routes
        (route/resources "/")
        (route/not-found "404 Not Found"))))