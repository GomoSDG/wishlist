(ns product-scraper.crawler.core
  (:require ["apify" :as apify]
            ["util" :as util]
            [cljs.core.async :refer [go <!]]
            [cljs.core.async.interop :refer-macros [<p!]]))

(def promisify util/promisify)

(defn create-crawler [{:keys [request-queue request-list handler]}]
  (let [maxR    (or maxRequests 100)
        maxC    (or maxConcurrency 1)
        _ (println handler)]
    (apify/PuppeteerCrawler. (js-obj "requestList" request-list
                                     "requestQueue" request-queue
                                     "handlePageFunction" (promisify handler)))))

(defn create-request-list [& urls]
  (go
    (println urls)
    (<p! (apify/openRequestList "my-list",  (to-array urls)))))

(defn handle-page-fn [options cb]
  (go
    (let [page    (.-page options)
          request (.-request options)
          html    (<p! (.content page))]
      (println "Stuff is happening" (<p! (.title page)))
      (cb))))

(defn default-crawler [url]
  (go
    (println "Url: " url)
    (let [list (<! (create-request-list url))]
      (create-crawler {:handler handle-page-fn
                       :request-list list}))))

(defn run-crawler [crawler]
  (go
    (let [cr (<! crawler)]
      (println "Hello")
      (println "The crawler is: " (<p! (.run cr))))))
