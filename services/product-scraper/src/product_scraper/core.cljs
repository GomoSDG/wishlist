(ns product-scraper.core
  (:require ["express" :as express]
            ["apify" :as apify]
            ["cheerio" :as cheerio]
            [cljs.core.async :refer [go <!]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [product-scraper.crawler.core :as crawler]
            [product-scraper.crawler.puppeteer :as puppeteer]
            [cognitect.transit :as t]))

(set! *warn-on-infer* true)

(defonce server (atom nil))

(defonce w (t/writer :json))

(defn scrape-url [req res]
  (go
    (let [{url "url"} (js->clj (.-params ^js req))
          prod (<! (puppeteer/scrape-page @puppeteer/browser url))]
      (println (t/write w prod))
      (.send res (t/write w prod)))))

(defn start-server []
  (let [app  (express)
        port (or 3003)]
    (.get app "/" (fn [req res] (.send res "Hello, world")))
    (.get app "/ts/:url(*)" scrape-url)
    (.listen app port (fn [] (println "Product Scraper listening on port " port "!")))))

(defn start! []
  ;; called by main and after reloading code
  (go
    (let []
      (puppeteer/make-browser)
      (reset! server (start-server)))))

(defn stop! []
  ;; called before reloading code
  (.close @server)
  (.close @puppeteer/browser)
  (reset! server nil))

(defn -main []
  (start!))
