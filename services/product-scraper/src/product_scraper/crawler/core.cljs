(ns product-scraper.crawler.core
  (:require ["apify" :as apify]
            ["util" :as util]
            ["cheerio" :as cheerio]
            [cljs.core.async :refer [go <!]]
            [cljs.core.async.interop :refer-macros [<p!]]))

(def promisify util/promisify)

(def sites {"www.takealot.com"
            {:root      ".pdp-main-panel"
             :selectors [{:attribute :name
                          :selector  ".cell.auto h1"}

                         {:attribute :description
                          :selector  ".subtitle"}]}})
