(ns wishlist-app.core
    (:require [reagent.core :as r]
              [reagent.dom :as rdom]
              [wishlist-app.layouts.site :refer [site-layout]]
              [wishlist-app.routes :as routes :refer [hook-browser-navigation!]]
              [wishlist-app.views :as views]
              [re-frame.core :as re-frame]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   {:active-panel :home
    :panels views/panels}))

(defn render-active-panel []
  (let [active-panel @(re-frame/subscribe [:active-panel])]
    (rdom/render
     [site-layout active-panel]
     (.getElementById js/document "app"))))

(defn init []
  (re-frame/dispatch-sync [:initialize-db])
  (hook-browser-navigation!)
  (render-active-panel))
