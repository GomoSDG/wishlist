(ns wishlist-app.views
  (:require [wishlist-app.panels.home :as home]
            [wishlist-app.panels.wishlist.list :as list]
            [re-frame.core :as re-frame]))

(def panels {:home               #'home/home
             :wishlist/lists     #'list/list-panel
             :wishlist/add-item #'list/add-item-panel})

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ panel]]
   (js/console.log "Setting active panel")
   (js/console.log panel)
   (assoc db :active-panel panel)))

(re-frame/reg-sub
 :active-panel
 (fn [db]
   (let [panels          (:panels db)
         active-panel    (:active-panel db)
         active-panel-fn (panels active-panel)]
     active-panel-fn)))


