(ns product-scraper.serverless.chrome-script
  (:require ["superagent" :as request]
            ["util" :as util]
            ["@serverless-chrome/lambda" :as launch-chrome]))

(def promisify util/promisify)

(def get-chrome (util/promisfy (fn [cb]
                                 (-> (launch-chrome)
                                     (.then (fn [^js chrome]
                                              (-> (.get request (str (.-url chrome) "/json/version"))
                                                  (.set "Conten-Type" "application/json")
                                                  (.then (fn [^js response]
                                                           (let [endpoint (-> (.-body response)
                                                                              (.-webSocketDebuggerUrl))]
                                                             (cb #js {:endpoint endpoint
                                                                      :instance chrome})))))))))))

(defn exports []
  #js {:get-chrome get-chrome
       :getChrome get-chrome})
