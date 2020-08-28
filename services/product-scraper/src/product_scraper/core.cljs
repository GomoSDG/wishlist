(ns product-scraper.core
  (:require ["express" :as express]
            ["apify" :as apify]
            [cljs.core.async :refer [go <!]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [product-scraper.crawler.core :as crawler]))

(set! *warn-on-infer* true)

(defonce server (atom nil))

(defonce request-queue (atom nil))


(defn scrape-url [req res]
  (go
    (let [{url "url"} (js->clj (.-params req))
          _           (.addRequest @request-queue #js {:url url})
          crawler     (crawler/create-crawler {:request-queue url
                                               :handler       crawler/handle-page-fn})]
      (.run crawler)
      (.send res url))))

(defn start-server []
  (println "Starting server")
  (let [app (express)]
    (.get app "/" (fn [req res] (.send res "Hello, world")))
    (.get app "/ts/:url(*)" scrape-url)
    (.listen app 3003 (fn [] (println "Example app listening on port 3000!")))))

(defn start! []
  ;; called by main and after reloading code
  (go
    (let []
      (reset! request-queue )
      (reset! server (start-server))))

(defn stop! []
  ;; called before reloading code
  (.close @server)
  (reset! server nil))

(def crawler (crawler/default-crawler "https://cljs.github.io/api/cljs.core/new"))

(crawler/run-crawler crawler)


(defn main []
  ;; executed once, on startup, can do one time setup here
  (start!))

(defn -main []
  (start!))
