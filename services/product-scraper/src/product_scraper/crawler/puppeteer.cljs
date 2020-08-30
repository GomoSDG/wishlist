(ns product-scraper.crawler.puppeteer
  (:require ["puppeteer" :as puppeteer]
            [cljs.core.async :refer [go <!]]
            [cljs.core.async.interop :refer-macros [<p!]]
            [product-scraper.crawler.core :refer [sites]]
            ["apify" :as apify]
            ["cheerio" :as cheerio]))

(def browser (atom nil))

(defn get-attribute-text [selector cs]
  (assoc {}
         (:attribute selector)
         (.text (cs (:selector selector)))))

(defn make-browser []
  (go
    (reset! browser (apify/PuppeteerPool. #js {:launchPuppeteerOptions #js {:stealth   true
                                                                            :useChrome true
                                                                            :headless  true}}))))

(defn goto-page [^js browser url]
  (go
    (let [page    (<p! (.newPage browser))
          options #js {:waitUntil "networkidle0"}
          goto-p  (.goto ^js page url options)
          resp    (<p! goto-p)]
      page)))

(defn scrape-page [^js browser url]
  (go
    (let [host                     (.-host (js/URL. url))
          {:keys [selectors root]} (sites host)
          page                     (<! (goto-page browser url))
          cs                       (.load cheerio (<p! (.content page)))
          attributes               (map get-attribute-text selectors (repeat cs))]
      (.close page)
      (apply merge attributes))))



