(ns product-scraper.serverless.core
  (:require [product-scraper.crawler.puppeteer :as puppeteer]))

(defonce w (t/writer :json))

(defn scrape-page [^js e ^js ctx cb]
  (go
    (let [paths   (.-pathParameters e)
          url     (.-url paths)
          product (<! (puppeteer/scrape-page @puppeteer/browser url))]
      (cb nil (clj->js
               {:statusCode 200
                :body (.stringify )})))))

(defn exports []
  #js {:scrape-page scrape-page})
