(ns product-scraper.crawler.core
  (:require ["apify" :as apify]
            ["util" :as util]
            [cljs.core.async :refer [go <!]]
            [cljs.core.async.interop :refer-macros [<p!]]))

(def promisify util/promisify)

(defn create-crawler [h r & [maxRequests maxConcurrency]]
  (let [maxR (or maxRequests 100)
        maxC (or maxConcurrency 1)]
    (println (clj->js {:requestList r
                       :handlePageFunction h
                       :launchPuppeteerOptions {:headless true
                                                :stealth true}}))
    (apify/PuppeteerCrawler. (js-obj "requestList" r
                                     "handlePageFunction" h))))

(defn create-request-list [& urls]
  (println (to-array urls))
  (go
    (<p! (apify/openRequestList "my-list",  (to-array urls)))))

(defn handle-page-fn [options]
  (go
    (let [page    (.-page options)
          request (.-request options)
          html    (<p! (.content page))]
      (println "The title is" html)
      (println "The request uniqueKey: " (.-uniqueKey request)))))

(defn default-crawler [url]
  (go
    (let [list (<! (create-request-list url))]
      (create-crawler handle-page-fn list))))

(defn run-crawler [crawler]
  (go
    (let [cr (<! crawler)]
      (.run cr))))
