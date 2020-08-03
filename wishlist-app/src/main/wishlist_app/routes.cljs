(ns wishlist-app.routes
    (:require [secretary.core :as secretary :refer-macros [defroute]]
              [re-frame.core :as re-frame]
              [goog.events :as events]
              [goog.history.EventType :as HistoryEventType])
    (:import goog.History))

(secretary/set-config! :prefix "/")

(defroute home "/" []
  (re-frame/dispatch [:set-active-panel :home]))

(defroute lists "/lists" []
  (re-frame/dispatch [:set-active-panel :wishlist/lists]))

(defroute add-lists "/list/items/add" []
  (re-frame/dispatch [:set-active-panel :wishlist/add-item]))

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     HistoryEventType/NAVIGATE
     (fn [event] 
       (secretary/dispatch! ^String (.-token event))))
    (.setEnabled true)))
