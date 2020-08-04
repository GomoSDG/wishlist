(ns wishlist-app.panels.home
  (:require [wishlist-app.icons :as icons]))

(defn ^{:navbar :landing} home []
  [:div.columns.is-centered
   [:div.column
    [:img {:src "/img/logo.png"}]
    [:h2.title "Where dreams come true"]]])
