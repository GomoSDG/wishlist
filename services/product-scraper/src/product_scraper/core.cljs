(ns product-scraper.core
  (:require ["express" :as express]
            ["apify" :as apify]
            [product-scraper.crawler.core :as crawler]))

(set! *warn-on-infer* true)

(defonce server (atom nil))

(defn start-server []
  (println "Starting server")
  (let [app (express)]
    (.get app "/" (fn [req res] (.send res "Hello, world")))
    (.listen app 3003 (fn [] (println "Example app listening on port 3000!")))))

(defn start! []
  ;; called by main and after reloading code
  (reset! server (start-server)))

(defn stop! []
  ;; called before reloading code
  (.close @server)
  (reset! server nil))

(def crawler (crawler/default-crawler "https://cljs.github.io/api/cljs.core/new"))

#_(def list (crawler/create-request-list "https://cljs.github.io/api/cljs.core/new"))

(crawler/run-crawler crawler)


(defn main []
  ;; executed once, on startup, can do one time setup here
  (start!))

(defn -main []
  (start!))
